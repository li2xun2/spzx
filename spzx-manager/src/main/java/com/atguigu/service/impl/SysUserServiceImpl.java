package com.atguigu.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.exception.SpzxException;
import com.atguigu.mapper.SysUserMapper;
import com.atguigu.mapper.SysUserRoleMapper;
import com.atguigu.service.SysUserService;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@Transactional
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public LoginVo login(LoginDto loginDto) {

//        校验步骤:
//        获取输入验证码和存储到redis的key名称 loginDto获取到
        String codeKey = loginDto.getCodeKey(); //后端传的redis验证码 key
        String codeValue = loginDto.getCaptcha();//输入的验证码



//        根据获取的redis里面的key，查询redis里面验证码
        String redisCodeValue = redisTemplate.opsForValue().get("user:login:codekey:" + codeKey);
//        比较输入验证码和redis验证码是否正确
        if(StrUtil.isEmpty(redisCodeValue) || !StrUtil.equalsIgnoreCase(redisCodeValue,codeValue))
//        不一致抛出异常
        {

            throw new SpzxException(ResultCodeEnum.VALIDATECODE_ERROR);//验证失败
        }
//        一致就删除redis的验证码
        redisTemplate.delete("user:login:codekey:" + codeKey);




      //登录步骤
//        第一步获取用户名 通过数据库查询看是否为空 为空则用户不存在
        String userName = loginDto.getUserName();

      SysUser sysUser= sysUserMapper.selectUserInfoByUserName(userName);
       if(sysUser==null) throw  new RuntimeException("用户不存在");

//       第二步如果不为空 让输入的密码 和 从数据库查出来的密码进行比较
//                由于输入的密码是明文 而数据库中的密码是MD5加密过的
//                        所以需要将明文用MD5加密 再比较
        String database_password = sysUser.getPassword();
        String input_password = loginDto.getPassword();

       input_password = DigestUtils.md5DigestAsHex(input_password.getBytes());
         if(!input_password.equalsIgnoreCase(database_password))
         {
             throw  new RuntimeException("登录失败");
         }
         //如果密码比对成功 则生成一个token（唯一标识符） 并存在redis里面
        String token =  UUID.randomUUID().toString().replaceAll("-","");
        redisTemplate.opsForValue().set(token, JSON.toJSONString(sysUser),7, TimeUnit.DAYS);
        String sysUserJson = redisTemplate.opsForValue().get(token);
        SysUser getSysUser = JSON.parseObject(sysUserJson, SysUser.class);
        LoginVo loginVo = new LoginVo();
         loginVo.setToken(token);
        log.info("token信息{}",token);
        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {

        String sysUserJson = redisTemplate.opsForValue().get(token);
        SysUser sysUser = JSON.parseObject(sysUserJson, SysUser.class);
        log.info("用户信息{}，token信息{}",sysUserJson,token);
        return sysUser;


    }
    @Override
    public void logout(String token) {
        redisTemplate.delete(token);

    }

    @Override
    public PageInfo<SysUser> getSysUserInfo(SysUserDto sysUserDto,Integer pageSize,Integer pageNum) {

            PageHelper.startPage(pageNum, pageSize);

            List<SysUser> sysUserList =sysUserMapper.selectUserInfo(sysUserDto);
            sysUserList.size();

        PageInfo listPageInfo = new PageInfo<>(sysUserList);
        log.info("用户信息{}",listPageInfo);
        return listPageInfo;
    }

    @Override
    public void insertUser(SysUser sysUser) {

        String userName = sysUser.getUserName();
        SysUser dbsysUser = sysUserMapper.selectUserInfoByUserName(userName);
        if(dbsysUser!=null) throw new SpzxException(ResultCodeEnum.USER_NAME_IS_EXISTS);

        String password = sysUser.getPassword();
        String s = DigestUtils.md5DigestAsHex(password.getBytes());
        sysUser.setPassword(s);

        sysUser.setStatus(1);
        sysUserMapper.insertSysUser(sysUser);
    }

    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.updateSysUser(sysUser);

    }

    @Override
    public void deleteById(Long userId) {
        sysUserMapper.deleteById(userId) ;
    }

    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {
        //要想保存数据 需要现根据用户id删除原有数据 再添加新的
        sysUserRoleMapper.deleteId(assginRoleDto.getUserId());
        List<Long> roleIdList = assginRoleDto.getRoleIdList();
        for (Long roleId : roleIdList) {
            sysUserRoleMapper.doAssign(assginRoleDto.getUserId(),roleId);
        }

    }
}
