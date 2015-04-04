package beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import models.Album;
import models.AlbumTrack;
import models.Track;

/**
 * @author chao
 * @since 7/11/14
 */
public class AlbumTrackBean {

	public String albumId;

	public String trackerId;

	public int weight;

	public List<TrackBean> tracklist;

	public static AlbumTrackBean build(AlbumTrack albumtrack) {
		AlbumTrackBean bean = new AlbumTrackBean();
		bean.albumId = albumtrack.albumId;
		bean.trackerId = albumtrack.trackerId;
		bean.weight = albumtrack.weight;
		bean.tracklist = TrackBean.buildList(Track
				.findAllByUserId(bean.albumId));
		return bean;
	}

	public static List<AlbumTrackBean> buildList(List<AlbumTrack> albumlist) {

		List<AlbumTrackBean> albumBeanList = new ArrayList<AlbumTrackBean>();
		for (int item = 0; item < albumlist.size(); item++) {
			AlbumTrack albumtrack = albumlist.get(item);
			AlbumTrackBean ab = build(albumtrack);
			albumBeanList.add(ab);

		}
		return albumBeanList;
	}
}
