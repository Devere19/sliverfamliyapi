package cn.edu.guet.mapper;



import cn.edu.guet.bean.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author 郭乐源
 * @Date 2022/7/31 16:56
 * @Version 1.0
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
