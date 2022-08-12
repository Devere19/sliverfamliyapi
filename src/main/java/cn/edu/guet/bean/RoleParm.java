package cn.edu.guet.bean;

import lombok.Data;

/**
 * @Author 郭乐源
 * @Date 2022/7/22 12:16
 * @Version 1.0
 */
@Data
public class RoleParm {
    private Long currentPage;
    private Long pageSize;
    private String roleName;
}
