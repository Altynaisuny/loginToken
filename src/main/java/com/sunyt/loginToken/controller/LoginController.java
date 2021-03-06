package com.sunyt.loginToken.controller;

import com.sunyt.loginToken.constant.UserConstant;
import com.sunyt.loginToken.dao.UserDao;
import com.sunyt.loginToken.dto.TokenModel;
import com.sunyt.loginToken.dto.User;
import com.sunyt.loginToken.service.TokenService;
import com.sunyt.loginToken.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    TokenService tokenService;
    @Autowired
    UserDao userDao;

    @PostMapping("/test")
    public ResponseVo login(@RequestParam (value = "userName") String userName,
                            @RequestParam (value = "password")String password,
                            HttpServletRequest request){
        Assert.notNull(userName, "username can not be empty");
        Assert.notNull(password, "password can not be empty");
        User user = userDao.findByUsernameAndPassword(userName, password);
        if (user == null){
            return ResponseVo.error("");
        }
        String userId = user.getId();
        TokenModel tokenModel = tokenService.createToken(userId);
        //用户信息存入session中
        WebUtils.setSessionAttribute(request, UserConstant.SESSION_USER_ID, tokenModel.getUserId());
        WebUtils.setSessionAttribute(request, UserConstant.SESSION_USER_INFO, user);
        return ResponseVo.success(HttpStatus.OK, tokenModel);
    }
}
