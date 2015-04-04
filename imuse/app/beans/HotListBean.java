package beans;

import java.util.ArrayList;
import java.util.List;



import models.Cover;
import models.HotList;
import models.Track;

public class HotListBean {
	
		    public String ownerId;


		    public int type;


		    public String weight;

		    public long data;
		    
		    public TrackBean track;
		public static HotListBean build(HotList hotlist) {
			HotListBean bean = new HotListBean();
			bean.data = hotlist.data;
			bean.ownerId = hotlist.ownerId;
			bean.type = hotlist.type;
			bean.weight =hotlist.weight;
			Track track =Track.findById(hotlist.ownerId);
			bean.track = TrackBean.build(track);
			
			return bean;
		}
	
		public static List<HotListBean> buildList(List<HotList> hotList) {
	
			List<HotListBean> HotListBeanList = new ArrayList<HotListBean>();
			for (int item = 0; item < hotList.size(); item++) {
				HotListBean h = HotListBean.build(hotList.get(item));
				HotListBeanList.add(h); 
			}
			return HotListBeanList;
		}
}
