package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sudocn.play.XJavaExtension;

import models.Album;
import models.AlbumTrack;
import models.Cover;

/**
 * @author chao
 * @since 7/11/14
 */
public class AlbumBean {

	public String name;

	public String author;

	public String note;
	
	public long createTime;

	public String createTimeDesc;
	
	public String ownerId;
	
	public String albumId;
	
	public int type;
	
	public TrackBean track;
	
    public List<CoverBean> coverList;
    public static AlbumBean build(Album album){
        AlbumBean bean = new AlbumBean();
        bean.ownerId = album.ownerId;
        bean.albumId=album.id;
        bean.name = album.name;
        bean.author = album.author;
        bean.note = album.note;
        bean.createTime = album.createTime;
        bean.createTimeDesc = XJavaExtension.simpleDateTime(new Date(album.createTime));
        bean.type = album.type;
        bean.coverList = CoverBean.buildList(Cover.findCoverByOwnerAndType(album.id, album.type));
        bean.track = TrackBean.build(AlbumTrack.findFirst(album.id).getTrack());
        return bean;
    }
    
    public static List<AlbumBean> buildList(List<Album> albums){
    	
    	List<AlbumBean> albumBeanList = new ArrayList<AlbumBean>();
    	for(int item = 0; item <albums.size();item ++){
    		Album album = albums.get(item);
    		AlbumBean ab = build(album);
    		albumBeanList.add(ab);
    		
    	}
    	return albumBeanList;
    }
}
