package com.github.lmg.brave.dubbox.utils;

import com.github.kristofa.brave.IdConversion;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by liaomengge on 17/5/25.
 */
public final class TraceLogUtil {

    private static final ThreadLocal<Map<String, String>> inheritableThreadLocal = new InheritableThreadLocal<>();
    private static final Random random = new Random(System.currentTimeMillis());

    public static void put(String key, String val) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        Map<String, String> map = inheritableThreadLocal.get();
        if (map == null) {
            map = new HashMap<>();
            inheritableThreadLocal.set(map);
        }
        map.put(key, val);
    }

    public static String get(String key) {
        Map<String, String> map = inheritableThreadLocal.get();
        if ((map != null) && (key != null)) {
            return map.get(key);
        }
        return null;
    }

    public static void remove(String key) {
        Map<String, String> map = inheritableThreadLocal.get();
        if (map != null) {
            map.remove(key);
        }
    }

    public static void clear() {
        Map<String, String> map = inheritableThreadLocal.get();
        if (map != null) {
            map.clear();
            inheritableThreadLocal.remove();
        }
    }

    public static Random getRandomSed() {
        return random;
    }

    public static String generateDefaultRandomSed() {
        return IdConversion.convertToString(random.nextLong());
    }

    public static String generateRandomSed(String str) {
        return str + "_" + IdConversion.convertToString(random.nextLong());
    }

    public static String generateTraceLogIdPrefix() {
        return DateUtil.getNowDate2String(DateUtil.yyyyMMdd) + "_";
    }

}
