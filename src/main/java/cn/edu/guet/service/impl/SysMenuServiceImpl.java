package cn.edu.guet.service.impl;


import cn.edu.guet.bean.SysMenu;
import cn.edu.guet.mapper.SysMenuMapper;
import cn.edu.guet.service.SysMenuService;
import cn.edu.guet.util.MakeMenuTree;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author Liwei
 * @Date 2021-08-13 18:12
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> findByUser(String userName) {
        if (userName == null || "".equals(userName) || "admin".equalsIgnoreCase(userName)) {
            return sysMenuMapper.findAll();
        }
        return sysMenuMapper.findByUserName(userName);
    }

    @Override
    public List<SysMenu> findTree(String userName, int menuType) {
        List<SysMenu> sysMenus = new ArrayList<>();
        List<SysMenu> menus = findByUser(userName);
        for (SysMenu menu : menus) {
            if (menu.getParentId() == null || menu.getParentId() == 0) {
                menu.setLevel(0);
                if (!exists(sysMenus, menu)) {
                    sysMenus.add(menu);
                }
            }
        }
        sysMenus.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
        findChildren(sysMenus, menus, menuType);
        return sysMenus;
    }

    private void findChildren(List<SysMenu> SysMenus, List<SysMenu> menus, int menuType) {
        for (SysMenu SysMenu : SysMenus) {
            List<SysMenu> children = new ArrayList<>();
            for (SysMenu menu : menus) {
                if (menuType == 1 && menu.getType() == 2) {
                    // 如果是获取类型不需要按钮，且菜单类型是按钮的，直接过滤掉
                    continue;
                }
                if (SysMenu.getId() != null && SysMenu.getId().equals(menu.getParentId())) {
                    menu.setParentName(SysMenu.getName());
                    menu.setLevel(SysMenu.getLevel() + 1);
                    if (!exists(children, menu)) {
                        children.add(menu);
                    }
                }
            }
            SysMenu.setChildren(children);
            children.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
            findChildren(children, menus, menuType);
        }
    }

    private boolean exists(List<SysMenu> sysMenus, SysMenu sysMenu) {
        boolean exist = false;
        for (SysMenu menu : sysMenus) {
            if (menu.getId().equals(sysMenu.getId())) {
                exist = true;
            }
        }
        return exist;
    }

    @Override
    public List<SysMenu> getParent() {
        //查询目录和菜单
        String[] type = {"0", "1"};
        List<String> strings = Arrays.asList(type);
        //构造查询条件
        QueryWrapper<SysMenu> query = new QueryWrapper<>();
        query.lambda().in(SysMenu::getType, strings).orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> menus = this.baseMapper.selectList(query);
        //组装顶级菜单（默认）
        SysMenu menu = new SysMenu();
        menu.setId(0L);
        menu.setParentId(-1L);
        menu.setName("顶级菜单");
        menus.add(menu);
        //组装树数据
        return MakeMenuTree.makeTree(menus, -1L);
    }

    @Override
    public List<SysMenu> getAllMenu() {
        //查询目录,菜单和按钮
        String[] type = {"0", "1", "2"};
        List<String> strings = Arrays.asList(type);
        //构造查询条件
        QueryWrapper<SysMenu> query = new QueryWrapper<>();
        query.lambda().in(SysMenu::getType, strings).orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> menus = this.baseMapper.selectList(query);
        //组装顶级菜单（默认）
        SysMenu menu = new SysMenu();
        menu.setId(0L);
        menu.setParentId(-1L);
        menu.setName("顶级菜单");
        menus.add(menu);
        //组装树数据
        return MakeMenuTree.makeTree(menus, -1L);
    }
}
