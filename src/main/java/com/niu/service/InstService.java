package com.niu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.niu.entity.Institution;
import com.niu.mapper.InstitutionMapper;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ami on 2018/11/21.
 */
@Service
public class InstService {
    @Resource
    private InstitutionMapper institutionMapper;

    public Institution getById(Long id) {
        return institutionMapper.selectByPrimaryKey(id);
    }

    public List<Institution> selectAllByPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<Institution> list = institutionMapper.selectAll();
//        PageInfo<Institution> pageInfo = new PageInfo<Institution>(list);
//        System.out.println("pageInfo:" + pageInfo);
        return list;
    }
    public List<Institution> selectAll() {
        List<Institution> list = institutionMapper.selectAll();
        return list;
    }
}
