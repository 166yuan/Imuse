package controllers;
import org.scauhci.common.util.StringUtil;

import play.mvc.Controller;

public class kingda extends Controller{

//主页
	public static void index() {
		render("/index_common/index.html");
	}
//乐手主页
	public static void singer() {
		render("/singer_common/singer.html");
	}
//乐队主页
	public static void band() {
		render("/band_common/band_index.html");
	}

//播放器
	public static void play(){

		render("/play_common/play_index.html");
	}
//灵感空间
	public static void inspirationSpace(){

		render("/inspirationSpace_common/inspirationSpace_index.html");
	}

//专辑主页
	public static void ablum(){
		render("/ablum_common/ablum_index.html");
	}
//注册
	public static void register(){
		render("/register_common/register_index.html");
	}
//灵感发布
	public static void upload(){
		render("/upload_common/upload_makeName.html");
	}

	public static void upload2(String title){
		if(StringUtil.isNotEmpty(title)){
			render("/upload_common/upload_typeSelect.html",title);
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
//我的空间
	public static void myspace(){
		render("/myspace_common/myspace_index.html");
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