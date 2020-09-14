package com.chintec.ikks.message.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Jack
 * @version 1.0
 * @date 2020/8/25
 */

@Data
@Accessors(chain = true)
public class BlobUpload {
    /**
     * 文件名
     */
    private String fileName;

    /**
     * 原文件
     */
    private String fileUrl;

    /**
     * 缩略图
     */
    private String thumbnailUrl;

}
