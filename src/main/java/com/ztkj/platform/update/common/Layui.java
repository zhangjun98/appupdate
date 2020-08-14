package com.ztkj.platform.update.common;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: zhang-jun
 * @Date: 2020/8/13 16:10
 * @Description:
 */
public class Layui extends HashMap<String, Object> {

    public static Layui data(Integer count, List<?> data) {
        Layui r = new Layui();
        r.put("code", 0);
        r.put("msg", "");
        r.put("count", count);
        r.put("data", data);
        return r;
    }
}
