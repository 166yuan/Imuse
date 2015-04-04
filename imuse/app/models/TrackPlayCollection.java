package models;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 曲目播放收藏
 *
 * @author chao
 * @since 7/11/14
 */
@Entity
@Table(name = "track_play_collection")
public class TrackPlayCollection extends BasicModel {

    @Column(name="user_id")
    public String userId;

    @Column(name="track_id")
    public String trackId;
    
    public static void addTrackPlayRecord(String userId,String trackId){
    	TrackPlayCollection trackPlayCollection = new TrackPlayCollection();
    	trackPlayCollection.trackId=trackId;
    	trackPlayCollection.userId = userId;
    	trackPlayCollection.save();
    }
    
    public static TrackPlayCollection findByUserIdAndTrackId(String userId,String trackId){
    	return TrackPlayCollection.find("userId=? and trackId=?", userId,trackId).first();
    }
}
