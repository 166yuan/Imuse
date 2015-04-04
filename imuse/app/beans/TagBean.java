package beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.IndexBanner;
import models.Tag;

public class TagBean {
	String name;
	String iconId;
	String creatorId;

	public static TagBean build(Tag tag) {
		TagBean tBean = new TagBean();
		tBean.name = tag.name;
		tBean.iconId = tag.iconId;
		tBean.creatorId = tag.creatorId;
		return tBean;
	}

	public static List<TagBean> buildList(List<Tag> list) {
		List<TagBean> list2 = new ArrayList<TagBean>();
		Iterator<Tag> it = list.iterator();
		while (it.hasNext()) {
			list2.add(build(it.next()));
		}
		return list2;
	}
}
