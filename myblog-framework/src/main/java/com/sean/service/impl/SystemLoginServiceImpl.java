package com.sean.service.impl;

import com.sean.constants.ApplicationConstans;
import com.sean.domain.ResponseResult;
import com.sean.domain.entity.LoginUser;
import com.sean.domain.entity.User;
import com.sean.service.SystemLoginService;
import com.sean.utils.JwtUtil;
import com.sean.utils.RedisCache;
import com.sean.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.sean.constants.ApplicationConstans.REDIS_ADMIN_LOGIN_KEY;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-19 13:20
 */
@Service
public class SystemLoginServiceImpl implements SystemLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("User name or password is wrong");
        }
        //获取userid，生成token，
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject(REDIS_ADMIN_LOGIN_KEY + userId, loginUser);
//
//        // 把token和userinfo封装，返回
//        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
//        //把token和userinfo 封装，返回
//        BlogUserLoginVo vo = new BlogUserLoginVo(jwt, userInfoVo);
//        return ResponseResult.okResult(vo);
        Map<String, String> map  = new HashMap<>();
        map.put("token" ,  jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        Long userId = SecurityUtils.getUserId();
        redisCache.deleteObject(ApplicationConstans.REDIS_ADMIN_LOGIN_KEY + userId);
        return ResponseResult.okResult();
    }

//    @Override
//    public ResponseResult logout() {
//        //获取token， 才能找到redis中对应的key （key = “bloglogin” + userid + ）
//        //ThreadLocal 存储
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
//        Long userId = loginUser.getUser().getId();
//        redisCache.deleteObject("bloglogin:" + userId);
//
//        return ResponseResult.okResult();
//    }
}
