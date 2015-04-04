package models;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 乐队成员
 *
 * @author chao
 * @since 7/10/14
 */
@Entity
@Table(name = "band_member")
public class BandMember extends BasicModel {

    /**
     * 乐队
     */
    @Column(name="band_id")
    public String bandId;

    /**
     * 成员
     */
    @Column(name="user_id")
    public String userId;

    /**
     * 角色，用户自行填写
     */
    @Column(name="role")
    public String role;
}
