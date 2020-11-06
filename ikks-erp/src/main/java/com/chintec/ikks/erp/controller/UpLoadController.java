package com.chintec.ikks.erp.controller;

import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.erp.feign.IUploadFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/26 14:16
 */
@RestController
@RequestMapping("v1")
@Api(value = "UpLoad", tags = {"文件上传管理"})
@Slf4j
public class UpLoadController {
    @Autowired
    private IUploadFileService iUploadFileService;

    @PostMapping("images")
    @ApiOperation("图片上上传")
    public ResultResponse uploadImage(MultipartFile file) {
        log.info("file size {}", file.getSize());
        AssertsUtil.isTrue(file.getSize() > 1024 * 1024 * 10, "图片过大,超出尺寸");
        return iUploadFileService.uploadImg(file);
    }

    @PostMapping("videos")
    @ApiOperation("视频/音频上传")
    public ResultResponse uploadVideos(MultipartFile file) {
        log.info("file size {}", file.getSize());
        AssertsUtil.isTrue(file.getSize() > 1024 * 1024 * 29, "视频过大,超出尺寸");
        return iUploadFileService.uploadVideo(file);
    }

    @PostMapping("files")
    @ApiOperation("文件上传")
    public ResultResponse uploadFile(MultipartFile file) {
        log.info("file size {}", file.getSize());
        AssertsUtil.isTrue(file.getSize() > 1024 * 1024 * 10, "文件过大,超出尺寸");
        return iUploadFileService.uploadFile(file);
    }
}
