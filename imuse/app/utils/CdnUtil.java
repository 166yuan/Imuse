package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import play.Logger;
import play.Play;

/**
 * CDN工具
 * @author chao
 *
 */
public class CdnUtil {
	
	static boolean init = false;
	static List<String> cdnServerList;
	
	
	synchronized static void init(){
		if(Play.mode == Play.Mode.PROD){//生产模式
			String cdnServersStr = Play.configuration.getProperty("application.cdn", "");
			List<String> cdnServerTempList = Arrays.asList(cdnServersStr.split(","));
			cdnServerList = new ArrayList<String>(cdnServerTempList.size());
			Iterator<String> it = cdnServerTempList.iterator();
			while(it.hasNext()){
				String string = it.next();
				if(!StringUtils.isEmpty(string)){
					cdnServerList.add(buildCdnBaseUrl(string));
				}
			}
		}
		if(cdnServerList == null){
			cdnServerList = new ArrayList<String>();
		}
		if(cdnServerList.isEmpty()){
			cdnServerList = new ArrayList<String>();
			cdnServerList.add("");
		}
		init = true;
	}
	
	/**
	 * 拼接CDN
	 * @param cdnServer
	 * @return
	 */
	static String buildCdnBaseUrl(String cdnServer){
		
		if((!cdnServer.startsWith("http://")) || (!cdnServer.startsWith("https://"))){
    		cdnServer = "http://" + cdnServer;
    	}
    	if(cdnServer.endsWith("/")){
	    	cdnServer = cdnServer.substring(0, cdnServer.length() - 1);
	    }
    	
    	return cdnServer;
	}
	
	/**
	 * 获得CDN地址（随机从application.conf里面）
	 * @return
	 */
	public static String getCdn(){
		if(!init){
			init();
		}
		if(cdnServerList.isEmpty()){
			return "";
		}
		Random r = new Random();
		try{
			return  cdnServerList.get(r.nextInt(cdnServerList.size()));
		}catch(Exception e){
			Logger.error(e, "CDN Config error!");
			return "";
		}
		
	}
}
