package com.caoyunhao.petshop_admin.common.security;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求中的摘要验证信息，包含用户id,随机数和签名
 */
public class RequestAuthorization {
    public static final String AUTHORIZATION_HEADER_NAME = "authorization";

    private Long userId;
    private String nonce;
    private String sign;

    public RequestAuthorization(HttpServletRequest request) throws Exception {
        String authorization = request.getHeader(AUTHORIZATION_HEADER_NAME);
        if (authorization == null) {
            authorization = request.getParameter(AUTHORIZATION_HEADER_NAME);
        }
        String[] authorizations = authorization.split(";");

        this.userId = new Long(authorizations[0]);

        this.nonce = authorizations[1];

        this.sign = authorizations[2];
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNouce(String nonce) {
        this.nonce = nonce;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}