package com.sean.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sean.domain.ResponseResult;
import com.sean.domain.dto.AddTagDto;
import com.sean.domain.dto.TagListDto;
import com.sean.domain.entity.Tag;
import com.sean.domain.vo.PageVo;
import com.sean.domain.vo.TagVo;
import com.sean.enums.AppHttpCodeEnum;
import com.sean.exception.SystemException;
import com.sean.mapper.TagMapper;
import com.sean.service.TagService;
import com.sean.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2022-09-21 14:43:25
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()), Tag::getName, tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()), Tag::getRemark, tagListDto.getRemark());

        Page<Tag>  page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);

        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        //封装数据返回；
        return ResponseResult.okResult(pageVo);

    }

    @Override
    public ResponseResult addTag(AddTagDto tagDto) {
        //tag内容不能为空。注意这里创建了一个枚举类型CONTENT_NOT_NULL
        if(!StringUtils.hasText(tagDto.getName())){
            throw new SystemException(AppHttpCodeEnum.TAG_NAME_NOT_NULL);
        }
        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId, Tag::getName);
        List<Tag> list = list(queryWrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }
}
