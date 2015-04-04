package com.sudocn.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import jj.play.ns.nl.captcha.backgrounds.FlatColorBackgroundProducer;
import jj.play.ns.nl.captcha.gimpy.GimpyRenderer;
import jj.play.ns.nl.captcha.gimpy.RippleGimpyRenderer;

import org.apache.commons.lang.StringUtils;

import play.Play;
import play.cache.Cache;
import play.exceptions.UnexpectedException;
import play.mvc.Http.Response;
import play.mvc.Scope.Session;

/**
 * 字母验证码	
 * @author chao
 *
 */
public class LetterCaptcha extends InputStream{
	
	/**
	 * 图片宽度
	 */
	public static final int WIDTH = 145;
	/**
	 * 图片高度
	 */
	public static final int HEIGHT = 180;
	/**
	 * 行高
	 */
	public static final int ROW_SIZE = 4;
	/**
	 * 列宽
	 */
	public static final int COLUMN_SIZE = 3;
	/**
	 * 字幕宽度
	 */
	public static final int LETTER_WIDTH = WIDTH / COLUMN_SIZE;
	/**
	 * 字母高度
	 */
	public static final int LETTER_HEIGHT = HEIGHT / ROW_SIZE;
	/**
	 * 候选字母
	 */
	public static final String LETTERS = "abdefghijkmnopqrtyABCDEFGHJKLMNPQRSTUVWXYZ23456789";
	
	/**
	 * 验证码字体
	 */
	private List<Font> fonts = new ArrayList<Font>(2);
	/**
	 * 正确码
	 */
	private List<Character> code = null;
	/**
	 * 候选码
	 */
	private List<Character> candidateCode = null;
	/**
	 * 正确码的序列
	 */
	int[] sequence = null;
	private ByteArrayInputStream bais = null;
	GimpyRenderer gimpy = new RippleGimpyRenderer();
	
	private LetterCaptcha(){
		this.fonts.add(new Font("Arial", Font.PLAIN, LETTER_HEIGHT - 8));
        this.fonts.add(new Font("Courier", Font.PLAIN, LETTER_HEIGHT - 8));
        generateCode();
	}
	
	public String getCode(){
		return toString(code);
	}
	
	public String getCandidateCode(){
		return toString(candidateCode);
	}
	
	public String getSequence(){
		StringBuilder sb = new StringBuilder();
		for(int seq:sequence){
			sb.append(seq);
		}
		return sb.toString();
	}
	
	void generateCode(){
		List<Character> letters = new LinkedList();
		for(int i = 0; i < LETTERS.length(); i++){
			letters.add(LETTERS.charAt(i));
		}
		candidateCode = randomPick(letters, 9);
		code = randomPick(new LinkedList(candidateCode), 4);
		sequence = new int[code.size()];
		for(int i = 0; i < code.size();i ++){
			char c = code.get(i);
			sequence[i] = candidateCode.indexOf(c);
		}
	}
	
	@Override
	public int read() throws IOException {
		check();
        return bais.read();
	}
	
	void check() {
		try {
            if (bais == null) {
                BufferedImage bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
                FlatColorBackgroundProducer background = new FlatColorBackgroundProducer(Color.WHITE); //默认背景为白色
                bi = background.addBackground(bi);
                makeNoise(bi, getRandomColor(), 3.0f, WIDTH, LETTER_HEIGHT);//勾画噪声线
                render(getCode(), bi, getRandomColor());//在背景上写code
                for(int i = 0;i < getCandidateCode().length();i++){
                	render(candidateCode.get(i) + "", bi, getRandomColor(), (i % 3) * LETTER_WIDTH + 8, (i / 3 + 1) * LETTER_HEIGHT + 35); //在背景上再画一字符
                }
                gimpy.gimp(bi);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bi, "png", baos);
                bais = new ByteArrayInputStream(baos.toByteArray());
                Response.current().contentType = "image/png";
            }
        } catch (Exception e) {
            throw new UnexpectedException(e);
        }
	}
	
	Color getRandomColor(){
		final int maxValue = 192;
		int val = new Random().nextInt(maxValue);
		return new Color(val, val, val);
	}
	
	void render(String word, BufferedImage image, Color color) {
		render(word, image, color, 25, 35);
	}
	
	void render(String word, BufferedImage image, Color color, int startPosX, int startPosY) {
        Graphics2D g = image.createGraphics();
        //设置渲染选项
        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON); //开启抗锯齿
        hints.add(new RenderingHints(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY)); //高质量渲染
        g.setRenderingHints(hints);

        g.setColor(color);
        FontRenderContext frc = g.getFontRenderContext();
        char[] wc = word.toCharArray();
        Random generator = new Random();
        for (char element : wc) {
            char[] itchar = new char[] { element };
            int choiceFont = generator.nextInt(fonts.size());
            Font itFont = fonts.get(choiceFont);
            g.setFont(itFont);

            GlyphVector gv = itFont.createGlyphVector(frc, itchar);
            double charWitdth = gv.getVisualBounds().getWidth();

            g.drawChars(itchar, 0, itchar.length, startPosX, startPosY);
            startPosX = startPosX + (int) charWitdth;
        }
    }
	
	void makeNoise(BufferedImage image, Color color, float strokeWidth, int width, int height) {
		Random RAND = new Random(System.currentTimeMillis());

        // the curve from where the points are taken
        CubicCurve2D cc = new CubicCurve2D.Float(width * .1f, height
                * RAND.nextFloat(), width * .1f, height
                * RAND.nextFloat(), width * .25f, height
                * RAND.nextFloat(), width * .9f, height
                * RAND.nextFloat());

        // creates an iterator to define the boundary of the flattened curve
        PathIterator pi = cc.getPathIterator(null, 2);
        Point2D tmp[] = new Point2D[200];
        int i = 0;

        // while pi is iterating the curve, adds points to tmp array
        while (!pi.isDone()) {
            float[] coords = new float[6];
            switch (pi.currentSegment(coords)) {
	            case PathIterator.SEG_MOVETO:
	            case PathIterator.SEG_LINETO:
	                tmp[i] = new Point2D.Float(coords[0], coords[1]);
            }
            i++;
            pi.next();
        }

        // the points where the line changes the stroke and direction
        Point2D[] pts = new Point2D[i];
        // copies points from tmp to pts
        System.arraycopy(tmp, 0, pts, 0, i);

        Graphics2D graph = (Graphics2D) image.getGraphics();
        graph.setRenderingHints(new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON));

        graph.setColor(color);

        // for the maximum 3 point change the stroke and direction
        for (i = 0; i < pts.length - 1; i++) {
            if (i < 3) {
            	graph.setStroke(new BasicStroke(strokeWidth));
            }
            graph.drawLine((int) pts[i].getX(), (int) pts[i].getY(),
                    (int) pts[i + 1].getX(), (int) pts[i + 1].getY());
        }
        graph.dispose();
    }

	static List<Character> randomPick(List<Character> chars, int length){
		List<Character> letters = new ArrayList(length);
		for(int i = 0; i < length; i ++){
			int index = (int)(Math.random()*chars.size());
			letters.add(chars.get(index));
			chars.remove(index);
		}
		return letters;
	}

	static String toString(List<Character> chars){
		StringBuilder sb = new StringBuilder();
		for(Character c : chars){
			sb.append(c);
		}
		return sb.toString();
	}
	
	static String cacheKey(){
		String sessionId = Session.current().getId();
		return "LetterCaptcha-Sequence-" + sessionId; 
	}
	
	/**
	 * 获取验证码对象
	 * @return
	 */
	public static LetterCaptcha get(){
		LetterCaptcha captcha = new LetterCaptcha();
		String cacheTime = Play.configuration.getProperty("sudocnCaptcha.cacheTime");
		Cache.set(cacheKey(), captcha.getSequence(), cacheTime);
		return captcha;
	}

	/**
	 * 检查验证码的序列是否正确，无论是否正确均会清除缓存中的序列
	 * @param sequence
	 * @return
	 */
	public static boolean check(String sequence){
		String cacheKey = cacheKey();
		String sequenceInCache = Cache.get(cacheKey, String.class);
		boolean result = StringUtils.equals(sequence, sequenceInCache);
		Cache.delete(cacheKey);
		return result;
	}
	
}
