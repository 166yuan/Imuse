package com.sudocn.play.mvc.results;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.commons.io.IOUtils;

import play.Logger;
import play.mvc.Http;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.mvc.results.Result;

/**
 * 206 OK with range support
 * @author chao
 *
 */
public class RenderBinaryWithRange extends Result {
//	Range 
//	　　用于请求头中，指定第一个字节的位置和最后一个字节的位置，一般格式：
//	　　Range:(unit=first byte pos)-[last byte pos]
//			
//	　　Content-Range
//	　　用于响应头，指定整个实体中的一部分的插入位置，他也指示了整个实体的长度。在服务器向客户返回一个部分响应，它必须描述响应覆盖的范围和整个实体长度。一般格式： 
//	　　Content-Range: bytes (unit first byte pos) - [last byte pos]/[entity legth]

	File file;

    public RenderBinaryWithRange(File file) {
        this.file = file;
    }

    @Override
    public void apply(Request request, Response response) {
        if(!file.exists()){
        	response.status=Http.StatusCode.NOT_FOUND;
        	return ;
        }
        RangeSettings settings = range(request, response);
        responseFile(request, response, settings);
    }
    
    private RangeSettings range(Request request, Response response){
    	long len = file.length();//文件长度
        String range = request.headers.get("range").value().replaceAll("bytes=", "");
        RangeSettings settings = getSettings(len, range);
        response.setHeader("Accept-Ranges", "bytes");

        
//        response.addHeader("Content-Disposition", "attachment; filename=\"" + IoUtil.toUtf8String(fileName) + "\"");
        if (!settings.isRange()){
            response.setHeader("Content-Length", String.valueOf(settings.getTotalLength()));
        }else{
            long start = settings.getStart();
            long end = settings.getEnd();
            long contentLength = settings.getContentLength();
            response.status = 206;
            response.setHeader("Content-Length", String.valueOf(contentLength));
            String contentRange = new StringBuffer("bytes ").append(start).append("-").append(end).append("/").append(settings.getTotalLength()).toString();
            response.setHeader("Content-Range", contentRange);
        }
        return settings;
    }
    
    private void responseFile(Request request, Response response, RangeSettings settings){
    	ByteArrayOutputStream os = response.out; 
        BufferedOutputStream out = null;
        RandomAccessFile raf = null;
        byte b[] = new byte[1024];//暂存容器
        long outputCounter = 0;
        try {
            out = new BufferedOutputStream(os);
            raf = new RandomAccessFile(file, "r");
            raf.seek(settings.getStart());
            try {
                    int n = 0;
                    while ((n = raf.read(b, 0, 1024)) != -1) {
                    	if(outputCounter + n > settings.getContentLength()){
                    		out.write(b, 0, (int)(settings.getContentLength() - outputCounter));
                    		outputCounter += settings.getContentLength() - outputCounter;
                    	}else{
                    		out.write(b, 0, n);
                    		outputCounter += n;
                    	}
                        if(outputCounter >= settings.getContentLength()){
                        	break;
                        }
                    }
                    out.flush();
            } catch(IOException ie) {
            }
        } catch (Exception e) {
            Logger.error(e, "Failed to read file");
        } finally {
            if (out != null) {
                IOUtils.closeQuietly(out);
            }
            if (raf != null) {
            	IOUtils.closeQuietly(raf);
            }
        }
    }

    private boolean canAsciiEncode(String string) {
        CharsetEncoder asciiEncoder = Charset.forName("US-ASCII").newEncoder();
        return asciiEncoder.canEncode(string);
    }
    
    RangeSettings getSettings(long len, String range) {
        long contentLength = 0;
        long start = 0;
        long end = 0;
        try{
	        if (range.startsWith("-"))// -500，最后500个
	        {
	             contentLength = Long.parseLong(range.substring(1));//要下载的量
	             end = len-1;
	             start = len - contentLength;
	        }
	        else if (range.endsWith("-"))//从哪个开始
	        {
	            start = Long.parseLong(range.replace("-", ""));
	            end = len -1;
	            contentLength = len - start;
	        }
	        else//从a到b
	        {
	            String[] se = range.split("-");
	            start = Long.parseLong(se[0]);
	            end = Long.parseLong(se[1]);
	            contentLength = end-start+1;
	        }
        }catch(Exception e){
        	contentLength = len;
            start = 0;
            end = len-1;
        }
        return new RangeSettings(start,end,contentLength,len);
    }
    
    class RangeSettings{  
   
        private long start;  
        private long end;  
        private long contentLength;  
        private long totalLength;  
        private boolean range;
        
        public RangeSettings(long start, long end, long contentLength,long totalLength) {  
            this.start = start;  
            this.end = end;  
            this.contentLength = contentLength;  
            this.totalLength = totalLength;  
            this.range = true;  
        }  
   
        public RangeSettings(long totalLength) {  
            this.totalLength = totalLength;  
        }  
   
        public long getStart() {  
            return start;  
        }  
   
        public void setStart(long start) {  
            this.start = start;  
        }  
   
        public long getEnd() {  
            return end;  
        }  
   
        public void setEnd(long end) {  
            this.end = end;  
        }  
   
        public long getContentLength() {  
            return contentLength;  
        }  
   
        public void setContentLength(long contentLength) {  
            this.contentLength = contentLength;  
        }  
   
        public long getTotalLength() {  
            return totalLength;  
        }  
   
        public void setTotalLength(long totalLength) {  
            this.totalLength = totalLength;  
        }  
           
        public boolean isRange() {  
            return range;  
        }  
   
           
    }   

}
