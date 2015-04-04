package beans;

import java.util.List;

import models.Album;
import models.Track;

public class MusicBoxBean {
	Album album;
	List<Track> trackList ;
	public  static MusicBoxBean buildMusicBox(String albumId){
		MusicBoxBean mBean=new MusicBoxBean();
		mBean.album = Album.findById(albumId);
		mBean.trackList = mBean.album.getTracksIncludeUnaudited();
		return mBean;	
	}
}
