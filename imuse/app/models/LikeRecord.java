package models;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 曲目点赞记录
 *
 * @author chao
 * @since 7/10/14
 */
@Entity
@Table(name = "like_record")
public class LikeRecord extends BasicModel {

    @Column(name="track_id")
    public String trackId;

    @Column(name="user_id")
    public String userId;
}
