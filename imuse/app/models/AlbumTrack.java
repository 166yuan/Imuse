package models;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * 专辑中的曲目
 *
 * @author chao
 * @since 7/10/14
 */
@Entity
@Table(name = "album_track")
public class AlbumTrack extends BasicModel {

    @Column(name = "album_id")
    public String albumId;

    @Column(name = "tracker_id")
    public String trackerId;

    @Column(name = "weight")
    public int weight;

    public Track getTrack(){
        return Track.findById(trackerId);
    }

    public static List<AlbumTrack> findByAlbum(String albumId){
        return find("albumId = ?", albumId).fetch();
    }
    public static AlbumTrack findFirst(String albumId){
    	return find("albumId = ?", albumId).first();
    }
}
