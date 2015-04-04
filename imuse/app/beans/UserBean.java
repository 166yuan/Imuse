package beans;

import java.util.ArrayList;
import java.util.List;

import models.User;

/**
 * @author chao
 * @since 7/11/14
 */
public class UserBean {

    public String nickname;

    public String avatarUrl;

    public int role;

    public String intro;
    
    public String userid;
    public static UserBean build(User user){
        UserBean bean = new UserBean();
        bean.nickname = user.nickname;
        bean.role = user.role;
        bean.intro = user.intro;
        bean.userid=user.id;
        /*bean.avatarUrl = user.getAvatar().getUrl();*/
        return bean;
    }
    public static List<UserBean> buildList(List<User> userlist){
    	List<UserBean> list = new ArrayList<UserBean>();
    	for(int item = 0;item < userlist.size(); item++){
    		UserBean u = UserBean.build(userlist.get(item));
    		list.add(u);
    	}
    	return list;
    }
}
