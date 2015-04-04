package controllers;

import java.util.List;

import org.scauhci.common.util.StringUtil;

import beans.PageBean;
import beans.PhotoAlbumTagBean;
import models.PhotoAlbum;
import models.PhotoAlbumTag;

/**
 * 相册服务
 *
 * @author chao
 * @since 7/21/14
 */
public class Photos extends WebService {

    public static void getTags(String ownerId, int type){
    	if(StringUtil.isNotEmpty(ownerId)&&(type == 0||type == 1)){
    		List<PhotoAlbumTag> photoAlbumList = PhotoAlbumTag.findByOwnerId(ownerId, type);
    		List<PhotoAlbumTagBean> photoAlbumTagBeanList =  PhotoAlbumTagBean.bulidAll(photoAlbumList);
    		wsOk(photoAlbumTagBeanList);
    	}
    	else{
    		wsError("参数错误");
    	}
        //TODO 谢易成 输入该用户或者乐队的相册标签，而且输出标签里第一张相片，排序按照updateTime倒序排列
    }


    public static void getPhotos(String tagId, int page){
    	if(StringUtil.isNotEmpty(tagId)){
    		long size = PhotoAlbum.countByTagId(tagId);
    		PageBean pageBean = PageBean.getInstance(page, size);
    		List<PhotoAlbum> photoAlbumList = PhotoAlbum.findByTagId(tagId, pageBean); 
    		wsOk(photoAlbumList);
    	}
    	else{
    		wsError("参数错误");
    	}
        //TODO 谢易成 查找标签下相片，支持分页，排序按照updateTime倒序排列
    	
    }

}
