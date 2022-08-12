package cn.edu.guet.bean;

/**
 * @Author 郭乐源
 * @Date 2022/8/5 15:16
 * @Version 1.0
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 微信顾客用户实体类
 */
@Data
@TableName("user")
public class User{
    @TableId(type = IdType.AUTO)
    private Long id;

    private String openId;
    private String avatar;
    private String nickName;
    private String sessionKey;
    private String phone;
    private String createBy;

    private Date createTime;

    private String lastUpdateBy;

    private Date lastUpdateTime;
}
