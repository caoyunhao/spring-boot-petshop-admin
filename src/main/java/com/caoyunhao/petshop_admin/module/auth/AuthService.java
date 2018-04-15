package com.caoyunhao.petshop_admin.module.auth;

import com.caoyunhao.petshop_admin.common.exception.ErrorCode;
import com.caoyunhao.petshop_admin.common.exception.WebBackendException;
import com.caoyunhao.petshop_admin.common.util.ConvertUtil;
import com.caoyunhao.petshop_admin.common.util.DataUtil;
import com.caoyunhao.petshop_admin.common.util.RowConverter;
import com.caoyunhao.petshop_admin.entity.Auth;
import com.caoyunhao.petshop_admin.repository.AuthRepository;
import com.caoyunhao.petshop_admin.repository.RoleAuthRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;


/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
@Service
@Transactional
public class AuthService {
    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private RoleAuthRepository roleAuthRepository;

    private static final long INIT_ID = 0;

    /**
     * 添加权限
     */
    public void addAuth(Auth auth) throws Exception {
        if (null == auth || StringUtils.isEmpty(auth.getAuthName())) {
            throw new WebBackendException(ErrorCode.OTHER);
        }
        if (authRepository.findByAuthName(auth.getAuthName()) != null) {
            throw new WebBackendException(ErrorCode.AUTH_EXISTS);
        }
        auth.setId(INIT_ID);
        authRepository.save(auth);
    }

    /**
     * 删除权限
     */
    public void deleteAuth(Long authId) throws Exception {
        authRepository.deleteById(authId);
        roleAuthRepository.deleteByAuthId(authId);
    }

    /**
     * 更新权限
     *
     * @param auth
     * @throws Exception
     */
    public void updateAuth(Auth auth, Long authId) throws Exception {
        if (!StringUtils.isEmpty(auth.getAuthName())) {
            if (!auth.getAuthName().equals(getAuth(authId).getAuthName()) && authRepository.countByAuthName(auth.getAuthName()) > 0) {
                throw new WebBackendException(ErrorCode.AUTH_EXISTS);
            }
            auth.setId(authId);
            authRepository.save(auth);
        } else {
            throw new WebBackendException(ErrorCode.OTHER);
        }
    }

    /**
     * 根据id查询权限
     *
     * @param authId
     * @return
     * @throws Exception
     */
    public Auth findAuthById(Long authId) throws Exception {
        return getAuth(authId);
    }

    /**
     * 分页展示分页展示权限
     *
     * @param pageable
     * @return
     * @throws Exception
     */
    public Page<Auth> findAllAuths(Pageable pageable) throws Exception {
        Page<Auth> authPage = authRepository.findAll(pageable);
        return ConvertUtil.convertPage(authPage, pageable, authPage.getTotalElements(), new RowConverter<Auth, Auth>() {
            @Override
            public Auth convertRow(Auth auth) throws Exception {
                Auth Auth = new Auth();
                BeanUtils.copyProperties(auth, Auth);
                return Auth;
            }
        });

    }

    public Auth getAuth(Long authId)
            throws WebBackendException {
        return DataUtil.getOrElse(id -> authRepository.findById(id), authId);
    }
}
