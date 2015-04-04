package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Album;
import models.AlbumTrack;
import models.User;

import org.scauhci.common.util.StringUtil;

import beans.AlbumBean;
import beans.AlbumTrackBean;
import beans.PageBean;
import play.mvc.Controller;

public class Albums extends WebService {

	/**
	 * 返回用户或者乐队专辑的数据 type值为1或者0指定类型
	 */
	public static void getAlbum(String ownerId, int curPage, int type) {

		if (StringUtil.isNotEmpty(ownerId)) {
			if (type == 0 || type == 1) {
				long size = Album.countByOwnerIdAndType(ownerId, type);
				PageBean pageBean = PageBean.getInstance(curPage, size);
				List<Album> albumList = Album.findByOwnerIdAndType(ownerId,
						pageBean, type);
				List<AlbumBean> albumBeanList = AlbumBean.buildList(albumList);

				wsOk(albumBeanList);
			} else {
				wsError("类型出错");
			}
		} else {
			wsError("找不到专辑");
		}

	}
	/**
	 * 
	 * @param albumId
	 * @param curPage
	 */
	public static void getAlbumById(String albumId) {
		if (StringUtil.isNotEmpty(albumId)) {
			Album als = Album.findById(albumId);
			AlbumBean albumBean = AlbumBean.build(als);
			wsOk(albumBean);
		} else {
			wsError("找不到数据");
		}

	}

	/**
	 * 返回专辑下的歌曲
	 */
	public static void getAlbumTracks(String albumId, int curPage) {
		if (StringUtil.isNotEmpty(albumId)) {
			AlbumTrack als = AlbumTrack.findFirst(albumId);
			AlbumTrackBean albumTrackBean = AlbumTrackBean.build(als);
			wsOk(albumTrackBean);
		} else {
			wsError("找不到数据");
		}

	}
	//localhost:9000/Albums/getAlbumTracks?albumId=album0&curPage=0
}
