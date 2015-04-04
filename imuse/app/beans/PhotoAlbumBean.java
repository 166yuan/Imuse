package beans;

import java.util.ArrayList;
import java.util.List;

import models.PhotoAlbum;


public class PhotoAlbumBean {
	    public String ownerId;

	    public int type;

	    public String photo;

	    public String tagId;
	    
	    public static PhotoAlbumBean bulid(PhotoAlbum photoalbum){
	    	PhotoAlbumBean bean = new PhotoAlbumBean();
	    	bean.photo = photoalbum.photo;
	    	bean.type = photoalbum.type;
	    	bean.ownerId = photoalbum.ownerId;
	    	bean.tagId = photoalbum.tagId;
	    	return bean;
	    }
	    public static List<PhotoAlbumBean> bulidAll(List<PhotoAlbum> photoAlbumList){
	    	List<PhotoAlbumBean> photoAlbumBeanList = new ArrayList<PhotoAlbumBean>();
	    	for(int item = 0;item < photoAlbumList.size();item++){
	    		PhotoAlbumBean bean = bulid(photoAlbumList.get(item));
	    		photoAlbumBeanList.add(bean);
	    	}
	    	return photoAlbumBeanList;
	    	
	    }
}
