package com.atguigu.service.impl;

import com.atguigu.mapper.SysRoleMapper;
import com.atguigu.mapper.SysUserRoleMapper;
import com.atguigu.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    @Override
    public PageInfo<SysRole> findByPage(Integer pageNum, Integer pageSize, SysRoleDto sysRoleDto) {
        PageHelper.startPage(pageNum,pageSize);

       List<SysRole> sysRoleList = sysRoleMapper.findByPage(sysRoleDto);

       PageInfo<SysRole> pageInfo = new PageInfo<>(sysRoleList);
        return pageInfo;
    }

    @Override
    public void saveRole(SysRole sysRole) {

        sysRoleMapper.save(sysRole);
    }

    @Override
    public void updateRole(SysRole sysRole) {
        sysRoleMapper.updateRole(sysRole);
    }

    @Override
    public void deleteRole(Long roleId) {
        sysRoleMapper.deleteRole(roleId);
    }

    @Override
    public Map<String, Object> findAllRoles(Long userId) {
     //查询所有角色 并且让选中的角色回显
      List<SysRole>  listRole = sysRoleMapper.findAllRoles();
        // 查询当前登录用户的角色数据
        List<Long> sysRoles = sysUserRoleMapper.findSysUserRoleByUserId(userId);
        HashMap<String, Object> listRoleMap = new HashMap<>();
        listRoleMap.put("allRolesList",listRole);
        listRoleMap.put("sysUserRoles", sysRoles);
        //
        return listRoleMap;
    }
}
