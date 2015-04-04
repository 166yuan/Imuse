package controllers;

import java.io.File;
import java.util.List;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.Bucket;
import com.aliyun.openservices.oss.model.OSSObjectSummary;
import com.aliyun.openservices.oss.model.ObjectListing;
import com.google.protobuf.UnknownFieldSet.Field;

import models.Album;
import models.AlbumTrack;
import models.Band;
import models.BandRequest;
import models.Cover;
import models.HomePage;
import models.HotList;
import models.MusicianRequest;
import models.OSSAttachment;
import models.PhotoAlbum;
import models.PhotoAlbumTag;
import models.Tag;
import models.TagRelation;
import models.Track;
import models.User;
import play.Play;
import play.mvc.Controller;
import play.mvc.Scope.Params;
import utils.DatabaseUtil;
import utils.OSSAttachments;
import utils.OSSUtil;
import utils.UploadUtil;
/**
 * @author chao
 * @since 7/14/14
 */
public class Dev extends Controller {
	public static void testindex() {
		render("/index_common/index.html");
	}

	public static void testsinger() {
		render("/singer_common/singer.html");
	}
	public static void uploadmusic(){
		render("/Application/upload.html");
	}
	public static void testRanklist() {
		render("/Ranklist/ranklist_index.html");
	}

	public static void testBand() {
		render("/band_common/band_index.html");
	}

	public static void dataBaseInit() {
		User user = User.findById("1");
		if (user != null) {
			return;
		}
		User u = new User();
		u.id = "1";
		u.role = User.ROLE_USER;
		u.email = "54574576@qq.com";
		u.nickname = "小胖墩ui";
		u.intro = "我是爱吃鬼";

		u.save();
		User u1 = new User();
		u1.role = User.ROLE_USER;
		u1.email = "5457457456@qq.com";
		u1.nickname = "小胖墩2";
		u1.intro = "我323是爱吃鬼";
		u1.save();
		User u2 = new User();
		u2.id = "2";
		u2.role = User.ROLE_MUSICIAN;
		u2.email = "545745756@qq.com";
		u2.nickname = "小胖墩3";
		u2.intro = "我是爱232吃鬼";
		u2.save();
		User u3 = new User();
		u3.role = User.ROLE_MUSICIAN;
		u3.email = "5457454576@qq.com";
		u3.nickname = "小胖墩4";
		u3.intro = "我23是爱吃鬼";
		u3.save();
		User u4 = new User();
		u4.role = User.ROLE_USER;
		u4.email = "54574576@qq.com";
		u4.nickname = "小胖墩5";
		u4.intro = "我是32爱吃鬼";
		u4.save();
		User u5 = new User();
		u5.role = User.ROLE_USER;
		u5.email = "54574576@qq.com";
		u5.nickname = "小胖墩6";
		u5.intro = "33我是爱吃鬼";
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
			au.copyright = "allowCopy";
			au.name = "第" + a + "个专辑";
			au.type = 0;
			au.note = "第" + a + "专辑简介";
			au.save();

			Album au1 = new Album();
			au1.id = "2" + a;
			au1.ownerId = u2.id;
			au1.author = "第" + a + "个作者";
			au1.copyright = "noCopy";
			au1.name = "第" + a + "个专辑";
			au1.type = 0;
			au1.note = "第" + a + "专辑简介";
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

			for (int as = 0; as < 10; as++) {

				Track mu = new Track();
				mu.id = as + "trsdacjk" + a;
				mu.ownerId = au1.id;
				mu.author = as + "作者";
				mu.copyright = "CopyAllow";
				mu.name = as + "歌曲";
				mu.type = 0;
				mu.note = "sd" + as;
				mu.audioFile = "文件路径" + as;
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
			}
			for (int as = 0; as < 10; as++) {

				Track mu = new Track();
				mu.id = a + "track" + as;
				mu.ownerId = au.id;
				mu.author = as + "作者";
				mu.copyright = "CopyAllow";
				mu.name = as + "歌曲";
				mu.type = 0;
				mu.note = "sd" + as;
				mu.audioFile = "文件路径" + as;
				mu.lyricFile = "歌词路径" + as;
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
				hotlist.timeType = 1;
				hotlist.save();

				HotList hotlist1 = new HotList();
				hotlist1.data = a + 7878;
				hotlist1.type = 1;
				hotlist1.ownerId = mu.id;
				hotlist1.weight = a + "fsfd";
				hotlist1.timeType = 0;
				hotlist1.save();

				HotList hotlist2 = new HotList();
				hotlist2.data = a + 65678;
				hotlist2.type = 0;
				hotlist2.ownerId = mu.id;
				hotlist2.weight = "sdsd" + a + "fsfd";
				hotlist2.timeType = 2;
				hotlist2.save();
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
			mu.audioFile = "文件路径" + as;
			mu.lyricFile = "歌词路径" + as;
			mu.sourceFile = as + "源文件";
			mu.duration = 0;
			mu.save();
			Track mu2 = new Track();
			mu2.ownerId = b.id;
			mu2.author = as + "作者";
			mu2.copyright = "CopyAllow";
			mu2.name = as + "歌曲";
			mu2.type = 0;
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
			hotlist.timeType = 2;
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

			for (int t = 0; t < 10; t++) {
				Tag tag = new Tag();
				tag.iconId = "id" + t;
				tag.name = t + "sd";
				tag.creatorId = u.id;
				tag.save();
			}
			for (int h = 0; h < 10; h++) {
				HomePage homePage = new HomePage();
				homePage.type = 1;
				homePage.backgroundImage = h + "/.jpg";
				homePage.ownerId = u.id;
				homePage.save();
			}

		}

	}

	/**
	 * http://localhost:9000/tracks/searchAlbum?tagId=%E6%A0%87%E7%AD%BEid&query
	 * =%E7%AC%AC&page=0
	 * http://localhost:9000/tracks/searchTrack?tagId=%E6%A0%87
	 * %E7%AD%BEid&query=%E6%AD%8C%E6%9B%B2&page=0
	 * http://localhost:9000/tracks/getTrackById?id=1track1
	 * http://localhost:9000/tracks/getTrackByOwnerId?id=21&curPage=0&type=0
	 * http://localhost:9000/albums/getAlbum?ownerId=1&curPage=0&type=1
	 * http://localhost:9000/albums/getAlbumTracks?albumId=21&curPage=0
	 * http://localhost:9000/HotLists/getHotListByType?type=1
	 * http://localhost:9000/Photos/getTags?ownerId=1&type=0
	 * http://localhost:9000/Photos/getPhotos?tagId=pu1
	 * http://localhost:9000/requests
	 * /createBandRequest?userId=www&content=ss&bandName=sww
	 * http://localhost:9000
	 * /requests/listBandRequest?userId=www&content=ss&bandName=sww
	 * http://localhost
	 * :9000/requests/createmusicianrequest?userId=www&content=ss
	 * http://localhost:9000/requests/getMusicianRequest?userId=www&content=ss
	 * http://localhost:9000/userwebservice/getUser?userId=1
	 * http://localhost:9000/userwebservice/searchMusician?query=%E5%B0%8F
	 * http://localhost:9000/bands/getBandByMusician?id=1
	 */
	public static void testupload() {
		String path = "E:/nothing.mp3";// please enter the path of your file
		File file=new File(path);								// here;
		String url = UploadUtil.upload(file, 1);
		System.out.println(url);
	}

	public static void listOject() {

        OSSClient client=OSSUtil.getOssClient();
		String name = Play.configuration.getProperty("oss.bucketName");
        System.out.println(name);
		ObjectListing listing = client.listObjects(name);
		
		// 遍历所有Object
		for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
			System.out.println(objectSummary.getKey());
		}
	}

    public static void getBucket(){
    OSSClient client=OSSUtil.getOssClient();
        OSSUtil.getBucket(client);
    }
	
}
