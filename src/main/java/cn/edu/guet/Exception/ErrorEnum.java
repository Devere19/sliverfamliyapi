package cn.edu.guet.Exception;

/**
 * @Author Liwei
 * @Date 2020-07-23 17:57:22
 */
public enum ErrorEnum {

    /**
     * 1000开头为通用错误类型
     */
    SYS_ERROR(10001, "系统开个小差"),
    MISSING_REQUEST_PARAM_ERROR(10002, "参数缺失"),
    VALIDATION_REQUEST_PARAM_ERROR(10003, "参数校验不正确"),


    /**
     * 2000开头为用户相关错误
     */
    USER_NOT_EXIST(20001, "用户不存在"),
    USER_PASSWORD_ERROR(20002, "密码错误"),
    USER_NOT_LOGIN(20003, "用户未登陆"),
    USER_ALREADY_EXISTS(20003, "该用户已存在"),
    /**
     * 30000开头为交易型错误
     */
    STOCK_NOT_ENOUGH(30001, "库存不足"),
    TRANSACTION_FAIL(30002, "交易失败"),
    RATE_LIMIT(30003,"活动太火爆,请稍后再试"),

    /**
     *
     */
    MQ_SEND_FAIL(40001, "发送库存扣减消息失败");

    private int errCode;
    private String errMsg;

    ErrorEnum(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

}