package models;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * 封面
 *
 * @author chao
 * @since 7/10/14
 */
@Entity
@Table(name = "cover")
public class Cover extends BasicModel {

    public static final int TYPE_TRACK = 0;
    public static final int TYPE_ALBUM = 1;
	public static final int TYPE_USER = 2;
	public static final int TYPE_BAND = 3;

    /**
     * 类型：曲目、专辑、用户、乐队
     */
    @Column(name = "type")
    public int type;

    /**
     * 曲目、专辑、用户、乐队
     */
    @Column(name = "owner_id")
    public String ownerId;

    /**
     * 附件
     */
    @Column(name = "attach_id")
    public String attachId;

    /**
     * 权重
     */
    @Column(name = "weight")
    public int weight;
    
    public static List<Cover> findCoverByOwnerAndType(String ownerId, int type){
    	return  find("ownerId = ? and type = ? order by weight asc", ownerId ,type).fetch();
    }

	public static List<Cover> findCoverByTrack(String trackId){
		return  findCoverByOwnerAndType(trackId ,TYPE_TRACK);
	}
	public static List<Cover> findCoverByAlbum(String albumId){
		return  findCoverByOwnerAndType(albumId ,TYPE_ALBUM);
	}
	public static List<Cover> findCoverByUser(String userId){
		return  findCoverByOwnerAndType(userId ,TYPE_USER);
	}
	public static List<Cover> findCoverByBand(String bandId){
		return  findCoverByOwnerAndType(bandId ,TYPE_BAND);
	}
}
