package utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 网络信息工具类
 * 
 * @author boxiZen
 */
public class NetworkUtil {
	public static String getIpByDomain(String domainName){
		InetAddress ipAddr;
		String ipStr = null;
		try {
			ipAddr = InetAddress.getByName(domainName);
			ipStr = ipAddr.toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		if(ipStr!=null)
			return ipStr.split("/")[1];
		return ipStr;
	}
}
