package com.sean.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.sean.domain.ResponseResult;
import com.sean.domain.entity.Category;
import com.sean.domain.vo.ExcelCategoryVo;
import com.sean.enums.AppHttpCodeEnum;
import com.sean.service.CategoryService;
import com.sean.utils.BeanCopyUtils;
import com.sean.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-22 16:54
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategoryList(){

        return categoryService.listAllCategory();
    }

    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        //设置下载文件的请求头
        try {
            WebUtils.setDownLoadHeader("category.xlsx", response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            // 把数据写入到excel
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).
                    autoCloseStream(Boolean.FALSE).sheet("category export")
                    .doWrite(excelCategoryVos);
        } catch (Exception e) {
            //如果出现异常也要相应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }
}
