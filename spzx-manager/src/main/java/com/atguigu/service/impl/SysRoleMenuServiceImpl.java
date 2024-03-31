package com.atguigu.service.impl;


import com.atguigu.mapper.SysMenuMapper;
import com.atguigu.mapper.SysRoleMenuMapper;
import com.atguigu.service.SysRoleMenuService;
import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Resource
    private  SysMenuMapper   sysMenuMapper;

    /***
     * 根据角色的id查询出其对应的菜单id，并且需要将系统中所有的菜单数据查询出来。
     * @param roleId
     * @return
     */
    @Override
    public Map<String, Object> findSysRoleMenuByRoleId(Long roleId) {

        HashMap<String,Object> result = new HashMap<>();

        List<SysMenu> sysMenuList = sysMenuMapper.findNodes();

       List<Long> roleMenuIds = sysRoleMenuMapper.findSysRoleMenuByRoleId(roleId);


        result.put("sysMenuList" , sysMenuList) ;
        result.put("roleMenuIds" , roleMenuIds) ;
        return result;
    }

    @Override
    public void doAssign(AssginMenuDto assginMenuDto) {
        Long roleId = assginMenuDto.getRoleId();

        sysRoleMenuMapper.deleteByRoleId(roleId);
        log.info("{}",roleId);
        if(assginMenuDto.getMenuIdList()!=null && assginMenuDto.getMenuIdList().size()>0)
        sysRoleMenuMapper.save(assginMenuDto);
    }
}
