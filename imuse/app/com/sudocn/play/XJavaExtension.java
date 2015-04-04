package com.sudocn.play;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import play.Logger;
import play.mvc.Router;
import play.templates.BaseTemplate.RawData;
import play.templates.JavaExtensions;
import play.utils.HTML;

import com.alibaba.fastjson.JSON;
import com.sudocn.utils.URLUtil;
import com.sudocn.utils.URLUtil.URLEntity;

/**
 * JavaExtension
 * 
 * @author chao
 */
public class XJavaExtension extends JavaExtensions {
	
	static final long ONE_DAY_MS = 24 * 60 * 60 * 1000;

	/**
	 * 格式化成考试时间，HH小时mm分钟
	 * 
	 * @param number
	 * @return
	 */
	public static String asExamTime(Number number) {
		if(number.intValue() == 0){
			return "0秒";
		}
		StringBuilder sb = new StringBuilder();
		int time = number.intValue() / 1000;

		int h = time / 60 / 60;
		int m = time / 60 % 60;
		int s = time % 60;
		if (h != 0)
			sb.append(h + "小时");
		if (m != 0)
			sb.append(m + "分钟");
		if (s != 0)
			sb.append(s + "秒");
		return sb.toString();
	}
	
	public static String asRMB(Number number){
		long price = number.longValue();
		long cent = price % 100;
		long yuan = price / 100;
		return "￥" + yuan + "." + fixedNumber((int)cent);
	}
	
	public static String asTime(Number number) {
		StringBuilder sb = new StringBuilder();
		int time = number.intValue() / 1000;

		int h = time / 60 / 60;
		int m = time / 60 % 60;
		int s = time % 60;
		if (h != 0){
			sb.append(fixedNumber(h) + ":");
			sb.append(fixedNumber(m));
		}
		return sb.toString();
	}
	
	public static String asUnfixedTime(Number number) {
		StringBuilder sb = new StringBuilder();
		int time = number.intValue() / 1000;

		int h = time / 60 / 60;
		int m = time / 60 % 60;
		int s = time % 60;
		if (h != 0){
			sb.append(h + ":");
			sb.append(m);
		}
		return sb.toString();
	}

	/**
	 * 格式化数字成两位
	 * 
	 * @param num
	 * @return
	 */
	static String fixedNumber(int num) {
		StringBuilder sb = new StringBuilder();
		sb.append(num / 10);
		sb.append(num % 10);
		return sb.toString();
	}
	
	/**
	 * 将一个java对象格式化成json输出
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj){
//		return new Gson().toJson(obj);
		return JSON.toJSONString(obj);
	}
	
	/**
	 * 将一个textile格式化成HTML输出
	 * @param textile
	 * @return
	 */
	public static String textileToHtml(String textile){
		return XTags.toHTML(textile);
	}
	
	static Date getToday(){
		Date date = new Date(System.currentTimeMillis());
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		return date;
	}
	
	static Date getYesterday(){
		Date date = new Date(getToday().getTime() - ONE_DAY_MS);
		return date;
	}
	
	static Date getTheDayBeforeYesterday(){
		Date date = new Date(getYesterday().getTime() - ONE_DAY_MS);
		return date;
	}

	/**
	 * 将时间格式化成人类友好的形式
	 * @param date
	 * @return
	 */
	public static String niceTime(Date date) {
		
		Date nowDate = new Date();
		
		Date today = getToday();
		Date yesterday = getYesterday();
		Date theDayBeforeYesterday = getTheDayBeforeYesterday();
		
		long now = nowDate.getTime();
		long time = date.getTime();
		long distance = (now - time) / 1000;
		if (distance < 10) { // 10s内
			return "刚刚";
		} else if (distance < 60) { // 1分钟内
			return distance + "秒前";
		} else if (distance < 60 * 60) { // 1小时内
			return (distance / 60) + "分钟前";
		} else if ( date.getTime() > today.getTime()) { //今天
			DateFormat dateFormatWithoutYear = new SimpleDateFormat(
					"今天 HH:mm");
			return dateFormatWithoutYear.format(date);
		}else if (date.getTime() > yesterday.getTime()) { //昨天
			DateFormat dateFormatWithoutYear = new SimpleDateFormat(
					"昨天 HH:mm");
			return dateFormatWithoutYear.format(date);
		}else if (date.getTime() > theDayBeforeYesterday.getTime()) { //前天
			DateFormat dateFormatWithoutYear = new SimpleDateFormat(
					"前天 HH:mm");
			return dateFormatWithoutYear.format(date);
		}else { // 早于前天
			if(date.getYear() != nowDate.getYear()){ // 今年以前
				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy年MM月dd日");
				return dateFormat.format(date);
			}else{ // 今年
				DateFormat dateFormatWithoutYear = new SimpleDateFormat(
						"MM月dd日");
				return dateFormatWithoutYear.format(date);
			}
		}
	}
	
	public static String fullDateTime(Date date) {
		if(date == null){
			return "未知";
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		return dateFormat.format(date);
	}
	
	public static String simpleDateTime(Date date) {
		if(date == null){
			return "未知";
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		return dateFormat.format(date);
	}
	
	public static String brief(String str, int maxLen){
		if(str.length() <= maxLen){
			return str;
		}else{
			return str.substring(0, maxLen) + "...";
		}
	}
	
	public static RawData asHTML(RawData data) {
        return new RawData(data.toString().replace("\n", "<br/>").replace(" ", "&nbsp;"));
    }

    public static RawData asHTML(Object data) {
        return new RawData(HTML.htmlEscape(data.toString()).replace("\n", "<br/>").replace(" ", "&nbsp;"));
    }
    
    public static URLEntity asURL(String url){
    	return URLUtil.build(url);
    }
    
    public static URLEntity asAction(String action){
    	if(action.contains("(") || action.contains(")")){
    		Logger.warn("\'%s\'.asAction() does not support arguments!", action);
    		action = action.replaceAll("\\(.*\\)", "");//去掉参数
    	}
    	return URLUtil.build(Router.getFullUrl(action));
    }

}
