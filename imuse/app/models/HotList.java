package models;

import java.util.ArrayList;
import java.util.List;

import beans.TrackBean;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 榜单
 *
 * @author chao
 * @since 7/15/14
 */
@Entity
@Table(name = "hot_list")
public class HotList extends BasicModel {
	/**
	 * 日榜单
	 */
	public static final int TIMETYPE_DAY= 0;
	/**
	 * 周榜单
	 */
	public static final int TIMETYPE_WEEK = 1;
	/**
	 * 月榜单
	 */
	public static final int TIMETYPE_MOUTH = 2;

    /**
     * 热门单曲
     */
    public static final int TYPE_TRACK = 0;
    /**
     * 热门专辑
     */
    public static final int TYPE_ALBUM = 1;
    /**
     * 热门歌手
     */
    public static final int TYPE_MUSICIAN = 2;
    /**
     * 热门乐队
     */
    public static final int TYPE_BAND = 3;

    @Column(name = "owner_id")
    public String ownerId;

    @Column(name = "type")
    public int type;
    
    /**
     * 日 周 月的分类
     */
    @Column(name = "time_type")
    public int timeType;

    /**
     * 榜单权重
     */
    @Column(name = "weight")
    public String weight;

    /**
     * 排名数据，如点赞量、访问量
     */
    @Column(name = "data")
    public long data;
    
    /**
     * 根据type取数据
     */
    public static List<HotList> findByTypeAndTimeType(int type,int timetype){
    	return HotList.find("type = ? and timeType = ? order by weight", type , timetype).fetch(10);
    }
    /**
     * 返回Tracklist
     */
    public static List<TrackBean> getTrackBeanList(List<HotList> hotlist){
    	List<TrackBean>  trackBeanlist = new ArrayList<TrackBean>();
    	for(int item = 0;item <hotlist.size();item ++){
    		Track track = Track.findById(hotlist.get(item).ownerId);
    		TrackBean tb = TrackBean.build(track);
    		trackBeanlist.add(tb);
    	}
    	return trackBeanlist;
    }
}
