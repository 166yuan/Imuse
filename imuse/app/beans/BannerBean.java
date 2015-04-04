package beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.functors.WhileClosure;

import models.IndexBanner;

public class BannerBean {
	String attach_id;
	String link;
	String url;
	int weight;
	boolean isvalid;

	public static BannerBean build(IndexBanner iBanner) {
		BannerBean bean = new BannerBean();
		bean.link = iBanner.link;
		bean.url = iBanner.url;
		bean.weight = iBanner.weight;
		bean.attach_id = iBanner.attachId;
		bean.isvalid = iBanner.enabled;
		return bean;
	}

	public static List<BannerBean> buildList(List<IndexBanner> list) {
		int num = 0;
		IndexBanner ib = new IndexBanner();
		List<BannerBean> list2 = new ArrayList<BannerBean>();
		Iterator<IndexBanner> it = list.iterator();
		while (it.hasNext()) {
			ib = it.next();
			if (ib.enabled == true) {
				list2.add(build(ib));
				num++;
				if (num == 4)
					break;
			} else {
				continue;
			}
		}
		return list2;
	}

}
