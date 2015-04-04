package utils;

import play.mvc.Scope.Flash;

/**
 * 竞考网模板消息提示生成工具
 * @author eric
 */
public class MessageUtil {
    
    public static void generateInfoMsg(Flash flash, String content){
        flash.put("messageType", "info");
        flash.put("messageContent", content);
    }
    
    /**
     * Flash使用的是 Flash.current()，生成的内容为String.format(content, args)
     * @param content
     * @param args
     */
    public static void generateInfoMsg(String content, Object... args){
    	generateInfoMsg(Flash.current(), String.format(content, args));
    }
    
    public static void generateErrorMsg(Flash flash, String content){
        flash.put("messageType", "error");
        flash.put("messageContent", content);
    }
    
    /**
     * Flash使用的是 Flash.current()，生成的内容为String.format(content, args)
     * @param content
     * @param args
     */
    public static void generateErrorMsg(String content, Object... args){
    	generateErrorMsg(Flash.current(), String.format(content, args));
    }
    
    /**
     * 清除flash里面的信息
     * @param flash
     */
    public static void cleanMsg(Flash flash){
        flash.remove("messageType");
        flash.remove("messageContent");
    }
}
