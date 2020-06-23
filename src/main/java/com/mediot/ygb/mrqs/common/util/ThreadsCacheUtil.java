package com.mediot.ygb.mrqs.common.util;

import java.util.concurrent.ConcurrentHashMap;

public class ThreadsCacheUtil {

    private static ThreadLocal<ConcurrentHashMap<Long,Thread>> threadLocal = new ThreadLocal<>();

    public static <T extends ConcurrentHashMap<Long,Thread>> void put(T t) {
        threadLocal.set(t);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get() {
        return (T) threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }

}
