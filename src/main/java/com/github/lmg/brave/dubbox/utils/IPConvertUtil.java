package com.github.lmg.brave.dubbox.utils;

/**
 * Created by liaomengge on 17/4/13.
 */
public final class IPConvertUtil {

    public static int convertToInt(String ip) {
        String[] ips = ip.split("\\.");
        int ipInt = 0;
        int value;
        int w = 24;
        for (String s : ips) {
            try {
                value = Integer.valueOf(s);
            } catch (NumberFormatException e) {
                value = 0;
            }
            ipInt |= value << w;
            w -= 8;
        }
        return ipInt;
    }
}
