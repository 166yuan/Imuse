package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import beans.AlbumBean;
import beans.PageBean;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 曲目
 *
 * @author chao
 * @since 7/10/14
 */
@Entity
@Table(name = "track")
public class Track extends BasicModel {

    public static final int TYPE_USER = 0;
    public static final int TYPE_BAND = 1;
    
    @Column(name="owner_id")
    public String ownerId;

    /**
     * 用户或乐队的
     */
    @Column(name="type")
    public int type;

    /**
     * mp3格式
     */
    @Column(name = "audio_file")
    public String audioFile;

    /**
     * ogg格式
     */
    @Column(name = "release_file")
    public String releaseFile;

    @Column(name = "name")
    public String name;

    @Column(name = "lyric_file")
    public String lyricFile;

    @Column(name = "source_file")
    public String sourceFile;

    @Column(name = "author")
    public String author;

    @Column(name = "copyright")
    public String copyright;
  
    @Column(name = "duration")
    public long duration;

    /**
     * 备注：JSON结构
     */
    @Lob
    @Column(name = "note")
    public String note;

    /**
     * 是否通过审查
     * 未通过审查的歌曲，只有所有人才能查看
     */
    @Column(name="audited")
    public boolean audited;

    /**
     * 未通过审查的原因，仅当audited = false是生效
     */
    @Column(name = "audit_reason")
    public String auditReason;
    
    /**
     * 根据用户id返回数据
     */
    public static List<Track> findByUserIdAndType(String id , int type ,PageBean pageBean){
    	
    	return Track.find("type = ? and ownerId = ? order by updateTime", type ,id).fetch(pageBean.getCurPage(),pageBean.getPerPage());
    			
    }
    
    
    /**
     * 返回userid拥有的歌曲数量
     */
    public static long countByUserIdAndType(String id , int type){
    	
    	return Track.count("ownerId = ? and type = ?", id , type);
    	
    }
    /**
     * 根据用户id返回数据返回全部歌曲
     */
    public static List<Track> findAllByUserId(String id){
    	return Track.find("ownerId = ?", id).fetch();
    }
    /**
     * 返回根据tagid和name取得的数据
     * @param tagId
     * @param name
     * @param type
     * @param pageBean
     * @return
     */

    public static  List<Track> findByTagIdAndName(String tagId,String name,int type,PageBean pageBean){
      name = "%"+name+"%";
      final String queryByTagIdAndNameHql = "select t from TagRelation tr, Track t where tr.tagId = ? and tr.type = ? and tr.ownerId = t.id and t.name like ?";
      return find(queryByTagIdAndNameHql, tagId,type,name).fetch(pageBean.getCurPage(),pageBean.getPerPage());
    }
    /**
     * 返回根据tagid和name取得数据的个数
     */
    public static long countByTagIdAndName(String tagId,String name,int type){
      name = "%"+name+"%";
      final String countByTagIdAndNameHql = "select count(t) from TagRelation tr, Track t where tr.tagId = ? and tr.type = ? and tr.ownerId = t.id and t.name like ?";
      return count(countByTagIdAndNameHql, tagId,type,name);
    }
    
    public static Track findByTrackId(String trackId){
    	Track it= Track.findById(trackId);
    	return it;
    }
    /**
     * 删除歌曲
     * @param trackId
     */
    public static void deleteByTrackId(String trackId){
    	Track.delete("id = ?", trackId);
    	List<Cover> coverlist = Cover.findCoverByTrack(trackId);
    	for(int item = 0; item <coverlist.size();item ++){
    		Cover cover = coverlist.get(item);
    		cover.delete();
    	}
    }
    /**
     * 根据歌曲种类取歌曲
     * @param type
     * @param pageBean
     * @return
     */
    public static List<Track> findAllByTypeAndStatus(int type,PageBean pageBean,boolean status){
    	return Track.find("type = ? and audited = ?", type,status).fetch(pageBean.getCurPage(),pageBean.getPerPage());
    }
 
    public static long countByType(int type,boolean status){
    	
    	return Track.count("type = ? and audited = ?", type,status);
    	
    }
}
