package models;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 博文
 *
 * @author chao
 * @since 7/11/14
 */
@Entity
@Table(name="blog")
public class Blog extends BasicModel {

    public static final int TYPE_USER = 0;
    public static final int TYPE_BAND = 1;

    public static final int VISIBILITY_OWNER = 0;
    public static final int VISIBILITY_PUBLIC = 1;

    @Column(name="owner_id")
    public String ownerId;

    /**
     * 类型：用户、乐队
     */
    @Column(name="type")
    public int type;

    /**
     * 标题
     */
    @Column(name="title")
    public String title;

    /**
     * 内容，富文本
     */
    @Column(name="content")
    public String content;

    /**
     * 可见度
     */
    @Column(name="visibility")
    public int visibility;
}
