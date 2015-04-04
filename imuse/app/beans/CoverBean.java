package beans;

import java.util.ArrayList;
import java.util.List;

import models.Cover;


public class CoverBean {
	    public int type;
	   
	    public String ownerId;

	    public String attachId;
	    
	    public String coverId;
	    
	    public int weight;
	   
	    public static CoverBean bulid(Cover cover){
	    	CoverBean bean = new CoverBean();
	    	bean.attachId = cover.attachId;
	    	bean.ownerId = cover.ownerId;
	    	bean.coverId= cover.id;
	    	bean.type = cover.type;
	    	bean.weight = cover.weight;
	    	return bean;
	    }
	    public static List<CoverBean> buildList(List<Cover> coverlist){
	    	List<CoverBean> list = new ArrayList<CoverBean>();
	    	for(int item = 0 ;item <coverlist.size();item ++){
	    		CoverBean c = bulid(coverlist.get(item));
	    		list.add(c);
	    	}
	    	return list;
	    }
}
