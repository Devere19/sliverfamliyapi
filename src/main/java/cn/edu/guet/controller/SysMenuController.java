package cn.edu.guet.controller;


import cn.edu.guet.bean.SysMenu;
import cn.edu.guet.http.HttpResult;
import cn.edu.guet.http.ResultUtils;
import cn.edu.guet.service.SysMenuService;
import cn.edu.guet.util.MakeMenuTree;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 菜单控制器
 *
 * @Author Liwei
 * @Date 2021-08-16 09:20
 */
@RestController
@RequestMapping("/api/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @PreAuthorize("hasAuthority('sys:menu:view')")
    @GetMapping(value = "/findNavTree/{username}")
    public HttpResult findNavTree(@PathVariable("username") String userName) {
        System.out.println("查找菜单树：" + userName);
        List<SysMenu> sysMenus = sysMenuService.findTree(userName, 1);
        return ResultUtils.success("查询成功",sysMenus);
    }

    //新增
    @PostMapping
    public HttpResult add(@RequestBody SysMenu sysMenu) {
        sysMenu.setCreateTime(new Date());
        if (sysMenuService.save(sysMenu)) {
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }

    //编辑
    @PutMapping
    public HttpResult edit(@RequestBody SysMenu sysMenu) {
        sysMenu.setLastUpdateTime(new Date());
        if (sysMenuService.updateById(sysMenu)) {
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    //删除
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") Long id) {
        if (sysMenuService.removeById(id)) {
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }

    @GetMapping("/list")
    public HttpResult list() {
        QueryWrapper<SysMenu> query = new QueryWrapper<>();
        query.lambda().orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> list = sysMenuService.list(query);
        //树形数据组装
        List<SysMenu> menuList = MakeMenuTree.makeTree(list, 0L);
        return ResultUtils.success("查询成功", menuList);
    }

    //查询上级树
    @GetMapping("/parent")
    public HttpResult getParent() {
        List<SysMenu> parent = sysMenuService.getParent();
        return ResultUtils.success("查询成功", parent);
    }

    //查询所有的菜单，包括按钮
    @GetMapping("/getAllMenu")
    public HttpResult getAllMenu() {
        List<SysMenu> menu = sysMenuService.getAllMenu();
        return ResultUtils.success("查询成功", menu);
    }
}
