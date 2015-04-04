package models;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 首页大图
 *
 * @author chao
 * @since 7/10/14
 */
@Entity
@Table(name = "index_banner")
public class IndexBanner extends BasicModel {

    /**
     * 图片
     */
    @Column(name = "attach_id")
    public String attachId;
    
    /**
     * 名字：用户为设定的大图名称
     */
    @Column(name = "banner_name")
    public String name;

    /**
     * 权重，越大排在越后
     */
    @Column(name = "weight")
    public int weight;

    /**
     * 点击后跳转链接
     */
    @Column(name = "link")
    public String link;

    /**
     * 是否生效
     */
    @Column(name = "enabled")
    public boolean enabled;
    
    /**
     * oss链接地址
     */
    @Column(name = "url")
    public String url;
}
