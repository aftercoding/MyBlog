package com.sean.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-23 11:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeRoleStatusDto {

    private Long roleId;
    private String status;
}

