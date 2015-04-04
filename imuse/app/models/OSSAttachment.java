package models;

import com.sudocn.play.BasicModel;
import play.Play;
import play.cache.Cache;
import play.db.jpa.JPABase;
import play.mvc.Router;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

/**
 * 附件
 *
 * @author chao
 * @version 2014-05-05
 */
@Entity
@Table(name = "oss_attachment")
public class OSSAttachment extends BasicModel {

    @Column(name = "path")
    public String path;

    @Column(name = "type")
    public int type = 0;

    /**
     * 获取附件的URL
     * 如果类型是link，直接返回链接；否则返回oss的输出地址（CDN需要配置application.cdn）
     *
     * @return
     */
    public String getUrl() {
        if (type == OSSAttachment.AttachmentType.link.value) {
            return path;
        } else if (type == OSSAttachment.AttachmentType.oss.value) {
            Map<String, Object> args = new HashMap(1);
            args.put("attachmentId", id);
            return Play.configuration.getProperty("application.cdn", "") + Router.reverse("Uploads.file", args).toString();
        } else {
            return "";
        }
    }

    public <T extends JPABase> T delete(){
        
        return super.delete();
    }


    public static OSSAttachment loadById(String id){
        OSSAttachment attachment = Cache.get("OSSAttachment-"+id, OSSAttachment.class);
        if(attachment == null){
            attachment = OSSAttachment.findById(id);
            Cache.set("OSSAttachment-"+id, attachment, "1h");
        }
        return attachment;
    }

    /**
     * 附件类型
     */
    public static enum AttachmentType {
        //URL外链，方便开发人员
        link(0),
        //数据放在阿里云上
        oss(1);

        public final int value;

        private AttachmentType(int value) {
            this.value = value;
        }

        public static AttachmentType fromValue(int value) {
            for (AttachmentType t : values()) {
                if (t.value == value) {
                    return t;
                }
            }
            return null;
        }
    }

}
