package cn.edu.guet.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


/**
 * @Author 张家维
 * @Date 2022-07-31 17:02
 * */
@Data

@TableName("product")
public class Product {
    @TableId(type = IdType.AUTO)
    private Long productId;

    private String productName;

    private String remark;

    //图片的地址
    private String productUrl;

    private Long productPrice;

    private Long productNum;

    private Date createTime;
    private Date lastUpdateTime;
    private String createBy;
    private String lastUpdateBy;

    private String productType;
}
