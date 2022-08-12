package cn.edu.guet.mapper;

import cn.edu.guet.bean.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author 郭乐源
 * @Date 2022/8/5 15:26
 * @Version 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User getOnlyUserByOpenId(String openId);
    List<User> findAll();
}
