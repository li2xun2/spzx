package com.atguigu.log.mapper;

import com.atguigu.spzx.model.entity.system.SysOperLog;
import jakarta.validation.constraints.Max;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.Mapping;


@Mapper
public interface SysOperLogMapper {
    void insert(SysOperLog sysOperLog);
}
