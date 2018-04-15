package com.caoyunhao.petshop_admin.module.user;

import com.caoyunhao.petshop_admin.common.BaseRequest;
import com.caoyunhao.petshop_admin.common.page.PageParams;
import com.caoyunhao.petshop_admin.common.response.BaseResponse;
import com.caoyunhao.petshop_admin.common.response.PageResponse;
import com.caoyunhao.petshop_admin.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 获取用户列表
     */
    @RequestMapping(method = RequestMethod.GET)
    public PageResponse<UserForm> findUsers(PageParams pageParams) throws Exception {
        return new PageResponse<>(userService.findUsers(pageParams.getPageRequest()));
    }

    /**
     * 根据用户名搜索用户列表
     */
    @RequestMapping(value="/name/{username}" ,method = RequestMethod.GET)
    public PageResponse<UserForm> findByUserName(@PathVariable(name="username") String userName,PageParams pageParams)throws Exception{
        return new PageResponse<UserForm>(userService.findByUserName(userName,pageParams.getPageRequest()));
    }

    /**
     * 获取用户
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BaseResponse<UserForm> findUser(@PathVariable Long id) throws Exception {
        return new BaseResponse<>(userService.findUserDataById(id));
    }

    /**
     * 添加用户
     */
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse<User> addUser(@RequestBody @Validated(UserFormWithPassword.Create.class) BaseRequest<UserFormWithPassword> request) throws Exception {
        return new BaseResponse<>(userService.addUser(request.getData()));
    }

    /**
     * 修改用户
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public BaseResponse<User> updateUser(@RequestBody @Validated(UserForm.Update.class) BaseRequest<UserForm> request, @PathVariable Long id) throws Exception {
        return new BaseResponse<>( userService.updateUser(request.getData(), id));
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "/updatepassword/{id}",method = RequestMethod.PUT)
    public BaseResponse updateUserPassword(@RequestBody @Valid BaseRequest<String> request, @PathVariable Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpSession httpSession)throws Exception{
        userService.updateUserPassword(request.getData(),id);
        return new BaseResponse();
    }

    /**
     * 删除用户
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public BaseResponse deleteUser(@PathVariable Long id) throws Exception {
        userService.deleteUser(id);
        return new BaseResponse();
    }
}
