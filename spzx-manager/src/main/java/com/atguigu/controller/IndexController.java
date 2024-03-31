package com.atguigu.controller;


import com.atguigu.properties.UserAuthProperties;
import com.atguigu.service.SysMenuService;
import com.atguigu.service.SysUserService;
import com.atguigu.service.ValidateCodeService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import com.atguigu.utils.AuthContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "用户接口")
@RequestMapping("/admin/system/index")
@EnableConfigurationProperties(value = {UserAuthProperties.class})
@Slf4j
public class IndexController {

    @Resource

    private SysUserService sysUserService;

    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private ValidateCodeService validateCodeService;

    @PostMapping("login")
    @Operation(summary = "登录功能")
    public Result login(@RequestBody LoginDto loginDto)
    {
      LoginVo loginVo = sysUserService.login(loginDto);
      return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }
    /***
     * 生成图片验证码
     */
    @GetMapping("/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode()
    {
       ValidateCodeVo validateCodeVo= validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo,ResultCodeEnum.SUCCESS);
    }
    @GetMapping("/getUserInfo")
    public Result getUserInfo()
    {
        SysUser userInfo = AuthContextUtil.get();

        return Result.build(userInfo,ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage());
    }

    @GetMapping("/logout")
    public Result logout(@RequestHeader("token") String token)
    {
        sysUserService.logout(token);
        return Result.build(null,ResultCodeEnum.SUCCESS);

    }

    @GetMapping("/menus")
    public Result menus() {
        List<SysMenuVo> sysMenuVoList =  sysMenuService.findUserMenuList() ;
        return Result.build(sysMenuVoList , ResultCodeEnum.SUCCESS) ;
    }
}
