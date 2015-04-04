package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.omg.CORBA.PRIVATE_MEMBER;

import play.Play;
import play.libs.Codec;

import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.ServiceException;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSErrorCode;
import com.aliyun.openservices.oss.OSSException;
import com.aliyun.openservices.oss.model.Bucket;
import com.aliyun.openservices.oss.model.CannedAccessControlList;
import com.aliyun.openservices.oss.model.GetObjectRequest;
import com.aliyun.openservices.oss.model.OSSObjectSummary;
import com.aliyun.openservices.oss.model.ObjectListing;
import com.aliyun.openservices.oss.model.ObjectMetadata;

/**
 * 该示例代码展示了如果在OSS中创建和删除一个Bucket，以及如何上传和下载一个文件。
 * 
 * 该示例代码的执行过程是：
 * 1. 创建一个Bucket（如果已经存在，则忽略错误码）；
 * 2. 上传一个文件到OSS；
 * 3. 下载这个文件到本地；
 * 4. 清理测试资源：删除Bucket及其中的所有Objects。
 * 
 * 尝试运行这段示例代码时OSSObjectSample需要注意：
 * 1. 为了展示在删除Bucket时除了需要删除其中的Objects,
 *    示例代码最后为删除掉指定的Bucket，因为不要使用您的已经有资源的Bucket进行测试！
 * 2. 请使用您的API授权密钥填充ACCESS_ID和ACCESS_KEY常量；
 * 3. 需要准确上传用的测试文件，并修改常量uploadFilePath为测试文件的路径；
 *    修改常量downloadFilePath为下载文件的路径。
 * 4. 该程序仅为示例代码，仅供参考，并不能保证足够健壮。
 *
 */
public class OSSUtil {

//    private static final String ACCESS_ID = "qPTdz5fpgtcVd4VK";
//    private static final String ACCESS_KEY = "qXa21E8b3lPIUcoVmLGnEAaNf9mljZ";
   private static final String PUB_URL = "http://imuse-oss.oss.aliyuncs.com/";
    public static final String bucketName = "imuse-oss";
    public static final String IMG = "image/";
    public static final String AUD = "audio/";
    public static final String LRC = "lrc/";
    

    public static void main(String[] args) throws Exception {
//      String fileExt = ".png";
//      String uuid = Codec.UUID();
//      String key = IMG+uuid+fileExt;
//        String uploadFilePath = "E:/tmp/iMuseLogo.png";
//        String downloadFilePath = "E:/tmp/test2.png";
//      
//        // 使用默认的OSS服务器地址创建OSSClient对象。
//        OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);
//        try {
//            //setBucketPublicReadable(client, bucketName);
//
//            System.out.println("正在上传...");
//            uploadFile(client, bucketName, key, uploadFilePath);
//          
//            System.out.println("正在下载...");
//            downloadFile(client, bucketName, key, downloadFilePath);
//            
//            System.out.println("生成url...");
//            System.out.println(getFileUrl(key));
//            
//        } finally {
//            //deleteBucket(client, bucketName);
//        }
    }

    // 创建Bucket.
    public static void ensureBucket(OSSClient client, String bucketName)
            throws OSSException, ClientException{

        try {
            // 创建bucket
            client.createBucket(bucketName);
            System.out.println("success create bucket");
        } catch (ServiceException e) {
            if (!OSSErrorCode.BUCKES_ALREADY_EXISTS.equals(e.getErrorCode())) {
                // 如果Bucket已经存在，则忽略
                System.out.println("bucket is already exist");
                throw e;
            }
        }
    }
    
    //查看所有bucket
    public static void getBucket(OSSClient client){
        List<Bucket> bucketList = client.listBuckets();
        for(Bucket bucket: bucketList){
            System.out.println(bucket.getName());
        }
    }
    
    // 删除一个Bucket和其中的Objects 
    private static void deleteBucket(OSSClient client, String bucketName)
            throws OSSException, ClientException {

        ObjectListing ObjectListing = client.listObjects(bucketName);
        List<OSSObjectSummary> listDeletes = ObjectListing
                .getObjectSummaries();
        for (int i = 0; i < listDeletes.size(); i++) {
            String objectName = listDeletes.get(i).getKey();
            // 如果不为空，先删除bucket下的文件
            client.deleteObject(bucketName, objectName);
        }
        client.deleteBucket(bucketName);
    }

    // 把Bucket设置为所有人可读
    private static void setBucketPublicReadable(OSSClient client, String bucketName)
            throws OSSException, ClientException {
        //创建bucket
        client.createBucket(bucketName);

        //设置bucket的访问权限，public-read-write权限
        client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
    }

    // 上传文件
    public static void uploadFile(OSSClient client, String bucketName, String key, File file,int Type)
            throws OSSException, ClientException, FileNotFoundException {
        String fileName=file.getName();
        String pf =fileName.substring(fileName.lastIndexOf("."));
        System.out.println(pf);
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(file.length());
        // 可以在metadata中标记文件类型
        if(Type==0){
        if(pf.equals(".jpg")||pf.equals(".jpeg")){
            objectMeta.setContentType("image/jpeg");
        }else if(pf.equals(".png")){
            objectMeta.setContentType("image/png");
        } else if (pf.equals(".bmp")){
            objectMeta.setContentType("image/bmp");
        }else if(pf.equals(".gif")){
            objectMeta.setContentType("image/gif");
        }
        }
        else if(Type==1){
            objectMeta.setContentType("audio/mpeg");
        }else{
            objectMeta.setContentType("lrc-application/octet-stream");
        }
        
        InputStream input = new FileInputStream(file);
        try {
            System.out.println(objectMeta.getContentType());
            client.putObject(bucketName, key, input, objectMeta);
        }catch (Exception e){
            System.out.println("上传失败");
            e.printStackTrace();
        }
    }

    // 下载文件
    private static void downloadFile(OSSClient client, String bucketName, String key, String filename)
            throws OSSException, ClientException {
        client.getObject(new GetObjectRequest(bucketName, key),
                new File(filename));
    }
    
    //获取文件链接
    private static String getFileUrl(String key){
        return PUB_URL+key;
    }
    
    public static OSSClient getOssClient(){
        String ACCESS_ID = Play.configuration.getProperty("oss.accessKeyId");
        String ACCESS_KEY = Play.configuration.getProperty("oss.accessKeySecret");
        String ENDPOINT =Play.configuration.getProperty("oss.endpoint");
        OSSClient client = new OSSClient(ENDPOINT,ACCESS_ID, ACCESS_KEY);
        return client;
    }
    
    public static String getBUcketName(){
        return Play.configuration.getProperty("oss.bucketName");
    }
    
}
