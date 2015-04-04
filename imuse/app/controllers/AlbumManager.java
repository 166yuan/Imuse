package controllers;

import java.io.File;
import java.util.List;

import org.postgresql.translation.messages_zh_TW;
import org.scauhci.common.util.StringUtil;

import play.Play;
import play.mvc.Controller;
import utils.MessageUtil;
import models.Album;
import models.User;

/**
 * 后台专辑管理
 * 
 * @author 陈思远
 * 
 */
public class AlbumManager extends Controller {

	public static void index() {
		setByUser(0);
	}

	public static void setByUser(int page) {
		List<User> userlist = User.findAll();
		render(userlist);
	}

	public static void setByAlbum(int page) {
		List<Album> albumlist = Album.findAll();
		render(albumlist);
	}
	
	public static void editByAlbumId(String albumId,String name,String type,String author,String copyright,String note){
		Album album=Album.findById(albumId);
		album.name=name;
		album.copyright=copyright;
		album.note=note;
		album.save();
	}
	
	public static void addAlbumById(String username,String name,String copyright,String note,File f){
		User user=User.find("nickname = ?",username).first();
		Album album=null;
		if(user==null){
			MessageUtil.generateErrorMsg(flash, "不存在的用户，请检查拼写");
			addAlbum();
		}else {
			if(StringUtil.isNotEmpty(name)&&StringUtil.isNotEmpty(copyright)){
			album=new Album();
			album.author=user.nickname;
			album.copyright=copyright;
			album.name=name;
			album.note=note;
			album.audited=false;
			if(f!=null){
			String key=utils.UploadUtil.upload(f, 0);
			if(key!=null){
				String url= Play.configuration.getProperty("oss.pub_url")+key;
				album.imageUrl=url;
			}else{
			MessageUtil.generateErrorMsg(flash, "专辑图片上传失败，云服务器出错");	
			}
			}
			album.save();
			MessageUtil.generateInfoMsg(flash, "保存成功");
			addAlbum();
			}else{
				MessageUtil.generateErrorMsg(flash, "专辑名和版权不能为空");
				addAlbum();
			}
		}
	}
	
	public static void addAlbum(){
		render();
	}
	
	public static void getAlbumById(String userId){
		List<Album>albumlsit =Album.find("owner_id=?", userId).fetch();
		render(albumlsit);
	}
	
	public static void editAlbum(String albumId){
		Album album=Album.findById(albumId);
		render(album);
	}
	
	public static void updataAlbum(String albumId,String name,String copyright,String author,String note,File f){

			Album album=Album.findById(albumId);
			if(StringUtil.isNotEmpty(author)){
				User user=User.find("nickname=?", author).first();
				if(user==null){
					MessageUtil.generateErrorMsg(flash, "不存在的用户，请检测拼写");
				}
				else{
					album.author=user.nickname;
				}
			}
			if(StringUtil.isNotEmpty(name)){
				album.name=name;
			}
			if(StringUtil.isNotEmpty(note)){
				album.note=note;
			}
			if(StringUtil.isNotEmpty(copyright)){
				album.copyright=copyright;
			}
			if(f!=null){
				String key = utils.UploadUtil.upload(f,0);
				if(key!=null){
					album.imageUrl=Play.configuration.getProperty("oss.pub_url")+key;
				}else{
					MessageUtil.generateErrorMsg(flash, "图片上传出错");
					editAlbum(albumId);
				}
			}
			album.save();
			MessageUtil.generateInfoMsg(flash, "修改成功");
		editAlbum(albumId);
	}
	public static void listAlbum(){
		List<Album>albumlist = Album.findAll();
		render(albumlist);
	}
	
	public static void accecptById(String id){
		Album album = Album.findById(id);
		album.audited=true;
		album.save();
	}
	
	public static void rejectedById(String id){
		Album album = Album.findById(id);
		album.audited=false;
		album.save();
	}
	
	public static void acceptAlbum(){
		List<Album> acceptlist = Album.find("audited = true").fetch();
		render(acceptlist);
	}
	
	public static void rejectAlbum(){
		List<Album> rejectlist = Album.find("audited = false").fetch();
		render(rejectlist);
	}
}
