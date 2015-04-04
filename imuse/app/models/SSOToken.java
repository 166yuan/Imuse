package models;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 第三方登录
 *
 * @author chao
 * @since 7/10/14
 */
@Entity
@Table(name = "sso_token")
public class SSOToken extends BasicModel {

    public static final int TYPE_WEIBO = 0;
    public static final int TYPE_QQ = 1;
    public static final int TYPE_YOUKU = 2;
    public static final int TYPE_FACEBOOK = 3;
    public static final int TYPE_TWITTER = 4;

    @Column(name = "type")
    public int type;

    @Column(name = "user_id")
    public String userId;

    @Column(name = "token")
    public String token;

    @Column(name = "token_2")
    public String token2;

}
