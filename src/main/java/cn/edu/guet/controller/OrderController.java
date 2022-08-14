package cn.edu.guet.controller;



import cn.edu.guet.bean.Order;
import cn.edu.guet.bean.OrderDetail;
import cn.edu.guet.bean.OrderParm;
import cn.edu.guet.http.HttpResult;
import cn.edu.guet.http.ResultUtils;
import cn.edu.guet.service.OrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author 郭乐源
 * @Date 2022/7/31 16:59
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    //新增


    //编辑
    @PutMapping
    public HttpResult edit(@RequestBody Order order){
        order.setLastUpdateTime(new Date());
        if(orderService.updateById(order)){
            return ResultUtils.success("编辑成功");
        }
        return ResultUtils.error("编辑失败");
    }

    //删除
    @DeleteMapping("/{orderId}/{orderTrace}")
    public HttpResult delete(@PathVariable("orderId") Long orderId,
                             @PathVariable("orderTrace") String orderTrace){
        orderService.delete(orderId,orderTrace);
        return ResultUtils.success("删除成功");
    }

    //查询
    @GetMapping("/list")
    public HttpResult list(OrderParm parm){
        IPage<Order> list = orderService.getList(parm);
        return ResultUtils.success("查询成功",list);
    }

    //根据订单号查询订单想想请
    @GetMapping("/detailList/{orderTrace}")
    public HttpResult getDetailList(@PathVariable("orderTrace") String orderTrace){
        List<OrderDetail> list = orderService.getDetailList(orderTrace);
        return ResultUtils.success("查询成功",list);
    }
    //根据时间查询订单，再根据订单号查询订单详情
    @GetMapping("/detailDayList/{orderCreateTime}")
    public HttpResult getDetailDayList(@PathVariable("orderCreateTime") String orderCreateTime){
        List<Order> list = orderService.getDetailDayList(orderCreateTime);
        return ResultUtils.success("查询成功",list);
    }


}
