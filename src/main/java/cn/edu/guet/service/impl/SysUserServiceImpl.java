package cn.edu.guet.service.impl;

import cn.edu.guet.bean.*;
import cn.edu.guet.mapper.SysRoleMapper;
import cn.edu.guet.mapper.SysUserMapper;
import cn.edu.guet.mapper.SysUserRoleMapper;
import cn.edu.guet.service.SysMenuService;
import cn.edu.guet.service.SysUserRoleService;
import cn.edu.guet.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @Author Liwei
 * @Date 2021-08-13 18:12
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysMenuService sysMenuService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Override
    public SysUser findByName(String name) {
        SysUser sysUser = sysUserMapper.findByName(name);
        if (sysUser != null) {
            List<SysUserRole> userRoles = findUserRoles(sysUser.getId());
            sysUser.setUserRoles(userRoles);
            sysUser.setRoleNames(getRoleNames(userRoles));
            return sysUser;
        }
        return null;
    }

    private String getRoleNames(List<SysUserRole> userRoles) {
        StringBuilder sb = new StringBuilder();
        for (Iterator<SysUserRole> iter = userRoles.iterator(); iter.hasNext(); ) {
            SysUserRole userRole = iter.next();
            SysRole sysRole = sysRoleMapper.selectByPrimaryKey(userRole.getRoleId());
            if (sysRole == null) {
                continue;
            }
            sb.append(sysRole.getRemark());
            if (iter.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    @Override
    public List<SysUserRole> findUserRoles(Long userId) {
        return sysUserRoleMapper.findUserRoles(userId);
    }

    @Override
    public Set<String> findPermissions(String userName) {
        Set<String> perms = new HashSet<>();
        List<SysMenu> sysMenus = sysMenuService.findByUser(userName);
        for (SysMenu sysMenu : sysMenus) {
            if (sysMenu.getPerms() != null && !"".equals(sysMenu.getPerms())) {
                perms.add(sysMenu.getPerms());
            }
        }
        return perms;
    }

    @Override
    @Transactional
    public void addUser(SysUser sysUser) {
        //先保存用户，然后保存用户对应的角色
        int insert = this.baseMapper.insert(sysUser);
        if (insert > 0) {
            //保存用户的角色
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(sysUser.getRoleId());
            sysUserRole.setUserId(sysUser.getId());
            sysUserRoleService.save(sysUserRole);
        }
    }

    @Override
    public void editUser(SysUser sysUser) {
        //先编辑用户，然后把原来的角色删除
        int insert = this.baseMapper.updateById(sysUser);
        if (insert > 0) {
            // 先删除原来的角色
            System.out.println("id:"+ sysUser.getId());
            QueryWrapper<SysUserRole> query = new QueryWrapper<>();
            query.lambda().eq(SysUserRole::getUserId, sysUser.getId());
            sysUserRoleService.remove(query);
            //再保存用户的角色
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(sysUser.getRoleId());
            sysUserRole.setUserId(sysUser.getId());
            sysUserRoleService.save(sysUserRole);
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        int i = this.baseMapper.deleteById(userId);
        if (i > 0) {
            //删除对应的角色
            // 删除原来的角色
            QueryWrapper<SysUserRole> query = new QueryWrapper<>();
            query.lambda().eq(SysUserRole::getUserId, userId);
            sysUserRoleService.remove(query);
        }
    }

    @Override
    public IPage<SysUser> getList(PageParm parm) {
        Page<SysUser> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        //构造查询条件
        if (StringUtils.isNotEmpty(parm.getNickName())) {
            query.lambda().like(SysUser::getNickName, parm.getNickName());
        }
        if (StringUtils.isNotEmpty(parm.getPhone())) {
            query.lambda().like(SysUser::getMobile, parm.getPhone());
        }
        return this.baseMapper.selectPage(page, query);
    }
}
