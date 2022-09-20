package com.sean.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sean.constants.SystemConstants;
import com.sean.domain.ResponseResult;
import com.sean.domain.entity.Comment;
import com.sean.domain.vo.CommentVo;
import com.sean.domain.vo.PageVo;
import com.sean.enums.AppHttpCodeEnum;
import com.sean.exception.SystemException;
import com.sean.mapper.CommentMapper;
import com.sean.service.CommentService;
import com.sean.service.UserService;
import com.sean.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-09-19 22:35:31
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;
    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询 对应 文章的跟评论

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();

        //如果为文章的评论的时候，对articleId进行判断,
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType), Comment::getArticleId, articleId);

        //根评论的rootID -1
        queryWrapper.eq(Comment::getRootId, -1);

        //评论类型
        queryWrapper.eq(Comment::getType, commentType);

        //分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<CommentVo> commentVoList = toCommentList(page.getRecords());
        //查询所有根评论对应的子评论集合， 并且赋值给对应的属性。
        for (CommentVo commentVo : commentVoList) {
            //查询对应的子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            //赋值
            commentVo.setChildren(children);
        }
        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        //评论内容不能为空。注意这里创建了一个枚举类型CONTENT_NOT_NULL
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }


    /*
    * 根据根评论的id查询所对应的子评论的集合
    * */

    private List<CommentVo> getChildren(Long id){

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);
        List<CommentVo> commentVos = toCommentList(comments);
        return commentVos;
    }

    private List<CommentVo> toCommentList(List<Comment> list){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);

        //遍历vo集合
        for(CommentVo commentVo : commentVos){
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
            //通过toCommentUserId 查询用户的昵称并赋值

            //如果toCommentUserId不为-1 才进行查询， 为-1代表直接对文章进行了评论
            if (commentVo.getToCommentId() != -1){
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVos;
    }
}
