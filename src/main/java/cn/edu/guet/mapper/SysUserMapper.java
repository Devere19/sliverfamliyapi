package cn.edu.guet.mapper;


import cn.edu.guet.bean.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author Liwei
 * @Date 2021-08-13 17:50
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    SysUser findByName(@Param(value = "name") String name);
}