package cn.edu.guet.http;

/**
 * @Author 郭乐源
 * @Date 2022/7/22 11:00
 * @Version 1.0
 */

/**
 * 数据返回工具类
 */
public class ResultUtils {
    /**
     * 无参数返回
     *
     * @return
     */
    public static HttpResult success() {
        return new HttpResult(null, StatusCode.SUCCESS_CODE, null);
    }

    public static HttpResult success(String msg) {
        return new HttpResult(msg, StatusCode.SUCCESS_CODE, null);
    }

    /**
     * 返回带参数
     *
     * @param msg
     * @param data
     * @return
     */
    public static HttpResult success(String msg, Object data) {
        return new HttpResult(msg, StatusCode.SUCCESS_CODE, data);
    }

    public static HttpResult success(String msg, int code, Object data) {
        return new HttpResult(msg, code, data);
    }
    // public static ResultVo Vo(String msg, int code, Object data) {
    //     return new ResultVo(msg, code, data);
    // }

    /**
     * 错误返回
     *
     * @return
     */
    public static HttpResult error() {
        return new HttpResult(null, StatusCode.ERROR_CODE, null);
    }

    public static HttpResult error(String msg) {
        return new HttpResult(msg, StatusCode.ERROR_CODE, null);
    }

    public static HttpResult error(String msg, int code, Object data) {
        return new HttpResult(msg, code, data);
    }

    public static HttpResult error(String msg, int code) {
        return new HttpResult(msg, code, null);
    }

    public static HttpResult error(String msg, Object data) {
        return new HttpResult(msg, StatusCode.ERROR_CODE, data);
    }
}