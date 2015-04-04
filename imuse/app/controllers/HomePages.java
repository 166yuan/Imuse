package controllers;

import models.HomePage;
import beans.HomeBean;

import com.sudocn.play.BasicModel;

/**
 * @author chao
 * @since 7/17/14
 */
public class HomePages extends WebService {
	
	/**
	 * 通过用户或者乐队ID和类型来获取主页数据
	 * 
	 * @param ownerId
	 * @param type
	 */
	public static void getHomePage(String ownerId, int type) {
		// TODO 陈思远
			HomePage homePage=HomePage.find("type=? and owner_id=?", type,ownerId).first();
			HomeBean bean=HomeBean.build(homePage);
			wsOk(bean);
	}
}
