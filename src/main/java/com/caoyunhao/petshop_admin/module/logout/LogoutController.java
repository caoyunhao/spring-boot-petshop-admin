package com.caoyunhao.petshop_admin.module.logout;

import com.caoyunhao.petshop_admin.common.response.BaseResponse;
import com.caoyunhao.petshop_admin.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
@RequestMapping("/logout")
@RestController
public class LogoutController {

    @Autowired
    LogoutService logoutService;

    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse logout(@RequestAttribute("user") User user) throws Exception {
        logoutService.logout(user.getId());
        return new BaseResponse<>();
    }
}
