package models;

import java.util.List;

import beans.PageBean;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 歌手申请
 *
 * @author chao
 * @since 7/11/14
 */
@Entity
@Table(name = "musician_request")
public class MusicianRequest extends BasicModel {

    public static final int STATUS_NEW = 0;
    public static final int STATUS_ACCEPT = 1;
    public static final int STATUS_REJECT = 2;

    /**
     * 申请人
     */
    @Column(name="user_id")
    public String userId;

    /**
     * 申请说明
     */
    @Lob
    @Column(name="content")
    public String content;

    /**
     * 申请状态
     */
    @Column(name="status")
    public int status;
    
    public static List<MusicianRequest> findByStatus(int stauts, PageBean pageBean){
    	
    	return MusicianRequest.find("status = ?", stauts).fetch(pageBean.getCurPage(), pageBean.getPerPage());
    
    }
    /**
     * 根据状态取总数
     */
    public static long countBystatus(int stauts){
    	
    	return MusicianRequest.count("status = ?", stauts);
    	
    }
    
    public static long countByNewstatus(){
    	
    	return MusicianRequest.count("status = ?", MusicianRequest.STATUS_NEW);
    	
    }
    
    public static long countByAcceptstatus(){
    	
    	return MusicianRequest.count("status = ?", MusicianRequest.STATUS_ACCEPT);
    	
    }

	public static long countByRejectstatus(){
	
		return MusicianRequest.count("status = ?", MusicianRequest.STATUS_REJECT);
	
	}
	
	public static void deleteAllByStatus(int status){
		
		MusicianRequest.delete("status = ", status);
		
	}
	
	public static User findUserByUserid(String userId){
		
		return User.findById(userId);
	}
	
	public static void addMusicianRequest(String userId, String content){
		
		MusicianRequest musicianRequest = new MusicianRequest();
		musicianRequest.userId = userId;
		musicianRequest.content = content;
		musicianRequest.status = MusicianRequest.STATUS_NEW; 
		musicianRequest.save();
	}
	
	public static List<MusicianRequest> findByUserId(String userId, PageBean pageBean){
    	
    	return MusicianRequest.find("user_id = ?", userId).fetch(pageBean.getCurPage(), pageBean.getPerPage());
    
    }
	
	public static long countByUserId(String userId){
		
		return MusicianRequest.count("user_id = ?", userId);
	
	}
  
}
