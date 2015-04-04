package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;

/**
 * 优酷视频,记录用户上传的视频信息
 *
 * @author boxiZen
 * @since 8/27/14
 */
@Entity
@Table(name = "video")
public class Video extends BasicModel{
	
	/*上传者id*/
	@Column(name="user_id")
    public String userId;
	
	/*上传者clientId*/
	@Column(name="client_id")
	public String clientId;
	
	/*优酷视频id*/
	@Column(name="v_id")
	public String vid;
	
	@Column(name="title")
	public String title;
	
	@Column(name="tag")
	public String tag;
}
