package com.sean.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-22 21:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelCategoryVo {
    @ExcelProperty("分类名")
    private String name;
    //描述
    @ExcelProperty("描述")
    private String description;

    //状态0:正常,1禁用
    @ExcelProperty("状态0:正常,1禁用")
    private String status;
}
