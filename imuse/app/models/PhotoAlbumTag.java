package models;

import java.util.List;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 相册标签
 *
 * @author chao
 * @since 7/10/14
 */
@Entity
@Table(name = "photo_album_tag")
public class PhotoAlbumTag extends BasicModel {

    @Column(name = "owner_id")
    public String ownerId;

    /**
     * @see models.PhotoAlbum
     */
    @Column(name = "type")
    public int type;

    @Column(name = "name")
    public String name;
    
    public static List<PhotoAlbumTag> findByOwnerId(String ownerId,int type){
    	return find("owner_id = ? and type = ? order by updateTime desc", ownerId, type).fetch();
    }
}
