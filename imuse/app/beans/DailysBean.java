package beans;

import java.util.List;

import utils.DateUtil;
import models.Tag;
import models.Track;
import controllers.Tracks;

/**
 * 获取用户当天发布的内容的bean
 * @author 陈思远
 *
 */
public class DailysBean {
	String time;
	public long createTimes;
	String[]tags;
	String linkPath;
	String client_Id;
	String tracksId;
	String videoId;
	String spiritsId;
	String logsId;
	String name;
	public static DailysBean CreateByTrackId(String trackId,String userId,long createTime,String name){
		DailysBean bean=new DailysBean();
		bean.tracksId=trackId;
		bean.videoId=null;
		bean.spiritsId=null;
		bean.logsId=null;
		bean.createTimes=createTime;
		bean.time=DateUtil.getFormatDate(createTime);
		bean.name=name;
		List<Tag>list=Tag.find("creatorId=?", userId).fetch();
		bean.tags=new String[list.size()];
		for(int i=0;i<list.size();i++){
			bean.tags[i]=list.get(i).name;
		}
		return bean;
	}
	public static DailysBean CreateByVideoId(String videoId,String userId,long createTime,String client_Id,String title){
		DailysBean bean=new DailysBean();
		bean.videoId=videoId;
		bean.createTimes=createTime;
		bean.client_Id=client_Id;
		bean.name=title;
		bean.time=DateUtil.getFormatDate(createTime);
		return bean;
	}
	
}
