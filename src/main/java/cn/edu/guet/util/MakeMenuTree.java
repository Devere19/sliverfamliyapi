package cn.edu.guet.util;


import cn.edu.guet.bean.SysMenu;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author 郭乐源
 * @Date 2022/7/28 22:25
 * @Version 1.0
 */
public class MakeMenuTree {

    public static List<SysMenu> makeTree(List<SysMenu> menuList, Long pid) {
        List<SysMenu> list = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && item.getParentId().equals(pid))
                .forEach(item -> {
                    SysMenu menu = new SysMenu();
                    BeanUtils.copyProperties(item, menu);
                    //递归查找下级
                    List<SysMenu> children = makeTree(menuList, item.getId());
                    menu.setChildren(children);
                    list.add(menu);
                });
        return list;
    }
}
