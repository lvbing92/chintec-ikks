package com.chintec.ikks.erp.feign;

import com.chintec.ikks.common.util.ResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/11/2 14:17
 */
@FeignClient(value = "ikks-message", path = "/v1")
public interface IUploadFileService {

    /**
     * 保存图片
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/img", consumes = "multipart/form-data")
    ResultResponse uploadImg(MultipartFile file);

    /**
     * 保存文件
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/files", consumes = "multipart/form-data")
    ResultResponse uploadFile(MultipartFile file);

    /**
     * 保存视频
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/video", consumes = "multipart/form-data")
    ResultResponse uploadVideo(MultipartFile file);
}
