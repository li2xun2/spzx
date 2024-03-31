package com.atguigu.service.impl;

import com.atguigu.mapper.ProductUnitMapper;
import com.atguigu.service.ProductUnitService;
import com.atguigu.spzx.model.entity.base.ProductUnit;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@Slf4j
public class ProductUnitServiceImpl implements ProductUnitService {

    @Resource
    private ProductUnitMapper productUnitMapper;
    @Override
    public List<ProductUnit> findAll() {
        List<ProductUnit> productUnitList = productUnitMapper.findAll();

        return productUnitList;
    }
}
