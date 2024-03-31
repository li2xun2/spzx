package com.atguigu.service;

import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface SysRoleService {
    PageInfo<SysRole> findByPage(Integer pageNum, Integer pageSize, SysRoleDto sysRoleDto);

    void saveRole(SysRole sysRole);

    void updateRole(SysRole sysRole);

    void deleteRole(Long roleId);

    Map<String, Object> findAllRoles(Long userId);
}
