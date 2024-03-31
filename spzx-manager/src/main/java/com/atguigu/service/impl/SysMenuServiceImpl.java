package com.atguigu.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.atguigu.exception.SpzxException;
import com.atguigu.helper.MenuHelper;
import com.atguigu.mapper.SysMenuMapper;
import com.atguigu.service.SysMenuService;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.atguigu.utils.AuthContextUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> findNodes() {

     List<SysMenu> sysMenuList =   sysMenuMapper.findNodes();
     log.info("查出的树形菜单为{}",sysMenuList);

     if(CollectionUtil.isEmpty(sysMenuList)) return null;

        List<SysMenu> treeList = MenuHelper.buildTree(sysMenuList); //构建树形数据

        log.info("转化的树形菜单为{}",treeList);
        return treeList;
    }

    @Override
    public void save(SysMenu sysMenu) {
        sysMenu.setStatus(0);
        sysMenuMapper.save(sysMenu);

    }

    @Override
    public void updateById(SysMenu sysMenu) {
        sysMenuMapper.update(sysMenu);
    }

    @Override
    public void removeById(Long id) {

       int count =  sysMenuMapper.selectById(id);

       if(count>0) throw new SpzxException(ResultCodeEnum.NODE_ERROR);

       sysMenuMapper.removeById(id);

    }

    @Override
    public List<SysMenuVo> findUserMenuList() {

        SysUser sysUser = AuthContextUtil.get();

        List<SysMenu> listMenu = sysMenuMapper.selectMenuListById(sysUser.getId());
        List<SysMenu> sysMenus = MenuHelper.buildTree(listMenu);

        List<SysMenuVo> MenuTrees=  buildTrees(sysMenus);


        return MenuTrees;
    }

    public static List<SysMenuVo> buildTrees(List<SysMenu> sysMenus)
    {

        List<SysMenuVo> treeMenu = new ArrayList<>();

        for (SysMenu sysMenu : sysMenus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            if(!CollectionUtil.isEmpty(sysMenu.getChildren()))
            {
                sysMenuVo.setChildren(buildTrees(sysMenu.getChildren()));
            }
            treeMenu.add(sysMenuVo);

        }

        return treeMenu;
    }
}
