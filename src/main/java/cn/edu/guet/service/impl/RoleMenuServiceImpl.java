package cn.edu.guet.service.impl;

import cn.edu.guet.bean.SaveMenuParm;
import cn.edu.guet.bean.SysRoleMenu;
import cn.edu.guet.mapper.RoleMenuMapper;
import cn.edu.guet.service.RoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author 郭乐源
 * @Date 2022/8/11 22:00
 * @Version 1.0
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, SysRoleMenu> implements RoleMenuService {
    @Transactional
    @Override
    public void saveMenu(SaveMenuParm parm) {
        //先删除角色原来的权限
        QueryWrapper<SysRoleMenu> query = new QueryWrapper<>();
        query.lambda().eq(SysRoleMenu::getRoleId,parm.getId());
        this.baseMapper.delete(query);
        //重新保存
        this.baseMapper.saveRoleMenu(parm.getId(),parm.getList());
    }
}
