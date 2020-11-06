package com.chintec.ikks.message.service;

import com.chintec.ikks.common.util.ResultResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Jack
 * @version 1.0
 * @date 2020/7/6 13:08
 */
public interface IUploadFileService {


    /**
     * 保存图片
     *
     * @param file
     * @return
     */
    ResultResponse uploadImg2Oss(MultipartFile file, Integer type);
}
