package cn.edu.guet.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author 郭乐源
 * @Date 2022/7/31 17:02
 * @Version 1.0
 */
@Data
@TableName("order_detail")
public class OrderDetail {
    @TableId(type = IdType.AUTO)
    private Long orderDetailId;

    private String orderDetailTrace;

    private Long orderDetailProductId;

    private String orderDetailProductName;

    private Long orderDetailNum;

    private BigDecimal orderDetailPrice;
    private String createBy;

    private Date createTime;

    private String lastUpdateBy;

    private Date lastUpdateTime;
}
