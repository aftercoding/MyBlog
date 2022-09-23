package com.sean.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sean.domain.ResponseResult;
import com.sean.domain.entity.Link;
import com.sean.domain.vo.PageVo;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-09-18 17:57:33
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    PageVo selectLinkPage(Link link, Integer pageNum, Integer pageSize);
}

