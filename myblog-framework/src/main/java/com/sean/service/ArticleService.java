package com.sean.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sean.domain.ResponseResult;
import com.sean.domain.dto.AddArticleDto;
import com.sean.domain.dto.ArticleDto;
import com.sean.domain.entity.Article;
import com.sean.domain.vo.ArticleVo;
import com.sean.domain.vo.PageVo;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-17 22:55
 */
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto article);

    PageVo selectArticlePage(Article article, Integer pageNum, Integer pageSize);

    ArticleVo getInfo(Long id);

    void edit(ArticleDto article);
}
