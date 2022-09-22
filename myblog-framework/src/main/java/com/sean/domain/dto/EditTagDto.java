package com.sean.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-22 14:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditTagDto {
    private Long id;
    //备注
    private String remark;
    //标签名
    private String name;
}
