package com.caoyunhao.petshop_admin.module.role;

import com.caoyunhao.petshop_admin.common.BaseRequest;
import com.caoyunhao.petshop_admin.common.page.PageParams;
import com.caoyunhao.petshop_admin.common.response.BaseResponse;
import com.caoyunhao.petshop_admin.common.response.PageResponse;
import com.caoyunhao.petshop_admin.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    RoleService roleService;

    /**
     * 获取角色列表
     */
    @RequestMapping(method=RequestMethod.GET)
    public PageResponse<RoleForm> findRoles(PageParams pageParams)throws Exception{
        return new PageResponse<>(roleService.findRoles(pageParams.getPageRequest()));
    }

    /**
     * 根据角色名搜索角色列表
     */
    @RequestMapping(value="/name/{rolename}" ,method = RequestMethod.GET)
    public PageResponse<RoleForm> findRolesByRoleName(@PathVariable(name="rolename") String roleName,PageParams pageParams)throws Exception{
        return new PageResponse<RoleForm>(roleService.findByRoleName(roleName,pageParams.getPageRequest()));
    }

    /**
     *获取角色
     */
    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    public BaseResponse<RoleForm> findRole(@PathVariable Long id)throws Exception{
        return new BaseResponse<RoleForm>(roleService.findRoleById(id));
    }

    /**
     * 添加角色
     */
    @RequestMapping(method=RequestMethod.POST)
    public BaseResponse<Role> addRole(@RequestBody @Valid BaseRequest<RoleForm> request)throws Exception{
        return new BaseResponse<>(roleService.addRole(request.getData()));
    }

    /**
     * 修改角色
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public BaseResponse<Role> updateRole(@RequestBody @Valid BaseRequest<RoleForm> request, @PathVariable Long id)throws Exception{
        roleService.updateRole(request.getData(),id);
        return new BaseResponse<>();
    }

    /**
     * 删除角色
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public BaseResponse deleteRole(@PathVariable Long id)throws Exception{
        roleService.deleteRole(id);
        return new BaseResponse();
    }
}
