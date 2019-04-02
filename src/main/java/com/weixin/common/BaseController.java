package com.weixin.common;


import net.sf.json.JSONObject;

/**
 * @create 2019/3/13
 * @since 1.0.0
 */

public class BaseController {
    public static String renderJson(R r){
        return JSONObject.fromObject(r).toString();
    }
}
