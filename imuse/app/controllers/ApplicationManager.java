package controllers;


import beans.PageBean;
import models.Band;
import models.BandRequest;
import models.MusicianRequest;
import models.User;

import org.scauhci.common.util.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import play.mvc.Controller;
import utils.MessageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 申请管理
 *
 * @author 谢易成
 */
public class ApplicationManager extends Controller {
	
	
	
	
	
	/**
	 * 用户列表
	 */
	public static void userList(int page){
		long size = User.count();
		PageBean pageBean = PageBean.getInstance(page, size);
		List<User> userList = User.findByPageBean(pageBean);
		render("@showuserlist",userList,pageBean);
	}
	/**
	 * 重置密码页面
	 */
	public static void reSetPassWord(String id){
		render("@resetpassword",id);
	}
	/**
	 * 设置密码
	 */
	public static void setPassword(String id,String password,String vaildword){
		if(!StringUtil.isEmpty(vaildword)&&!StringUtil.isEmpty(password)&&!StringUtil.isEmpty(id)){
			if(password.equals(vaildword)){
				User user = User.findById(id);
				if(user != null){
					user.passwd = password;
					user.save();
					MessageUtil.generateInfoMsg("设置成功");
					userList(0);
				}
				else{
					MessageUtil.generateErrorMsg("用户不存在");
					reSetPassWord(id);
				}
			}
			else{
				MessageUtil.generateErrorMsg("两次输入不一致");
				reSetPassWord(id);
			}
		}
		else{
			MessageUtil.generateErrorMsg("请输入数据");
			reSetPassWord(id);
		}
	
		
	}
    /**
     * 歌手申请列表
     */
    public static void getNewMusicianRequest(int page) {
        long size = MusicianRequest.countByNewstatus();
        PageBean pageBean = PageBean.getInstance(page, size);
        List<MusicianRequest> requestlist = MusicianRequest.findByStatus(MusicianRequest.STATUS_NEW, pageBean);
        render("@newmusician", requestlist, pageBean);

    }

    /**
     * 已同意歌手申请列表
     */
    public static void getAcceptMusicianRequest(int page) {
        long size = MusicianRequest.countByAcceptstatus();
        PageBean pageBean = PageBean.getInstance(page, size);
        List<MusicianRequest> requestlist = MusicianRequest.findByStatus(MusicianRequest.STATUS_ACCEPT, pageBean);
        render("@acceptmusician", requestlist, MusicianRequest.STATUS_ACCEPT, pageBean);

    }

    /**
     * 已拒绝歌手申请列表
     */
    public static void getRejectMusicianRequest(int page) {
        long size = MusicianRequest.countByRejectstatus();
        PageBean pageBean = PageBean.getInstance(page, size);
        List<MusicianRequest> requestlist = MusicianRequest.findByStatus(MusicianRequest.STATUS_REJECT, pageBean);
        render("@rejectmusician", requestlist, MusicianRequest.STATUS_REJECT, pageBean);
    }

    /**
     * 同意歌手申请
     */
    public static void acceptMusician(String id, int page, int status) {
        MusicianRequest musicrequest = MusicianRequest.findById(id);
        if (musicrequest != null && musicrequest.status != MusicianRequest.STATUS_ACCEPT) {
            User user = User.findById(musicrequest.userId);
            user.role = User.ROLE_MUSICIAN;
            user.save();
            setMusicianRequestStatus(musicrequest, MusicianRequest.STATUS_ACCEPT, page);
        } else {
            MessageUtil.generateErrorMsg("该请求已被处理或未处在");
            getAcceptMusicianRequest(page);
            // 屏幕提示
        }
    }

    /**
     * 拒绝歌手申请
     */
    public static void rejectMusician(String id, int page, int status) {
        MusicianRequest musicrequest = MusicianRequest.findById(id);
        if (musicrequest != null  && musicrequest.status != MusicianRequest.STATUS_REJECT) {
            setMusicianRequestStatus(musicrequest, MusicianRequest.STATUS_REJECT, page);
        } else {
            MessageUtil.generateErrorMsg("该请求已被处理或未处在");
            getRejectMusicianRequest(page);
            // 屏幕提示
        }
    }

    /**
     * 重置歌手申请
     */
    public static void resetMusician(String id, int page, int status) {
        MusicianRequest musicrequest = MusicianRequest.findById(id);
        if (musicrequest != null && musicrequest.status != MusicianRequest.STATUS_NEW) {
            setMusicianRequestStatus(musicrequest, MusicianRequest.STATUS_NEW, page);
        } else {
            MessageUtil.generateErrorMsg("该请求已被处理或未处在");
            getRejectMusicianRequest(page);
            // 屏幕提示
        }
    }

    static void setMusicianRequestStatus(MusicianRequest musicrequest, int status, int page){
        musicrequest.status = status;
        musicrequest.save();
        MessageUtil.generateInfoMsg("处理成功");
        if (status == MusicianRequest.STATUS_ACCEPT) {
            getAcceptMusicianRequest(page);
        } else if (status == MusicianRequest.STATUS_NEW) {
            getNewMusicianRequest(page);
        } else if (status == MusicianRequest.STATUS_REJECT) {
            getRejectMusicianRequest(page);
        } else {
            notFound();
        }
    }
    
    static void setBandRequestStatus(BandRequest bandrequest, int status, int page){
        bandrequest.status = status;
        bandrequest.save();
        MessageUtil.generateInfoMsg("处理成功");
        if (status == MusicianRequest.STATUS_ACCEPT) {
           getAcceptBandRequest(page);
        } else if (status == MusicianRequest.STATUS_NEW) {
            getNewBandRequest(page);
        } else if (status == MusicianRequest.STATUS_REJECT) {
            getRejectBandRequest(page);
        } else {
            notFound();
        }
    }
    
    static void reDirectBandRequest( int status, int page){
  
        if (status == MusicianRequest.STATUS_ACCEPT) {
           getAcceptBandRequest(page);
        } else if (status == MusicianRequest.STATUS_NEW) {
            getNewBandRequest(page);
        } else if (status == MusicianRequest.STATUS_REJECT) {
            getRejectBandRequest(page);
        } else {
            notFound();
        }
    }

    /**
     * 乐队申请列表
     */
    public static void getNewBandRequest(int page) {
        long size = BandRequest.countByNewStatus();
        PageBean pageBean = PageBean.getInstance(page, size);
        List<BandRequest> requestlist = BandRequest.findByStatus(BandRequest.STATUS_NEW, pageBean);
        render("@newband", requestlist, pageBean);

    }

    /**
     * 已同意乐队申请列表
     */
    public static void getAcceptBandRequest(int page) {
        long size = BandRequest.countByAcceptStatus();
        PageBean pageBean = PageBean.getInstance(page, size);
        List<BandRequest> requestlist = BandRequest.findByStatus(BandRequest.STATUS_ACCEPT, pageBean);
        render("@acceptband", requestlist, pageBean);

    }

    /**
     * 已拒绝乐队申请列表
     */
    public static void getRejectBandRequest(int page) {
        long size = BandRequest.countByRejectStatus();
        PageBean pageBean = PageBean.getInstance(page, size);
        List<BandRequest> requestlist = BandRequest.findByStatus(BandRequest.STATUS_REJECT, pageBean);
        render("@rejectband", requestlist, pageBean);

    }

    /**
     * 同意乐队申请
     */
    public static void acceptBandRequest(String id, int page, int status) {
        BandRequest bandrequest = null;
        bandrequest = BandRequest.findById(id);
        if (bandrequest != null) {
            Band band = Band.findByCreator(bandrequest.userId);
            if (band != null) {
                MessageUtil.generateErrorMsg("该用户已创建乐队");
                reDirectBandRequest(status,page);
                // 提示出错
            } else {
            	setBandRequestStatus(bandrequest,BandRequest.STATUS_ACCEPT,page);

            }
        } else {
            MessageUtil.generateErrorMsg("该请求不存在");
            reDirectBandRequest(status,page);
            // 屏幕提示
        }
    }

    /**
     * 拒绝乐队申请
     */
    public static void rejectBandRequest(String id, int page, int status) {
        BandRequest bandrequest = null;
        bandrequest = BandRequest.findById(id);
        if (bandrequest != null) {
            Band band = Band.findByCreator(bandrequest.userId);
            if (band != null) {
                MessageUtil.generateErrorMsg("该用户已创建乐队");
                reDirectBandRequest(status,page);
                // 提示出错
            } else {
            	setBandRequestStatus(bandrequest,BandRequest.STATUS_REJECT,page);
                // 渲染回去页面
            }
        } else {
            MessageUtil.generateInfoMsg("该请求不存在");
            reDirectBandRequest(status,page);
            // 屏幕提示
        }
    }

    /**
     * 重置乐队申请
     */
    public static void BandResetStatus(String id, int page, int status) {
        BandRequest bandrequest = BandRequest.findById(id);
        if (bandrequest != null && bandrequest.status != BandRequest.STATUS_NEW) {
        	setBandRequestStatus(bandrequest,BandRequest.STATUS_NEW,page);

        } else {
            MessageUtil.generateInfoMsg("该请求已被处理或不处在");
            reDirectBandRequest(status,page);

        }

    }

    /**
     * 设置歌手
     */
    public static void setMusician(String id) {
        User user = User.findById(id);
        if (user != null) {
            if (user.role != User.ROLE_MUSICIAN) {
                user.role = User.ROLE_MUSICIAN;
                user.save();
                MessageUtil.generateInfoMsg("设置成功");
                getIsnotMusician();
            } else {
                MessageUtil.generateErrorMsg("该用户已经是歌手");
                getIsnotMusician();
            }
        } else {
            MessageUtil.generateErrorMsg("该用户不存在");
            getIsnotMusician();
        }

    }

    /**
     * 设置歌手视图
     */
    public static void getIsnotMusician() {
        List<User> userList = User.getTenSampleByrole(User.ROLE_USER);
        render("@userlist", userList);
    }

    /**
     * 搜索歌手
     */
    public static void searchUser(String nickname) {
        List<User> userList = new ArrayList<User>();
        if (StringUtil.isNotEmpty(nickname)) {
            userList.addAll(User.search(nickname));
        } else {
            MessageUtil.generateErrorMsg("请输入数据");
        }
        render("@userlist", userList);
    }
    
    public static void searchUserForUserlist(String nickname) {
        List<User> userList = new ArrayList<User>();
        if (StringUtil.isNotEmpty(nickname)) {
            userList.addAll(User.search(nickname));
        } else {
            MessageUtil.generateErrorMsg("请输入数据");
        }
  
       PageBean pageBean = PageBean.getInstance(0, 1);
        render("@showuserlist", userList, pageBean);
    }

    /**
     * 设置乐队视图
     */
    public static void setBand() {
        render();
    }

    /**
     * 设置乐队
     */
    public static void setBandAction(String email, String bandname, String bandinfo,File file) {
        if (StringUtil.isNotEmpty(email) && StringUtil.isNotEmpty(bandname) && StringUtil.isNotEmpty(bandinfo)) {
            User user = User.findByEmail(email);
            if (user != null) {
                Band band = Band.findByCreator(user.id);
                if (band != null) {
                    MessageUtil.generateErrorMsg("该用户已有乐队");
                    setBand();
                } else {
                    Band newband = new Band();
                    newband.creatorId = user.id;
                    newband.name = bandname;
                    newband.intro = bandinfo;
                    newband.avatarId=copy(file, new File("public/images/"));
                    newband.save();
                    MessageUtil.generateInfoMsg("设置成功");
                    setBand();
                }
            } else {
                MessageUtil.generateErrorMsg("该用户不存在");
                setBand();
            }
        } else {
            MessageUtil.generateErrorMsg("请输入数据");
            setBand();
        }

    }
	/**
	 * 功能描述：拷贝一个目录或者文件到指定路径下，即把源文件拷贝到目标文件路径下
	 * 
	 * @param source
	 *            源文件
	 * @param target
	 *            目标文件路径
	 * @return void
	 */
	static String copy(File source, File target) {
		File tarpath = new File(target, source.getName());
		if (source.isDirectory()) {
			tarpath.mkdir();
			File[] dir = source.listFiles();
			for (int i = 0; i < dir.length; i++) {
				copy(dir[i], tarpath);
			}
		} else {
			try {
				InputStream is = new FileInputStream(source); // 用于读取文件的原始字节流
				OutputStream os = new FileOutputStream(tarpath); // 用于写入文件的原始字节的流
				byte[] buf = new byte[1024];// 存储读取数据的缓冲区大小
				int len = 0;
				while ((len = is.read(buf)) != -1) {
					os.write(buf, 0, len);
				}
				is.close();
				os.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return tarpath.getPath();
	}
    
    
    
}