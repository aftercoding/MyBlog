package com.sean.runner;

import com.sean.domain.entity.Article;
import com.sean.mapper.ArticleMapper;
import com.sean.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sean.constants.ApplicationConstans.REDIS_VIEWCOUNT_KEY;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-21 8:53
 */
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        //查询博客信息， Id 和 viewcount
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap((Article article1) -> article1.getId().toString(),
                        article -> article.getViewCount().intValue()));

        //存储到redis中
        redisCache.setCacheMap(REDIS_VIEWCOUNT_KEY, viewCountMap);
    }
}
