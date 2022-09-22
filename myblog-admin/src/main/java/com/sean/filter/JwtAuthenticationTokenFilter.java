package com.sean.filter;

import com.alibaba.fastjson.JSON;
import com.sean.domain.ResponseResult;
import com.sean.domain.entity.LoginUser;
import com.sean.enums.AppHttpCodeEnum;
import com.sean.utils.JwtUtil;
import com.sean.utils.RedisCache;
import com.sean.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.sean.constants.ApplicationConstans.REDIS_ADMIN_LOGIN_KEY;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-19 18:13
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token
        String token = httpServletRequest.getHeader("token");
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        //解析获取userid
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //tocken超时 token非法
            //响应告诉前端重新登陆
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
            return;
        }
        String userId = claims.getSubject();
        //从redis中获取用户信息
        LoginUser loginUser = redisCache.getCacheObject(REDIS_ADMIN_LOGIN_KEY + userId);
        if(Objects.isNull(loginUser)){
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
            return;
        }
        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
