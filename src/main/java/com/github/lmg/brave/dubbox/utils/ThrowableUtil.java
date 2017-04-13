package com.github.lmg.brave.dubbox.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @since: 15/11/3.
 * @author: yangjunming
 */
public final class ThrowableUtil {

    public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            t.printStackTrace(pw);
            return sw.toString();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }
}
