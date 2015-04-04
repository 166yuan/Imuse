package models;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 标签
 *
 * @author chao
 * @since 7/17/14
 */
@Entity
@Table(name = "tag")
public class Tag extends BasicModel {

    /**
     * 标签名
     */
    @Column(name="name")
    public String name;

    /**
     * 图标（仅官方style有图标）
     */
    @Column(name = "icon_id")
    public String iconId;

    /**
     * 标签创建者
     */
    @Column(name = "creator_id")
    public String creatorId;

    /**
     * 创建一个标签，如同名标签已存在，则返回直接该标签
     * @return
     */
    public static Tag createTag(String name, String creatorId){
    	Tag tag=null;
    	tag=Tag.find("creatorId=? and name=?", creatorId,name).first();
    	if(tag!=null){
    		return tag;
    	}else {
    		tag=new Tag();
    		tag.name=name;
    		tag.creatorId=creatorId;
    		return tag;
    	}
    }
}
