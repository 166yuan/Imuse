package utils;

import java.io.File;

import play.Play;
import play.libs.Codec;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.internal.OSSUtils;

/**
 * 文件上传通用类
 * 
 * @author chensiyuan
 * 
 */
public class UploadUtil {
	public static String fileExt = null;
	public static int IMAGE = 0;
	public static int AUDIO = 1;
	public static int LYRIC = 2;

	public static String upload(File file, int type) {
		OSSClient client = OSSUtil.getOssClient();
		String bucketName = OSSUtil.getBUcketName();
		String uuid = Codec.UUID();
		String key = null;
		fileExt = file.getAbsolutePath().substring(file.getAbsolutePath().length() - 4, file.getAbsolutePath().length());
		System.out.println(fileExt);
		try {
			if (type == IMAGE) {
				key = OSSUtil.IMG + uuid + fileExt;
				OSSUtil.uploadFile(client, bucketName, key, file,0);
			} else if (type == AUDIO) {
				key = OSSUtil.AUD + uuid + fileExt;
				OSSUtil.uploadFile(client, bucketName, key, file,1);
			} else {
				key = OSSUtil.LRC + uuid + fileExt;
				OSSUtil.uploadFile(client, bucketName, key, file,2);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}
}
