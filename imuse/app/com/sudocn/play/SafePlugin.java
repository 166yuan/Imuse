package com.sudocn.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import play.Logger;
import play.Play;
import play.PlayPlugin;
import play.exceptions.UnexpectedException;
import play.libs.MimeTypes;
import play.mvc.Http;
import play.mvc.Scope;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.templates.TemplateLoader;
import play.vfs.VirtualFile;

public class SafePlugin extends PlayPlugin{
	
	List<String> blackList = null;
	
    public void loadBlackList() {
    	if(blackList != null){
    		return;
    	}
		blackList = new ArrayList();
		String uris = Play.configuration.getProperty("SafePlugin.blacklist");
		String[] strs = uris.split("\\|");
		for(String s: strs){
			blackList.add(s);
		}
		if(blackList.isEmpty()){
			Logger.info("safe plugin has loaded: black list is empty");
		}else{
			Logger.info("safe plugin has loaded:" + StringUtils.join(blackList, "|"));
		}
    }
	
	@Override
    public boolean serveStatic(VirtualFile file, Request request, Response response) {
		boolean pass = true;
		loadBlackList();
		for(String b: blackList){
			if(StringUtils.contains(request.path, b)){
				pass = false;
			}
		}
		if(pass){
			return false;
		}else{
			forbidden(request, response);
			return true;
		}
	}

	private void forbidden(Request request, Response response) {
		response.status = Http.StatusCode.FORBIDDEN;
		try {
			response.out.write("Access Denied".getBytes());
		} catch (IOException e) {
			Logger.error(e, "Error in SafePlugin forbidden()");
		}
	}
	
}