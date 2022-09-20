package com.sean.service;

import com.sean.domain.ResponseResult;
import com.sean.domain.entity.User;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-19 13:08
 */
public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
