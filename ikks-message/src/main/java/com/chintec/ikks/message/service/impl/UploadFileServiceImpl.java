package com.chintec.ikks.message.service.impl;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.chintec.ikks.common.entity.BlobUpload;
import com.chintec.ikks.common.enums.CommonCodeEnum;
import com.chintec.ikks.common.enums.FileTypeEnum;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.message.service.IUploadFileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.*;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

    private static final Logger log = LogManager.getLogger(UploadFileServiceImpl.class);

    @Value("${spring.oss.endpoint}")
    private String endpoint;

    @Value("${spring.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${spring.oss.accessKeySecret}")
    private String accessKeySecret;

    @Resource
    private OSS ossClient;

    private String bucketName;

    @Value("${spring.oss.bucketNameVideo}")
    private String bucketNameVideo;
    @Value("${spring.oss.bucketNameImg}")
    private String bucketNameImg;
    @Value("${spring.oss.bucketNameFile}")
    private String bucketNameFile;

    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @Override
    public ResultResponse uploadImg2Oss(MultipartFile file, Integer type) {
        bucketName = getBucketName(type);
        // 设置存储空间的访问权限为public。
        ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = getObjectMetadata(file);
            // 创建PutObjectRequest对象。
            String fileName = getFileName(file);
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, fileName,
                    new ByteArrayInputStream(file.getBytes()), objectMetadata);
            log.info("上传结果:{}", putObjectResult.getETag());
            Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
            URL url = ossClient.generatePresignedUrl(bucketName, fileName, expiration);
            log.info("上传URL：{}", url);
            BlobUpload upload = new BlobUpload();
            upload.setFileUrl(url.toString());
            return ResultResponse.successResponse("上传文件成功", upload);
        } catch (Exception oe) {
            oe.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        return ResultResponse.failResponse(CommonCodeEnum.COMMON_FALSE_CODE.getCode(), "上传图片失败");
    }

    @Override
    public ResultResponse uploadImgOssToken(Integer type) {
        String roleArn = "acs:ram::1106804457081196:role/ramosstest";
        String roleSessionName = "web";
        String policy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"oss:*\"\n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:oss:*:*:*\" \n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        try {
            // 添加endpoint（直接使用STS endpoint，前两个参数留空，无需添加region ID）
            DefaultProfile.addEndpoint("", "", "Sts", endpoint);
            // 构造default profile（参数留空，无需添加region ID）
            IClientProfile profile = DefaultProfile.getProfile("", accessKeyId, accessKeySecret);
            // 用profile构造client
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setMethod(MethodType.POST);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            // 若policy为空，则用户将获得该角色下所有权限
            request.setPolicy(policy);
            // 设置凭证有效时间
            request.setDurationSeconds(1000L);
            final AssumeRoleResponse response = client.getAcsResponse(request);
            Map<String, Object> result = new HashMap<>(2);
            result.put("credentials", response.getCredentials());
            result.put("bucket", getBucketName(type));
            return ResultResponse.successResponse(result);
        } catch (com.aliyuncs.exceptions.ClientException e) {
            e.printStackTrace();
        }
        return ResultResponse.failResponse("获取失败");
    }

    private String getFileName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        return random.nextInt(10000) + System.currentTimeMillis() + substring;
    }

    private ObjectMetadata getObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setCacheControl("no-cache");
        objectMetadata.setHeader("Pragma", "no-cache");
        objectMetadata.setContentType(getContentType(Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."))));
        objectMetadata.setContentDisposition("inline;filename=" + file.getName());
        return objectMetadata;
    }

    private String getContentType(String filenameExtension) {
        if (".bmp".equalsIgnoreCase(filenameExtension)) {
            return "image/bmp";
        }

        if (".gif".equalsIgnoreCase(filenameExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(filenameExtension) || ".jpg".equalsIgnoreCase(filenameExtension)
                || ".png".equalsIgnoreCase(filenameExtension)) {
            return "image/jpg";
        }
        if (".html".equalsIgnoreCase(filenameExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(filenameExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.visio";
        }
        if (".pptx".equalsIgnoreCase(filenameExtension) || ".ppt".equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".docx".equalsIgnoreCase(filenameExtension) || ".doc".equalsIgnoreCase(filenameExtension)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(filenameExtension)) {
            return "text/xml";
        }
        if (".pdf".equalsIgnoreCase(filenameExtension)) {
            return "application/pdf";
        }
        return "image/jpg";
    }


    private String getBucketName(Integer type) {
        if (FileTypeEnum.FILE_TYPE_ENUM.getCode().equals(type)) {
            return bucketNameFile;
        } else if (FileTypeEnum.IMG_TYPE_ENUM.getCode().equals(type)) {
            return bucketNameImg;
        } else if (FileTypeEnum.VIDEO_TYPE_ENUM.getCode().equals(type)) {
            return bucketNameVideo;
        }
        return null;
    }
}
