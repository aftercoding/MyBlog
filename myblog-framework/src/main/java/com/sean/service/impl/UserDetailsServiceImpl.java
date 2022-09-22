package com.sean.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sean.constants.SystemConstants;
import com.sean.domain.entity.LoginUser;
import com.sean.domain.entity.User;
import com.sean.mapper.MenuMapper;
import com.sean.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-19 13:43
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    MenuMapper menuMapper;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);

        User user = userMapper.selectOne(queryWrapper);
        //判断是否查到用户，如果没查到，抛出异常
        if(Objects.isNull(user)){
            throw new RuntimeException("The user doesn't exit");
        }
        //查到用户，返回用户信息
        // 查询权限信息封装
        //如果是后台用户才需要查询权限封装
        if(user.getType().equals(SystemConstants.ADMIN)){
            List<String> list = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user,list);
        }

        return new LoginUser(user, null);
    }
}
