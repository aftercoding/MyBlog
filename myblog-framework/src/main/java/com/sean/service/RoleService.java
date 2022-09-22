package com.sean.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sean.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2022-09-21 19:44:58
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long userId);
}

