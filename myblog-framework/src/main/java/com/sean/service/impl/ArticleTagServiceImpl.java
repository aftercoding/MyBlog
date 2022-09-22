package com.sean.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sean.domain.entity.ArticleTag;
import com.sean.mapper.ArticleTagMapper;
import com.sean.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2022-09-22 18:44:15
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}
