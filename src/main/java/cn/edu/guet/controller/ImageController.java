package cn.edu.guet.controller;


import cn.edu.guet.bean.Image;
import cn.edu.guet.http.HttpResult;
import cn.edu.guet.http.ResultUtils;
import cn.edu.guet.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    //新增
    @PostMapping
    public HttpResult add(@RequestBody Image image) {
        image.setCreateTime(new Date());
        if (imageService.save(image)) {
            return ResultUtils.success("新增轮播图成功");
        } else {
            return ResultUtils.error("新增轮播图失败!");
        }
    }

    //查询所有
//    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value = "/findAll")
    public HttpResult findAll(){
        List<Image> list = imageService.list();
        return ResultUtils.success("查询成功", list);
    }

    //删除角色
    @DeleteMapping("/{ImageId}")
    public HttpResult deleteRole(@PathVariable("ImageId") Long ImageId) {
        if (imageService.removeById(ImageId)) {
            return ResultUtils.success("删除成功!");
        }
        //删除对应的角色权限
        return ResultUtils.error("删除失败!");
    }

    //编辑
    @PutMapping
    public HttpResult edit(@RequestBody Image image) {
        image.setLastUpdateTime(new Date());
        if (imageService.updateById(image)) {
            return ResultUtils.success("编辑轮播图成功");
        } else {
            return ResultUtils.error("编辑轮播图失败!");
        }
    }
}
