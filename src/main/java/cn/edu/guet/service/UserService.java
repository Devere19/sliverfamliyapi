package cn.edu.guet.service;

import cn.edu.guet.bean.User;
import cn.edu.guet.bean.UserParm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author 郭乐源
 * @Date 2022/8/5 15:29
 * @Version 1.0
 */

public interface UserService extends IService<User> {

    User getOnlyUserByOpenId(String openId);
    List<User> findAll();
    IPage<User> getUserAll(UserParm userParm);

}
