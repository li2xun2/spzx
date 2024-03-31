package com.atguigu.service.impl;

import com.atguigu.mapper.ProductSpecMapper;
import com.atguigu.service.ProductSpecService;
import com.atguigu.spzx.model.entity.product.ProductSpec;
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
public class ProductSpecServiceImpl implements ProductSpecService {

    @Resource
    private ProductSpecMapper productSpecMapper;
    @Override
    public PageInfo<ProductSpec> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);

      List<ProductSpec> productSpecList=productSpecMapper.findByPage();

        PageInfo<ProductSpec> productSpecPageInfo = new PageInfo<>(productSpecList);


        return productSpecPageInfo;
    }

    @Override
    public void save(ProductSpec productSpec) {

        productSpecMapper.save(productSpec);
    }

    @Override
    public void updateById(ProductSpec productSpec) {
        productSpecMapper.updateById(productSpec);
    }

    @Override
    public void deleteById(Long id) {
        productSpecMapper.deleteById(id);
    }

    @Override
    public List<ProductSpec> findAll() {
        return productSpecMapper.findAll();
    }
}
