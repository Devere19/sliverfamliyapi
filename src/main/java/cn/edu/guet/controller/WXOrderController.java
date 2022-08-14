package cn.edu.guet.controller;

import cn.edu.guet.bean.Order;
import cn.edu.guet.bean.WXProduct;
import cn.edu.guet.http.HttpResult;
import cn.edu.guet.http.ResultUtils;
import cn.edu.guet.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 郭乐源
 * @Date 2022/8/9 16:50
 * @Version 1.0
 */
@RestController
@RequestMapping("/wx/order")
public class WXOrderController {
    @Resource
    private OrderService orderService;


    @PostMapping("/add")
    public HttpResult add(@RequestBody WXProduct wxProduct) {
        //微信zhifu
        System.out.println("支付成功");

        System.out.println("开始生成订单,接收参数为"+wxProduct);
        orderService.insert(wxProduct);
        return ResultUtils.success("成功生成订单");
    }

    @PostMapping("/addlist")
    public HttpResult add(@RequestBody List<WXProduct> list) {
        //微信zhifu
        System.out.println("支付成功");

        System.out.println("开始生成订单,接收参数为"+list);
        orderService.insert(list);
        return ResultUtils.success("成功生成订单");
    }

    @PostMapping("/findOrder")
    public HttpResult findOrder(String orderPhone){
        System.out.println("success");
        List<Order> list= orderService.findOrder(orderPhone);

        return ResultUtils.success("查询到全部订单",list);
    }


}
