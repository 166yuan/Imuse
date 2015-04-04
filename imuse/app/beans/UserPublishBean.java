package beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utils.CompareUtil;
import utils.DateUtil;
import models.Track;
import models.User;
import models.Video;

/**
 * 获取用户空间（主页）发布统计信息的接口类
 * 
 * @author 陈思远
 * 
 */
public class UserPublishBean {

	int sumOfDays;
	int sumOfSongs;
	int sumOfVideos;
	int sumOfSpirits;
	int sumOfLogs;
	String userName;
	String userImage;
	String bandImage;
	ArrayList<DailysBean> list;

	public static void getTracks(String userId, ArrayList<DailysBean> list) {
		List<Track> tracklist = Track.find("ownerId=?", userId).fetch();
		for (int i = 0; i < tracklist.size(); i++) {
			list.add(DailysBean.CreateByTrackId(tracklist.get(i).id, userId,
					tracklist.get(i).createTime, tracklist.get(i).name));
		}
	}

	public static void getVideos(String userId, ArrayList<DailysBean> list) {
		List<Video> videolist = Video.find("userId=?", userId).fetch();
		for (int i = 0; i < videolist.size(); i++) {
			list.add(DailysBean.CreateByVideoId(videolist.get(i).vid, userId,
					videolist.get(i).createTime, videolist.get(i).clientId,videolist.get(i).title));
		}
	}

	public static void getSpirits(String userId, ArrayList<DailysBean> list) {

	}

	public static void getLogs(String userId, ArrayList<DailysBean> list) {

	}

	public static UserPublishBean getMyInfo(String userId) {
		UserPublishBean ubean = new UserPublishBean();
		User user = User.findById(userId);
		ubean.sumOfDays = DateUtil.countDaysByTime(user.createTime);
		ubean.sumOfSongs = (int) Track.count("ownerId=?", userId);
		ubean.userName = user.nickname;
		ubean.sumOfVideos = (int) Video.count("userId=?", userId);
		return ubean;
	}
	
	public static UserPublishBean getInfoByDays(String userId){
		UserPublishBean ubean = new UserPublishBean();
		User user = User.findById(userId);
		ubean.list = new ArrayList<DailysBean>();
		getTracks(userId, ubean.list);
		getVideos(userId, ubean.list);
		getSpirits(userId, ubean.list);
		getLogs(userId, ubean.list);
		CompareUtil<DailysBean> cutil = new CompareUtil<DailysBean>(2);
		Collections.sort(ubean.list, cutil);
		return ubean;
	}

}
