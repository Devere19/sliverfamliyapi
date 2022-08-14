package cn.edu.guet.service.impl;

import cn.edu.guet.bean.*;
import cn.edu.guet.mapper.OrderMapper;
import cn.edu.guet.service.OrderDetailService;
import cn.edu.guet.service.OrderService;
import cn.edu.guet.service.ProductService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author 郭乐源
 * @Date 2022/7/31 16:58
 * @Version 1.0
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private OrderDetailService orderDetailService;

    @Resource
    private ProductService productService;


    @Override
    public void insert(WXProduct wxProduct) {
        //生成订单
        Order order = new Order();
        order.setOrderTrace(UUID.randomUUID().toString());
        order.setOrderUsername(wxProduct.getOrderUsername());
        order.setOrderPhone(wxProduct.getOrderPhone());
        order.setOrderAddress(wxProduct.getOrderAddress());
        order.setOrderPrice(wxProduct.getProductTotalPrice());
        order.setOrderStatus("0");
        order.setCreateTime(new Date());
        this.baseMapper.insert(order);
        //生成订单详情表
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderDetailTrace(order.getOrderTrace());
        orderDetail.setOrderDetailProductId(wxProduct.getProductId());
        orderDetail.setOrderDetailProductName(wxProduct.getProductName());
        orderDetail.setOrderDetailNum(wxProduct.getNum());
        orderDetail.setOrderDetailPrice(wxProduct.getProductPrice());
        orderDetailService.save(orderDetail);
        //修改库存数量
        Product product = productService.getById(wxProduct.getProductId());
        Long productNum = product.getProductNum();
        productNum = productNum - wxProduct.getNum();
        product.setProductNum(productNum);
        productService.updateById(product);
    }

    @Override
    public void insert(List<WXProduct> list) {
        //生成订单
        Order order = new Order();
        order.setOrderTrace(UUID.randomUUID().toString());
        order.setOrderUsername(list.get(0).getOrderUsername());
        order.setOrderPhone(list.get(0).getOrderPhone());
        order.setOrderAddress(list.get(0).getOrderAddress());
        order.setOrderPrice(list.get(0).getProductTotalPrice());
        order.setOrderStatus("0");
        order.setCreateTime(new Date());
        this.baseMapper.insert(order);
        //生成订单详情表
        for (WXProduct wxProduct : list) {
            //生成订单详情表
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderDetailTrace(order.getOrderTrace());
            orderDetail.setOrderDetailProductId(wxProduct.getProductId());
            orderDetail.setOrderDetailProductName(wxProduct.getProductName());
            orderDetail.setOrderDetailNum(wxProduct.getNum());
            orderDetail.setOrderDetailPrice(wxProduct.getProductPrice());
            orderDetailService.save(orderDetail);
            //修改库存数量
            Product product = productService.getById(wxProduct.getProductId());
            Long productNum = product.getProductNum();
            productNum = productNum - wxProduct.getNum();
            product.setProductNum(productNum);
            productService.updateById(product);
        }
    }

    @Override
    public IPage<Order> getList(OrderParm parm) {
        // 构造一个分页对象
        IPage<Order> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        //构造查询条件
        QueryWrapper<Order> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(parm.getOrderUsername())) {
            query.lambda().like(Order::getOrderUsername, parm.getOrderUsername());
        }
        if (StringUtils.isNotEmpty(parm.getOrderTrace())) {
            query.lambda().like(Order::getOrderTrace, parm.getOrderTrace());
        }
        if (StringUtils.isNotEmpty(parm.getOrderPhone())) {
            query.lambda().like(Order::getOrderPhone, parm.getOrderPhone());
        }
        return this.baseMapper.selectPage(page, query);
    }

    @Override
    @Transactional
    public void delete(Long orderId, String orderTrace) {
        this.baseMapper.deleteById(orderId);
        //删除订单详情表
        QueryWrapper<OrderDetail> query = new QueryWrapper<>();
        query.lambda().eq(OrderDetail::getOrderDetailTrace, orderTrace);
        orderDetailService.remove(query);
    }

    @Override
    public List<OrderDetail> getDetailList(String orderTrace) {
        QueryWrapper<OrderDetail> query = new QueryWrapper<>();
        query.lambda().eq(OrderDetail::getOrderDetailTrace,orderTrace);
        return orderDetailService.list(query);
    }

    @Override
    public List<Order> findOrder(String orderPhone) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Order::getOrderPhone,orderPhone).orderBy(true,true,Order::getOrderStatus);
        List<Order> list = this.baseMapper.selectList(queryWrapper);
        for (Order order : list) {
            List<OrderDetail> detailList = getDetailProductList(order.getOrderTrace());
            order.setOrderDetails(detailList);
        }
        return list;
    }

    @Override
    public List<OrderDetail> getDetailProductList(String orderTrace) {
        QueryWrapper<OrderDetail> query = new QueryWrapper<>();
        query.lambda().eq(OrderDetail::getOrderDetailTrace,orderTrace);
        return orderDetailService.list(query);
    }

    @Override
    public List<Order> getDetailDayList(String createTime) {
        //判断是每天的销售还是每月的销售
        //当0的时候是查询今日的销售数据
        //当1的时候是查询每周的销售
        //当2的时候是查询没月的销售
        String preDay=null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c= Calendar.getInstance();;
        Date time;
        if(createTime.equals("0")){
            c.add(Calendar.DATE, -1);
            time = c.getTime();
            preDay = sdf.format(time);
        }else if(createTime.equals("1")){
            c.add(Calendar.DATE, -7);
            time = c.getTime();
            preDay = sdf.format(time);
        }else if(createTime.equals("2")){
            c.add(Calendar.MONTH, -1);
            time = c.getTime();
            preDay = sdf.format(time);
        }

        List<Order> list = null;
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().lt(Order::getCreateTime, new Date()).ge(Order::getCreateTime, preDay);
        list = this.baseMapper.selectList(queryWrapper);
        List<Product> productList= productService.list();
        for (Order order : list) {
            List<OrderDetail> detailList = getDetailProductList(order.getOrderTrace());
            order.setOrderDetails(detailList);
            order.setProduct(productList);
        }
        //对查询到的订单进行处理


        return list;
    }
}
