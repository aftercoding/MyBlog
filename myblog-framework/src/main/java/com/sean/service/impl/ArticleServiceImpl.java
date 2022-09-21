package com.sean.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sean.constants.SystemConstants;
import com.sean.domain.ResponseResult;
import com.sean.domain.entity.Article;
import com.sean.domain.entity.Category;
import com.sean.domain.vo.ArticleDetailVo;
import com.sean.domain.vo.ArticleListVo;
import com.sean.domain.vo.HotArticleVo;
import com.sean.domain.vo.PageVo;
import com.sean.mapper.ArticleMapper;
import com.sean.service.ArticleService;
import com.sean.service.CategoryService;
import com.sean.utils.BeanCopyUtils;
import com.sean.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.sean.constants.ApplicationConstans.REDIS_VIEWCOUNT_KEY;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-17 22:56
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    //查询热门文章 封装成ResponseResult 返回
    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);// queryWrapper.eq("status", 0) 也可以这样写，但是如果字段写错了，不能提前发现。
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只能查出10条（limit， 或者page的方式实现）
        Page<Article> page = new Page(1, 10);
        page(page, queryWrapper);
        List<Article> articles = page.getRecords();
        // bean 拷贝
        //传统写法
//        List<HotArticleVo> articleVos = new ArrayList<>();
//        for (Article article : articles) {
//            HotArticleVo vo  = new HotArticleVo();
//            BeanUtils.copyProperties(article, vo);
//            articleVos.add(vo);
//        }
        //封装成help方法后
        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(vs);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //如果有categoryId 就要 查询和传入的相同。如果没有categoryID就正常查找
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0,
                                    Article::getCategoryId, categoryId);
        //状态是正式发布的
       lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL) ;

        //对isTop进行降序
       lambdaQueryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);
        // 查询categoryName （有两种写法）
        List<Article> articles = page.getRecords();
        //第二种写法：
        articles.stream()
                //获取分类id， 查询分类信息，获取分类名称
                //把分类名称设置给article
                //这里用到了链式编程的小技巧
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        //根据articleID 去查询articleName 进行设置
        // 第一种写法：
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
        //从redis中获取viewCount, 并刷新到页面
        for (Article article : articles) {
            Integer viewCount = redisCache.getCacheMapValue(REDIS_VIEWCOUNT_KEY, article.getId().toString());
            article.setViewCount(viewCount.longValue());
        }

        // 封装成查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());



        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);

        //从redis中获取viewCount, 并刷新到页面
        Integer viewCount = redisCache.getCacheMapValue(REDIS_VIEWCOUNT_KEY, id.toString());
        article.setViewCount(viewCount.longValue());

        //转换成vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);

        //根据分类id查询分类名称
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if(category != null){
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装相应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应的id浏览量
        redisCache.incrementCacheMapValue(REDIS_VIEWCOUNT_KEY, id.toString(), 1);
        return ResponseResult.okResult();
    }
}
