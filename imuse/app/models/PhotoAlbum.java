package models;

import java.util.List;

import beans.PageBean;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 相册图片
 *
 * @author chao
 * @since 7/10/14
 */
@Entity
@Table(name = "photo_album")
public class PhotoAlbum extends BasicModel {

    public static final int TYPE_USER = 0;
    public static final int TYPE_BAND = 1;

    @Column(name="owner_id")
    public String ownerId;

    @Column(name="type")
    public int type;

    @Column(name="photo")
    public String photo;

    @Column(name="tag_id")
    public String tagId;
    
    public static List<PhotoAlbum> findByTagId(String tagId, PageBean pageBean){
    	return find("tagId = ? order by updateTime desc", tagId).
    			fetch(pageBean.getCurPage(),pageBean.getPerPage());
    }
    public static PhotoAlbum findFirstByTagId(String tagId){
    	return find("tagId = ? order by updateTime desc", tagId).first();
    }
    public static long countByTagId(String tagId){
    	return count("tagId = ?", tagId);
    }

}
