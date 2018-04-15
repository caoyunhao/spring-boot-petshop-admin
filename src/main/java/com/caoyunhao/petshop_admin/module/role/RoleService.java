package com.caoyunhao.petshop_admin.module.role;

import com.caoyunhao.petshop_admin.common.exception.ErrorCode;
import com.caoyunhao.petshop_admin.common.exception.WebBackendException;
import com.caoyunhao.petshop_admin.common.util.ConvertUtil;
import com.caoyunhao.petshop_admin.common.util.DataUtil;
import com.caoyunhao.petshop_admin.common.util.RowConverter;
import com.caoyunhao.petshop_admin.entity.Auth;
import com.caoyunhao.petshop_admin.entity.Role;
import com.caoyunhao.petshop_admin.entity.RoleAuth;
import com.caoyunhao.petshop_admin.module.auth.AuthService;
import com.caoyunhao.petshop_admin.repository.AuthRepository;
import com.caoyunhao.petshop_admin.repository.RoleAuthRepository;
import com.caoyunhao.petshop_admin.repository.RoleRepository;
import com.caoyunhao.petshop_admin.repository.UserRoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
@Service
@Transactional
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    RoleAuthRepository roleAuthRepository;
    @Autowired
    AuthService authService;

    /**
     * 获取角色列表
     */
    public Page<RoleForm> findRoles(Pageable pageable) throws Exception {
        Page<Role> rolePage = roleRepository.findAll(pageable);
        Page<RoleForm> roleFormPage = ConvertUtil.convertPage(rolePage, pageable, rolePage.getTotalElements(), new RowConverter<Role, RoleForm>() {
            @Override
            public RoleForm convertRow(Role role) throws Exception {
                RoleForm roleForm = new RoleForm();
                BeanUtils.copyProperties(role, roleForm);
                roleForm.setAuthList(getAuthList(roleForm.getId()));
                return roleForm;
            }
        });
        return roleFormPage;
    }

    /**
     * 根据角色名搜索
     */
    public Page<RoleForm> findByRoleName(String roleName, Pageable pageable) throws Exception {
        Page<Role> rolePage = roleRepository.findByRoleNameLike("%" + roleName + "%", pageable);
        //找不到用户
        if (rolePage == null)
            throw new WebBackendException(ErrorCode.ROLE_NOT_FOUND);
        Page<RoleForm> roleFormPage = ConvertUtil.convertPage(rolePage, pageable, rolePage.getTotalElements(), new RowConverter<Role, RoleForm>() {
            @Override
            public RoleForm convertRow(Role role) throws Exception {
                RoleForm roleForm = new RoleForm();
                BeanUtils.copyProperties(role, roleForm);
                roleForm.setAuthList(getAuthList(roleForm.getId()));
                return roleForm;
            }
        });
        return roleFormPage;
    }

    /**
     * 获取角色
     */
    public RoleForm findRoleById(Long roleId) throws Exception {
        Role role = getRole(roleId);
        RoleForm roleForm = new RoleForm();
        BeanUtils.copyProperties(role, roleForm);
        roleForm.setAuthList(getAuthList(roleId));
        return roleForm;
    }

    /**
     * 添加角色
     */
    public Role addRole(RoleForm roleForm) throws Exception {
        Role role = new Role();
        BeanUtils.copyProperties(roleForm, role, "id");
        //角色名冲突
        if (roleRepository.countByRoleName(roleForm.getRoleName()) > 0)
            throw new WebBackendException(ErrorCode.ROLE_EXISTS);
        roleRepository.save(role);
        roleAuthRepository.saveAll(generateRoleAuthList(roleForm.getAuthList(), role.getId()));
        return role;
    }

    /**
     * 修改角色
     */
    public Role updateRole(RoleForm roleForm, Long roleId) throws Exception {
        Role role = getRole(roleId);
        //修改后的角色名冲突
        if (!role.getRoleName().equals(roleForm.getRoleName()))
            if (roleRepository.countByRoleName(roleForm.getRoleName()) > 0)
                throw new WebBackendException(ErrorCode.ROLE_EXISTS);
        BeanUtils.copyProperties(roleForm, role);
        roleRepository.save(role);
        roleAuthRepository.deleteAllByRoleId(roleId);
        roleAuthRepository.saveAll(generateRoleAuthList(roleForm.getAuthList(), roleId));
        return role;
    }

    /**
     * 删除角色
     */
    public void deleteRole(Long roleId) throws Exception {
        userRoleRepository.deleteByRoleId(roleId);
        roleRepository.deleteById(roleId);
        roleAuthRepository.deleteAllByRoleId(roleId);
    }

    /**
     * 根据用户角色获取权限列表
     */
    public List<Auth> getAuthList(Long roleId) throws Exception {
        List<RoleAuth> roleAuthList = roleAuthRepository.findByRoleId(roleId);
        List<Auth> authList = new ArrayList<Auth>();
        if (roleAuthList == null)
            return null;
        for (RoleAuth roleAuth : roleAuthList) {
            authList.add(authService.getAuth(roleAuth.getAuthId()));
        }
        return authList;
    }

    public Role getRole(Long roleId)
            throws WebBackendException {
        return DataUtil.getOrElse(id -> roleRepository.findById(id), roleId);
    }

    /**
     * 根据角色和权限列表插入关联
     */
    private List<RoleAuth> generateRoleAuthList(List<Auth> authList, Long roleId) throws Exception {
        if (authList == null || authList.size() == 0)
            return null;
        List<RoleAuth> roleAuthList = new ArrayList<RoleAuth>();
        for (Auth auth : authList) {
            RoleAuth roleAuth = new RoleAuth();
            roleAuth.setRoleId(roleId);
            roleAuth.setAuthId(auth.getId());
            roleAuthList.add(roleAuth);
        }
        return roleAuthList;
    }
}
