package controllers;

import java.util.List;

import models.BandRequest;
import models.MusicianRequest;

import org.scauhci.common.util.StringUtil;

import beans.PageBean;

import com.sudocn.play.BasicModel;

/**
 * @author chao
 * @since 7/17/14
 */
public class Requests extends WebService {

    /**
     * 创建歌手申请
     *
     * @param userId
     * @param content
     */
    public static void createMusicianRequest(String userId, String content){
    	if(StringUtil.isNotEmpty(content)&&StringUtil.isNotEmpty(userId)){
    		MusicianRequest.addMusicianRequest(userId, content);
    		wsOk("success");
    	}
    	else{
    		wsError("参数错误");
    	}
    }

    /**
     * 该用户所有的歌手申请状态
     * @param userId
     */
    public static void getMusicianRequest(String userId,int page){
    	if(StringUtil.isNotEmpty(userId)){
    		long size = MusicianRequest.countByUserId(userId);
    		PageBean pageBean = PageBean.getInstance(page, size);
    		List<MusicianRequest> musicianRequestList = MusicianRequest.findByUserId(userId, pageBean);
    		wsOk(musicianRequestList);
    	}
    	else{
    		wsError("参数错误");
    	}
    }

    /**
     * 创建乐队申请
     *
     * @param userId
     * @param bandName
     * @param content
     */
    public static void createBandRequest(String userId, String bandName, String content){
    	if(StringUtil.isNotEmpty(content)&&StringUtil.isNotEmpty(userId)&&StringUtil.isNotEmpty(bandName)){
    		BandRequest.addBandRequest(userId, bandName, content);
    		wsOk("success");
    	}
    	else{
    		wsError("参数错误");
    	}
    }

    /**
     * 列出该用户所有的乐队申请状态
     * @param userId
     */
    public static void listBandRequest(String userId,int page){
        
    	if(StringUtil.isNotEmpty(userId)){
    		long size = BandRequest.countByUserId(userId);
    		PageBean pageBean = PageBean.getInstance(page, size);
    		List<BandRequest> bandRequestList = BandRequest.findByUserId(userId, pageBean);
    		wsOk(bandRequestList);
    	}
    	else{
    		wsError("参数错误");
    	}
    }
}
