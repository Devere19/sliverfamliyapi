package cn.edu.guet.bean;

import lombok.Data;

/**
 * @Author 郭乐源
 * @Date 2022/7/24 17:17
 * @Version 1.0
 */
@Data
public class PageParm {
    private Long currentPage;
    private Long pageSize;
    private String phone;
    private String nickName;
}