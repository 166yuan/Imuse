package models;

import java.util.List;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 标签关系
 *
 * @author chao
 * @since 7/17/14
 */
@Entity
@Table(name = "tag_relation")
public class TagRelation extends BasicModel {

    public static final int TYPE_TRACK = 0;
    public static final int TYPE_ALBUM = 1;

    /**
     * 类型：曲目、专辑
     */
    @Column(name = "type")
    public int type;

    /**
     * 曲目或者专辑
     */
    @Column(name = "owner_id")
    public String ownerId;

    @Column(name = "tag_id")
    public String tagId;
    
    public static List<TagRelation> findTagRelationByTagId(String tagId,int type){
    	return find("tagId = ? and type = ?", tagId,type).fetch();
    }
}
