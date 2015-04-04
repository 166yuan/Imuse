package models;

import beans.PageBean;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import java.util.List;

/**
 * 乐队申请
 *
 * @author chao
 * @since 7/11/14
 */
@Entity
@Table(name = "band_request")
public class BandRequest extends BasicModel {

    public static final int STATUS_NEW = 0;
    public static final int STATUS_ACCEPT = 1;
    public static final int STATUS_REJECT = 2;

    /**
     * 申请人
     */
    @Column(name = "user_id")
    public String userId;

    /**
     * 乐队名称
     */
    @Column(name = "band_name")
    public String bandName;

    /**
     * 申请说明
     */
    @Lob
    @Column(name = "content")
    public String content;

    /**
     * 申请状态
     */
    @Column(name = "status")
    public int status;

    /*
     * 根据状态取数据
     */
    public static List<BandRequest> findByStatus(int stauts, PageBean pageBean) {

        return BandRequest.find("status = ?", stauts).fetch(pageBean.getCurPage(), pageBean.getPerPage());

    }
    
    /*
     * 根据状态取总数
     */

    public static long countByNewStatus() {
        return BandRequest.count("status = ?", BandRequest.STATUS_NEW);
    }

    public static long countByAcceptStatus() {
        return BandRequest.count("status = ?", BandRequest.STATUS_ACCEPT);
    }

    public static long countByRejectStatus() {
        return BandRequest.count("status = ?", BandRequest.STATUS_REJECT);
    }
    
    public static long countByUserId(String userId) {
        return BandRequest.count("userId = ?", userId);
    }
    public static void addBandRequest(String userId, String bandName, String content){
    	BandRequest bandRequest = new BandRequest();
    	bandRequest.userId = userId;
    	bandRequest.bandName = bandName;
    	bandRequest.content = content;
    	bandRequest.status = BandRequest.STATUS_NEW;
    	bandRequest.save();
    	
    }
    public static List<BandRequest> findByUserId(String userId, PageBean pageBean){
    	return BandRequest.find("userId = ?", userId).fetch(pageBean.getCurPage(), pageBean.getPerPage());
    }
   
    public static User findUserByUserId(String id){
    	return User.findById(id);
    }
}
