package beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.IndexBanner;
import models.TrackPlayList;

public class TracklistBean {
	private String trackId;
	private String playListId;
	public int weight;
	public static TracklistBean build(TrackPlayList list){
		TracklistBean tBean=new TracklistBean();
		tBean.trackId=list.trackId;
		tBean.playListId=list.playListId;
		tBean.weight=list.weight;
		return tBean;
	}
	public static List<TracklistBean> buildList(List<TrackPlayList> list) {
		List<TracklistBean> list2 = new ArrayList<TracklistBean>();
		Iterator<TrackPlayList> it = list.iterator();
		while (it.hasNext()) {
			list2.add(build(it.next()));
		}
		return list2;
	}
	
}
