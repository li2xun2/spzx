package com.atguigu.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.utils.AuthContextUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Component
public class LoginAuthInterceptor implements HandlerInterceptor {
     @Resource
     private RedisTemplate redisTemplate;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
     // 获取请求方式 如果是跨域预检请求，直接放行
        String method = request.getMethod();
        if("OPTIONS".equalsIgnoreCase(method)) return true;

        // 获取token 如果为空就返回208
        String token = request.getHeader("token");
        if(StrUtil.isEmpty(token))
        {
            responseNoLoginInfo(response);
            return false;
        }
        // 如果token不为空，那么此时验证token的合法性
        String sysUserInfoJson = (String) redisTemplate.opsForValue().get(token);
        if(StrUtil.isEmpty(sysUserInfoJson))
        {
            responseNoLoginInfo(response);
            return false;

        }
        // 将用户数据存储到ThreadLocal中
        SysUser sysUser = JSON.parseObject(sysUserInfoJson, SysUser.class);
        AuthContextUtil.set(sysUser);
        // 重置Redis中的用户数据的有效时间
        redisTemplate.expire(token,1, TimeUnit.HOURS);
        // 放行
        return true;
    }

    //响应208 状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {


        AuthContextUtil.delete();

    }
}
