package cn.edu.guet.Exception;


import lombok.Data;
import org.springframework.util.Assert;

/**
 * 统一返回参数
 *
 * @param <T>
 * @author Liwei
 * @date 2020-07-23 19:30:10
 */
@Data
public final class CommonResult<T> {

    public static Integer CODE_SUCCESS = 0;

    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误提示
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;


    public static <T> CommonResult<T> error(Integer code, String message) {
        Assert.isTrue(!CODE_SUCCESS.equals(code), "code 必须是错误的！");
        CommonResult<T> result = new CommonResult<>();
        result.code = code;
        result.message = message;
        return result;
    }

    public static <T> CommonResult<T> error(ErrorEnum errorEnum) {
        CommonResult<T> result = new CommonResult<>();
        result.code = errorEnum.getErrCode();
        result.message = errorEnum.getErrMsg();
        return result;
    }

    public static <T> CommonResult<T> error(ErrorEnum errorEnum, String message) {
        CommonResult<T> result = new CommonResult<>();
        result.code = errorEnum.getErrCode();
        result.message = message;
        return result;
    }

    public static <T> CommonResult<T> error(BusinessException businessException) {
        CommonResult<T> result = new CommonResult<>();
        result.code = businessException.getErrorCode();
        result.message = businessException.getErrorMsg();
        return result;
    }


    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.code = CODE_SUCCESS;
        result.data = data;
        result.message = "成功";
        return result;
    }


    public static <T> CommonResult<T> success() {
        CommonResult<T> result = new CommonResult<>();
        result.code = CODE_SUCCESS;
        result.message = "成功";
        return result;
    }
}
