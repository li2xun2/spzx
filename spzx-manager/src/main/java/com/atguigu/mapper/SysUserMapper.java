package com.atguigu.mapper;

import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {
     SysUser selectUserInfoByUserName(String userName) ;

     List<SysUser> selectUserInfo(SysUserDto sysUserDto);

     void insertSysUser(SysUser sysUser);

     void updateSysUser(SysUser sysUser);

     void deleteById(Long userId);
}
