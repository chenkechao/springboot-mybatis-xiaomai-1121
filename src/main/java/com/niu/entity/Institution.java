package com.niu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ami on 2018/11/20.
 */

@Table(name = "institution")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Institution {
    @Id
    @Column(name = "inst_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long instId;

    /**
     * 机构账号
     */
    @Column(name = "inst_admin")
    private String instAdmin;

    /**
     * 机构名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 机构描述
     */
    @Column(name = "description")
    private String description;


    @Column(name = "gmt_create")
    private Date gmtCreate;

    @Column(name = "gmt_modify")
    private Date gmtModify;

    public static class column {
        public static final String instId = "instId";

        public static final String instAdmin = "instAdmin";

        public static final String name = "name";

        public static final String description = "description";

        public static final String gmtCreate = "gmtCreate";

        public static final String gmtModify = "gmtModify";

    }
}
