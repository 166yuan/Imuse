package models;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 关注关系
 *
 * @author chao
 * @since 7/11/14
 */
@Entity
@Table(name = "follow")
public class Follow extends BasicModel {

    public static final int TYPE_USER = 0;
    public static final int TYPE_BAND = 1;

    /**
     * 用户或者乐队
     */
    @Column(name="owner_id")
    public String ownerId;

    /**
     * 类型：用户、乐队
     */
    @Column(name="type")
    public int type;

    @Column(name = "user_id")
    public String userId;

    @Column(name = "fan_id")
    public String fanId;
}
