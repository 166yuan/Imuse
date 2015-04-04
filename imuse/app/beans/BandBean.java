package beans;

import java.util.List;

import models.Album;
import models.Band;
import models.Track;

/**生成乐队bean
 * @author 陈思远
 *
 */
public class BandBean {
	
	public String name;
	public String creatorId;
	public String avatarId;
	public String bandId;
	public String intro;
	public List<TrackBean> trackList;
	public List<AlbumBean> albumList;

	/** 生产乐队bean
	 * @param b
	 * @return
	 */
	public static BandBean build(Band b) {
		BandBean bean = new BandBean();
		bean.name = b.name;
		bean.avatarId = b.avatarId;
		bean.creatorId = b.creatorId;
		bean.bandId=b.id;
		bean.intro = b.intro;
		PageBean pageBean = PageBean.getInstance(0, 10);
		bean.trackList = TrackBean.buildList(Track.findByUserIdAndType(b.id, Track.TYPE_BAND, pageBean));
	    bean.albumList = AlbumBean.buildList(Album.findByOwnerIdAndType(b.id, pageBean, Album.TYPE_BAND));
		return bean;
	}

}
