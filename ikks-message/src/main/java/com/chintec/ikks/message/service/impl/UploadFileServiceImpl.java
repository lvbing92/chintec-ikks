package com.chintec.ikks.message.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.chintec.ikks.common.enums.CommonCodeEnum;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.message.entity.BlobUpload;
import com.chintec.ikks.message.service.IUploadFileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Date;
import java.util.Random;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

    private static final Logger log = LogManager.getLogger(UploadFileServiceImpl.class);

    @Value("${spring.oos.endpoint}")
    private String endpoint;

    @Value("${spring.oos.accessKeyId}")
    private String accessKeyId;

    @Value("${spring.oos.accessKeySecret}")
    private String accessKeySecret;

    @Value("${spring.oos.bucketName}")
    private String bucketName;

    /**
     * 得到阿里云的容器
     */
    private OSS getContainer() {

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {

            if (ossClient.doesBucketExist(bucketName)) {
                System.out.println("您已经创建Bucket：" + bucketName + "。");
            } else {
                System.out.println("您的Bucket不存在，创建Bucket：" + bucketName + "。");
                // 创建Bucket。
                ossClient.createBucket(bucketName);
            }
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ossClient;
    }

    /**
     * 上传图片
     *
     * @param file
     *
     * @return
     */
    @Override
    public ResultResponse uploadImg2Oss(MultipartFile file) {

        OSS ossClient = getContainer();
        // 设置存储空间的访问权限为public。
        ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        try {

            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = getObjectMetadata(file);

            // 创建PutObjectRequest对象。
            String fileName=getFileName(file);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName,
                    new ByteArrayInputStream(file.getBytes()),objectMetadata);
            PutObjectResult result=ossClient.putObject(putObjectRequest);

            log.info("上传结果："+result.getETag());
            Date expiration = new Date(System.currentTimeMillis()+3600L * 1000 * 24 * 365 * 10);
            URL url = ossClient.generatePresignedUrl(bucketName,fileName,expiration);
            log.info("上传URL："+url);

            BlobUpload upload=new BlobUpload();
            upload.setFileUrl(url.toString());
            return ResultResponse.successResponse("上传图片成功",upload);

        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }

        return ResultResponse.failResponse(CommonCodeEnum.COMMON_FALSE_CODE.getCode(),"上传图片失败");
    }

    private String getFileName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String fileName = random.nextInt(10000) + System.currentTimeMillis() + substring;
        return fileName;
    }

    private ObjectMetadata getObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setCacheControl("no-cache");
        objectMetadata.setHeader("Pragma", "no-cache");
        objectMetadata.setContentType(getContentType(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."))));
        objectMetadata.setContentDisposition("inline;filename=" + file.getName());
        return objectMetadata;
    }

    private  String getContentType(String filenameExtension) {
        if (filenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }

        if (filenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (filenameExtension.equalsIgnoreCase(".jpeg") || filenameExtension.equalsIgnoreCase(".jpg")
                || filenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (filenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (filenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (filenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (filenameExtension.equalsIgnoreCase(".pptx") || filenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (filenameExtension.equalsIgnoreCase(".docx") || filenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (filenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        if (filenameExtension.equalsIgnoreCase(".pdf")) {
            return "application/pdf";
        }
        return "image/jpeg";
    }
}
