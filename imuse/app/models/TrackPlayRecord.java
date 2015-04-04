package models;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 曲目播放记录
 *
 * @author chao
 * @since 7/11/14
 */
@Entity
@Table(name = "track_play_record")
public class TrackPlayRecord extends BasicModel {

    @Column(name="user_id")
    public String userId;

    @Column(name="track_id")
    public String trackId;
    
    public static void addTrackPlayRecord(String userId,String trackId){
    	TrackPlayRecord trackPlayRecord = new TrackPlayRecord();
    	trackPlayRecord.trackId = trackId;
    	trackPlayRecord.userId = userId;
    	trackPlayRecord.save();
    }
    
    public static TrackPlayRecord findByUserIdAndTrackId(String userId,String trackId){
    	return TrackPlayRecord.find("userId=? and trackId=?", userId,trackId).first();
    }
}
