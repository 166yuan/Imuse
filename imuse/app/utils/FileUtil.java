package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

import org.h2.constant.SysProperties;

/**
 * 文件信息获取工具类
 * 
 * @author boxiZen
 */

public class FileUtil {
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	protected static MessageDigest messagedigest = null;
	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String getFileMD5String(File file) {
		try {
			FileInputStream in = new FileInputStream(file);
			FileChannel ch = in.getChannel();
			MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY,
					0, file.length());
			messagedigest.update(byteBuffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bufferToHex(messagedigest.digest());
	}

	public static String getMD5String(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer strBuf = new StringBuffer(2 * n);
		int k = m + n;
		for (int i = m; i < k; i++) {
			appendHexPair(bytes[i], strBuf);
		}
		return strBuf.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer strBuf) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		strBuf.append(c0);
		strBuf.append(c1);
	}
	
	public static String getFileExt(File file){
		/*return file.getName().split("/.")[0];*/
		//TODO 返回文件后缀名
		return null;
	}
	
	public static String getFileExt(String filePath){
		/*return filePath.split(".")[filePath.split("\\.").length];*/
		//TODO 返回文件后缀名
		return null;
	}
	
	public static void main(String[] args) {
		File file = new File("/home/boxizen/图片/image.jpg");
		//System.out.println(FileUtil.getFileMD5String(file));
		System.out.println(getFileExt(file));
	}
}
