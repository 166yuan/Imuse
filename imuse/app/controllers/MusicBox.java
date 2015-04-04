package controllers;

import java.util.ArrayList;
import java.util.List;

import beans.AlbumTrackBean;
import beans.MusicBoxBean;
import beans.TrackBean;
import beans.TracklistBean;
import models.Album;
import models.Track;
import models.User;
import play.Play;
import play.mvc.*;

/**
 * 音乐盒
 * 
 * @author boxiZen
 * @since 7/16/14 
 */

public class MusicBox extends WebService {
	
	public static void play(){
		render("/play_common/play_index.html");
	}
	
	public static void playByAlbum(String albumId) {
        albumId = "album0";
		MusicBoxBean bean=MusicBoxBean.buildMusicBox(albumId);
		wsOk(bean);
	}
	
	/*public static void test(){
		Track track = new Track();
		track.author = "boxiZen";
		List<Track> trackList = new ArrayList();
		trackList.add(track);
		render("/MusicBox/play.html",trackList);
	}*/
	
	public static void playByTrack(String trackId){
		trackId = "0trsdacjk0";
		Track track = Track.findByTrackId(trackId);
		TrackBean trackBean = TrackBean.build(track);
		wsOk(trackBean);
	}
}
