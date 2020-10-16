package com.chintec.ikks.erp.request;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/10/10 11:05
 */
@Data
public class DepartmentRequest {

    private Integer id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 是否默认
     */
    private Boolean isDefault;

}
