package beans;

import play.db.jpa.GenericModel;

/**
 * @author boxiZen
 * @since 7/30/14
 */

public class TokenBean extends GenericModel{
	public static final int TYPE_YOUKU = 0;
	public String code ; 
	public String access_token;
	public String refresh_token;
	public String upload_token;
	public String upload_server_uri;
	public String upload_server_ip;
	public String instant_upload;
	public int slice_task_id;
	public int offset;
	public int length;
	public int transferred;
	public boolean finished;
	public String crc;
	public String hash;
}
