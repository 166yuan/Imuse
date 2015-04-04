package models;

import com.sudocn.play.BasicModel;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 播放列表
 * @author chao
 * @since 7/11/14
 */
@Entity
@Table(name="play_list")
public class PlayList extends BasicModel {

    public String ownerId;

    public String name;

}
