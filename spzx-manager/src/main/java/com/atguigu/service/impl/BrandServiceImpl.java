package com.atguigu.service.impl;


import com.atguigu.mapper.BrandMapper;
import com.atguigu.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class BrandServiceImpl implements BrandService {

    @Resource
    private BrandMapper brandMapper;
    @Override
    public PageInfo<Brand> findByPage(Integer page, Integer limit) {

        PageHelper.startPage(page,limit);

       List<Brand> list =  brandMapper.findByPage();

        PageInfo<Brand> brandPageInfo = new PageInfo<>(list);

        return brandPageInfo;


    }

    @Override
    public void save(Brand brand) {

        brandMapper.insert(brand);
    }

    @Override
    public void updateById(Brand brand) {
        brandMapper.update(brand);
    }

    @Override
    public void deleteById(Long id) {
        brandMapper.deleteById(id);
    }

    @Override
    public List<Brand> findAll() {

        List<Brand> listBrand =    brandMapper.findAll();


        return listBrand;
    }
}
