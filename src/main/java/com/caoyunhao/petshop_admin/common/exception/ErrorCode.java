package com.caoyunhao.petshop_admin.common.exception;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
public enum ErrorCode {
    // 以下为系统错误
    SUCCESS(0, "成功"),
    ERROR_404(404, "您访问的页面不存在"),
    NOT_VALID_PARAM(10001, "参数格式不正确"),
    //登录相关
    USER_PASSWORD_NOT_VALID(10103, "用户名密码不正确"),
    TOKEN_ERROR(10104, "token已过期"),
    VERIFY_CODE_NOT_VALID(10201, "验证码无效，请刷新重试"),
    VERIFY_CODE_GET_FAILURE(10202, "获取验证码失败，请刷新重试"),
    VERIFY_CODE_NEED_TOKEN(10301, "获取验证码需要Token"),

    //用户相关
    USER_NOT_FOUND(20101, "找不到用户"),
    USER_EXISTS(20102, "用户名已存在"),

    QUERY_DATA_EMPTY(31002, "数据查询为空"),

    DELETE_USED_DATA(41002, "不能删除被使用的数据"),

    //文件相关
    FILE_PARAM_IS_REQUIRED(51001, "文件参数是必需的"),
    FILE_NOT_VALID(51011, "文件无效"),
    IMAGE_ID_NOT_VALID(51201, "图片ID为空"),

    AUTH_EXISTS(40001,"权限已经存在"),
    AUTH_NOT_FOUND(40002,"权限不存在"),

    //角色相关
    ROLE_EXISTS(40101,"角色已存在"),
    ROLE_NOT_FOUND(40102,"找不到角色"),

    //品类相关
    CATEGORY_EXISTS(50101,"品类已存在"),
    CATEGORY_NOT_FOUND(50102,"找不到品类"),
    CATEGORY_HAVE_NO_COMMODITY(50103,"品类中没有商品"),
    //商品相关
    COMMODITY_EXISTS(60101,"商品已存在"),
    COMMODITY_NOT_FOUND(60102,"找不到商品"),

    // 登录登出相关
    LOGOUT_FAILED(70101, "退出登录失败"),

    OTHER(99991, "其他类型错误");
    private int errorCode;
    private String errorInfo;

    ErrorCode(int errorCode, String errorInfo) {
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
    }

    public int getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

}
