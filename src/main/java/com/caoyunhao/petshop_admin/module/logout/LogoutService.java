package com.caoyunhao.petshop_admin.module.logout;

import com.caoyunhao.petshop_admin.common.exception.ErrorCode;
import com.caoyunhao.petshop_admin.common.exception.WebBackendException;
import com.caoyunhao.petshop_admin.repository.UserNonceRepository;
import com.caoyunhao.petshop_admin.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
@Service
@Transactional
public class LogoutService {
    @Autowired
    UserTokenRepository userTokenRepository;
    @Autowired
    UserNonceRepository userNonceRepository;

    public void logout(Long id) throws WebBackendException {
        try {
            userTokenRepository.deleteByUserId(id);
            userNonceRepository.deleteByUserId(id);
        } catch (Exception e) {
            throw new WebBackendException(ErrorCode.LOGOUT_FAILED);
        }
    }
}
