package com.sean.utils;

import com.sean.domain.entity.Article;
import com.sean.domain.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-18 10:39
 */
public class BeanCopyUtils {
    //只提供静态方法，所以constructor 写成私有，单例模式
    private BeanCopyUtils() {
    }
    //注意object泛型
    //通过Class clazz 字节码，反射方式拿到目标对象。
    public static <V> V copyBean(Object source, Class<V> clazz) {
        //创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            //实现属性copy
            BeanUtils.copyProperties(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <O,V> List<V> copyBeanList(List<O> list, Class<V> clazz){
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
    public static void main(String[] args) {
        Article article = new Article();
        article.setId(1L);
        article.setTitle("ss");
        HotArticleVo hotArticleVo = copyBean(article, HotArticleVo.class);
        System.out.println(hotArticleVo);

    }
}
