package cn.edu.guet.controller;

import cn.edu.guet.http.HttpResult;
import cn.edu.guet.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author 郭乐源
 * @Date 2022/8/6 19:01
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/upload")
public class UploadController {
    @Autowired
    private PictureService pictureService;

    // private String filePath="D:/";

    @PostMapping("/picture")
    @CrossOrigin
    public HttpResult uploadPicture(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println(file.getOriginalFilename());
        System.out.println("后端获取图片");
        return pictureService.upload(file);
    }

}
