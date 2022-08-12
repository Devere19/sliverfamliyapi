package cn.edu.guet.service;

import cn.edu.guet.bean.SaveMenuParm;
import cn.edu.guet.bean.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author 郭乐源
 * @Date 2022/8/11 21:59
 * @Version 1.0
 */
public interface RoleMenuService extends IService<SysRoleMenu> {
    //保存角色权限
    void saveMenu(SaveMenuParm parm);
}
