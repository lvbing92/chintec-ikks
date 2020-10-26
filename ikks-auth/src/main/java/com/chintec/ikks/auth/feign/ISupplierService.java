package com.chintec.ikks.auth.feign;

import com.chintec.ikks.common.util.ResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/10/26 17:59
 */
@FeignClient(value = "ikks-supplier",path = "v1")
public interface ISupplierService {
    @GetMapping("/supplier/{id}")
    ResultResponse supplier(@PathVariable Integer id);
}
