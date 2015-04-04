package models;

import beans.PageBean;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * 专辑
 *
 * @author chao
 * @since 7/10/14
 */
@Entity
@Table(name = "album")
public class Album extends BasicModel {


    public static final int TYPE_USER = 0;
    public static final int TYPE_BAND = 1;

    /**
     * 用户或者乐队
     */
    @Column(name = "owner_id")
    public String ownerId;

    /**
     * 类型：用户、乐队
     */
    @Column(name = "type")
    public int type;

    /**
     * 曲目名称
     */
    @Column(name = "name")
    public String name;

    /**
     * 作者名字
     */
    @Column(name = "author")
    public String author;

    /**
     * 版权说明
     */
    @Lob
    @Column(name = "copyright")
    public String copyright;

    /**
     * 备注：JSON结构
     */
    @Lob
    @Column(name = "note")
    public String note;
    
    /**
     * 是否通过审查
     * 未通过审查的专辑，只有所有人才能查看
     */
    @Column(name="audited")
    public boolean audited;

    @Column(name="image_url")
    public String imageUrl;
    /**
     * 获取专辑下所有审核通过的歌曲
     * @return
     */
    public List<Track> getTracks() {
        List<AlbumTrack> ats = AlbumTrack.findByAlbum(id);
        List<Track> tracks = new ArrayList(ats.size());
        for (AlbumTrack at : ats) {
            Track t = at.getTrack();
            if (t.audited) {
                tracks.add(t);
            }
        }
        return tracks;
    }

    /**
     * 获取专辑下所有歌曲
     * @return
     */
    public List<Track> getTracksIncludeUnaudited() {
        List<AlbumTrack> ats = AlbumTrack.findByAlbum(id);
        List<Track> tracks = new ArrayList(ats.size());
        for (AlbumTrack at : ats) {
            Track t = at.getTrack();
            tracks.add(t);
        }
        return tracks;
    }
    
    /**
     * 根据用户id返回数据
     */
    public static List<Album> findByOwnerIdAndType(String id , PageBean pageBean, int type){
    	
    	return Album.find("ownerId = ? and type = ? ", id ,type).fetch(pageBean.getCurPage(),pageBean.getPerPage());
    			
    }
    
    /**
     * 返回ownerid拥有的专辑数量
     */
    public static long countByOwnerIdAndType(String id ,int type){
    	
    	return Album.count("ownerId = ? and type = ?", id , type);
    	
    }
    public static  List<Album> findByTagIdAndName(
    		String tagId,String name,int type,PageBean pageBean){
        name = "%"+name+"%";
        final String queryByTagIdAndNameHql =
        		"select t "
        		+ "from TagRelation tr, Album t "
        		+ "where tr.tagId = ?"
        		+ "  and tr.type = ?"
        		+ "  and tr.ownerId = t.id"
        		+ "  and t.name like ?";
        return find(queryByTagIdAndNameHql, tagId,type,name).
        		fetch(pageBean.getCurPage(),pageBean.getPerPage());
      }
      /**
       * 返回根据tagid和name取得数据的个数
       */
    public static long countByTagIdAndName(String tagId,String name,int type){
        name = "%"+name+"%";
        final String countByTagIdAndNameHql = "select count(t) from TagRelation tr, Album t where tr.tagId = ? and tr.type = ? and tr.ownerId = t.id and t.name like ?";
        return count(countByTagIdAndNameHql, tagId,type,name);
     }
}
