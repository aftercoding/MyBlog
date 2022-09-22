package com.sean.controller;

import com.sean.domain.ResponseResult;
import com.sean.domain.dto.AddTagDto;
import com.sean.domain.dto.EditTagDto;
import com.sean.domain.dto.TagListDto;
import com.sean.domain.entity.Tag;
import com.sean.domain.vo.PageVo;
import com.sean.service.TagService;
import com.sean.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-21 14:50
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }


    @PostMapping
    public ResponseResult addTag(@RequestBody AddTagDto addTagDto){

        return tagService.addTag(addTagDto);
    }


    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable Long id){

         tagService.removeById(id);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult getTag(@PathVariable(value = "id") Long id){
        Tag tag = tagService.getById(id);
        return ResponseResult.okResult(tag);
    }

    @PutMapping
    public ResponseResult editTag(@RequestBody EditTagDto tagDto){
        //因为从页面传来的是展示tagdto，要更新数据库所以这里要转换成tag
        Tag tag = BeanCopyUtils.copyBean(tagDto,Tag.class);
        tagService.updateById(tag);
        return ResponseResult.okResult();
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){

        return tagService.listAllTag();
    }


}
