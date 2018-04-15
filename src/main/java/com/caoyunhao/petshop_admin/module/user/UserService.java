package com.caoyunhao.petshop_admin.module.user;

import com.caoyunhao.petshop_admin.common.BaseService;
import com.caoyunhao.petshop_admin.common.exception.ErrorCode;
import com.caoyunhao.petshop_admin.common.exception.WebBackendException;
import com.caoyunhao.petshop_admin.common.util.ConvertUtil;
import com.caoyunhao.petshop_admin.common.util.DataUtil;
import com.caoyunhao.petshop_admin.common.util.EncryptionUtil;
import com.caoyunhao.petshop_admin.common.util.RowConverter;
import com.caoyunhao.petshop_admin.entity.*;
import com.caoyunhao.petshop_admin.module.role.RoleService;
import com.caoyunhao.petshop_admin.repository.RoleRepository;
import com.caoyunhao.petshop_admin.repository.UserImageRepository;
import com.caoyunhao.petshop_admin.repository.UserRepository;
import com.caoyunhao.petshop_admin.repository.UserRoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
@Service
@Transactional
public class UserService extends BaseService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    RoleService roleService;
    @Autowired
    UserImageRepository userImageRepository;

    /**
     * 获取用户列表
     */
    public Page<UserForm> findUsers(Pageable pageable) throws Exception {
        Page<User> userPage = userRepository.findAll(pageable);
        Page<UserForm> userFormPage = ConvertUtil.convertPage(userPage, pageable, userPage.getTotalElements(), new RowConverter<User, UserForm>() {
            @Override
            public UserForm convertRow(User user) throws Exception {
                UserForm userForm = new UserForm();
                BeanUtils.copyProperties(user, userForm);
                userForm.setRoleList(getRoleList(userForm.getId()));
                return userForm;
            }
        });
        return userFormPage;
    }

    /**
     * 根据用户名搜索
     */
    public Page<UserForm> findByUserName(String userName, Pageable pageable) throws Exception {
        Page<User> userPage = userRepository.findByUserNameLike("%" + userName + "%", pageable);
        //找不到用户
        if (userPage == null)
            throw new WebBackendException(ErrorCode.ROLE_NOT_FOUND);
        Page<UserForm> userFormPage = ConvertUtil.convertPage(userPage, pageable, userPage.getTotalElements(), new RowConverter<User, UserForm>() {
            @Override
            public UserForm convertRow(User user) throws Exception {
                UserForm userForm = new UserForm();
                BeanUtils.copyProperties(user, userForm);
                userForm.setRoleList(getRoleList(userForm.getId()));
                return userForm;
            }
        });
        return userFormPage;
    }

    /**
     * 修改密码
     */
    public void updateUserPassword(String password, Long userid) throws Exception {
        User user = getUser(userid);
        user.setPassword(password);
        userRepository.save(user);
    }

    /**
     * 获取用户
     */
    public UserForm findUserDataById(Long userId) throws Exception {
        User user = getUser(userId);
        UserForm userForm = new UserForm();
        BeanUtils.copyProperties(user, userForm);
        userForm.setRoleList(getRoleList(userId));
        return userForm;
    }

    /**
     * 获取用户
     */
    public User findUserById(Long userId) throws Exception {
        return getUser(userId);
    }

    /**
     * 添加用户
     */
    public User addUser(UserFormWithPassword userFormWithPassword) throws Exception {
        User user = new User();
        String userName = userFormWithPassword.getUserName();
        userFormWithPassword.setUserName(EncryptionUtil.encryptFrontString(userName));
        userFormWithPassword.setPassword(EncryptionUtil.encryptFrontString(userFormWithPassword.getPassword()));
        BeanUtils.copyProperties(userFormWithPassword, user, "id", "userAvatar");
        user.setUserName(userName);
        user.setUserNameEncrypted(userFormWithPassword.getUserName());
        user.setPassword(EncryptionUtil.encryptPassword(user.getPassword()));
        //id冲突
        if (userRepository.countByUserName(userFormWithPassword.getUserName()) > 0)
            throw new WebBackendException(ErrorCode.USER_EXISTS);
        //用户名冲突
        if (userRepository.countByUserName(user.getUserName()) > 0)
            throw new WebBackendException(ErrorCode.USER_EXISTS);
        userRepository.save(user);
        userRoleRepository.saveAll(generateUserRoleList(userFormWithPassword.getRoleList(), user.getId()));
        return user;
    }

    /**
     * 修改用户
     */
    public User updateUser(UserForm userForm, Long userId) throws Exception {
        User user = getUser(userId);
        //修改后用户名冲突
        if (!user.getUserName().equals(userForm.getUserName()))
            if (userRepository.countByUserName(userForm.getUserName()) > 0)
                throw new WebBackendException(ErrorCode.USER_EXISTS);
        BeanUtils.copyProperties(userForm, user, "id", "userAvatarId");
        user.setPassword(EncryptionUtil.encryptPassword(user.getPassword()));
        userRoleRepository.deleteByUserId(userId);
        userRepository.save(user);
        userRoleRepository.saveAll(generateUserRoleList(userForm.getRoleList(), userId));
        return user;
    }

    /**
     * 删除用户
     */
    public void deleteUser(Long userId) throws Exception {
        userRoleRepository.deleteByUserId(userId);
        userImageRepository.deleteAllByUserId(userId);
        userRepository.deleteById(userId);
    }

    public User getUser(Long userId)
            throws WebBackendException {
        return DataUtil.getOrElse(id -> userRepository.findById(id), userId);
    }

    /**
     * 根据用户id获取其角色列表
     */
    private List<Role> getRoleList(Long userId) throws Exception {
        List<UserRole> userRoleList = userRoleRepository.findByUserId(userId);
        List<Role> roleList = new ArrayList<Role>();
        if (userRoleList == null)
            return null;
        for (UserRole userRole : userRoleList) {
            roleList.add(roleService.getRole((userRole.getRoleId())));
        }
        return roleList;
    }

    /**
     * 根据用户和角色列表插入关联
     */
    private List<UserRole> generateUserRoleList(List<Role> roleList, Long userId) throws Exception {
        if (roleList == null || roleList.size() == 0)
            return null;
        List<UserRole> userRoleList = new ArrayList<UserRole>();
        for (Role role : roleList) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(role.getId());
            userRoleList.add(userRole);
        }
        return userRoleList;
    }
}
