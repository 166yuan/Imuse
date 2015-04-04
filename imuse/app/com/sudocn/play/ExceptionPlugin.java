package com.sudocn.play;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;

import play.Logger;
import play.Play;
import play.PlayPlugin;
import play.data.Upload;
import play.exceptions.UnexpectedException;
import play.libs.MimeTypes;
import play.mvc.Http;
import play.mvc.Http.Header;
import play.mvc.Scope;
import play.mvc.Http.Cookie;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.mvc.Scope.Params;
import play.templates.TemplateLoader;
import play.vfs.VirtualFile;

public class ExceptionPlugin extends PlayPlugin{
	
	public static boolean enable = false;
	
	public static String cookiesName;
	
	@Override
    public void onLoad() {
		cookiesName = Play.configuration.getProperty("application.session.cookie", "PLAY") + "_SESSION";
    }
	
	@Override
	public void onInvocationException(Throwable e) {
		if(enable){
			Request request = Request.current();
			if(Response.current().status != Http.StatusCode.NOT_FOUND){
				StringBuilder msg = new StringBuilder();
				msg.append(logJKSessionMsg(request)).append(" ");
				if(!request.method.equalsIgnoreCase("GET")){
					msg.append(generateFormMsg(request)).append(" ");
		    	}
				msg.append(logAttachmentInfo(request));
				Logger.error(msg.toString());
			}
		}
	}
	
	private String generateFormMsg(Request request){
		StringBuilder params = new StringBuilder("formData:[");
		Params p = request.params;
		Map<String, String[]> map = p.all();
		String patten = "%s:%s";
		for(String key : map.keySet()){
			if(!"action".equals(key) && !"controller".equals(key) && !"body".equals(key)){
				String[] values = map.get(key);
				if(values != null && values.length > 0){
					if(values.length == 1){
						params.append(String.format(patten, key, values[0])).append(",");
					}else{
						for(int i = 0, size = values.length; i < size; i++){
							params.append(String.format(patten, key, values[i])).append(",");
						}
					}
				}
			}
		}
		params.append("]");
		return params.toString();
	}
	
	private String logJKSessionMsg(Request request){
		StringBuilder sb = new StringBuilder("cookies:[");
		Cookie cookie = request.cookies.get(cookiesName);
    	if(cookie != null){
    		sb.append("value:").append(cookie.value);
    	}
    	sb.append("]");
    	return sb.toString();
	}
	
	private String logAttachmentInfo(Request request){
		StringBuilder sb = new StringBuilder("attachments:[");
    	List<Upload> uploads = (List<Upload>) request.args.get("__UPLOADS");
    	for(String key : request.args.keySet()){
    		System.out.println(key);
    	}
    	if(uploads != null && !uploads.isEmpty()){
    		List<File> fileArray = new ArrayList<File>();
    		for (Upload upload : uploads) {
	        	File file = upload.asFile();
	            if (file.length() > 0) {
	                fileArray.add(file);
	            }
	        }
	        if(!fileArray.isEmpty()){
	        	for(int i = 0, size = fileArray.size(); i < size; i++){
	        		sb.append(fileArray.get(i).getAbsolutePath());
	        		if(i < (size - 1)){
	        			sb.append(",");
	        		}
	        	}
	        }
    	}
    	sb.append("]");
    	return sb.toString();
	}
}