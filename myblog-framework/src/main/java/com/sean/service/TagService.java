package com.sean.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sean.domain.ResponseResult;
import com.sean.domain.dto.AddTagDto;
import com.sean.domain.dto.TagListDto;
import com.sean.domain.entity.Tag;
import com.sean.domain.vo.PageVo;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2022-09-21 14:42:32
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(AddTagDto addTagDto);

    ResponseResult listAllTag();
}

