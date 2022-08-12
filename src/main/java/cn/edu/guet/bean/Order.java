package cn.edu.guet.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author 郭乐源
 * @Date 2022/7/31 16:53
 * @Version 1.0
 */
@Data
@TableName("product_order")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long orderId;
    //订单号
    private String orderTrace;
    private String orderUsername;
    private String orderPhone;
    private String orderAddress;
    private BigDecimal orderPrice;
    // 0 待配送  1  配送中  2  待收货  3 已收货
    private String orderStatus;
    private String createBy;

    private Date createTime;

    private String lastUpdateBy;

    private Date lastUpdateTime;

    @TableField(exist = false)
    private List<OrderDetail> orderDetails;

    @TableField(exist = false)
    private List<Product> product;


}
