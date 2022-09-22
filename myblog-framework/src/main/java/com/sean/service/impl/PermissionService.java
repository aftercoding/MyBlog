package com.sean.service.impl;

import com.sean.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-22 21:56
 */
@Service("ps")
public class PermissionService {

    public boolean hasPermission(String needPermission){
        if(SecurityUtils.isAdmin()){
            return true;
        }
        List<String> userPermissions = SecurityUtils.getLoginUser().getPermissions();
        return userPermissions.contains(needPermission);
    }

}
