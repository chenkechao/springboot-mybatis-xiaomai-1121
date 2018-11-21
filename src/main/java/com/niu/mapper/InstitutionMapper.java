package com.niu.mapper;

import com.niu.common.MyMapper;
import com.niu.common.SqlMapper;
import com.niu.entity.Institution;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by ami on 2018/11/20.
 */
@Mapper
//public interface InstitutionMapper extends SqlMapper<Institution> {
//}
public interface InstitutionMapper extends MyMapper<Institution> {
}