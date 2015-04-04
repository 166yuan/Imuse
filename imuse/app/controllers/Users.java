package controllers;

import java.util.List;

import models.Admin;
import models.User;

import org.apache.commons.lang.StringUtils;
import org.scauhci.common.util.StringUtil;

import play.mvc.Controller;
import utils.MessageUtil;
import beans.PageBean;

public class Users extends Controller {

	   public static void form() {
		        render();
	   }
	
	public static void add( String email, String passwd, String nickname, String intro, int role)  {
		if(StringUtils.isEmpty(email)){
			MessageUtil.generateErrorMsg(flash, "邮箱不能为空");
			form();
		}
		if(StringUtil.isEmpty(passwd)) {
			MessageUtil.generateErrorMsg(flash, "密码不能为空");
			form();
		}
		if(User.findByEmail(email) != null) {
			MessageUtil.generateErrorMsg(flash, "该邮箱已经注册了");
			form();
		}
		
		User u = new User();
		u.email = email;
		u.passwd = passwd;
		u.nickname = nickname;
		u.intro = intro;
		u.role = role;
		u.emailVerify = false;
		u.save();
		
		show(u.id);
	}
	
	public static void show(String id) {
		User user = User.findById(id);
		render(user);
	}
	
	public static void list(int current) {
		List<User> users = User.findAll();
		PageBean pageBean = PageBean.getInstance(current, User.count());
	        render(pageBean, users);
	}
	
	public static void addTest(){
		String userId = "boxizen@qq.com";
    	String nickname = "boxizen";
    	String pwd = "19911024";
        Admin admin = new Admin();
        admin.userId = userId;
        admin.save();
        User user = new User();
        user.id = userId;
        user.nickname = "boxiZen";
        user.passwd = pwd;
        user.intro = "testing~";
        user.save();
	}
}
