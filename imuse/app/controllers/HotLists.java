package controllers;

import java.util.List;

import org.scauhci.common.util.StringUtil;

import beans.HotListBean;
import beans.TrackBean;
import models.HotList;
import models.Track;

public class HotLists extends WebService{
		
	
	/**
	 * 根据type取排行数据
	 * @param type
	 */
	public static void getHotListByType(int type,int timeType){
		if((type >= 0&&type <= 3)&&(timeType >=0&&timeType <=3)){
			List<HotList> hotlists = HotList.findByTypeAndTimeType(type,timeType);
			List<HotListBean> hotListBeanList = HotListBean.buildList(hotlists);
			wsOk(hotListBeanList);
		}else{
			wsError("type error");
		}
	}
}
