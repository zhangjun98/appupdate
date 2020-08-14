package com.ztkj.platform.update.common;

import lombok.Data;

/**
 * @Author: zhang-jun
 * @Date: 2020/8/8 12:28
 * @Description:
 */
@Data
public class haveNewVersionResponse<T> extends CommonResponse {
    T data;

    public haveNewVersionResponse(String code, String message) {
        super(code, message);
    }
    public haveNewVersionResponse(String code, String message,T data){
        super(code, message);
       this.data=data;
    }
}
