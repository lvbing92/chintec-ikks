package com.chintec.ikks.erp.controller;

import com.chintec.ikks.common.util.ResultResponse;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/26 14:16
 */
@RestController
@RequestMapping("v1")
@Api(value = "UpLoad", tags = {"文件上传管理"})
public class UpLoadController {
    public ResultResponse uploadImage() {
            return null;
    }

    public ResultResponse uploadVideos() {
        return null;
    }

    public ResultResponse uploadFile() {
        return null;
    }
}
