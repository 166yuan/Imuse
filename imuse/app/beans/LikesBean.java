package beans;

public class LikesBean {
	private boolean islike;
	private long count = 0;

	public static LikesBean build(boolean like) {
		LikesBean lBean = new LikesBean();
		lBean.islike = like;
		return lBean;
	}
	public static LikesBean buildCountBean(long count,boolean islike){
		LikesBean lBean = new LikesBean();
		lBean.islike=islike;
		lBean.count=count;
		return lBean;
	}
}
