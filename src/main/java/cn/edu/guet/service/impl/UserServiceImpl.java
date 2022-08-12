package cn.edu.guet.service.impl;

import cn.edu.guet.bean.User;
import cn.edu.guet.bean.UserParm;
import cn.edu.guet.mapper.UserMapper;
import cn.edu.guet.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 郭乐源
 * @Date 2022/8/5 15:30
 * @Version 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public User getOnlyUserByOpenId(String openId) {
        User onlyUserByOpenId = userMapper.getOnlyUserByOpenId(openId);
        return onlyUserByOpenId;
    }

    @Override
    public List<User> findAll() {
        List list=userMapper.findAll();
        return null;
    }

    @Override
    public IPage<User> getUserAll(UserParm userParm) {
        //构造一个分页对象
        IPage<User> page = new Page<>(userParm.getCurrentPage(), userParm.getPageSize());
        //构造查询条件
        QueryWrapper<User> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(userParm.getPhone())) {
            query.lambda().like(User::getPhone, userParm.getPhone());
        }
        return this.baseMapper.selectPage(page, query);
    }


}
