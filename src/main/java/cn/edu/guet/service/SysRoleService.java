package cn.edu.guet.service;


import cn.edu.guet.bean.RoleParm;
import cn.edu.guet.bean.SysRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 角色管理
 *
 * @Author Liwei
 * @Date 2021-08-13 18:04
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 查询全部
     *
     * @return
     */
    List<SysRole> findAll();

    IPage<SysRole> getList(RoleParm parm);

}
