package cn.edu.guet.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("image")
public class Image {
    @TableId(type = IdType.AUTO)
    private Long imageId;

    private String imageUrl;
    private String imageNum;
    private String createBy;

    private Date createTime;

    private String lastUpdateBy;

    private Date lastUpdateTime;

}
