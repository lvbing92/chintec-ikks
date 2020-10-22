package com.chintec.ikks.feign;

import com.chintec.ikks.common.entity.Credentials;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author rubin.lv
 * @since 2020年10月22日
 */
@FeignClient(value = "ikks-auth", path = "v1")
public interface ICredentialsService {

    /**
     * 添加登录信息
     *
     * @param credentials 用户登录信息
     * @return boolean
     */
    @ApiOperation(value = "添加增用户")
    @PostMapping("/user/addLoginMsg")
    boolean addLoginMsg(@RequestBody Credentials credentials);
}
