package beans;

import java.text.SimpleDateFormat;
import java.util.Date;

import play.db.jpa.GenericModel;

/**
 * @author boxiZen
 * @since 7/31/14
 */
public class VideoBean {
	public String client_id;
	public String client_secret;
	public String access_token;
	public String title;
	public String[] tags;
	public String category;
	public String copyright_type;
	public String public_type;
	public String watch_password;
	public String description;
	public String file_name;
	public String file_md5;
	public int file_size;
	public double latitude;
	public double longitude;
	public String shoot_time;
	
	public Date getDateTime(){
		Date shoot_date = null;
		/*输入日期的字符串格式*/
		String formatPattern = "yyyy-MM-dd";
		SimpleDateFormat inputFormat = new SimpleDateFormat(formatPattern);
		try{
			shoot_date = inputFormat.parse(this.shoot_time);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return shoot_date;
	}
}
