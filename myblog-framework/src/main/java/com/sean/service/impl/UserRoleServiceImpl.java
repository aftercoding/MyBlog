package com.sean.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sean.domain.entity.UserRole;
import com.sean.mapper.UserRoleMapper;
import com.sean.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2022-09-23 15:11:55
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
