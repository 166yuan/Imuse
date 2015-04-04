package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.org.apache.bcel.internal.generic.NEW;

/** 时间处理相关类
 * @author 陈思远
 *
 */
public class DateUtil {

	/** 获取系统当前时间
	 * @return 当前时间距离1970-1-01 00:00:00.000 到当前时刻的秒数
	 */
	public static long getCurrentTime(){
		return System.currentTimeMillis();
	}
	
	/** 返回当前系统时间到创建时间的间隔天数
	 * @param pastTimeMillis
	 * @return 间隔天数
	 */
	public static int countDaysByTime(long pastTimeMillis){
		long temp =getCurrentTime()-pastTimeMillis;
		long count= temp/(1000*60*60*24);
		int num=(int)count;
		return num;
	}
	
	/** 返回标准格式化时间
	 * @param time 时间秒数
	 * @return 格式化时间
	 */
	public static String getFormatDate(long time){
		SimpleDateFormat df = new SimpleDateFormat("yyyy年-MM月-dd日");
		String transDate=df.format(new Date(time));
		return transDate;
	}
}
