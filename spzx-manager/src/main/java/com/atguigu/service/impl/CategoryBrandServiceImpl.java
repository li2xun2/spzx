package com.atguigu.service.impl;

import com.atguigu.mapper.CategoryBrandMapper;
import com.atguigu.service.CategoryBrandService;
import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
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
public class CategoryBrandServiceImpl implements CategoryBrandService {

    @Resource
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    public PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto) {
        PageHelper.startPage(page,limit);

      List<CategoryBrand> categoryBrandList= categoryBrandMapper.findByPage(categoryBrandDto);

        PageInfo<CategoryBrand> categoryBrandPageInfo = new PageInfo<>(categoryBrandList);

        return categoryBrandPageInfo;
    }

    @Override
    public void save(CategoryBrand categoryBrand) {

        categoryBrandMapper.save(categoryBrand);

    }

    @Override
    public void updateById(CategoryBrand categoryBrand) {

        categoryBrandMapper.updateById(categoryBrand);
    }

    @Override
    public void deleteById(Long id) {
        categoryBrandMapper.deleteById(id);
    }

    @Override
    public List<Brand> findBrandByCategoryId(Long categoryId) {


        return categoryBrandMapper.findBrandByCategoryId(categoryId);
    }
}
