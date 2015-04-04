package controllers;

import java.io.File;

import models.IdeaAudio;
import models.IdeaText;
import models.IdeaVideo;

import org.scauhci.common.util.StringUtil;

import play.mvc.Controller;
import utils.UploadUtil;

public class Ideas extends Controller{
		
	public static void addIdea(String title,String videourl,String text,
			String audeourl,int type,File song,File lrc){
		if(type==0){
			if (StringUtil.isNotEmpty(title)&&StringUtil.isNotEmpty(text)) {
				IdeaText ideatext = new IdeaText();
				ideatext.title = title;
				ideatext.text = text;
				ideatext.save();
				render("/upload_common/upload_index_end.html");
			} else {
				renderJSON("error");
			}
		}
		if(type==1){
			if(StringUtil.isNotEmpty(title)&&song!=null&&lrc!=null){
				IdeaAudio ideaAudio=new IdeaAudio();
				ideaAudio.title=title;
				String songKey=UploadUtil.upload(song, 1);
				String lrcKey=UploadUtil.upload(lrc, 2);
				ideaAudio.TrackId=songKey;
				ideaAudio.LrcId=lrcKey;
				ideaAudio.save();
				render("/upload_common/upload_index_end.html");
			}else{
				renderJSON("error");
			}
			
		}
		if(type==2){
			if (StringUtil.isNotEmpty(title)&&StringUtil.isNotEmpty(videourl)) {
				IdeaVideo ideavideo = new IdeaVideo();
				ideavideo.title = title;
				ideavideo.url = videourl;
				ideavideo.save();
				render("/upload_common/upload_index_end.html");
				} else {
				renderJSON("error");
			}
		}
	}
}
