package com.weixin.common;


import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yan.yuchen111@ztesoft.com
 * @create 2019/3/13
 * @since 1.0.0
 */

public class R extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 0);
        put("msg", "success");
    }

    public static R error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(500, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }
    public static R okNew(Object data) {
        R r = new R();
        r.put("data", data);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public R putAllItem(Map value) {
        super.putAll(value);
        return this;
    }

    public static String renderJson(R r){
        return JSONObject.fromObject(r).toString();
    }
}

