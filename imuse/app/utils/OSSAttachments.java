package utils;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.OSSObject;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.aliyun.openservices.oss.model.PutObjectResult;
import models.OSSAttachment;
import org.apache.commons.lang.StringUtils;
import play.Logger;
import play.Play;
import play.libs.Codec;
import play.libs.IO;

import java.io.*;

/**
 * OSS附件工具
 *
 * @author chao
 * @version 2014-05-15
 */
public class OSSAttachments {

    /**
     * 获取OSS Client
     *
     * @return
     */
    public static OSSClient client() {
        String accessKeyId = Play.configuration.getProperty("oss.accessKeyId");
        String accessKeySecret = Play.configuration.getProperty("oss.accessKeySecret");
        String endpoint = Play.configuration.getProperty("oss.endpoint");
        // 初始化一个OSSClient
        if(StringUtils.isEmpty(endpoint)){
            return new OSSClient(accessKeyId, accessKeySecret);
        }else{
            return new OSSClient(endpoint,accessKeyId,accessKeySecret);
        }
    }

    /**
     * 保存文件到OSS
     *
     * @param bucketName
     * @param file
     * @return
     */
    public static OSSAttachment saveFile(String bucketName, File file) {

        try {

            String attachmentKey = Codec.UUID();

            OSSClient client = client();

            InputStream content = new FileInputStream(file);

            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();

            // 必须设置ContentLength
            meta.setContentLength(file.length());

            // 上传Object.
            PutObjectResult result = client.putObject(bucketName, attachmentKey, content, meta);

            if (result == null) {
                return null;
            }

            OSSAttachment attachment = new OSSAttachment();
            attachment.path = attachmentKey;
            attachment.type = OSSAttachment.AttachmentType.oss.value;
            attachment.save();
            return attachment;
        } catch (Exception e) {
            Logger.error(e, "Error in saveFile()");
            return null;
        }
    }

    /**
     * 保存文件到OSS
     *
     * @param file
     * @return
     */
    public static OSSAttachment saveFile(File file) {
        String bucketName = Play.configuration.getProperty("oss.bucketName");
        return saveFile(bucketName, file);
    }

    public static OSSAttachment deleteAttachment(OSSAttachment attach){
        String bucketName = Play.configuration.getProperty("oss.bucketName");
        return deleteAttachment(bucketName, attach);
    }

    public static OSSAttachment deleteAttachment(String bucketName, OSSAttachment attach){
        OSSClient client = client();
        client.deleteObject(bucketName, attach.path);
        return attach;
    }

    /**
     * 输出OSS文件
     *
     * @param bucketName
     * @param out
     * @param key
     * @throws IOException
     */
    public static void output(String bucketName, OutputStream out, String key) throws IOException {

        // 初始化OSSClient
        OSSClient client = client();

        // 获取Object，返回结果为OSSObject对象
        OSSObject object = client.getObject(bucketName, key);

        // 获取ObjectMeta
        ObjectMetadata meta = object.getObjectMetadata();

        // 获取Object的输入流
        InputStream objectContent = object.getObjectContent();

        // 处理Object
        IO.copy(objectContent, out);
    }

    /**
     * 输出OSS文件
     *
     * @param out
     * @param key
     * @throws IOException
     */
    public static void output(OutputStream out, String key) throws IOException {
        String bucketName = Play.configuration.getProperty("oss.bucketName");
        output(bucketName, out, key);
    }

}