package cn.edu.guet.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;

/**
 * @Author 郭乐源
 * @Date 2022/8/6 20:18
 * @Version 1.0
 */
@Component
public class QiniuUtils {


    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private Auth auth;

    @Value("${qiniu.bucketName}")
    private String bucketName;
    @Value("${qiniu.path}")
    private String url;

    public String upload(FileInputStream file, String fileName) throws QiniuException {
        String token = auth.uploadToken(bucketName);
        Response res = uploadManager.put(file, fileName, token, null, null);
        if (!res.isOK()) {
            throw new RuntimeException("上传七牛云出错:" + res);
        }
        return url + "/" + fileName;
    }


}
