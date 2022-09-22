package com.sean.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sean.domain.entity.Role;
import com.sean.mapper.RoleMapper;
import com.sean.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2022-09-21 19:51:19
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long userId) {
        //判断是否是管理员，如果是返回集合中只需要有admin
        if(userId == 1L){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }

//        否则返回插叙用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(userId);
    }
}

