package cn.edu.guet.mapper;


import cn.edu.guet.bean.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Liwei
 * @Date 2021-08-13 17:50
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<SysMenu> findAll();

    List<SysMenu> findByUserName(@Param(value = "userName") String userName);


}
