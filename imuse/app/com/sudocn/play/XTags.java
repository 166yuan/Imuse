package com.sudocn.play;

import groovy.lang.Closure;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jj.play.org.eclipse.mylyn.wikitext.core.parser.MarkupParser;
import jj.play.org.eclipse.mylyn.wikitext.textile.core.TextileLanguage;

import org.apache.commons.lang.StringUtils;

import play.Logger;
import play.Play;
import play.cache.Cache;
import play.exceptions.TagInternalException;
import play.exceptions.TemplateExecutionException;
import play.libs.IO;
import play.templates.FastTags;
import play.templates.GroovyTemplate.ExecutableTemplate;
import utils.CdnUtil;

/**
 * 拓展页面模板标签
 * 
 * @author chao
 */
public class XTags extends FastTags {
	
	static int getConcatMaxFiles(){
		String maxNum = Play.configuration.getProperty("asset.max");
		try{
			return Integer.parseInt(maxNum);
		}catch(Exception e){
			return 10;
		}
	}

	/**
	 * 输出静态文件
	 * 
	 * @param out
	 * @param start 开始标签
	 * @param assets 静态资源文件
	 * @param end 结束标签
	 */
	static void asset(PrintWriter out, Map<?, ?> args, String baseUrl, String start, List<String> assets, String end, boolean isConcat) {
		if (Play.mode.isProd()) {// 添加CDN
			if (!baseUrl.startsWith("http://") // 绝对地址
					&& !baseUrl.startsWith("https://") // 绝对地址
					&& baseUrl.startsWith("/") // 相对根路径地址
			) {
				baseUrl = CdnUtil.getCdn() + baseUrl;
			}
		}

		
		if (Play.mode.isProd() && isConcat) {
			int max = getConcatMaxFiles();
			for(int i=0; i < assets.size();){
				out.print(start);
				out.print(baseUrl);
				out.print("??");
				int endIndex = ((i + max) < assets.size()) ? (i + max) : assets.size();
				StringBuilder path = new StringBuilder();
				for(int index = i; index < endIndex; index ++){ //join
					path.append(assets.get(index));
					if(index != endIndex -1){
						path.append(',');
					}
				}
				out.print(path);
				out.print(end);
				i = endIndex;
			}
			
//			out.print(start);
//			out.print(baseUrl);
//			out.print("??");
//			String path = StringUtils.join(assets, ','); 
//			out.print(path);
//			out.print(end);
		} else {
			for (String asset : assets) {
				out.print(start);
				out.print(baseUrl);
				out.print(asset);
				out.print(end);
			}
		}
	}

	/**
	 * 输出脚本
	 * 
	 * @param args
	 * @param body
	 * @param out
	 * @param template
	 * @param fromLine
	 */
	public static void _js(Map<?, ?> args, Closure body, PrintWriter out, ExecutableTemplate template, int fromLine) {
		boolean isConcat = false; // 默认不启用，防止出错
		try {
			isConcat = Boolean.parseBoolean(Play.configuration.getProperty("asset.concat", "false"));
		} catch (Exception e) {
			Logger.error(e, "asset.concat should be true / false!!");
		}
		String baseUrl = Play.configuration.getProperty("asset.base.js", Play.configuration.getProperty("asset.base", ""));
		if (args.containsKey("base")) {
			baseUrl = args.get("base").toString();
		}
		List<String> assets = (List<String>) args.get("arg");
		if (assets == null || assets.isEmpty()) {
			return;
		}
		
		List<String> finalAssets = new ArrayList(assets.size());
		
		for (String a : assets) {
			if (Play.mode.isProd()) {
				if (isConcat && a.contains(".coffee")) {
					finalAssets.add(a.replace(".coffee", ".js"));
				} else {
					finalAssets.add(a);
				}
			} else {
				finalAssets.add(a);
			}
		}

		asset(out, args, baseUrl, "<script src=\"", finalAssets, "\"></script>",isConcat);
	}

	/**
	 * 输出样式
	 * 
	 * @param args
	 * @param body
	 * @param out
	 * @param template
	 * @param fromLine
	 */
	public static void _css(Map<?, ?> args, Closure body, PrintWriter out, ExecutableTemplate template, int fromLine) {
		boolean isConcat = false; // 默认不启用，防止出错
		try {
			isConcat = Boolean.parseBoolean(Play.configuration.getProperty("asset.concat", "false"));
		} catch (Exception e) {
			Logger.error(e, "asset.concat should be true / false!!");
		}
		String baseUrl = Play.configuration.getProperty("asset.base.css", Play.configuration.getProperty("asset.base", ""));
		if (args.containsKey("base")) {
			baseUrl = args.get("base").toString();
		}
		
		List<String> assets = (List<String>) args.get("arg");
		if (assets == null || assets.isEmpty()) {
			return;
		}
		List<String> finalAssets = new ArrayList(assets.size());

		for (String a : assets) {
			if (Play.mode.isProd()) {
				if (isConcat && a.contains(".less")) {
					finalAssets.add(a.replace(".less", ".css"));
				} else {
					finalAssets.add(a);
				}
			} else {
				finalAssets.add(a);
			}
		}

		asset(out, args, baseUrl, "<link rel=\"stylesheet\" media=\"all\" href=\"", finalAssets, "\">", isConcat);
	}

	/**
	 * 输出静态文件，用于避免引擎解析的文本输出
	 * 
	 * @param args
	 * @param body
	 * @param out
	 * @param template
	 * @param fromLine
	 */
	public static void _output(Map<?, ?> args, Closure body, PrintWriter out, ExecutableTemplate template, int fromLine) {
		if (!args.containsKey("arg") || args.get("arg") == null) {
			throw new TemplateExecutionException(template.template, fromLine, "Specify a file name",
					new TagInternalException("Specify a file name"));
		}
		String filePath = args.get("arg").toString();
		String filePathInProject = "app/views/" + filePath;
		try {
			String key = "OutputFile-" + filePathInProject;
			String content = Cache.get(key, String.class);
			if (content == null) {
				File file = Play.getVirtualFile(filePathInProject).getRealFile();
				content = IO.readContentAsString(file);
				if (Play.mode.isProd()) {
					Cache.set(key, content);
				}
			}
			out.write(content);
		} catch (Exception e) {
			Logger.error(e, "Cannot output %s", filePath);
		}
	}

	/**
	 * 编译输出textile文件
	 * 
	 * @param args
	 * @param body
	 * @param out
	 * @param template
	 * @param fromLine
	 */
	public static void _textile(Map<?, ?> args, Closure body, PrintWriter out, ExecutableTemplate template, int fromLine) {
		if (!args.containsKey("arg") || args.get("arg") == null) {
			throw new TemplateExecutionException(template.template, fromLine, "Specify a file name",
					new TagInternalException("Specify a file name"));
		}
		String filePath = args.get("arg").toString();
		String filePathInProject = "app/views/" + filePath;
		try {
			String key = "OutputTextileFile-" + filePathInProject;
			String content = Cache.get(key, String.class);
			if (content == null) {
				File file = Play.getVirtualFile(filePathInProject).getRealFile();
				content = toHTML(IO.readContentAsString(file));
				if (Play.mode.isProd()) {
					Cache.set(key, content);
				}
			}
			out.write(content);
		} catch (Exception e) {
			Logger.error(e, "Cannot compile %s", filePath);
		}
	}

	public static void _asTextile(Map<?, ?> args, Closure body, PrintWriter out, ExecutableTemplate template,
			int fromLine) {
		if (!args.containsKey("arg") || args.get("arg") == null) {
			// do nothing
			return;
		}
		String textileContent = args.get("arg").toString();
		try {
			String htmlcontent = toHTML(textileContent);
			out.write(htmlcontent);
		} catch (Exception e) {
			Logger.error(e, "Cannot compile %s", textileContent);
		}
	}

	/**
	 * 将textile转换成html标签，去除<body/>标签
	 * 
	 * @param textile
	 * @return
	 */
	static String toHTML(String textile) {
		String html = new MarkupParser(new TextileLanguage()).parseToHtml(textile);
		html = html.substring(html.indexOf("<body>") + "<body>".length(), html.lastIndexOf("</body>"));
		return html;
	}

}
