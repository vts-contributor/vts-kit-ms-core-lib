package com.viettel.vtskit.common.rest;

import com.viettel.vtskit.common.utils.RestUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController extends AbstractRestController{

    @GetMapping("/health/status")
    public ResponseEntity<String> getHealthStatus(){
        return RestUtils.responseOk("ok");
    }

}
