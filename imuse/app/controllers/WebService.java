package controllers;

import beans.WebServiceBean;
import com.sudocn.play.XController;
import play.mvc.Before;

/**
 * 接口服务
 *
 * @author chao
 * @since 7/11/14
 */
public abstract class WebService extends XController {

    @Before
    static void checkAccess(){
        //TODO check app key
    }

    static void wsOk(Object data){
        WebServiceBean bean = new WebServiceBean();
        bean.result = 0;
        bean.data = data;
        renderJSON(bean);
    }

    static void wsError(String msg){
        WebServiceBean bean = new WebServiceBean();
        bean.result = 1;
        bean.data = msg;
        renderXJSON(bean);
    }

    static void wsNotFoundIfNull(Object obj){
        if(obj == null){
            wsError("找不到资源");
        }
    }

    static void wsForbidden(){
        wsError("无权限");
    }

}
