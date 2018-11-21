package com.niu.query;

import com.niu.entity.InstitutionExample;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by ami on 2018/11/21.
 */
@Data
@Builder
public class InstQuery {
    private Long instId;
    private List<Long> instIds;
    private String instAdminLike;
    private String nameLike;
    private String description;
    private Date gmtCreate;
    private Date gmtModify;
    private Date beginTime;
    private Date endTime;

    public InstitutionExample toExample() {
        InstitutionExample example = new InstitutionExample();
        InstitutionExample.Criteria condition = example.createCriteria();

        if (null != instId) {
            condition.andInstIdEqualTo(instId);
        }
        if (CollectionUtils.isNotEmpty(instIds)) {
            condition.andInstIdIn(instIds);
        }
        if (StringUtils.isNotEmpty(instAdminLike)) {
            condition.andInstAdminLike(instAdminLike);
        }
        if (StringUtils.isNotEmpty(nameLike)) {
            condition.andNameLike(nameLike);
        }
        if (StringUtils.isNotEmpty(description)) {
            condition.andDescriptionEqualTo(description);
        }
        if (null != gmtCreate) {
            condition.andGmtCreateEqualTo(gmtCreate);
        }
        if (null != gmtModify) {
            condition.andGmtModifyEqualTo(gmtModify);
        }
        if (null != beginTime && null != endTime) {
            condition.andGmtCreateBetween(beginTime,endTime);
        }
        return example;
    }


}
