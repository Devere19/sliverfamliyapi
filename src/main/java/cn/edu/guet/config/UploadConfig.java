package cn.edu.guet.config;

import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 郭乐源
 * @Date 2022/8/6 20:16
 * @Version 1.0
 */
@Configuration
public class UploadConfig {

    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Bean
    public Auth getAuth() {
        return Auth.create(accessKey, secretKey);
    }

    @Bean
    public UploadManager getUploadManager() {
        return new UploadManager(new com.qiniu.storage.Configuration());
    }

}
