package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Query;

import models.IndexBanner;

import org.apache.ivy.plugins.resolver.IBiblioResolver;
import org.hibernate.annotations.Sort;
import org.ietf.jgss.Oid;
import org.scauhci.common.util.StringUtil;

import com.aliyun.openservices.oss.OSSClient;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import play.Play;
import play.db.jpa.JPA;
import play.mvc.Controller;
import utils.CompareUtil;
import utils.MessageUtil;

/**
 * @author 陈思远
 * 大图通用设置
 */
public class WebSetting extends Controller {

	/**
	 * 设置页面
	 */
	public static void settings() {
		List<IndexBanner> bannerList = null;
		bannerList = IndexBanner.findAll();
		CompareUtil<IndexBanner> cUtil=new CompareUtil<IndexBanner>(0);
		Collections.sort(bannerList, cUtil);
		render(bannerList);
	}

	/**
	 * @param f ：页面传来的文件
	 * @param bannername：大图的名称，运行重复，不允许空
	 */
	public static void upload(File f, String bannername,String link) {
		if (f != null && StringUtil.isNotEmpty(bannername)&& link!=null) {
			List<IndexBanner> bannerList = null;
			bannerList = IndexBanner.all().fetch();
			// System.out.print(bannerList.size());
			IndexBanner ib = new IndexBanner();
			ib.name = bannername;
			ib.enabled = true;
			if (f != null) {
				ib.link = link;
				ib.attachId = utils.UploadUtil.upload(f, 0);
				if(ib.attachId!=null){
				ib.url = Play.configuration.getProperty("oss.pub_url")+ib.attachId;
				ib.weight = bannerList.size() + 1;
				ib.save();
				MessageUtil.generateInfoMsg("上传成功");
				settings();
			}
				else{
					MessageUtil.generateErrorMsg("上传出错，服务器错误");
					addBanner();
				}
			}	
		} else {
			MessageUtil.generateErrorMsg("上传出错，请补充完整信息");
			addBanner();
		}
	}

	/**
	 *  添加（新增）大图的页面
	 */
	public static void addBanner() {
		render();
	}

	/** 删除制定id的大图
	 * @param Id：大图id
	 */
	public static void deleBanner(String Id) {
		IndexBanner ib = IndexBanner.find("id=?", Id).first();
		List<IndexBanner> list = IndexBanner.findAll();
		if (ib != null) {
			int temp = ib.weight;
			for (int i = 0; i < list.size(); i++) {
				IndexBanner ibb = list.get(i);
				if (ibb.weight > temp) {
					ibb.weight--;
					ibb.save();
				}
			}
			ib.delete();
			MessageUtil.generateInfoMsg("删除成功");
		} else {
			MessageUtil.generateErrorMsg("删除出错");
		}
		settings();

	}


	/**
	 * @param id :大图id
	 * @param isEnable：是否生效
	 */
	public static void setEnable(String id, boolean isEnable) {
		IndexBanner ib = IndexBanner.find("id=?", id).first();
		// System.out.print(ib.name);
		if (ib != null) {
			ib.enabled = isEnable;
			ib.save();
			flash.put("msg", "Delete Banner Success");
		}
		settings();
	}

	
	/** 改变大图次序
	 * @param weigh ：大图权重
	 * @param direction ：移动方向
	 */
	public static void Move(int weigh, int direction) {
		int temp = weigh + direction;
		int count = (int) IndexBanner.count();
		// System.out.print(count);
		if (temp != 0 && temp <= count) {
			IndexBanner iBanner1 = IndexBanner.find("weight=?", weigh).first();
			IndexBanner iBanner2 = IndexBanner.find("weight=?", temp).first();
			iBanner2.weight = iBanner1.weight;
			iBanner1.weight = temp;
			iBanner1.save();
			iBanner2.save();

		}
		// System.out.print(iBanner1.weight);
		settings();
	}

	/**
	 * 功能描述：拷贝一个目录或者文件到指定路径下，即把源文件拷贝到目标文件路径下
	 * 
	 * @param source
	 *            源文件
	 * @param target
	 *            目标文件路径
	 * @return void
	 */
	static String copy(File source, File target) {
		File tarpath = new File(target, source.getName());
		if (source.isDirectory()) {
			tarpath.mkdir();
			File[] dir = source.listFiles();
			for (int i = 0; i < dir.length; i++) {
				copy(dir[i], tarpath);
			}
		} else {
			try {
				InputStream is = new FileInputStream(source); // 用于读取文件的原始字节流
				OutputStream os = new FileOutputStream(tarpath); // 用于写入文件的原始字节的流
				byte[] buf = new byte[1024];// 存储读取数据的缓冲区大小
				int len = 0;
				while ((len = is.read(buf)) != -1) {
					os.write(buf, 0, len);
				}
				is.close();
				os.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return tarpath.getName();
	}
	
	public static void showPics(String url){
		render(url);
	}

}
