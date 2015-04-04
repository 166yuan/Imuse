package utils;

import java.util.Comparator;

import beans.DailysBean;
import models.IndexBanner;
import models.TrackPlayList;

/**
 * 通用排序比较类
 * @author 陈思远
 * 如有需要，自行添加方法
 */
public  class CompareUtil<T> implements Comparator<T>{
	int type;
	public CompareUtil(int type){
		this.type=type;
	}
	@Override
	public int compare(T o1, T o2) {
		// TODO Auto-generated method stub
		if(type==0){
			return BannerWeight(o1,o2);
		}else if(type==1){
			return TrackWeight(o1,o2);
		}else if(type==2){
			return (int) CompareByTime(o1,o2);
		}else{
			return 0;
		}
		
	}
	
	public int TrackWeight(T o1,T o2){
		TrackPlayList ib1 = (TrackPlayList) o1;
		TrackPlayList ib2 = (TrackPlayList) o2;
		int flag = ib1.weight - ib2.weight;
		return flag;
	}
	
	public int BannerWeight(T o1,T o2){
		IndexBanner ib1 = (IndexBanner) o1;
		IndexBanner ib2 = (IndexBanner) o2;
		int flag = ib1.weight - ib2.weight;
		return flag;
	}
	
	public long CompareByTime(T o1,T o2){
		DailysBean db1=(DailysBean)o1;
		DailysBean db2=(DailysBean)o2;
		long flag= db1.createTimes-db2.createTimes;
		return flag;
	}
}
