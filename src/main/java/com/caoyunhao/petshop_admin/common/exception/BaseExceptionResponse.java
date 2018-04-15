package com.caoyunhao.petshop_admin.common.exception;


import com.caoyunhao.petshop_admin.common.response.BaseResponse;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
public class BaseExceptionResponse extends BaseResponse {
    private String errorInfo;

    public BaseExceptionResponse(WebBackendException e) {
        this.setErrorCode(e.getErrorCode());
        this.errorInfo = e.getErrorInfo();
    }

    public BaseExceptionResponse(ErrorCode e) {
        this.setErrorCode(e.getErrorCode());
        this.errorInfo = e.getErrorInfo();
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}
