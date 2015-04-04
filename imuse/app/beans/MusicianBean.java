package beans;

import java.util.List;

import models.Album;
import models.Band;
import models.Track;
import models.User;

public class MusicianBean {
	public String nickname;

    public String avatarUrl;

    public int role;

    public String intro;
    
    public List<TrackBean> trackList;
    
    public List<AlbumBean> albumList;
    
    public BandBean band;
 
    public static MusicianBean build(User user){
        MusicianBean bean = new MusicianBean();
        bean.nickname = user.nickname;
        bean.role = user.role;
        bean.intro = user.intro;
        Band band = Band.findById(user.id);
        bean.band = BandBean.build(band);
        PageBean pageBean = PageBean.getInstance(0, 10);
        bean.trackList = TrackBean.buildList(Track.findByUserIdAndType(user.id, Track.TYPE_USER, pageBean));
        bean.albumList = AlbumBean.buildList(Album.findByOwnerIdAndType(user.id, pageBean, Album.TYPE_USER));
        /*bean.avatarUrl = user.getAvatar().getUrl();*/
        return bean;
    }
}
