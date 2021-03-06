package com.tuotuogroup.login.web;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 *
 *
 * @author qlsc
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request,Model model) {
        String exceptionClassName = (String) request.getAttribute("shrioLoginFailure");
        String error = null;
        if (UnknownAccountException.class.getName().equals(exceptionClassName)){
            error = "用户名或密码错误";
        }else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)){
            error = "用户名或密码错误";
        }else if (exceptionClassName != null){
            error = "其他错误: "+exceptionClassName;
        }
        model.addAttribute("error",error);
        return "login";
    }
}