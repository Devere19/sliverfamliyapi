package cn.edu.guet.mapper;


import cn.edu.guet.bean.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author Liwei
 * @Date 2021-08-13 17:50
 */
@Mapper
public interface SysRoleMapper  extends BaseMapper<SysRole> {
    SysRole selectByPrimaryKey(Long id);
    List<SysRole> findAll();
}