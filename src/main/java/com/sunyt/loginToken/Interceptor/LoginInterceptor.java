package com.sunyt.loginToken.Interceptor;

import com.sunyt.loginToken.aop.Authorization;
import com.sunyt.loginToken.constant.UserConstant;
import com.sunyt.loginToken.dto.TokenModel;
import com.sunyt.loginToken.service.TokenService;
import com.sunyt.loginToken.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    TokenService tokenService;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        request.setCharacterEncoding("UTF-8");
        //从header中得到token
        String authorization = request.getHeader("authorization");
        TokenModel tokenModel = tokenService.getToken(authorization);
        //检查session中是否有效
        Object sessionId = WebUtils.getSessionAttribute(request, UserConstant.SESSION_USER_ID);
        if (StringUtils.isEmpty(sessionId) || !sessionId.equals(tokenModel.getUserId())){
            return false;
        }
        //检redis中token是否有效
        if (!tokenService.checkToken(tokenModel)){
            //如果token验证成功，将token对应的用户id存在request中，便于之后注入
            return false;
        }
        //如果验证token失败，并且方法注明了Authorization，返回401错误
        if (method.getAnnotation(Authorization.class) != null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }
}
