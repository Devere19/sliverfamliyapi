package cn.edu.guet.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理
 *
 * @Author Liwei
 * @Date 2020-07-23 23:18:55
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.OK)
    public CommonResult handleBizException(Exception ex) {
        CommonResult result;
        if (ex instanceof BusinessException) {
            result = CommonResult.error((BusinessException) ex);
        } else if (ex instanceof ServletRequestBindingException) {
            result = CommonResult.error(ErrorEnum.SYS_ERROR, "url绑定路由错误");
        } else if (ex instanceof NoHandlerFoundException) {
            result = CommonResult.error(ErrorEnum.SYS_ERROR, "没有找到访问路径");
        } else {
            result = CommonResult.error(ErrorEnum.SYS_ERROR);
        }
        System.out.println(result);
        ex.printStackTrace();
        return result;
    }
}