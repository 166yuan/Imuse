package beans;

import groovy.lang.Buildable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.Tag;
import models.TrackPlayCollection;
import models.TrackPlayRecord;

/**
 * 放回所有的播放记录的trackID bean
 * 
 * @author 陈思远
 * 
 */
public class TrackPLayRecordBean {
	String trackId;

	public static TrackPLayRecordBean build(TrackPlayRecord tr) {
		TrackPLayRecordBean bean = new TrackPLayRecordBean();
		bean.trackId = tr.trackId;
		return bean;
	}
	/**
	 * @param list
	 * @return trackID组成的bean
	 */
	public static List<TrackPLayRecordBean> buildPLayRecordIdBean(List<TrackPlayRecord>list){
		List<TrackPLayRecordBean>list2=new ArrayList<TrackPLayRecordBean>();
		Iterator<TrackPlayRecord> it = list.iterator();
		while (it.hasNext()) {
			list2.add(build(it.next()));
		}
		return list2;
	}
}
