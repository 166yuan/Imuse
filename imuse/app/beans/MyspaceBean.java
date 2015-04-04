package beans;

import models.*;

import java.util.List;
/**
 * Created by Administrator on 2014/12/3.
 */
public class MyspaceBean {
    public String userId;
    public String nickName;
    public String intro;
    public String avatarId;
    public Band band;
    public List<Track> tracksList;
    public List<Album> albumList;
    public List<HotList>hotLists;
    public List<Video>videoList;
    public void buildList(String userId){
        this.userId=userId;
        User user= User.findById(userId);
        nickName=user.nickname;
        avatarId=user.avatarId;
        intro=user.intro;
        tracksList=Track.find("ownerId=?",userId).fetch(0,3);
        albumList=Album.find("ownerId=?",userId).fetch(0,5);
        band=Band.findById(user.bandId);
    }

}
