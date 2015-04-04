package models;

import java.util.List;

import beans.PageBean;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户
 *
 * @author chao
 * @since 7/10/14
 */
@Entity
@Table(name = "user")
public class User extends BasicModel {

    public static final int ROLE_USER = 0;
    public static final int ROLE_MUSICIAN = 1;
    /**
     * 邮箱
     */
    @Column(name = "email")
    public String email;

    /**
     * 密码
     */
    @Column(name = "passwd")
    public String passwd;

    /**
     * 头像
     */
    @Column(name="avatar_id")
    public String avatarId;

    /**
     * 邮箱验证
     */
    @Column(name="email_verify")
    public boolean emailVerify;

    /**
     * 角色：普通用户、歌手
     */
    @Column(name="role")
    public int role;

    /**
     * 昵称
     */
    @Column(name="nickname")
    public String nickname;

    /**
     * 简介
     */
    @Column(name="intro")
    public String intro;
    
    /**
     * 乐队ID
     */
    @Column(name="band_id")
    public String bandId;
    
    public OSSAttachment getAvatar(){
        return OSSAttachment.findById(avatarId);
    }
    
    public static List<User> findByrole(int role , PageBean pageBean){
    	return User.find("role = ?", role).fetch(pageBean.getCurPage(), pageBean.getPerPage());
    }
    
    public static List<User> getTenSampleByrole(int role){
    	return User.find("role = ?", role).fetch(10);
    }
    
    public static long countByrole(int role){
    	return User.count("role = ?", role);
    }
    public static User findByEmail(String email) {
	    return User.find("email = ?", email).first();
    }
    public static List<User> findByPageBean(PageBean pageBean) {
	    return User.find("order by createTime desc").fetch(pageBean.getCurPage(), pageBean.getPerPage());
    }
    
    public static User findByNickname(String nickname){
    	return User.find("nickname = ?", nickname).first();
    }

    public static List<User> search(String query){
        return User.find("nickname like ?", String.format("%%%s%%", query)).fetch();
    }
    public static List<User> searchMusician(String query){
        return User.find("nickname like ? and role = ?", String.format("%%%s%%", query),User.ROLE_MUSICIAN).fetch();
    }
    public static List<User> findByBandId(String bandId){
        return User.find("bandId = ?", bandId).fetch();
    }
    
    
}
