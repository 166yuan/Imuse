package controllers;

import org.apache.log4j.lf5.LF5Appender;

import beans.LikesBean;
import models.LikeRecord;

/**
 * 喜欢操作服务
 * 
 * @author chao
 * @since 7/17/14
 */
public class Likes extends WebService {

	/**
	 * 更新喜欢状态
	 * 
	 * @param trackId
	 *            歌曲ID
	 * @param posterId
	 *            用户ID
	 */
	public static void toggleLikeStatus(String trackId, String posterId) {
		// TODO 陈思远
		String t1=trackId;
		String t2=posterId;
		LikeRecord like = LikeRecord.find("track_id = ? and user_id = ?",
				t1, t2).first();
		if (like == null) {
			LikeRecord likeRecord = new LikeRecord();
			likeRecord.trackId = t1;
			likeRecord.userId = t2;
			likeRecord.save();
			LikesBean lBean=LikesBean.build(true);
			wsOk(lBean);
		}
		else{
			like.delete();
			LikesBean lBean=LikesBean.build(false);
			wsOk(lBean);
		}

	}

	/**
	 * 获得歌曲喜欢的统计数字以及该用户是否喜欢
	 * 
	 * @param userId
	 * @param trackId
	 */
	public static void getTrackLike(String userId, String trackId) {
		// TODO 陈思远
		long count=LikeRecord.count("track_id=?",trackId);
		LikeRecord like = LikeRecord.find("track_id = ? and user_id = ?",
				trackId, userId).first();
		boolean isLike=false;
		if(like!=null){
			isLike=true;
		}
		LikesBean lBean=LikesBean.buildCountBean(count, isLike);
		wsOk(lBean);
	}
}
