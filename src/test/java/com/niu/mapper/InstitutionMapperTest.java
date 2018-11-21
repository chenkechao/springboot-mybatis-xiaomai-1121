package com.niu.mapper;

import com.niu.entity.Institution;
import com.niu.query.InstQuery;
import com.niu.query.InstitutionQuery;
import com.niu.utils.DateUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

import java.util.List;

/**
 * Created by ami on 2018/11/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InstitutionMapperTest {

    @Resource
    private InstitutionMapper institutionMapper;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGet() {
        List<Institution> institutions = institutionMapper.selectAll();
        institutions.forEach(System.out::println);

    }

    @Test
    public void testGetLike() {
        InstitutionQuery institutionQuery = InstitutionQuery.builder().instAdminLike("100").build();

        List<Institution> institutions = institutionMapper.selectByExample(institutionQuery.toExample());
        institutions.forEach(System.out::println);
    }

    @Test
    public void testGetLikeExample() {
        InstQuery instQuery = InstQuery.builder().instAdminLike("100").build();
        List<Institution> institutions = institutionMapper.selectByExample(instQuery.toExample());
        institutions.forEach(System.out::println);
    }

    @Test
    public void testGetLike2() {
        Example example = new Example(Institution.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("instAdmin", "%" + 100 + "%");
        List<Institution> institutions = institutionMapper.selectByExample(example);
        institutions.forEach(System.out::println);
    }

    @Test
    public void testGetBetween() {

        InstitutionQuery institutionQuery = InstitutionQuery.builder()
                .beginTime(DateUtils.stringToDate("2018-11-19","yyyy-MM-dd"))
                .endTime(DateUtils.stringToDate("2018-11-21","yyyy-MM-dd")).build();
        Example example = institutionQuery.toExample();
        List<Institution> institutions = institutionMapper.selectByExample(example);
        institutions.forEach(System.out::println);
    }




}