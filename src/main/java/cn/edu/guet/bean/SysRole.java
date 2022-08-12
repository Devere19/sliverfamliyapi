package cn.edu.guet.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author Liwei
 * @Date 2021-08-13 17:38
 */
@Data
@TableName("sys_role")
public class SysRole extends BaseModel {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String remark;

    private Byte delFlag;


}