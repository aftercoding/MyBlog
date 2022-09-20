package com.sean.controller;

import com.sean.constants.SystemConstants;
import com.sean.domain.ResponseResult;
import com.sean.domain.entity.Comment;
import com.sean.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-19 22:59
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }

    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT, articleId, pageNum, pageSize);
    }
}
