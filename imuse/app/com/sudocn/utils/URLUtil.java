package com.sudocn.utils;

import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import play.Logger;


/**
 * URL工具，可以将URL解析成Java结构
 * @author chao
 *
 */
public class URLUtil {
	
	/**
	 * 构建URL对象
	 * @param url
	 * @return
	 */
	public static URLEntity build(String url) {
		try{
			URLEntity ue = new URLEntity();
			URI u = new URI(url);
			ue.uri = u;
			ue.url = url;
			String q = u.getQuery();
			if(StringUtils.isNotEmpty(q)){
				String[] params = q.split("&");
				for(String p : params){
					if(StringUtils.isNotEmpty(p)){
						String[] kv = p.split("=");
						if(kv.length == 2 && StringUtils.isNotEmpty(kv[1])){
							ue.params.put(kv[0],kv[1]);
						}
					}
				}
			}
			ue.ref = u.getFragment();
			return ue;
		}catch(Exception e){
			Logger.error(e, "Invalidate URL[%s]", url);
			return null;
		}
	}

	/**
	 * URL对象
	 * @author chao
	 *
	 */
	public static class URLEntity{
		/**
		 * URI对象
		 */
		public URI uri;
		/**
		 * 原URL
		 */
		public String url;
		/**
		 * 参数列表
		 */
		public Map<String, Object> params = new HashMap();
		/**
		 * 锚点
		 */
		public String ref;
		
		/**
		 * 设置参数
		 * @param k
		 * @param v
		 * @return
		 */
		public URLEntity set(String k, Object v){
			params.put(k, v);
			return this;
		}
		
		/**
		 * 清除所有参数
		 * @return
		 */
		public URLEntity clear(){
			params.clear();
			return this;
		}
		
		/**
		 * 设置锚点
		 * @param ref
		 * @return
		 */
		public URLEntity setRef(String ref){
			this.ref = ref;
			return this;
		}
		
		/**
		 * 设置锚点
		 * @param ref
		 * @return
		 */
		public URLEntity clearRef(){
			ref =  null;
			return this;
		}
		
		/**
		 * 生成没有参数的URL
		 * @return
		 */
		public String getURLWithoutQuery(){
			//URI格式 [scheme:][//authority][path][?query][#fragment]
			StringBuilder sb = new StringBuilder();
			String url = "";
			//处理scheme
			if(StringUtils.isNotEmpty(uri.getScheme())){
				sb.append(uri.getScheme());
				sb.append(':');
			}
			//处理authority
			if(StringUtils.isNotEmpty(uri.getAuthority())){
				sb.append("//");
				sb.append(uri.getAuthority());
			}
			//处理path
			if(uri == null){
				sb.append('/');
			}else{
				sb.append(uri.getPath());
			}
			url = sb.toString();
			return url;
		}
		/**
		 * 生成URL
		 * @return
		 */
		public String getURL(){
			//URI格式 [scheme:][//authority][path][?query][#fragment]
			StringBuilder sb = new StringBuilder(getURLWithoutQuery());
			//处理query
			Set<Entry<String, Object>> set = params.entrySet();
			if(!set.isEmpty()){
				sb.append('?');
				for(Entry<String, Object> e: set){
					try{
						sb.append(e.getKey());
						sb.append('=');
						sb.append(URLEncoder.encode(e.getValue() == null ? "" : e.getValue().toString(), "UTF-8"));
						sb.append('&');
					}catch(Exception ex){
						Logger.error(ex, "Failed to encode [%s] to url", e.getValue());
					}
				}
				url = sb.substring(0, sb.length()-1);
			}else{
				url = sb.toString();
			}
			//处理fragment
			if(StringUtils.isNotEmpty(ref)){
				url += "#" + ref;
			}
			return url;
		}
		
		@Override
		public String toString(){
			return getURL();
		}
	}
	
	
}
