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
    @PostMapping(value = "/imgFiles",consumes = "multipart/form-data")
   ResultResponse uploadImg(MultipartFile file);
}
