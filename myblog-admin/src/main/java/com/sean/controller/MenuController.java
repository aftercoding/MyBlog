package com.sean.controller;

import com.sean.domain.ResponseResult;
import com.sean.domain.entity.Menu;
import com.sean.domain.vo.MenuTreeVo;
import com.sean.domain.vo.MenuVo;
import com.sean.domain.vo.RoleMenuTreeSelectVo;
import com.sean.enums.AppHttpCodeEnum;
import com.sean.service.MenuService;
import com.sean.utils.BeanCopyUtils;
import com.sean.utils.SystemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-23 9:05
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @GetMapping("/list")
    public ResponseResult getMenu(Menu menu){
        List<Menu> menus = menuService.selectMenuList(menu);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }

    @PostMapping
    public ResponseResult addMenu(@RequestBody Menu menu){
        menuService.save(menu);
        return ResponseResult.okResult();
    }

    @GetMapping(value = "/{menuId}")
    public ResponseResult getInfo(@PathVariable Long menuId)
    {
        return ResponseResult.okResult(menuService.getById(menuId));
    }

    @PutMapping
    public ResponseResult editMenu(@RequestBody Menu menu){
        if(menu.getId().equals(menu.getParentId())){
            return ResponseResult.errorResult(AppHttpCodeEnum.MENU_NOT_PARENT);
        }
        menuService.updateById(menu);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{menuId}")
    public ResponseResult deleteTag(@PathVariable Long menuId){
        if (menuService.hasChild(menuId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.MENU_HAS_CHILDREN);
        }
        menuService.removeById(menuId);
        return ResponseResult.okResult();
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public ResponseResult treeselect() {
        //复用之前的selectMenuList方法。方法需要参数，参数可以用来进行条件查询，而这个方法不需要条件，所以直接new Menu()传入
        List<Menu> menus = menuService.selectMenuList(new Menu());
        List<MenuTreeVo> options =  SystemConverter.buildMenuSelectTree(menus);
        return ResponseResult.okResult(options);
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public ResponseResult roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
        List<Menu> menus = menuService.selectMenuList(new Menu());
        List<Long> checkedKeys = menuService.selectMenuListByRoleId(roleId);
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(checkedKeys,menuTreeVos);
        return ResponseResult.okResult(vo);
    }


}
