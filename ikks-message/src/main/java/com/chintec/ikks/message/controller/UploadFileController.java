package com.chintec.ikks.message.controller;

import com.chintec.ikks.common.enums.FileTypeEnum;
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
     * 保存图片
     *
     * @param file
     * @return
     */
    @PostMapping("/img")
    public ResultResponse uploadImg(MultipartFile file) {
        return iUploadFileService.uploadImg2Oss(file, FileTypeEnum.IMG_TYPE_ENUM.getCode());
    }

    /**
     * 保存文件
     *
     * @param file
     * @return
     */
    @PostMapping("/files")
    public ResultResponse uploadFile(MultipartFile file) {
        return iUploadFileService.uploadImg2Oss(file, FileTypeEnum.FILE_TYPE_ENUM.getCode());
    }

    /**
     * 保存视频
     *
     * @param file
     * @return
     */
    @PostMapping("/video")
    public ResultResponse uploadVideo(MultipartFile file) {
        return iUploadFileService.uploadImg2Oss(file, FileTypeEnum.VIDEO_TYPE_ENUM.getCode());
    }
}
