package cn.edu.guet.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author 郭乐源
 * @Date 2022/8/9 16:48
 * @Version 1.0
 */
@Data
public class WXProduct {
    private Long productId;
    private String productName;
    private String remark;

    //图片的地址
    private String productUrl;
    private BigDecimal productPrice;
    private BigDecimal productTotalPrice;
    private String orderUsername;
    private String orderPhone;
    private String orderAddress;
    private Long num;
    private String productType;
}
