package cn.edu.guet.service;


import cn.edu.guet.bean.PageParm;
import cn.edu.guet.bean.SysUser;
import cn.edu.guet.bean.SysUserRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 用户管理
 *
 * @Author Liwei
 * @Date 2021-08-13 18:03
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 查找用户的菜单权限标识集合
     *
     * @param userName
     * @return
     */
    Set<String> findPermissions(String userName);

    SysUser findByName(String username);

    List<SysUserRole> findUserRoles(Long userId);

    //新增
    void addUser(SysUser sysUser);

    //编辑
    void editUser(SysUser sysUser);

    //删除
    void deleteUser(Long userId);

    IPage<SysUser> getList(PageParm parm);
}
