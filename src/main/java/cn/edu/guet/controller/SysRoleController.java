package cn.edu.guet.controller;

import cn.edu.guet.bean.*;
import cn.edu.guet.http.HttpResult;
import cn.edu.guet.http.ResultUtils;
import cn.edu.guet.service.RoleMenuService;
import cn.edu.guet.service.SysMenuService;
import cn.edu.guet.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 角色控制器
 *
 * @author Liwei
 * @Date 2021-08-15 11:25
 */
@RestController
@RequestMapping("/api/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private SysMenuService sysMenuService;

    @PreAuthorize("hasAuthority('sys:role:view')")
    @GetMapping(value = "/findAll")
    public HttpResult findAll() {
        return ResultUtils.success("查询成功", sysRoleService.findAll());
    }

    //新增
    @PostMapping
    public HttpResult add(@RequestBody SysRole sysRole) {
        sysRole.setCreateTime(new Date());
        if (sysRoleService.save(sysRole)) {
            return ResultUtils.success("新增角色成功");
        } else {
            return ResultUtils.error("新增角色失败!");
        }
    }

    //编辑
    @PutMapping
    public HttpResult edit(@RequestBody SysRole sysRole) {
        sysRole.setLastUpdateTime(new Date());
        if (sysRoleService.updateById(sysRole)) {
            return ResultUtils.success("编辑角色成功");
        } else {
            return ResultUtils.error("编辑角色失败!");
        }
    }

    //删除角色
    @DeleteMapping("/{roleId}")
    public HttpResult deleteRole(@PathVariable("roleId") Long roleId) {
        if (sysRoleService.removeById(roleId)) {
            return ResultUtils.success("删除成功!");
        }
        //删除对应的角色权限

        return ResultUtils.error("删除失败!");
    }

    //角色列表
    @GetMapping("/list")
    public HttpResult getList(RoleParm parm) {
        IPage<SysRole> list = sysRoleService.getList(parm);
        return ResultUtils.success("查询成功", list);
    }

    //获取
    @GetMapping("/getSelect")
    public HttpResult getListSelect() {
        List<SysRole> list = sysRoleService.list();
        List<SelectType> selectTypeList = new ArrayList<>();
        if (list.size() > 0) {
            list.stream().forEach(item -> {
                SelectType type = new SelectType();
                type.setValue(item.getId());
                type.setLabel(item.getName());
                selectTypeList.add(type);
            });
        }
        return ResultUtils.success("查询成功", selectTypeList);
    }

    //分配权限保存
    @PostMapping("/saveRoleMenu")
    public HttpResult saveRoleMenu(@RequestBody SaveMenuParm parm){
        System.out.println("接收到的parm"+parm);
        roleMenuService.saveMenu(parm);
        return ResultUtils.success("分配成功");
    }

    //通过role的id获取拥有的所有的menuid
    @GetMapping("/getOwnMenu/{id}")
    public HttpResult getOwnMenu(@PathVariable("id") String id){
        ArrayList<Long> strings = new ArrayList<>();
        System.out.println("接收到的id"+id);
        QueryWrapper<SysRoleMenu> query = new QueryWrapper<>();
        query.lambda().eq(SysRoleMenu::getRoleId,id);
        List<SysRoleMenu> list = roleMenuService.list(query);
        for (SysRoleMenu sysRoleMenu : list) {
            strings.add(sysRoleMenu.getMenuId());
        }
        return ResultUtils.success("获取menu成功",strings);
    }


}
