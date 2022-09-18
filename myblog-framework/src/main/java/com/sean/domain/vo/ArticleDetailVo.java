package com.sean.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-18 17:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVo {
    private Long id;
    //标题
    private String title;

    //文章摘要
    private String summary;
    //所属分类id
    private Long categoryId;
    //所属分类name
    private String categoryName;
    //文章内容
    private String content;

    private String thumbnail;

    //访问量
    private Long viewCount;

    private Date createTime;
}

