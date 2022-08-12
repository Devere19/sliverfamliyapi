package cn.edu.guet.Exception;

/**
 * 包装器业务异常类实现
 *
 * @Author Liwei
 * @Date 2020-07-23 16:33:19
 */
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private int errorCode;

    /**
     * 错误原因
     */
    private String errorMsg;


    /**
     * @param errorEnum
     */
    public BusinessException(ErrorEnum errorEnum) {
        this.errorCode = errorEnum.getErrCode();
        this.errorMsg = errorEnum.getErrMsg();
    }

    /**
     * 接收自定义errMsg的方式构造业务异常（通过覆盖原本errMsg）
     *
     * @param errorEnum
     * @param errMsg
     */
    public BusinessException(ErrorEnum errorEnum, String errMsg) {
        this.errorCode = errorEnum.getErrCode();
        this.errorMsg = errMsg;
    }


    @Override
    public String toString() {
        return "BusinessException {" +
                "errorCode = " + this.errorCode +
                "; errorMsg = " + this.errorMsg +
                " }";
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}