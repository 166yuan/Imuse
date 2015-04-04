package controllers;
import models.User;
import play.mvc.Controller;
import utils.MessageUtil;

/**
 * Created by Administrator on 2014/11/30.
 */
public class Register extends Controller {
    public  static  void addUser(String email, String passwd, String nickname){
        if(User.findByEmail(email) != null) {
            Object[] array = {"0","该邮箱已经注册了"};
            renderJSON(array);
        }else{
            User user=new User();
            user.email=email;
            user.nickname=nickname;
            user.passwd=passwd;
            user.emailVerify=false;
            user.save();
            session.put("userId",user.id);
            session.put("nickName",nickname);
            Object[] array = {"1","注册成功"};
            renderJSON(array);
        }

    }
}
