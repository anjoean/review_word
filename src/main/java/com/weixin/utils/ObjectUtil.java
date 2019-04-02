package com.weixin.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ObjectUtil {

    private ObjectUtil() {
    }

    public static boolean isNotBlank(Object obj) {
        return !isBlank(obj);
    }

    public static boolean isBlank(Object obj) {
        if (obj == null) {
            return true;
        }
        return StrUtil.isBlank(obj.toString());
    }

    public static final String parseToString(Object obj) {
        return obj != null ? obj.toString() : "";
    }

    public static String transferSearchMap(Map<String, Object> params) {
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() != null && entry.getValue() != "") {
                    Map<String, Object> sub = new HashMap<String, Object>();
                    sub.put("fieldName", (String) entry.getKey());
                    sub.put("fieldValue", (String) entry.getValue());
                    ret.add(sub);
                }

            }
        }
        return JSONObject.toJSONString(ret);
    }
}
