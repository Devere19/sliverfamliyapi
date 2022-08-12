package cn.edu.guet.mapper;

import cn.edu.guet.bean.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author 郭乐源
 * @Date 2022/8/11 21:54
 * @Version 1.0
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<SysRoleMenu> {
    boolean saveRoleMenu(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);
}
