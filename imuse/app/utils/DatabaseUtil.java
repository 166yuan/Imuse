package utils;

import java.io.File;
import java.util.List;

import com.aliyun.openservices.oss.OSSClient;
import controllers.PlayLists;
import controllers.Tracks;
import models.Album;
import models.AlbumTrack;
import models.Band;
import models.BandRequest;
import models.Cover;
import models.HotList;
import models.LikeRecord;
import models.MusicianRequest;
import models.PhotoAlbum;
import models.PhotoAlbumTag;
import models.PlayList;
import models.TagRelation;
import models.Track;
import models.TrackPlayCollection;
import models.TrackPlayList;
import models.TrackPlayRecord;
import models.User;
import models.Video;
import play.Play;

public class DatabaseUtil {
	public static void init() {
		if (User.findById("1") != null) {
			return;
		}
		User u = new User();
		u.id = "1";
		u.role = User.ROLE_USER;
		u.email = "54574576@qq.com";
		u.nickname = "小胖墩ui";
		u.intro = "我是爱吃鬼";
		u.bandId = "1";
		u.save();
		User u1 = new User();
		u1.role = User.ROLE_USER;
		u1.email = "5457457456@qq.com";
		u1.nickname = "小胖墩2";
		u1.intro = "我323是爱吃鬼";
		u1.bandId = "1";
		u1.save();
		User u2 = new User();
		u2.id = "2";
		u2.role = User.ROLE_MUSICIAN;
		u2.email = "545745756@qq.com";
		u2.nickname = "小胖墩3";
		u2.intro = "我是爱232吃鬼";
		u2.bandId = "1";
		u2.save();
		User u3 = new User();
		u3.role = User.ROLE_MUSICIAN;
		u3.email = "5457454576@qq.com";
		u3.nickname = "小胖墩4";
		u3.intro = "我23是爱吃鬼";
		u3.bandId = "1";
		u3.save();
		User u4 = new User();
		u4.role = User.ROLE_USER;
		u4.email = "54574576@qq.com";
		u4.nickname = "小胖墩5";
		u4.intro = "我是32爱吃鬼";
		u4.bandId = "1";
		u4.save();
		User u5 = new User();
		u5.role = User.ROLE_USER;
		u5.email = "54574576@qq.com";
		u5.nickname = "小胖墩6";
		u5.intro = "33我是爱吃鬼";
		u5.bandId = "1";
		u5.save();
		User u6 = new User();
		u6.role = User.ROLE_USER;
		u6.email = "54574576@qq.com";
		u6.nickname = "小胖墩7";
		u6.intro = "我是爱吃鬼2";
		u6.save();
		Band b = new Band();
		b.id = "1";
		b.creatorId = u.id;
		b.intro = "乐队简介";
		b.name = "乐队名一";
		b.avatarId = "logo的URL";
		b.inviteCode = "乐队邀请码";
		b.save();
		Band b1 = new Band();
		b1.creatorId = u1.id;
		b1.intro = "乐队简介2";
		b1.name = "乐队名一2";
		b1.avatarId = "logo的URL";
		b1.inviteCode = "乐队邀请码2";
		b1.save();
		Band b3 = new Band();
		b3.creatorId = u3.id;
		b3.intro = "乐队简介3";
		b3.name = "乐队名一3";
		b3.avatarId = "logo的URL3";
		b3.inviteCode = "乐队邀请码3";
		b3.save();
		Band b2 = new Band();
		b2.creatorId = u2.id;
		b2.intro = "乐队简介2";
		b2.name = "乐队名一2";
		b2.avatarId = "logo的URL2";
		b2.inviteCode = "乐队邀请码2";
		b2.save();
		Band b4 = new Band();
		b4.creatorId = u4.id;
		b4.intro = "乐队简介4";
		b4.name = "乐队名一4";
		b4.avatarId = "logo的URL4";
		b4.inviteCode = "乐队邀请码4";
		b4.save();
		for (int a = 0; a < 10; a++) {

			MusicianRequest q = new MusicianRequest();
			q.userId = u.id;
			q.content = "第" + a + "申请";
			q.status = q.STATUS_NEW;
			q.save();
			MusicianRequest w = new MusicianRequest();
			w.userId = u6.id;
			w.content = "第" + a + "申请";
			w.status = q.STATUS_ACCEPT;
			w.save();
			MusicianRequest e = new MusicianRequest();
			e.userId = u.id;
			e.content = "第" + a + "申请";
			e.status = q.STATUS_REJECT;
			e.save();

		}
		for (int a = 0; a < 10; a++) {

			MusicianRequest q = new MusicianRequest();
			q.userId = u2.id;
			q.content = "第" + a + "申请";
			q.status = q.STATUS_REJECT;
			q.save();
			MusicianRequest w = new MusicianRequest();
			w.userId = u4.id;
			w.content = "第" + a + "申请";
			w.status = q.STATUS_NEW;
			w.save();
			MusicianRequest e = new MusicianRequest();
			e.userId = u3.id;
			e.content = "第" + a + "申请";
			e.status = q.STATUS_ACCEPT;
			e.save();

		}

		for (int a = 0; a < 10; a++) {

			BandRequest q = new BandRequest();
			q.userId = u.id;
			q.content = "第" + a + "申请";
			q.status = q.STATUS_NEW;
			q.bandName = "弟" + a + "乐队";
			q.save();
			BandRequest q1 = new BandRequest();
			q1.userId = u6.id;
			q1.content = "第" + a + "申请";
			q1.status = q1.STATUS_ACCEPT;
			q1.bandName = "弟" + a + "乐队";
			q1.save();
			BandRequest q2 = new BandRequest();
			q2.userId = u2.id;
			q2.content = "第" + a + "申请";
			q2.status = q2.STATUS_REJECT;
			q2.bandName = "弟" + a + "乐队";
			q2.save();
		}
		for (int a = 0; a < 10; a++) {

			Album au = new Album();
			au.id = "album" + a;
			au.ownerId = u.id;
			au.author = "第" + a + "个作者";
			au.copyright = "ay";
			au.name = "第" + a + "个专辑";
			au.type = 1;
			au.note = "第" + a + "56565";
			au.save();

			Album au1 = new Album();
			au1.id = "2" + a;
			au1.ownerId = u2.id;
			au1.author = "第" + a + "个作者";
			au1.copyright = "noCopy";
			au1.name = "第" + a + "个专辑";
			au1.type = 0;
			au1.note = "第" + a + "787878";
			au1.save();
			TagRelation t = new TagRelation();
			t.ownerId = au.id;
			t.tagId = "标签id";
			t.type = 1;
			t.save();
			Cover c = new Cover();
			c.ownerId = au.id;
			c.type = c.TYPE_ALBUM;
			c.weight = a;
			c.attachId = a + "附件id";
			c.save();
			Cover cw = new Cover();
			cw.ownerId = au1.id;
			cw.type = 0;
			cw.weight = a;
			cw.attachId = a + "附件id";
			cw.save();

			for (int as = 0; as < 10; as++) {

				Track mu = new Track();
				mu.id = as + "trsdacjk" + a;
				mu.ownerId = b.id;
				mu.author = as + "作者";
				mu.copyright = "CopyAllow";
				mu.name = as + "歌曲";
				mu.type = 1;
				mu.note = "sd" + as;
				mu.audioFile = "/res/test.mp3";
				mu.releaseFile = "/res/sound.ogg";
				mu.lyricFile = "歌词路径" + as;
				mu.sourceFile = as + "源文件";
				mu.duration = 0;
				mu.save();
				Cover c1 = new Cover();
				c1.ownerId = mu.id;
				c1.type = c1.TYPE_TRACK;
				c1.weight = a;
				c1.attachId = a + "附件id";
				c1.save();
				AlbumTrack i = new AlbumTrack();
				i.weight = a;
				i.albumId = au.id;
				i.trackerId = mu.id;
				i.save();
				AlbumTrack i2 = new AlbumTrack();
				i2.weight = a;
				i2.albumId = au1.id;
				i2.trackerId = mu.id;
				i2.save();
				Cover cs = new Cover();
				cs.ownerId = au.id;
				cs.type = mu.type;
				cs.weight = a;
				cs.attachId = a + "附件id";
				cs.save();
				LikeRecord lr = new LikeRecord();
				lr.trackId = mu.id;
				lr.userId = u.id;
				lr.save();
			}
			for (int as = 0; as < 10; as++) {

				Track mu = new Track();
				mu.id = a + "track" + as;
				mu.ownerId = u.id;
				mu.author = as + "作者";
				mu.copyright = "CopyAllow";
				mu.name = as + "歌曲";
				mu.type = 0;
				mu.note = "sd" + as;
				mu.audioFile = "/res/test.mp3";
				mu.lyricFile = "/res/sound.ogg";
				mu.sourceFile = as + "源文件";
				mu.duration = 0;
				mu.save();

				AlbumTrack i = new AlbumTrack();
				i.weight = a;
				i.albumId = au1.id;
				i.trackerId = mu.id;
				i.save();
				Cover cs = new Cover();
				cs.ownerId = au.id;
				cs.type = mu.type;
				cs.weight = a;
				cs.attachId = a + "附件id";
				cs.save();
				HotList hotlist = new HotList();
				hotlist.data = a;
				hotlist.type = 1;
				hotlist.ownerId = mu.id;
				hotlist.weight = a + "";
				hotlist.save();

				HotList hotlist1 = new HotList();
				hotlist1.data = a + 7878;
				hotlist1.type = 2;
				hotlist1.ownerId = mu.id;
				hotlist1.weight = a + "fsfd";
				hotlist1.save();

				HotList hotlist2 = new HotList();
				hotlist2.data = a + 65678;
				hotlist2.type = 0;
				hotlist2.ownerId = mu.id;
				hotlist2.weight = "sdsd" + a + "fsfd";
				hotlist2.save();
				LikeRecord lr = new LikeRecord();
				lr.trackId = mu.id;
				lr.userId = u1.id;
				lr.save();
			}
		}

		for (int as = 0; as < 10; as++) {

			Track mu = new Track();
			mu.ownerId = u.id;
			mu.author = as + "作者";
			mu.copyright = "CopyAllow";
			mu.name = as + "歌曲";
			mu.type = 0;
			mu.note = "sd" + as;
			mu.audioFile = "/res/test.mp3";
			mu.lyricFile = "/res/sound.ogg";
			mu.sourceFile = as + "源文件";
			mu.duration = 0;
			mu.save();
			Track mu2 = new Track();
			mu2.ownerId = b.id;
			mu2.author = as + "作者";
			mu2.copyright = "CopyAllow";
			mu2.name = as + "歌曲";
			mu2.type = 1;
			mu2.note = "sd" + as;
			mu2.audioFile = "文件路径" + as;
			mu2.lyricFile = "歌词路径" + as;
			mu2.sourceFile = as + "源文件";
			mu2.duration = 0;
			mu2.save();
			TagRelation t = new TagRelation();
			t.ownerId = mu.id;
			t.tagId = "标签id";
			t.type = 0;
			t.save();
			AlbumTrack i = new AlbumTrack();
			i.weight = as;
			i.albumId = u1.id;
			i.trackerId = mu.id;
			i.save();
			HotList hotlist = new HotList();
			hotlist.data = as;
			hotlist.type = 1;
			hotlist.ownerId = mu.id;
			hotlist.weight = as + "";
			hotlist.save();
			Cover c = new Cover();
			c.ownerId = mu.id;
			c.type = c.TYPE_TRACK;
			c.weight = as;
			c.attachId = as + "附件id";
			c.save();
		}

		for (int a = 0; a < 10; a++) {
			PhotoAlbumTag pu = new PhotoAlbumTag();
			pu.id = "pu" + a;
			pu.name = a + "乐队album";
			pu.ownerId = b.id;
			pu.type = PhotoAlbum.TYPE_BAND;
			pu.save();

			for (int s = 0; s < 10; s++) {
				PhotoAlbum p = new PhotoAlbum();
				p.tagId = pu.id;
				p.ownerId = pu.id;
				p.photo = s + "图片" + a;
				p.type = PhotoAlbum.TYPE_BAND;
				p.save();
			}
			u.save();
			PhotoAlbumTag pu1 = new PhotoAlbumTag();
			pu1.name = a + "乐队album";
			pu1.ownerId = u.id;
			pu1.type = PhotoAlbum.TYPE_USER;
			pu1.save();

			for (int s = 0; s < 10; s++) {
				PhotoAlbum p = new PhotoAlbum();
				p.tagId = pu1.id;
				p.ownerId = pu1.id;
				p.photo = s + "图片" + a;
				p.type = PhotoAlbum.TYPE_USER;
				p.save();
			}
			u.save();
		}
		Track mu = new Track();
		mu.ownerId = u3.id;
		mu.author = "author1";
		mu.copyright = "CopyAllow";
		mu.name = "songbook1";
		mu.type = 0;
		mu.note = "sd1";
		mu.audioFile = "/res/test.mp3";
		mu.lyricFile = "none";
		mu.sourceFile = "/res/test.mp3";
		mu.duration = 223;
		mu.save();

		Track mu2 = new Track();
		mu2.ownerId = b.id;
		mu2.author = "author2";
		mu2.copyright = "CopyAllow";
		mu2.name = "songbook2";
		mu2.type = 1;
		mu2.note = "sd2";
		mu2.audioFile = "";
		mu2.releaseFile = "/res/sound.ogg";
		mu2.lyricFile = "none";
		mu2.sourceFile = "/res/sound.ogg";
		mu2.duration = 31;
		mu2.save();
		for (int a = 0; a < 10; a++) {
			PlayList list = new PlayList();
			list.name = "mylist" + a;
			list.ownerId = u.id;
			list.save();
			TrackPlayList trackPlayList = new TrackPlayList();
			trackPlayList.weight = 0;
			trackPlayList.trackId = mu.id;
			trackPlayList.playListId = list.id;
			trackPlayList.save();
		}
		for (int a = 0; a < 10; a++) {
			PlayList list = new PlayList();
			list.name = "mylist" + a;
			list.ownerId = u1.id;
			list.save();
			TrackPlayList trackPlayList = new TrackPlayList();
			trackPlayList.weight = 0;
			trackPlayList.trackId = mu.id;
			trackPlayList.playListId = list.id;
			trackPlayList.save();
		}
		for (int a = 0; a < 10; a++) {
			PlayList list = new PlayList();
			list.name = "mylist" + a;
			list.ownerId = u2.id;
			list.save();
			TrackPlayList trackPlayList = new TrackPlayList();
			trackPlayList.weight = 0;
			trackPlayList.trackId = mu.id;
			trackPlayList.playListId = list.id;
			trackPlayList.save();
		}
		for (int a = 0; a < 10; a++) {
			PlayList list = new PlayList();
			list.name = "mylist" + a;
			list.ownerId = u3.id;
			list.save();
			TrackPlayList trackPlayList = new TrackPlayList();
			trackPlayList.weight = 0;
			trackPlayList.trackId = mu.id;
			trackPlayList.playListId = list.id;
			trackPlayList.save();
		}
		for (int a = 0; a < 10; a++) {
			PlayList list = new PlayList();
			list.name = "mylist" + a;
			list.ownerId = u4.id;
			list.save();
			TrackPlayList trackPlayList = new TrackPlayList();
			trackPlayList.weight = 0;
			trackPlayList.trackId = mu.id;
			trackPlayList.playListId = list.id;
			trackPlayList.save();
		}
		TrackPlayCollection tpc = new TrackPlayCollection();
		tpc.trackId = mu.id;
		tpc.userId = u.id;
		tpc.save();
		TrackPlayCollection tpc2 = new TrackPlayCollection();
		tpc2.trackId = mu2.id;
		tpc2.userId = u.id;
		tpc2.save();
		TrackPlayCollection tpc3 = new TrackPlayCollection();
		tpc3.trackId = mu.id;
		tpc3.userId = u1.id;
		tpc3.save();
		TrackPlayCollection tpc4 = new TrackPlayCollection();
		tpc4.trackId = mu2.id;
		tpc4.userId = u1.id;
		tpc4.save();

		TrackPlayRecord tpr = new TrackPlayRecord();
		tpr.trackId = "1track8";
		tpr.userId = u.id;
		tpr.save();
		TrackPlayRecord tpr2 = new TrackPlayRecord();
		tpr2.trackId = "1track8";
		tpr2.userId = u.id;
		tpr2.save();
		TrackPlayRecord tpr3 = new TrackPlayRecord();
		tpr3.trackId = "1track7";
		tpr3.userId = u1.id;
		tpr3.save();
		TrackPlayRecord tpr4 = new TrackPlayRecord();
		tpr4.trackId = "1track7";
		tpr4.userId = u1.id;
		tpr4.save();
		
		Video v=new Video();
		v.clientId="101a44fe680019bc";
		v.userId=u.id;
		v.vid="XNzM0MTI2NDM2";
		v.title="万1";
		v.save();
		Video v2=new Video();
		v2.clientId="101a44fe680019bc";
		v2.userId=u.id;
		v2.vid="XNzQwNjExOTIw";
		v2.title="万2";
		v2.save();
		Video v3=new Video();
		v3.clientId="101a44fe680019bc";
		v3.userId=u.id;
		v3.title="万3";
		v3.vid="XNzQ0MDk5NzY4";
		v3.save();
	}

    public static  void checkOSS(){
        OSSClient client=OSSUtil.getOssClient();
        String bucketName= Play.configuration.getProperty("oss.bucketName");
        OSSUtil.ensureBucket(client,bucketName);

    }
}
