package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sudocn.play.XJavaExtension;

import models.Album;
import models.AlbumTrack;
import models.Cover;
import models.Track;

public class TrackBean {

	public String ownerId;
    
	public String trackId;
	
	public int type;

	public String audioFile;
    
	public String name;

	public String lyricFile;

	public String sourceFile;

	public String author;

	public String copyright;

	public String note;

	public boolean audited;

	public String auditReason;

	public long createTime;

	public long duration;

	public String createTimeDesc;

	public List<CoverBean> coverList;

	public String albumName;

	public static TrackBean build(Track track) {
		TrackBean bean = new TrackBean();
		AlbumTrack at = AlbumTrack.find("tracker_id = ?", track.id).first();
		if (at != null) {
			Album album = Album.findById(at.albumId);
			if (album != null) {
				bean.albumName = album.name;
			} else {
				bean.albumName = "未知专辑";
			}
		} else {
			bean.albumName = "未知专辑";
		}
		bean.audioFile = track.audioFile;
		bean.audited = track.audited;
		bean.auditReason = track.auditReason;
		bean.author = track.author;
		bean.copyright = track.copyright;
		bean.lyricFile = track.lyricFile;
		bean.name = track.name;
		bean.note = track.note;
		bean.ownerId = track.ownerId;
		bean.sourceFile = track.sourceFile;
		bean.type = track.type;
		bean.createTime = track.createTime;
		bean.duration = track.duration;
		bean.trackId=track.id;
		bean.coverList = CoverBean.buildList(Cover.findCoverByOwnerAndType(
				track.id, track.type));
		bean.createTimeDesc = XJavaExtension.simpleDateTime(new Date(
				track.createTime));
		return bean;
	}

	public static List<TrackBean> buildList(List<Track> tracks) {

		List<TrackBean> trackBeanList = new ArrayList<TrackBean>();
		for (int item = 0; item < tracks.size(); item++) {
			Track track = tracks.get(item);
			TrackBean ab = build(track);
			trackBeanList.add(ab);

		}
		return trackBeanList;
	}
}
