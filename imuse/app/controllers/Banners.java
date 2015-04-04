package controllers;

import java.util.Collections;
import java.util.List;

import utils.CompareUtil;
import beans.BannerBean;
import models.IndexBanner;

/** 测试获得大图接口
 * @author 陈思远
 *
 */
public class Banners extends WebService {

	/**
	 * 获得首页大图json的接口
	 */
	public static void GetBanner() {
		List<IndexBanner> bannerList = IndexBanner.all().fetch();
		if (bannerList.size() == 0) {
			wsOk(Collections.emptyList());
			
		} else {
			CompareUtil<IndexBanner> cUtil=new CompareUtil<IndexBanner>(0);
			Collections.sort(bannerList, cUtil);
			List<BannerBean> bList = BannerBean.buildList(bannerList);
			wsOk(bList);
		}
	}

}
