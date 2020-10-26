package com.chintec.ikks.common.entity.response;

import lombok.Data;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/26 16:41
 */
@Data
public class DepartTaskResponse {

    private Integer id;

    private String name;

    private String companyName;

    private String categoryName;

    private String status;
}
