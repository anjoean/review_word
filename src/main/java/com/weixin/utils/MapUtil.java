package com.weixin.utils;

import org.apache.commons.collections.KeyValue;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;

public class MapUtil {

    public MapUtil() {
    }

    public static Object getObject(Map<?, ?> map, Object key) {
        return map != null ? map.get(key) : null;
    }

    public static String getString(Map<?, ?> map, Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                return answer.toString();
            }
        }

        return null;
    }

    public static Boolean getBoolean(Map<?, ?> map, Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                if (answer instanceof Boolean) {
                    return (Boolean) answer;
                }

                if (answer instanceof String) {
                    return new Boolean((String) answer);
                }

                if (answer instanceof Number) {
                    Number n = (Number) answer;
                    return n.intValue() != 0 ? Boolean.TRUE : Boolean.FALSE;
                }
            }
        }

        return null;
    }

    public static Number getNumber(Map<?, ?> map, Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                if (answer instanceof Number) {
                    return (Number) answer;
                }

                if (answer instanceof String) {
                    try {
                        String text = (String) answer;
                        return NumberFormat.getInstance().parse(text);
                    } catch (ParseException var4) {
                        logInfo(var4);
                    }
                }
            }
        }

        return null;
    }

    public static Byte getByte(Map<?, ?> map, Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else {
            return answer instanceof Byte ? (Byte) answer : new Byte(answer.byteValue());
        }
    }

    public static Short getShort(Map<?, ?> map, Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else {
            return answer instanceof Short ? (Short) answer : new Short(answer.shortValue());
        }
    }

    public static Integer getInteger(Map<?, ?> map, Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else {
            return answer instanceof Integer ? (Integer) answer : new Integer(answer.intValue());
        }
    }

    public static Long getLong(Map<?, ?> map, Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else {
            return answer instanceof Long ? (Long) answer : new Long(answer.longValue());
        }
    }

    public static Float getFloat(Map<?, ?> map, Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else {
            return answer instanceof Float ? (Float) answer : new Float(answer.floatValue());
        }
    }

    public static Double getDouble(Map<?, ?> map, Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else {
            return answer instanceof Double ? (Double) answer : new Double(answer.doubleValue());
        }
    }

    public static Map<?, ?> getMap(Map<?, ?> map, Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null && answer instanceof Map) {
                return (Map) answer;
            }
        }

        return null;
    }

    public static Object getObject(Map<?, ?> map, Object key, Object defaultValue) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                return answer;
            }
        }

        return defaultValue;
    }

    public static String getString(Map<?, ?> map, Object key, String defaultValue) {
        String answer = getString(map, key);
        if (answer == null) {
            answer = defaultValue;
        }

        return answer;
    }

    public static Boolean getBoolean(Map<?, ?> map, Object key, Boolean defaultValue) {
        Boolean answer = getBoolean(map, key);
        if (answer == null) {
            answer = defaultValue;
        }

        return answer;
    }

    public static Number getNumber(Map<?, ?> map, Object key, Number defaultValue) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            answer = defaultValue;
        }

        return answer;
    }

    public static Byte getByte(Map<?, ?> map, Object key, Byte defaultValue) {
        Byte answer = getByte(map, key);
        if (answer == null) {
            answer = defaultValue;
        }

        return answer;
    }

    public static Short getShort(Map<?, ?> map, Object key, Short defaultValue) {
        Short answer = getShort(map, key);
        if (answer == null) {
            answer = defaultValue;
        }

        return answer;
    }

    public static Integer getInteger(Map<?, ?> map, Object key, Integer defaultValue) {
        Integer answer = getInteger(map, key);
        if (answer == null) {
            answer = defaultValue;
        }

        return answer;
    }

    public static Long getLong(Map<?, ?> map, Object key, Long defaultValue) {
        Long answer = getLong(map, key);
        if (answer == null) {
            answer = defaultValue;
        }

        return answer;
    }

    public static Float getFloat(Map<?, ?> map, Object key, Float defaultValue) {
        Float answer = getFloat(map, key);
        if (answer == null) {
            answer = defaultValue;
        }

        return answer;
    }

    public static Double getDouble(Map<?, ?> map, Object key, Double defaultValue) {
        Double answer = getDouble(map, key);
        if (answer == null) {
            answer = defaultValue;
        }

        return answer;
    }

    public static Map<?, ?> getMap(Map<?, ?> map, Object key, Map<?, ?> defaultValue) {
        Map<?, ?> answer = getMap(map, key);
        if (answer == null) {
            answer = defaultValue;
        }

        return answer;
    }

    public static boolean getBooleanValue(Map<?, ?> map, Object key) {
        Boolean booleanObject = getBoolean(map, key);
        return booleanObject == null ? false : booleanObject;
    }

    public static byte getByteValue(Map<?, ?> map, Object key) {
        Byte byteObject = getByte(map, key);
        return byteObject == null ? 0 : byteObject;
    }

    public static short getShortValue(Map<?, ?> map, Object key) {
        Short shortObject = getShort(map, key);
        return shortObject == null ? 0 : shortObject;
    }

    public static int getIntValue(Map<?, ?> map, Object key) {
        Integer integerObject = getInteger(map, key);
        return integerObject == null ? 0 : integerObject;
    }

    public static long getLongValue(Map<?, ?> map, Object key) {
        Long longObject = getLong(map, key);
        return longObject == null ? 0L : longObject;
    }

    public static float getFloatValue(Map<?, ?> map, Object key) {
        Float floatObject = getFloat(map, key);
        return floatObject == null ? 0.0F : floatObject;
    }

    public static double getDoubleValue(Map<?, ?> map, Object key) {
        Double doubleObject = getDouble(map, key);
        return doubleObject == null ? 0.0D : doubleObject;
    }

    public static boolean getBooleanValue(Map<?, ?> map, Object key, boolean defaultValue) {
        Boolean booleanObject = getBoolean(map, key);
        return booleanObject == null ? defaultValue : booleanObject;
    }

    public static byte getByteValue(Map<?, ?> map, Object key, byte defaultValue) {
        Byte byteObject = getByte(map, key);
        return byteObject == null ? defaultValue : byteObject;
    }

    public static short getShortValue(Map<?, ?> map, Object key, short defaultValue) {
        Short shortObject = getShort(map, key);
        return shortObject == null ? defaultValue : shortObject;
    }

    public static int getIntValue(Map<?, ?> map, Object key, int defaultValue) {
        Integer integerObject = getInteger(map, key);
        return integerObject == null ? defaultValue : integerObject;
    }

    public static long getLongValue(Map<?, ?> map, Object key, long defaultValue) {
        Long longObject = getLong(map, key);
        return longObject == null ? defaultValue : longObject;
    }

    public static float getFloatValue(Map<?, ?> map, Object key, float defaultValue) {
        Float floatObject = getFloat(map, key);
        return floatObject == null ? defaultValue : floatObject;
    }

    public static double getDoubleValue(Map<?, ?> map, Object key, double defaultValue) {
        Double doubleObject = getDouble(map, key);
        return doubleObject == null ? defaultValue : doubleObject;
    }

    public static Properties toProperties(Map<?, ?> map) {
        Properties answer = new Properties();
        if (map != null) {
            Iterator iter = map.entrySet().iterator();

            while (iter.hasNext()) {
                Entry entry = (Entry) iter.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                answer.put(key, value);
            }
        }

        return answer;
    }

    public static Map<String, Object> toMap(ResourceBundle resourceBundle) {
        Enumeration<?> enumeration = resourceBundle.getKeys();
        HashMap map = new HashMap();

        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            Object value = resourceBundle.getObject(key);
            map.put(key, value);
        }

        return map;
    }

    protected static void logInfo(Exception ex) {
        System.out.println("INFO: Exception: " + ex);
    }

    public static Map<Object, Object> putAll(Map<Object, Object> map, Object[] array) {
        map.size();
        if (array != null && array.length != 0) {
            Object obj = array[0];
            int i;
            if (obj instanceof Map.Entry) {
                for (i = 0; i < array.length; ++i) {
                    Entry entry = (Entry) array[i];
                    map.put(entry.getKey(), entry.getValue());
                }
            } else if (obj instanceof KeyValue) {
                for (i = 0; i < array.length; ++i) {
                    KeyValue keyval = (KeyValue) array[i];
                    map.put(keyval.getKey(), keyval.getValue());
                }
            } else if (obj instanceof Object[]) {
                for (i = 0; i < array.length; ++i) {
                    Object[] sub = (Object[]) ((Object[]) array[i]);
                    if (sub == null || sub.length < 2) {
                        throw new IllegalArgumentException("Invalid array element: " + i);
                    }

                    map.put(sub[0], sub[1]);
                }
            } else {
                i = 0;

                while (i < array.length - 1) {
                    map.put(array[i++], array[i++]);
                }
            }

            return map;
        } else {
            return map;
        }
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static Map<String, Object> convertValueStringToObject(Map<String, String> params) {
        Map<String, Object> result = new HashMap();
        if (isNotEmpty(params)) {
            Iterator it = params.entrySet().iterator();

            while (it.hasNext()) {
                Entry<String, String> entry = (Entry) it.next();
                result.put(entry.getKey(), entry.getValue());
            }
        }

        return result;
    }

    public static Map<String, String> convertValueObjectToString(Map<String, Object> params) {
        Map<String, String> result = new HashMap();
        if (isNotEmpty(params)) {
            Iterator it = params.entrySet().iterator();

            while (it.hasNext()) {
                Entry<String, Object> entry = (Entry) it.next();
                result.put(entry.getKey(), ObjectUtil.parseToString(entry.getValue()));
            }
        }

        return result;
    }
}
