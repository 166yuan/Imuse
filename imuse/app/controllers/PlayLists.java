package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import utils.CompareUtil;
import beans.PlayListBean;
import beans.TagBean;
import beans.TrackBean;
import beans.TrackPLayRecordBean;
import beans.TracklistBean;
import models.IndexBanner;
import models.PlayList;
import models.Track;
import models.TrackPlayCollection;
import models.TrackPlayList;
import models.TrackPlayRecord;

/**
 * @author chao
 * @since 7/17/14
 */
public class PlayLists extends WebService {

	/**
	 * 列出当前用户所有的播放列表，如果没有则创建一个“默认”的列表
	 * 
	 * @param userId
	 */
	public static void list(String userId) {
		// TODO 陈思远
		List<PlayList> list = PlayList.find("ownerId=?", userId).fetch();
		if (list.size() == 0) {
			createPlayList(userId, "默认");
		} else {
			List<PlayListBean> bList2 = PlayListBean.buildList(list);
			wsOk(bList2);
		}
	}

	/**
	 * 创建一个播放列表，并返回该列表
	 * 
	 * @param userId
	 * @param name
	 */
	public static void createPlayList(String userId, String name) {
		// TODO 陈思远
		PlayList temp = PlayList.find("name=?", name).first();
		if (temp != null) {
			wsError("播放列表已经存在！");
		} else {
			PlayList list = new PlayList();
			list.name = name;
			list.ownerId = userId;
			list.save();
			PlayListBean lBean = PlayListBean.build(list);
			wsOk(lBean);
		}
	}

	/**
	 * 更新播放列表的名字
	 * 
	 * @param playListId
	 * @param newName
	 */
	public static void renamePlayList(String playListId, String newName) {
		// TODO 陈思远
		PlayList list = PlayList.find("id=?", playListId).first();
		list.name = newName;
		list.save();
		PlayListBean lBean = PlayListBean.build(list);
		wsOk(lBean);
	}

	/**
	 * 获取播放列表下的所有歌曲
	 * 
	 * @param playListId
	 */
	public static void getTracksFromList(String playListId) {
		List<TrackPlayList> list = TrackPlayList.find("playListId=?",
				playListId).fetch();
		CompareUtil<TrackPlayList> cutil = new CompareUtil<TrackPlayList>(1);
		Collections.sort(list, cutil);
		List<Track> list2 = new ArrayList<Track>();
		Iterator<TrackPlayList> it = list.iterator();
		while (it.hasNext()) {
			list2.add(Track.findByTrackId(it.next().trackId));
		}
		List<TrackBean> list3 = TrackBean.buildList(list2);
		wsOk(list3);
	}

	/**
	 * 从播放列表里面移除一首歌
	 * 
	 * @param playListId
	 * @param trackId
	 */
	public static void removeFromList(String playListId, String trackId) {
		// TODO 陈思远
		TrackPlayList list = TrackPlayList.find(
				"play_list_id=? and track_id=?", playListId, trackId).first();
		if (list == null) {
			wsError("歌曲不存在");
		} else {
			List<TrackPlayList> allList = TrackPlayList.find("play_list_id=?",
					playListId).fetch();
			System.out.print(allList.size());
			int tag = list.weight;
			Iterator<TrackPlayList> it = allList.iterator();
			while (it.hasNext()) {
				TrackPlayList temp = it.next();
				if (temp.weight > tag) {
					temp.weight--;
					temp.save();
				}
			}
			list.delete();
		}
		// 返回删除后所有列表
		getTracksFromList(playListId);
	}

	/**
	 * 批量从播放列表删除
	 * 
	 * @param playListId
	 * @param trackId
	 */
	public static void batchRemove(String playListId, String trackId[]) {
		for (int i = 0; i < trackId.length; i++) {
			TrackPlayList list = TrackPlayList.find(
					"play_list_id=? and track_id=?", playListId, trackId)
					.first();
			if (list == null) {
				wsError("歌曲不存在");
			} else {
				List<TrackPlayList> allList = TrackPlayList.find(
						"play_list_id=?", playListId).fetch();
				System.out.print(allList.size());
				int tag = list.weight;
				Iterator<TrackPlayList> it = allList.iterator();
				while (it.hasNext()) {
					TrackPlayList temp = it.next();
					if (temp.weight > tag) {
						temp.weight--;
						temp.save();
					}
				}
				list.delete();
			}
		}
		getTracksFromList(playListId);
	}

	/**
	 * 添加一首歌到播放列表
	 * 
	 * @param playListId
	 * @param trackId
	 */
	public static void addToList(String playListId, String trackId) {
		// TODO 陈思远
		TrackPlayList trackPlayList = new TrackPlayList();
		TrackPlayList playlist = TrackPlayList.find(
				"play_list_id=? and track_id=?", playListId, trackId).first();
		if (playlist != null) {
		} else {
			List<TrackPlayList> list = TrackPlayList.find("play_list_id = ?",
					playListId).fetch();
			trackPlayList.trackId = trackId;
			trackPlayList.playListId = playListId;
			trackPlayList.weight = list.size();
			trackPlayList.save();
		}
		getTracksFromList(playListId);
	}

	public static void batchAddToList(String playListId, String [] trackId){
		for(int i=0;i<trackId.length;i++){
			TrackPlayList trackPlayList = new TrackPlayList();
			TrackPlayList playlist = TrackPlayList.find(
					"play_list_id=? and track_id=?", playListId, trackId).first();
			if (playlist != null) {
			} else {
				List<TrackPlayList> list = TrackPlayList.find("play_list_id = ?",
						playListId).fetch();
				trackPlayList.trackId = trackId[i];
				trackPlayList.playListId = playListId;
				trackPlayList.weight = list.size();
				trackPlayList.save();
			}
		}
		getTracksFromList(playListId);
	}
	
	/**
	 * 添加一首歌到播放记录
	 * 
	 */
	public static void addToRecord(String trackId, String userId) {
		List<TrackPlayRecord> list = TrackPlayRecord.find(
				"userId=? and trackId=?", userId, trackId).fetch();
		if (list.size() == 0) {
			TrackPlayRecord.addTrackPlayRecord(userId, trackId);
		}
	}

	/**
	 * 将曲目从播放记录中删除
	 * 
	 * @param userId
	 * @param trackId
	 */
	public static void deleteFromRecord(String userId, String trackId) {
		TrackPlayRecord tr = TrackPlayRecord.findByUserIdAndTrackId(userId,
				trackId);
		if (tr != null) {
			tr.delete();
		}
		getRecord(userId);
	}

	/**
	 * 批量删除播放记录
	 * 
	 * @param userId
	 * @param trackId
	 */
	public static void batchDelFromRecord(String userId, String[] trackId) {
		for (int i = 0; i < trackId.length; i++) {
			TrackPlayRecord tr = TrackPlayRecord.findByUserIdAndTrackId(userId,
					trackId[i]);
			if (tr != null) {
				tr.delete();
			}
		}
		getRecord(userId);
	}

	/**
	 * 获取当前用户播放历史中曲目接口
	 * 
	 * @param userId
	 */
	public static void getRecord(String userId) {
		List<TrackPlayRecord> list = TrackPlayRecord.find("userId", userId)
				.fetch();
		List<Track> list2 = new ArrayList<Track>();
		Iterator<TrackPlayRecord> it = list.iterator();
		while (it.hasNext()) {
			list2.add(Track.findByTrackId(it.next().trackId));
		}
		// System.out.println(list2.size());
		List<TrackBean> list3 = TrackBean.buildList(list2);
		wsOk(list3);
	}

	/**
	 * 获取当前用户播放历史中曲目的id的接口
	 * 
	 * @param useId
	 */
	public static void getRecordId(String userId) {
		List<TrackPlayRecord> list = TrackPlayRecord.find("userId", userId)
				.fetch();
		List<TrackPLayRecordBean> list2 = TrackPLayRecordBean
				.buildPLayRecordIdBean(list);
		wsOk(list2);
	}

	/**
	 * 添加一首歌到收藏列表
	 * 
	 * @param userId
	 * @param trackId
	 */
	public static void addToCollection(String userId, String trackId) {
		List<TrackPlayCollection> list = TrackPlayCollection.find(
				"userId=? and trackId=?", userId, trackId).fetch();
		if (list.size() == 0) {
			TrackPlayCollection.addTrackPlayRecord(userId, trackId);
		}
	}
	
	/**批量添加到收藏
	 * @param userId
	 * @param trackId
	 */
	public static void batchAddtoCollection(String userId, String []trackId){
		for(int i=0;i<trackId.length;i++){
			List<TrackPlayCollection> list = TrackPlayCollection.find(
					"userId=? and trackId=?", userId, trackId[i]).fetch();
			if (list.size() == 0) {
				TrackPlayCollection.addTrackPlayRecord(userId, trackId[i]);
			}
		}
	}
	
	/**
	 * 获得收藏列表歌曲的接口
	 * 
	 * @param userId
	 */
	public static void getCollection(String userId) {
		List<TrackPlayCollection> list = TrackPlayCollection.find("userId",
				userId).fetch();
		List<Track> list2 = new ArrayList<Track>();
		Iterator<TrackPlayCollection> it = list.iterator();
		while (it.hasNext()) {
			list2.add(Track.findByTrackId(it.next().trackId));
		}
		List<TrackBean> list3 = TrackBean.buildList(list2);
		wsOk(list3);
	}

	/**
	 * 将曲目从收藏列表中删除
	 * 
	 * @param userId
	 * @param trackId
	 */
	public static void deleteFromCollection(String userId, String trackId) {
		TrackPlayCollection tr = TrackPlayCollection.findByUserIdAndTrackId(
				userId, trackId);
		if (tr != null) {
			tr.delete();
		}
		getCollection(userId);
	}

	/**批量从收藏列表删除
	 * @param userId
	 * @param trackId
	 */
	public static void batchDelFromCollection(String userId, String[] trackId) {
		for (int i = 0; i < trackId.length; i++) {
			TrackPlayCollection tr = TrackPlayCollection
					.findByUserIdAndTrackId(userId, trackId[i]);
			if (tr != null) {
				tr.delete();
			}
		}
		getCollection(userId);
	}

}
