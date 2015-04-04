package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sudocn.play.XJavaExtension;

import models.PhotoAlbum;
import models.PhotoAlbumTag;


public class PhotoAlbumTagBean {
	    public String ownerId;
	    
	    public int type;

	    public String name;
	    	 
	    public PhotoAlbum photoAlbum;
	    
	    public String id;
	    
		public String createTimeDesc;
	    
	    public static PhotoAlbumTagBean bulid(PhotoAlbumTag photoalbumtag){
	    	PhotoAlbumTagBean bean = new PhotoAlbumTagBean();
	    	bean.name = photoalbumtag.name;
	    	bean.type = photoalbumtag.type;
	    	bean.ownerId = photoalbumtag.ownerId;
	    	bean.id = photoalbumtag.id;
	        bean.createTimeDesc = XJavaExtension.simpleDateTime(new Date(photoalbumtag.createTime));
	    	return bean;
	    }
	    public static List<PhotoAlbumTagBean> bulidAll(List<PhotoAlbumTag> photoAlbumTagList){
	    	List<PhotoAlbumTagBean> photoAlbumTagBeanList = new ArrayList<PhotoAlbumTagBean>();
	    	for(int item = 0;item < photoAlbumTagList.size();item++){
	    		PhotoAlbumTagBean bean = bulid(photoAlbumTagList.get(item));
	    		if(item == 0){
	    			bean.photoAlbum = PhotoAlbum.findFirstByTagId(photoAlbumTagList.get(item).id);
	    		}
	    		else{
	    			bean.photoAlbum = null;
	    		}
	    		photoAlbumTagBeanList.add(bean);
	    	}
	    	return photoAlbumTagBeanList;
	    	
	    }
}
