package com.sean.domain.vo;

import com.sean.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-21 23:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVo {
    private List<Menu> menus;
}
