package com.sean.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-18 15:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListVo {
    private Long id;
    //标题
    private String title;

    //文章摘要
    private String summary;
    //所属分类name
    private String categoryName;
    //缩略图
    private String thumbnail;

    //访问量
    private Long viewCount;

    private Date createTime;

}
