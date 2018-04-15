package com.caoyunhao.petshop_admin.module.login;

import com.caoyunhao.petshop_admin.common.BaseRequest;
import com.caoyunhao.petshop_admin.common.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
@RequestMapping(value = "/login")
@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse<LoginData> login(@RequestBody @Valid BaseRequest<LoginForm> request, HttpSession httpSession, HttpServletResponse httpServletResponse, HttpServletRequest httpRequest) throws Exception {
        return new BaseResponse<>(loginService.login(request.getData(), httpSession, httpServletResponse, httpRequest));
    }
}
