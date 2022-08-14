package cn.edu.guet.controller;

import cn.edu.guet.bean.User;
import cn.edu.guet.bean.UserParm;
import cn.edu.guet.http.HttpResult;
import cn.edu.guet.http.ResultUtils;
import cn.edu.guet.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/member")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value = "/findAll")
    public HttpResult findAll(){
        System.out.println("hbhbbh:"+userService.findAll());
        return ResultUtils.success("查询成功", userService.findAll());
    }
//查询全部会员
    @GetMapping("/getUserAll")
    public HttpResult getUserAll(UserParm userParm){
        System.out.println(userParm);
        IPage<User> list = userService.getUserAll(userParm);
        return ResultUtils.success("查询成功", list);
    }

    //编辑
    @PutMapping
    public HttpResult edit(@RequestBody User user) {
        user.setLastUpdateTime(new Date());
        if (userService.updateById(user)) {
            return ResultUtils.success("编辑角色成功");
        } else {
            return ResultUtils.error("编辑角色失败!");
        }
    }

    //新增
    @PostMapping
    public HttpResult add(@RequestBody User user) {
        user.setCreateTime(new Date());
        if (userService.save(user)) {
            return ResultUtils.success("新增角色成功");
        } else {
            return ResultUtils.error("新增角色失败!");
        }
    }

    //删除角色
    @DeleteMapping("/{MemberId}")
    public HttpResult deleteRole(@PathVariable("MemberId") Long MemberId) {
        if (userService.removeById(MemberId)) {
            return ResultUtils.success("删除成功!");
        }
        //删除对应的角色权限

        return ResultUtils.error("删除失败!");
    }

}
