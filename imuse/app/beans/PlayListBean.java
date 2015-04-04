package beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.PlayList;
import models.Tag;

/** 获取后台播放列表的接口
 * @author 陈思远
 *
 */
public class PlayListBean {
	private String name;
	private String Id;
	private String ownerId;
	/** 生成bean
	 * @param list
	 * @return 生成的bean
	 */
	public static PlayListBean build(PlayList list) {
		PlayListBean pBean = new PlayListBean();
		pBean.name = list.name;
		pBean.Id=list.id;
		pBean.ownerId=list.ownerId;
		return pBean;
	}

	/** 合成bean列表
	 * @param list
	 * @return bean列表
	 */
	public static List<PlayListBean> buildList(List<PlayList> list) {
		List<PlayListBean> list2 = new ArrayList<PlayListBean>();
		Iterator<PlayList> it = list.iterator();
		while (it.hasNext()) {
			list2.add(build(it.next()));
		}
		return list2;
	}

}
