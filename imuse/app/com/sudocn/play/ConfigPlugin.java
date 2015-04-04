package com.sudocn.play;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import play.Logger;
import play.Play;
import play.PlayPlugin;
import play.libs.IO;
import play.mvc.Http;
import play.utils.OrderSafeProperties;
import play.vfs.VirtualFile;

/**
 * 配置文件拓展插件
 * @author chao
 *
 */
public class ConfigPlugin extends PlayPlugin{
	
	@Override
	public void onConfigurationRead() {
		//按优先级高低的顺序读取配置
		
		//处理@extend配置
		loadExtendConfigurationFile();
		//载入模块的配置文件
		loadModuleConfigurationFile();
		
		magicTrick();
	}
	
	/**
	 * 解决Play初始化配置文件的问题
	 */
	private void magicTrick(){
		// Default cookie domain
        Http.Cookie.defaultDomain = Play.configuration.getProperty("application.defaultCookieDomain", null);
        if (Http.Cookie.defaultDomain!=null) {
            Logger.info("Using default cookie domain: " + Http.Cookie.defaultDomain);
        }
        
        //application secretKey
        if (StringUtils.isEmpty(Play.secretKey)) {
        	Play.secretKey = Play.configuration.getProperty("application.secret", "").trim();
        	
        	if (StringUtils.isEmpty(Play.secretKey)) {
        		Logger.warn("No secret key defined. Sessions will not be encrypted");
        	}
        }
	}
	
	private void loadModuleConfigurationFile(){
		Map<String, VirtualFile> modules = Play.modules;
		for(Map.Entry<String, VirtualFile> entry: modules.entrySet()){
			VirtualFile root = entry.getValue();
			if (root.child("conf/application.conf").exists()) {
				String moduleConfig = root.child("conf/application.conf").getRealFile().getAbsolutePath();
				Properties moduleProp = loadConfigurationFile(moduleConfig);
				putAllWithoutOverride(Play.configuration, moduleProp);
	        }
		}
	}
	
	private void loadExtendConfigurationFile(){
		Properties toExtend = new Properties();
		for (Object key : Play.configuration.keySet()) {
            if (key.toString().startsWith("@extend.")) {
                try {
                    String filenameToInclude = Play.configuration.getProperty(key.toString());
                    toExtend.putAll(loadConfigurationFile(filenameToInclude));
                } catch (Exception ex) {
                    Logger.warn("Fail to extend configuraion: %s", key);
                }
            }
        }
		putAllWithoutOverride(Play.configuration, toExtend);
	}
	
	protected Properties loadConfigurationFile(String filename){
		Properties propsFromFile=null;

        VirtualFile conf = VirtualFile.open(filename);
        
        Logger.info("Loading extend configuration %s", conf.getRealFile().getAbsolutePath());
        
        try {
            propsFromFile = IO.readUtf8Properties(conf.inputstream());
        } catch (RuntimeException e) {
            if (e.getCause() instanceof IOException) {
                Logger.fatal("Cannot read "+filename);
                Play.fatalServerErrorOccurred();
            }
        }
        Play.confs.add(conf);
        
        // OK, check for instance specifics configuration
        Properties newConfiguration = new OrderSafeProperties();
        Pattern pattern = Pattern.compile("^%([a-zA-Z0-9_\\-]+)\\.(.*)$");
        for (Object key : propsFromFile.keySet()) {
            Matcher matcher = pattern.matcher(key + "");
            if (!matcher.matches()) {
                newConfiguration.put(key, propsFromFile.get(key).toString().trim());
            }
        }
        for (Object key : propsFromFile.keySet()) {
            Matcher matcher = pattern.matcher(key + "");
            if (matcher.matches()) {
                String instance = matcher.group(1);
                if (instance.equals(Play.id)) {
                    newConfiguration.put(matcher.group(2), propsFromFile.get(key).toString().trim());
                }
            }
        }
        propsFromFile = newConfiguration;
        // Resolve ${..}
        pattern = Pattern.compile("\\$\\{([^}]+)}");
        for (Object key : propsFromFile.keySet()) {
            String value = propsFromFile.getProperty(key.toString());
            Matcher matcher = pattern.matcher(value);
            StringBuffer newValue = new StringBuffer(100);
            while (matcher.find()) {
                String jp = matcher.group(1);
                String r;
                if (jp.equals("application.path")) {
                    r = Play.applicationPath.getAbsolutePath();
                } else if (jp.equals("play.path")) {
                    r = Play.frameworkPath.getAbsolutePath();
                } else if (Play.configuration.containsKey(jp)){
                	r = Play.configuration.getProperty(jp, "");
                }else{
                    r = System.getProperty(jp);
                    if (r == null) {
                        r = System.getenv(jp);
                    }
                    if (r == null) {
                        Logger.warn("Cannot replace %s in configuration (%s=%s)", jp, key, value);
                        continue;
                    }
                }
                matcher.appendReplacement(newValue, r.replaceAll("\\\\", "\\\\\\\\"));
            }
            matcher.appendTail(newValue);
            propsFromFile.setProperty(key.toString(), newValue.toString());
        }
        
        return propsFromFile;
	}
	
	protected void putAllWithoutOverride(Properties appProp, Properties defaultProp){
		for (Object key : defaultProp.keySet()) {
			if((!appProp.containsKey(key)) || appProp.get(key) == null){
				appProp.put(key, defaultProp.get(key));
			}
		}
	}

}
