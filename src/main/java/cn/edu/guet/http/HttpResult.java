package cn.edu.guet.http;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author 郭乐源
 * @Date 2022/7/22 10:54
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class HttpResult<T> {
    private String msg;
    private int code;
    private T data;
}
