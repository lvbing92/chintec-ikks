package com.chintec.ikks.message.controller;

import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.message.service.IUploadFileService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1")
@Api(value = "文件上传功能管理", tags = {"文件上传功能管理"})
public class UploadFileController {


    @Autowired
    private IUploadFileService iUploadFileService;

    /**
     * 保存视频
     *
     *
     * @param file
     * @return
     */
    @PostMapping("/imgFiles")
    public ResultResponse uploadImg(MultipartFile file) {
        return iUploadFileService.uploadImg2Oss(file);
    }
}
