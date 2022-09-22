package com.sean.controller;

import com.sean.domain.ResponseResult;
import com.sean.domain.entity.LoginUser;
import com.sean.domain.entity.Menu;
import com.sean.domain.entity.User;
import com.sean.domain.vo.AdminUserInfoVo;
import com.sean.domain.vo.RoutersVo;
import com.sean.domain.vo.UserInfoVo;
import com.sean.enums.AppHttpCodeEnum;
import com.sean.exception.SystemException;
import com.sean.service.MenuService;
import com.sean.service.RoleService;
import com.sean.service.SystemLoginService;
import com.sean.utils.BeanCopyUtils;
import com.sean.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-19 12:16
 */
@RestController
public class SystemLoginController {
    @Autowired
    private SystemLoginService systemLoginService;

    @Autowired
    private RoleService roleService;

    @Autowired
    MenuService menuService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if (!StringUtils.hasText(user.getUserName())){
            //提示必须输入用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return systemLoginService.login(user);
    }
//
    @PostMapping("/user/logout")
    public ResponseResult logout(){
       return   systemLoginService.logout();
    }

    @GetMapping("getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登陆的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户ID查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户ID查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeyList, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }
}
