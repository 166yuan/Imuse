package controllers;

import beans.AlbumBean;
import beans.PageBean;
import beans.TrackBean;
import beans.TrackInfoBean;
import models.Album;
import models.IndexBanner;
import models.TagRelation;
import models.Track;
import models.TrackPlayRecord;

import org.scauhci.common.util.StringUtil;

import play.Play;
import utils.MessageUtil;
import utils.MusicUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 歌曲和专辑服务
 *
 * @author 谢易成
 */
public class Tracks extends WebService {


    /**
     * 返回的数据
     */
    public static void getTrackByOwnerId(String id, int curPage, int type) {
        if (StringUtil.isNotEmpty(id)) {
            long size = Track.countByUserIdAndType(id, type);
            PageBean pageBean = PageBean.getInstance(curPage, size);
            List<Track> TrackList = Track.findByUserIdAndType(id, type, pageBean);
            List<TrackBean> trackbeanlist = TrackBean.buildList(TrackList);
            wsOk(trackbeanlist);
        } else {
            wsError("找不到歌曲");
        }

    }

    /**
     * 根据歌曲id取歌曲
     *
     * @param id
     */
    public static void getTrackById(String id) {
        if (StringUtil.isNotEmpty(id)) {
            Track track = Track.findById(id);
            if (track != null) {
                TrackBean trackBean = TrackBean.build(track);
                wsOk(trackBean);
            } else {
                wsError("找不到歌曲");
            }
        } else {
            wsError("找不到歌曲");
        }
    }

    /**
     * 新增一条歌曲播放记录
     *
     * @param userId
     * @param trackId
     */
    public static void newTrackPlayRecord(String userId, String trackId) {
    	if(StringUtil.isNotEmpty(trackId)&&StringUtil.isNotEmpty(userId)){
    		TrackPlayRecord.addTrackPlayRecord(userId, trackId);
    		wsOk("success");
    	}
    	else{
    		wsError("error");
    	}
    }


    /**
     * 通过tagIds以及歌曲名称来查找歌曲
     *
     * @param tagId
     * @param query
     */
  
    public static void searchTrack(String tagId, String query, int page) {
        
    	if(StringUtil.isNotEmpty(query)&&StringUtil.isNotEmpty(tagId)){
    		long size = Track.countByTagIdAndName(tagId, query, TagRelation.TYPE_TRACK);
    		PageBean pageBean = PageBean.getInstance(page, size);
    		List<Track> trackList = Track.findByTagIdAndName(tagId, query, TagRelation.TYPE_TRACK, pageBean);
    		List<TrackBean> trackBeanList = TrackBean.buildList(trackList);
    	
    		wsOk(trackBeanList);
    	}
    	else{
    		wsError("参数出错");
    	}
    }

    /**
     * 通过tagIds以及专辑名称来查找专辑
     *
     * @param tagId
     * @param query
     */
    public static void searchAlbum(String tagId, String query, int page) {
       
    	if(StringUtil.isNotEmpty(query)&&StringUtil.isNotEmpty(tagId)){
    		long size = Album.countByTagIdAndName(tagId, query, TagRelation.TYPE_ALBUM);
    		PageBean pageBean = PageBean.getInstance(page, size);
    		List<Album> albumList = Album.findByTagIdAndName(tagId, query, TagRelation.TYPE_ALBUM, pageBean);
    		List<AlbumBean> albumBeanList = AlbumBean.buildList(albumList);
    	
    		wsOk(albumBeanList);
    	}
    	else{
    		wsError("参数出错");
    	}
    }
    
    public static void addtracks(){
    	render();
    }
    
    /**
     * 上传歌曲
	 * @param f
	 * 新文件
     * 
     */
   
    public static void upload(File music,File lyrics,int TYPE,String id) {
    	//Object o = {};
    	//renderJSON(o);
    	//存储格式类型
    	Track track=new Track();
    	long count=Track.count();
    	count++;
//		System.out.println("1");
    	if (music != null) {
//    		System.out.println("2");
    		 String [] aStrings= {".mp3", ".ogg"};
    		String fa = music.getName();
    		String trackname=null;
    		int index=fa.lastIndexOf(aStrings[0]);
    		if(index!=-1){
    			track.audioFile=String.valueOf(count);
    		}else{
    			index=fa.lastIndexOf(aStrings[1]);
    			track.releaseFile=String.valueOf(count);
    		}
    		trackname=fa.substring(0,index);
    		System.out.println(trackname);
    		track.name=trackname;
    		String path = copy(music, new File("res"));
		int length=	MusicUtil.getDuration(path);
		track.ownerId=id;
		track.type=TYPE;
//		track.sourceFile="/"+path.replace('\\', '/');
//		System.out.println(track.sourceFile);
//		track.audioFile = "/"+path.replace('\\', '/');
//		System.out.println(track.audioFile);
		String key=utils.UploadUtil.upload(music, 1);
		if(key!=null){
			String url = Play.configuration.getProperty("oss.pub_url")+key;
			track.sourceFile= url;
			track.audioFile =url;
		}else{
			MessageUtil.generateErrorMsg(flash, "上传失败，服务器异常！");
		}
		track.duration=length;
		track.save();
    	}
    	if(lyrics!=null){
    		String key=utils.UploadUtil.upload(lyrics, 2);
    		if(key!=null){
    			String url = Play.configuration.getProperty("oss.pub_url")+key;
    			track.lyricFile=url;
        		track.save();
    		}else{
    			MessageUtil.generateErrorMsg(flash, "上传失败，服务器异常！");
    		}
    	}
    	MessageUtil.generateInfoMsg("上传成功");
    	myTracks(id);
    }
    /**
	 * 功能描述：拷贝一个目录或者文件到指定路径下，即把源文件拷贝到目标文件路径下
	 * 
	 * @param source
	 *            源文件
	 * @param target
	 *            目标文件路径
	 * @return void
	 */
	static String copy(File source, File target) {
		File tarpath = new File(target, source.getName());
		if (source.isDirectory()) {
			tarpath.mkdir();
			File[] dir = source.listFiles();
			for (int i = 0; i < dir.length; i++) {
				copy(dir[i], tarpath);
			}
		} else {
			try {
				InputStream is = new FileInputStream(source); // 用于读取文件的原始字节流
				OutputStream os = new FileOutputStream(tarpath); // 用于写入文件的原始字节的流
				byte[] buf = new byte[1024];// 存储读取数据的缓冲区大小
				int len = 0;
				while ((len = is.read(buf)) != -1) {
					os.write(buf, 0, len);
				}
				is.close();
				os.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return tarpath.getPath();
	}
	
	/** 仅供测试使用方法
	 *  获取当前用户所有歌曲
	 * @param userId
	 */
	public static void myTracks(String userId){
		List<Track> trackList = null;
		trackList = Track.find("ownerId=?",userId).fetch();
		render(trackList);
	}
	
	/**仅供测试用方法
	 * 由歌曲id上传歌曲对应歌词文件
	 * @param tracksId
	 */
	public static void upLoadLyricsByUserID(File f,String tracksId,String userId){
		Track track=Track.findByTrackId(tracksId);
		if(f!=null){
    		String key=utils.UploadUtil.upload(f, 2);
    		if(key!=null){
    			String url = Play.configuration.getProperty("oss.pub_url")+key;
    			track.lyricFile=url;
        		track.save();
        		MessageUtil.generateInfoMsg("上传成功");
    		}else{
    			MessageUtil.generateErrorMsg(flash, "上传失败，服务器异常！");
    		}
    	}
		else{
			MessageUtil.generateErrorMsg(flash, "上传失败，请选择歌词文件");
		}
    	myTracks(userId);
	}
	
	public static void deleteTrack(String id){
		
	}
	/**
	 * 后台查看申请歌曲
	 * @param type
	 * @param curPage
	 */
	public static void showTrackByType(int type,int page,boolean status){
		 if (type==0||type==1) {
	            long size = Track.countByType(type,status);
	            PageBean pageBean = PageBean.getInstance(page, size);
	            List<Track> TrackList = Track.findAllByTypeAndStatus(type, pageBean,status);
	            List<TrackBean> trackbeanlist = TrackBean.buildList(TrackList);
	            if(status&&type==0){
			        render("/ApplicationManager/showPassedTrack.html",trackbeanlist,pageBean);
	            }
	            else if(!status&&type==0){
		        render("/ApplicationManager/showTrack.html",trackbeanlist,pageBean);
	            }
	            else if(status&&type==1){
			        render("/ApplicationManager/showBandPassTrack.html",trackbeanlist,pageBean);

		        }
	            else if(!status&&type==1){
			        render("/ApplicationManager/showBandTrack.html",trackbeanlist,pageBean);

		        }
		 } else {
	            wsError("找不到歌曲");
	        }
	}
	
	public static void passTrack(String trackId,int type,boolean status){
		if(StringUtil.isNotBlank(trackId)){
			Track track = Track.findByTrackId(trackId);
			track.audited = true;
			track.save();
			MessageUtil.generateInfoMsg("处理成功");
			showTrackByType(type, 0, status);
			
		}
		else{
			wsError("error");
		}
		
	}
	public static void rejectTrack(String trackId,int type,boolean status){
		if(StringUtil.isNotBlank(trackId)){
			Track track = Track.findByTrackId(trackId);
			track.audited = false;
			track.save();
			MessageUtil.generateInfoMsg("处理成功");
			showTrackByType(type, 0, status);
			
		}
		else{
			wsError("error");
		}
		
	}
	
	public static void showTrackInfo(String trackId){
		Track track = Track.findByTrackId(trackId);
		TrackInfoBean trackbean = TrackInfoBean.build(track);
        render("/ApplicationManager/trackinfo.html",trackbean);
	}
	
	
	
}
