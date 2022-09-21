package com.sean.job;

import com.sean.domain.entity.Article;
import com.sean.service.ArticleService;
import com.sean.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sean.constants.ApplicationConstans.REDIS_VIEWCOUNT_KEY;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-21 10:37
 */
@Component
public class UpdateViewCountJob {
    @Autowired
    RedisCache redisCache;

    @Autowired
    ArticleService articleService;

    @Scheduled(cron = "0/55 * * * * ?")
    public void updateViewCount(){
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(REDIS_VIEWCOUNT_KEY);
        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新数据库
        articleService.updateBatchById(articles);
    }
}
