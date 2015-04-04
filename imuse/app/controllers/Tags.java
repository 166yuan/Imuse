package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beans.BannerBean;
import beans.PageBean;
import beans.TagBean;
import beans.TrackBean;
import models.IndexBanner;
import models.Tag;
import models.Track;

/**
 * 曲目或者专辑的标签服务
 * 
 * @author chao
 * @since 7/17/14
 */
public class Tags extends WebService {

	/**
	 * 用户通过搜索来查找标签
	 * 
	 * @param query
	 */
	public static void findTags(String query) {
		// TODO 陈思远
		System.out.println("query:" + query);
		query = "%" + query + "%";
		List<Tag> taglist = Tag.find("name like ?", query).fetch();
		if (taglist.size() == 0) {
			wsError("对不起，找不到对应的标签，请检查拼写");
		} else {
			List<TagBean> bList = TagBean.buildList(taglist);
			wsOk(taglist);
		}
	}

	/**
	 * 通过标签名查找标签
	 * 
	 * @param tagName
	 */
	public static void getTagByName(String tagName) {
		// TODO 陈思远
		tagName = tagName + "%";
		List<Tag> taglist = Tag.find("name like ?", tagName).fetch();
		if (taglist.size() == 0) {
			wsError("对不起，找不到对应的标签，请检查拼写");
		} else {
			List<TagBean> bList = TagBean.buildList(taglist);
			wsOk(taglist);
		}
	}

	/**
	 * 通过标签名称来完全匹配查找歌曲
	 * 
	 * @param tagName
	 * @param page
	 */
	public static void listTracksByTagName(String tagName, int page) {
		// TODO 陈思远
		long size = Track.count("name= ?", tagName);
		PageBean pageBean = PageBean.getInstance(page, size);
		List<Track> tracklist2 = Track.find("name= ?", tagName).fetch(
				pageBean.getCurPage(), pageBean.getPerPage());
		if (tracklist2.size() == 0) {
			wsError("对不起，找不到您要的内容");
		} else {
			List<TrackBean> bList = TrackBean.buildList(tracklist2);
			wsOk(tracklist2);
		}
	}

}
