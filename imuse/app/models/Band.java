package models;

import java.util.List;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 乐队
 *
 * @author chao
 * @since 7/10/14
 */
@Entity
@Table(name = "band")
public class Band extends BasicModel {

    /**
     * 乐队名称
     */
    @Column(name="name")
    public String name;

    /**
     * 创建者
     */
    @Column(name="creator_id")
    public String creatorId;

    /**
     * 邀请码
     */
    @Column(name="invite_code")
    public String inviteCode;

    /**
     * 乐队Logo
     */
    @Column(name="avatar_id")
    public String avatarId;

    /**
     * 乐队简介
     */
    @Column(name="intro")
    public String intro;
    
    /**
     * 通过创建者取数据
     */
    public static Band findByCreator(String creatorid){
    	
    	return Band.find("creator_id = ?",creatorid).first();
    }

}
