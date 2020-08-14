package com.ztkj.platform.update.common;

import lombok.Data;

/**
 * @Author: zhang-jun
 * @Date: 2020/8/8 11:57
 * @Description: 返回前端的参数
 */
@Data
public  class  CommonResponse {
    String code;
    String message;
    public CommonResponse(String code,String message){
        this.code=code;
        this.message=message;
    }
}
