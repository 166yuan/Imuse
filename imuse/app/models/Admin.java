package models;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 网站管理员，该表记录了网站管理员的账号
 *
 * @author chao
 * @since 7/11/14
 */
@Entity
@Table(name = "admin")
public class Admin extends BasicModel {

    @Column(name = "user_id")
    public String userId;

    public User getUser(){
    	Admin admin = new Admin();
        return User.findById(userId);
    }

}
