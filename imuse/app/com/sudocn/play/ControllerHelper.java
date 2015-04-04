package com.sudocn.play;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import play.mvc.Http;
import play.mvc.Http.Header;
import play.mvc.Http.Request;
import play.mvc.Http.Response;

/**
 * Controller工具
 * @author chao
 *
 */
public class ControllerHelper {
	
	/**
	 * 在Header中设置X-Accel-Redirect，让Nginx来发送文件
	 * @param path
	 */
	public static void nginx(String path) {
		Header h = new Header();
		h.name = "X-Accel-Redirect";
		h.values = new ArrayList<String>(1);
		h.values.add(path);
		Response.current().headers.put("X-Accel-Redirect", h);
	}
	
	/**
	 * 在Header中设置X-Accel-Redirect，让Nginx来发送文件
	 * @param path
	 * @param contentType
	 */
	public static void nginx(String path, String contentType) {
		nginx(path);
		if(StringUtils.isNotEmpty(contentType)){
			Response.current().setContentTypeIfNotSet(contentType);
		}
	}
	
	/**
	 * 在Header中设置Content-Disposition，编码UTF-8
	 * @param filename
	 */
	public static void setContentDisposition(String filename){
		try {
			Response.current().setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
