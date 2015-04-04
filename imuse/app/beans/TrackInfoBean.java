package beans;

import java.util.Date;
import java.util.List;

import models.Album;
import models.AlbumTrack;
import models.Cover;
import models.Track;
import models.User;

import com.sudocn.play.XJavaExtension;

public class TrackInfoBean {
	public String ownerId;
	
	public String username;

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

	public static TrackInfoBean build(Track track) {
		TrackInfoBean bean = new TrackInfoBean();
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

		User user = User.findById(track.ownerId);
		bean.username = user.nickname;
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
}
