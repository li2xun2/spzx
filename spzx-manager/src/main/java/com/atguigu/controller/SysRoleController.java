package com.atguigu.controller;

import com.atguigu.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/system/sysRole")
@Slf4j
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleservice;

    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public Result<PageInfo<SysRole>> findByPage (@PathVariable("pageNum") Integer pageNum,
                                                 @PathVariable("pageSize") Integer pageSize,
                                                 @RequestBody SysRoleDto sysRoleDto)
    {
      PageInfo<SysRole> pageInfo =  sysRoleservice.findByPage(pageNum,pageSize,sysRoleDto);
      log.info("分页返回的数据{}",pageInfo);
       return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
    @GetMapping(value = "/findAllRoles/{userId}")
    public Result<Map<String , Object>> findAllRoles(@PathVariable(value = "userId") Long userId) {
        Map<String, Object> resultMap = sysRoleservice.findAllRoles(userId);
        return Result.build(resultMap , ResultCodeEnum.SUCCESS)  ;
    }
    @PostMapping("/saveSysRole")
    public Result saveRole(@RequestBody SysRole sysRole)
    {
        sysRoleservice.saveRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);

    }

    @PutMapping("/updateSysRole")
    public Result updateRole(@RequestBody SysRole sysRole)
    {

         sysRoleservice.updateRole(sysRole);
         log.info("更新");
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteById/{roleId}")
    public Result deleteRole(@PathVariable(value = "roleId") Long roleId)
    {
        sysRoleservice.deleteRole(roleId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
