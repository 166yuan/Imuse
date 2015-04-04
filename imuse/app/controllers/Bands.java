package controllers;

import java.util.List;

import play.mvc.Controller;
import models.Band;
import models.User;
import beans.BandBean;
import beans.UserBean;

/**
 * 获取乐队相关信息的接口类
 * 
 * @author 陈思远
 * 
 */
public class Bands extends WebService {
	
	/** 获取乐队信息的接口
	 * @param id:
	 */
	public static void getBandByMusician(String id) {
		Band band = Band.findByCreator(id);
		if (band != null) {
			BandBean bean = BandBean.build(band);
			wsOk(bean);
		} else {
			wsError("找不到乐队");
		}
	}
	public static void getBandMember(String id){
		if(id!=null){
		List<User> userList = User.findByBandId(id);
		List<UserBean> userBeanList = UserBean.buildList(userList);
		wsOk(userBeanList);
		}
		 else {
				wsError("找不到乐队");
			}
		
	}

}
