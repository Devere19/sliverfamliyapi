package cn.edu.guet.service.impl;

import cn.edu.guet.http.HttpResult;
import cn.edu.guet.http.ResultUtils;
import cn.edu.guet.service.PictureService;
import cn.edu.guet.util.QiniuUtils;
import cn.edu.guet.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author 郭乐源
 * @Date 2022/8/6 18:58
 * @Version 1.0
 */
@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private QiniuUtils qiniuUtils;

    @Override
    public HttpResult upload(MultipartFile file) {
        if (file.isEmpty()) {
            return ResultUtils.error("上传文件为空,请重试...");
        }
        String fileName = StringUtils.getRandomImgName(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            FileInputStream uploadFile = (FileInputStream) file.getInputStream();
            String path = qiniuUtils.upload(uploadFile, fileName);
            path="http://"+path;
            System.out.println("图片访问url"+path);
            return ResultUtils.success("上传成功",path);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtils.error("服务器内部错误...");
        }
    }

}
