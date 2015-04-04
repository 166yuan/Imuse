package controllers;

import models.*;
import play.Play;
import play.mvc.*;

/**
 * 用户登录
 * 
 * @author boxiZen
 * @since 7/15/14
 */

public class Login extends Controller {
	public static void login(){
		render();
	}
	
	public static void index() {
		render();
	}
	
	public static void ajax(String email, String password) {
		if (session.get("userId") != null) {
			Object[] array = {"0","该用户已经登陆"};
			renderJSON(array);
		} else {
			findUser(email, password);
		}
	}

	public static void findUser(String email, String password) {
        System.out.println("email is :"+email);
        System.out.println("password is :"+password);
		User user = User.find("email = ?",email).first();
		if (user != null) {
			if (user.passwd.equals(password)) {
                session.put("nickName",user.nickname);
                session.put("userId",user.id);
				Object[] array = {"1"};
				renderJSON(array);
			}
			else{
				Object[] array = {"0","密码错误"};
				renderJSON(array);
			}
		} 
		else {
			Object[] array = {"0","该用户不存在"};
			renderJSON(array);
		}
	}
}
