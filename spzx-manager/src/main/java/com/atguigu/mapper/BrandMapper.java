package com.atguigu.mapper;


import com.atguigu.spzx.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BrandMapper {
    List<Brand> findByPage();

    void insert(Brand brand);

    void update(Brand brand);

    void deleteById(Long id);

    List<Brand> findAll();
}
