package com.atguigu.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.atguigu.service.ValidateCodeService;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public ValidateCodeVo generateValidateCode() {

        //通过hutool工具生成图片
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 5);
        String codeValue = circleCaptcha.getCode();
        String imageBase64 = circleCaptcha.getImageBase64();
        String codeKey = UUID.randomUUID().toString().replaceAll("-", "");


        //把验证码存到redis里面 并设置k=uuid v=验证码 存储时长
         redisTemplate.opsForValue().set("user:login:codekey:"+codeKey,codeValue,3, TimeUnit.MINUTES);
         ValidateCodeVo validateCodeVo =new ValidateCodeVo();
         validateCodeVo.setCodeKey(codeKey);
         validateCodeVo.setCodeValue("data:image/png;base64,"+imageBase64);
        return validateCodeVo;
    }
}
