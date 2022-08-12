package cn.edu.guet.mapper;

import cn.edu.guet.bean.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Liwei
 * @Date 2021-08-13 17:50
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    List<SysUserRole> findUserRoles(@Param(value = "userId") Long userId);
}