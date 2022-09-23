package com.sean.controller;

import com.sean.domain.ResponseResult;
import com.sean.domain.dto.AddArticleDto;
import com.sean.domain.dto.ArticleDto;
import com.sean.domain.entity.Article;
import com.sean.domain.vo.ArticleVo;
import com.sean.domain.vo.PageVo;
import com.sean.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-22 18:33
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }

    @GetMapping("/list")
    public ResponseResult listArticle(Article article, Integer pageNum, Integer pageSize){
        PageVo pageVo = articleService.selectArticlePage(article, pageNum, pageSize);
        return ResponseResult.okResult(pageVo);
    }
    @GetMapping(value = "/{id}")
    public ResponseResult getArticleInfo(@PathVariable(value = "id") Long id){
        ArticleVo articleVo = articleService.getInfo(id);
        return ResponseResult.okResult(articleVo);
    }

    @PutMapping
    public ResponseResult updateArticle(@RequestBody ArticleDto article){
         articleService.edit(article);
        return ResponseResult.okResult();
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable(value = "id") Long id){

        articleService.removeById(id);
        return ResponseResult.okResult();
    }


}
