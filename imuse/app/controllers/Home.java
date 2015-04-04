package controllers;

import beans.MyspaceBean;
import org.scauhci.common.util.StringUtil;

import play.mvc.Controller;
import models.User;
public class Home extends Controller{

	/**
	 * 主页
	 */
	public static void index() {
		String nickname=session.get("nickName");
        String userId=session.get("userId");
        Boolean islogin=false;
        if(nickname!=null&&userId!=null)
            islogin=true;

        render("/index_common/index.html",islogin,nickname,userId);
	}
	
	/**
	 * 歌手主页
	 */
	public static void singer() {
		render("/singer_common/singer.html");
	}
	
	/**
	 *  排行版
	 */
	public static void ranklist() {
		render("/Ranklist/ranklist_index.html");
	}
	
	/**
	 * 乐队主页
	 */
	public static void band(){
		render("/band_common/band_index.html");
	}
	
	/**
	 * 播放列表
	 */
	public static void play(){
		render("/play_common/play_index.html");
	}
	
	/**
	 * 个人空间
	 */
	public static void myspace(){
        String nickname=session.get("nickName");
        String userId=session.get("userId");
        Boolean islogin=false;
        if(nickname!=null&&userId!=null)
            islogin=true;
		render("/myspace_common/myspace_index.html",nickname);
	}

    public static void getMyspace(){
        String userId=session.get("userId");
        MyspaceBean bean=new MyspaceBean();
        bean.buildList(userId);
        renderJSON(bean);
    }
    public static void login(){
        render("/Login/index.html");
    }

	/**
	 * 注册
	 */
	public static void register(){
		render("/register_common/register_index.html");
	}


	/**
	 * 专辑主页
	 */
	public static void ablum(){
		render("/ablum_common/ablum_index.html");
	}
	
	public static void upload(){
		render("/upload_common/upload_index.html");
	}
	public static void upload2(String title){

		if(StringUtil.isNotEmpty(title)){
			render("/upload_common/upload_index_2.html",title);
		}
		
	}
	public static void upload3(int type,String title){

		if(type==0){
			render("/upload_common/upload_index_text.html",type,title);
		}
		else if(type==1){
			render("/upload_common/upload_index_audio.html",type,title);
		}
		else if(type==2){
			render("/upload_common/upload_index_vedio.html",type,title);
		}
		else{
			renderJSON("error");
		}
		
	}

    //音乐频道
    public static void musicChannel(){
        render("Channel_common/musicChannel_common/musicChannel_index.html");
    }
    //乐手乐队频道
    public static void singerChannel(){
        render("Channel_common/singerChannel_common/singerChannel_index.html");
    }


}
