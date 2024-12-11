package com.hahaha.musicshare.common.cache;

import com.hahaha.musicshare.common.constant.Constant;

import java.util.HashMap;
import java.util.Map;

//请求的上下文环境，将需要全局存储的变量可以放在本类管理
public class RequestContext {
//    使用ThreadLocal<Map<Object, Object>>通过get()方法只获得当前线程map对象的键值对，实现线程安全
    private static final ThreadLocal<Map<Object, Object>> RESOURCES = new InheritableThreadLocalMap<>();

    public static void put(Object key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        if (value == null) {
            RESOURCES.get().remove(key);
            return;
        }
        RESOURCES.get().put(key, value);
    }

    public static Integer getUserId() {
        Object result = get(Constant.USER_ID);
        if (result == null) {
            throw new IllegalArgumentException("user id cannot be null");
        }
        return Integer.valueOf(String.valueOf(result));
    }
    public static String getUserRole() {
        Object result = get(Constant.USER_ROLE);
        if (result == null) {
            throw new IllegalArgumentException("user role cannot be null");
        }
        return String.valueOf(result);
    }

    private static Object get(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        return RESOURCES.get().get(key);
    }

    public static void clear() {
        RESOURCES.remove();
    }

    private static final class InheritableThreadLocalMap<T extends Map<Object, Object>>
            extends InheritableThreadLocal<Map<Object, Object>> {
        @Override
        protected Map<Object, Object> initialValue() {
            return new HashMap<>();
        }

        @Override
        protected Map<Object, Object> childValue(Map<Object, Object> parentValue) {
            if (parentValue != null) {
                return (Map<Object, Object>) ((HashMap<Object, Object>) parentValue).clone();
            } else {
                return null;
            }
        }
    }
}