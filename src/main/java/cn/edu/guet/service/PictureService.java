package cn.edu.guet.service;

import cn.edu.guet.http.HttpResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author 郭乐源
 * @Date 2022/8/6 18:57
 * @Version 1.0
 */
public interface PictureService {
     HttpResult upload(MultipartFile file);
}
