package com.caoyunhao.petshop_admin.module.login;

import com.caoyunhao.petshop_admin.common.exception.ErrorCode;
import com.caoyunhao.petshop_admin.common.exception.WebBackendException;
import com.caoyunhao.petshop_admin.common.util.*;
import com.caoyunhao.petshop_admin.entity.*;
import com.caoyunhao.petshop_admin.module.verify_code.VerifyCodeService;
import com.caoyunhao.petshop_admin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
@Service
public class LoginService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginRecordRepository loginRecordRepository;

    @Autowired
    UserTokenRepository userTokenRepository;

    @Autowired
    UserNonceRepository userNonceRepository;

    @Autowired
    VerifyCodeService verifyCodeService;

    @Value("${token-overdue}")
    private int USER_TOKEN_TIME_DUE;

    public LoginData login(LoginForm loginForm, HttpSession httpSession, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws Exception {
        String verifyCode = loginForm.getVerifyCode();
        String verifyToken = loginForm.getVerifyToken();

        if (!validVerifyCode(verifyCode, verifyToken)) {
            throw new WebBackendException(ErrorCode.VERIFY_CODE_NOT_VALID);
        }

        System.out.println("username        = " + loginForm.getUserName());
        System.out.println("password        = " + loginForm.getPassword());
        System.out.println("encryptPassword = " + EncryptionUtil.encryptPassword(loginForm.getPassword()));

        User user = validUserNameAndPassword(loginForm.getUserName(), loginForm.getPassword());

        if (null == user) {
            throw new WebBackendException(ErrorCode.USER_PASSWORD_NOT_VALID);
        }

        UserToken userToken = updateUserToken(user.getId(), httpSession);

        addLoginRecord(user, httpServletRequest);

        UserNonce userNonce = updateUserNonce(user.getId(), httpServletRequest.getRequestURI());

        String newNonce = userNonce.getNonce();

        SignUtil.writeAuthorization(httpServletResponse, newNonce);

        LoginData loginData = new LoginData();
        loginData.setUserName(EncryptionUtil.encryptAesBase64(user.getUserName(), newNonce));
        loginData.setUserId(EncryptionUtil.encryptAesBase64(String.valueOf(user.getId()), newNonce));
        loginData.setUserToken(EncryptionUtil.encryptAesBase64(userToken.getUserToken(), newNonce));

        return loginData;
    }

    /**
     * 验证用户名密码
     */
    public User validUserNameAndPassword(String userName, String password) {
        return userRepository.findByUserNameEncryptedAndPassword(userName, EncryptionUtil.encryptPassword(password));
    }

    /**
     * 验证验证码,包括验证过期时间, true意味着通过
     */
    public boolean validVerifyCode(String verifyCode, String verifyToken) {
        return verifyCodeService.checkVerifyRecord(verifyCode, verifyToken);

    }

    /**
     * 写入登录日志
     */
    public void addLoginRecord(User user, HttpServletRequest httpServletRequest) {
        LoginRecord loginRecord = new LoginRecord();

        loginRecord.setId(0);
        loginRecord.setUserId(user.getId());
        loginRecord.setIp(CommonUtil.getRemoteIP(httpServletRequest));
        loginRecord.setLoginTime(DatetimeUtil.getCurrentTimestamp());

        loginRecordRepository.save(loginRecord);
    }

    /**
     * 更新用户Token, 包括时间
     */
    public UserToken updateUserToken(Long userId, HttpSession session) {
        UserToken userToken = new UserToken();

        userToken.setUserToken(getNewUserToken(session));
        userToken.setUserId(userId);
        userToken.setOverdueTime(new Timestamp(System.currentTimeMillis() + USER_TOKEN_TIME_DUE));

        return userTokenRepository.save(userToken);
    }

    /**
     * 更新用户Nonce, 包括时间
     */
    public UserNonce updateUserNonce(Long userId, String requestPath) {
        UserNonce userNonce = new UserNonce();

        userNonce.setUserId(userId);
        userNonce.setNonce(SignUtil.generateNonce(requestPath, userId));
        userNonce.setOverdueTime(new Timestamp(System.currentTimeMillis() + USER_TOKEN_TIME_DUE));

        return userNonceRepository.save(userNonce);
    }

    /**
     * 更新token过期时间
     */
    public void updateTokenOverdue(UserToken userToken) {
        userToken.setOverdueTime(new Timestamp(System.currentTimeMillis() + USER_TOKEN_TIME_DUE));
        userTokenRepository.save(userToken);
    }

    /**
     * 获取新的Token
     */
    public String getNewUserToken(HttpSession session) {
        return EncryptionUtil.getMd5(session.getId() + DatetimeUtil.getCurrentTimestamp().toString());
    }

    public UserToken findUserTokenByUserId(Long userId) throws WebBackendException {
        return DataUtil.getOrElse(id_ -> userTokenRepository.findByUserId(id_), userId);
    }

    public UserNonce findUserNonceByUserId(Long userId) throws WebBackendException {
        return DataUtil.getOrElse(id_ -> userNonceRepository.findByUserId(id_), userId);
    }
}
