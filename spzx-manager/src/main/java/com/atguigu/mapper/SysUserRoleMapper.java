package com.atguigu.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserRoleMapper {


    void deleteId(Long userId);

    void doAssign(Long userId, Long roleId);

    List<Long> findSysUserRoleByUserId(Long userId);
}
