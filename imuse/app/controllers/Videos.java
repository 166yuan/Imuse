package controllers;

import models.Video;

/*import java.util.Date;
 import java.util.HashMap;
 import java.util.Map;

 import org.json.JSONException;
 import org.json.JSONObject;

 import play.cache.Cache;
 import play.libs.WS;
 import play.libs.WS.HttpResponse;
 import play.libs.WS.WSRequest;
 import play.mvc.Scope;
 import utils.NetworkUtil;

 import beans.TokenBean;
 import beans.VideoBean;*/

/**
 * 音乐视频
 * 
 * @author boxiZen
 * @since 7/30/14
 */
public class Videos extends WebService {

	public static String client_id = "101a44fe680019bc";
	public static String client_secret = "5f27c9c442a1481ac4d1eade517683df";
	public static String auth_uri = "https://openapi.youku.com/v2/oauth2/authorize";
	public static String redirect_uri = "http://imuse.sudocn.com/Videos/authRedirect";

	/* 登陆授权 */
	public static void loginAuth() {
		redirect(auth_uri + "?client_id=" + client_id
				+ "&response_type=code&redirect_uri=" + redirect_uri);
	}

	/* 上传页面 */
	public static void upload() {
		render();
	}

	/* 页面回调 */
	public static void authRedirect() {
		render("/videos/oauth_result.html");
	}

	/**
	 * 把上传完成的视频信息保存到数据库
	 * 
	 * @param vid
	 * @param client_id
	 */
	public static void saveInfo(String vid, String client_id, String title) {
		Video v = new Video();
		v.title = title;
		v.vid = vid;
		v.clientId = client_id;
		v.save();
	}
}
