package cn.edu.guet.service;


import cn.edu.guet.bean.Order;
import cn.edu.guet.bean.OrderDetail;
import cn.edu.guet.bean.OrderParm;
import cn.edu.guet.bean.WXProduct;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author 郭乐源
 * @Date 2022/7/31 16:58
 * @Version 1.0
 */
public interface OrderService extends IService<Order> {

    //新增订单
    void insert(WXProduct wxProduct);

    //新增订单
    void insert(List<WXProduct> list);

    //分页查询
    IPage<Order> getList(OrderParm parm);

    //删除订单表，同时删除订单详情表
    void delete(Long orderId,String orderTrace);

    //查询
    List<OrderDetail> getDetailList(String orderTrace);

    List<Order> findOrder(String orderPhone);

    List<OrderDetail> getDetailProductList(String orderTrace);
}
