package com.caoyunhao.petshop_admin.module.custom;


import com.caoyunhao.petshop_admin.common.BaseService;
import com.caoyunhao.petshop_admin.common.exception.ErrorCode;
import com.caoyunhao.petshop_admin.common.exception.WebBackendException;
import com.caoyunhao.petshop_admin.common.util.ConvertUtil;
import com.caoyunhao.petshop_admin.common.util.DataUtil;
import com.caoyunhao.petshop_admin.common.util.EncryptionUtil;
import com.caoyunhao.petshop_admin.common.util.RowConverter;
import com.caoyunhao.petshop_admin.entity.Custom;
import com.caoyunhao.petshop_admin.entity.CustomRole;
import com.caoyunhao.petshop_admin.entity.CustomWallet;
import com.caoyunhao.petshop_admin.entity.Role;

import com.caoyunhao.petshop_admin.module.role.RoleService;
import com.caoyunhao.petshop_admin.repository.*;
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
public class CustomService extends BaseService {

    private final double CUSTOM_DEFAULT_BALANCE = 0.0;

    @Autowired
    CustomRepository customRepository;
    @Autowired
    CustomRoleRepository customRoleRepository;
    @Autowired
    RoleService roleService;
    @Autowired
    CustomImageRepository customImageRepository;
    @Autowired
    CustomWalletRepository customWalletRepository;

    /**
     * 获取顾客列表
     */
    public Page<CustomData> findCustoms(Pageable pageable) throws Exception {
        return toCustomDataPage(customRepository.findAll(pageable), pageable);
    }

    /**
     * 根据顾客名搜索
     */
    public Page<CustomData> searchByCustomName(String customName, Pageable pageable) throws Exception {
        Page<Custom> customPage = customRepository.findAllByCustomNameLike("%" + customName + "%", pageable);
        //找不到顾客
        if (customPage == null)
            throw new WebBackendException(ErrorCode.ROLE_NOT_FOUND);
        return toCustomDataPage(customPage, pageable);
    }

    /**
     * 获取顾客
     */
    public CustomData findCustomById(Long customId) throws Exception {
        return toCustomData(getCustom(customId));
    }

    /**
     * 添加顾客
     * test: 99aa7342ba09523f6b3e7ccdbea93fe3b18adeb9
     */
    public CustomData addCustom(CustomFormWithPassword customFormWithPassword) throws Exception {
        Custom custom = new Custom();
        BeanUtils.copyProperties(customFormWithPassword, custom);
        custom.setPassword(EncryptionUtil.encryptPassword(custom.getPassword()));

        //顾客名冲突
        if (customRepository.countByCustomName(custom.getCustomName()) > 0)
            throw new WebBackendException(ErrorCode.USER_EXISTS);

        customRepository.save(custom);
        customRoleRepository.saveAll(generateCustomRoleList(toRoleIdList(customFormWithPassword.getRoleList()), custom.getId()));

        // 钱包初始化
        CustomWallet customWallet = new CustomWallet();
        customWallet.setCustomId(custom.getId());
        customWallet.setWalletBalance(CUSTOM_DEFAULT_BALANCE);
        customWalletRepository.save(customWallet);

        return toCustomData(custom);
    }

    /**
     * 修改顾客, 不包括密码
     */
    public CustomData updateCustom(CustomForm customForm, Long customId) throws Exception {
        Custom custom = getCustom(customId);
        // 用户名不可以被修改
        BeanUtils.copyProperties(customForm, custom, "customName");
        // custom.setPassword(EncryptionUtil.encryptPassword(custom.getPassword()));
        customRoleRepository.deleteByCustomId(customId);
        customRepository.save(custom);
        customRoleRepository.saveAll(generateCustomRoleList(toRoleIdList(customForm.getRoleList()), customId));
        return toCustomData(custom);
    }

    /**
     * 删除顾客
     */
    public void deleteCustom(Long customId) throws Exception {
        // 删除用户实体
        customRepository.deleteById(customId);
        // 删除角色关联
        customRoleRepository.deleteByCustomId(customId);
        // 删除头像图片关联
        customImageRepository.deleteAllByCustomId(customId);
        // 删除钱包实体
        customWalletRepository.deleteByCustomId(customId);
    }

    /**
     * 根据顾客id获取其角色列表
     */
    private List<Role> getRoleList(Long customId) throws Exception {
        List<CustomRole> customRoleList = customRoleRepository.findByCustomId(customId);
        List<Role> roleList = new ArrayList<Role>();
        if (customRoleList == null)
            return null;
        for (CustomRole customRole : customRoleList) {
            roleList.add(roleService.getRole(customRole.getRoleId()));
        }
        return roleList;
    }

    /**
     * 根据角色实体类list转变为roleIdList
     */
    private List<Long> toRoleIdList(List<Role> roleList) throws Exception {
        if (null == roleList) {
            return null;
        }
        List<Long> idList = new ArrayList<>();
        for (Role role : roleList) {
            if (null != role) {
                Long id = role.getId();
                if (0 != id && null != roleService.getRole(id)) {
                    idList.add(role.getId());
                }
            }
        }
        return idList;
    }

    /**
     * 根据顾客和角色列表插入关联
     */
    private List<CustomRole> generateCustomRoleList(List<Long> roleIdList, Long customId) throws Exception {
        if (roleIdList == null || roleIdList.size() == 0)
            return null;
        List<CustomRole> customRoleList = new ArrayList<CustomRole>();
        for (Long roleId : roleIdList) {
            CustomRole customRole = new CustomRole();
            customRole.setCustomId(customId);
            customRole.setRoleId(roleId);
            customRoleList.add(customRole);
        }
        return customRoleList;
    }

    /**
     * 获取顾客的角色列表
     */
    private List<Role> getCustomRoleList(Long customId)
            throws WebBackendException {
        List<Role> roleList = new ArrayList<>();
        List<CustomRole> customRoleList = customRoleRepository.findByCustomId(customId);
        if (null == customRoleList) {
            return roleList;
        }
        for (CustomRole customRole : customRoleList) {
            Role role = roleService.getRole(customRole.getRoleId());
            if (null != role) {
                roleList.add(role);
            }
        }
        return roleList;
    }

    public Custom getCustom(Long customId)
            throws WebBackendException {
        return DataUtil.getOrElse(id -> customRepository.findById(id), customId);
    }

    /**
     * 将Custom转变为CustomData
     */
    private CustomData toCustomData(Custom custom)
            throws WebBackendException {
        CustomData customData = new CustomData();
        BeanUtils.copyProperties(custom, customData);
        customData.setRoleList(getCustomRoleList(custom.getId()));
        customData.setCustomImageUrl("");
        return customData;
    }

    /**
     * 将Page<Custom>转变为Page<CustomData>
     */
    private Page<CustomData> toCustomDataPage(Page<Custom> customPage, Pageable pageable) throws Exception {
        return ConvertUtil.convertPage(customPage, pageable, customPage.getTotalElements(), new RowConverter<Custom, CustomData>() {
            @Override
            public CustomData convertRow(Custom custom) throws Exception {
                return toCustomData(custom);
            }
        });
    }
}
