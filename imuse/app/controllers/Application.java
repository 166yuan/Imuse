package controllers;

import play.*;
import play.mvc.*;
import utils.MessageUtil;

import java.util.*;

import beans.PageBean;
import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }
    
    public static void demo(){
    	MessageUtil.generateInfoMsg("消息提示：我要食肉。。。。。");
    	PageBean pageBean = PageBean.getInstance(3, 100);
        render(pageBean);
    }

}