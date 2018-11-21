package com.niu.query;

import com.niu.mybatis.AbstractQuery;
import com.niu.entity.Institution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;


/**
 * Created by ami on 2018/11/19.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionQuery extends AbstractQuery<Institution> {
    private Long instId;
    private List<Long> instIds;
    private String instAdminLike;
    private String instNameLike;
    private String instAdminOrNameLike;
    private String description;
    private Date gmtCreate;
    private Date gmtModify;
    private Date beginTime;
    private Date endTime;

    @Override
    protected void addCondition(Example.Criteria criteria) {
        if (null != instId) {
            criteria.andEqualTo(Institution.column.instId, instId);
        }
        if (CollectionUtils.isNotEmpty(instIds)) {
            criteria.andIn(Institution.column.instId, instIds);
        }
        if (StringUtils.isNotBlank(instAdminLike)) {
            criteria.andLike(Institution.column.instAdmin, '%' + instAdminLike + '%');
        }
        if (StringUtils.isNotBlank(instNameLike)) {
            criteria.andLike(Institution.column.name, '%' + instNameLike + '%');
        }
        if (StringUtils.isNotBlank(instAdminOrNameLike)) {
            criteria.andCondition(
                    Institution.column.instAdmin + " like '%" + instAdminOrNameLike + "%' or " +
                    Institution.column.name + " like '%" + instAdminOrNameLike + "%'");
        }
        if (null != description) {
            criteria.andLike(Institution.column.description, '%'+description+"%");
        }
        if (beginTime != null && endTime != null) {
            criteria.andBetween(Institution.column.gmtCreate, beginTime, endTime);
        }
        if (null != gmtCreate) {
            criteria.andEqualTo(Institution.column.gmtCreate, gmtCreate);
        }
        if (null != gmtModify) {
            criteria.andEqualTo(Institution.column.gmtModify, gmtModify);
        }


    }
}
