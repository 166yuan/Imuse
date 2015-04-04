package controllers;

import java.util.List;

import org.scauhci.common.util.StringUtil;

import beans.BandBean;
import beans.MusicianBean;
import beans.UserBean;
import beans.UserPublishBean;
import models.User;
import models.Band;

/**
 * @author chao
 * @since 7/11/14
 */
public class UserWebService extends WebService {

	public static void getUser(String userId) {
		User user = User.findById(userId);
		wsNotFoundIfNull(user);
		wsOk(UserBean.build(user));
	}

	/**
	 * 搜索歌手
	 * 
	 * @param query
	 */
	public static void searchMusician(String query) {

		if (StringUtil.isNotEmpty(query)) {
			List<User> userlist = User.searchMusician(query);
			wsOk(userlist);
		} else {
			wsError("error");
		}
	}

	public static void searchMusicianById(String id) {
		if (StringUtil.isNotEmpty(id)) {
			User user = User.findById(id);
			wsOk(MusicianBean.build(user));
		} else {
			wsError("error");
		}
	}

	/**
	 * 获得我的空间（个人主页）统计信息
	 * 
	 * @param userId
	 */
	public static void getMyCountedInfo(String userId) {
		wsOk(UserPublishBean.getMyInfo(userId));
	}

	public static void getMyPublishInfo(String userId) {
		wsOk(UserPublishBean.getInfoByDays(userId));
	}
}
