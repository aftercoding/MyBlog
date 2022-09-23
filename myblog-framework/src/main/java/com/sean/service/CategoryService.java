package com.sean.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sean.domain.ResponseResult;
import com.sean.domain.entity.Category;
import com.sean.domain.vo.PageVo;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-09-18 11:47:36
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

    PageVo selectCategoryPage(Category category, Integer pageNum, Integer pageSize);
}

