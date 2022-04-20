package com.viettel.his.common.rest;

import org.springframework.http.ResponseEntity;

public abstract class AbstractRestController {

    protected <T> ResponseEntity<T> responseOk(T data){
        return ResponseEntity.ok(data);
    }

}
