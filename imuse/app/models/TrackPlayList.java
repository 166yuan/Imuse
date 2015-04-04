package models;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 播放列表曲目
 *
 * @author chao
 * @since 7/11/14
 */
@Entity
@Table(name = "track_play_list")
public class TrackPlayList extends BasicModel {

    @Column(name = "play_list_id")
    public String playListId;

    @Column(name = "track_id")
    public String trackId;

    @Column(name = "weight")
    public int weight;
    
    
}
