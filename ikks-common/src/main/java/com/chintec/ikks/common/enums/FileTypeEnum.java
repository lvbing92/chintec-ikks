package com.chintec.ikks.common.enums;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/11/3 14:24
 */
public enum FileTypeEnum {
    /**
     * 枚举值
     */
    FILE_TYPE_ENUM(1, "文件"),
    IMG_TYPE_ENUM(2, "图片"),
    VIDEO_TYPE_ENUM(3, "视频");

    private Integer code;
    private String msg;

    FileTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
