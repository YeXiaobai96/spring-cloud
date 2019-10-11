//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mts.springboot.common.util;

import org.apache.shiro.util.PatternMatcher;

public class ServletPathMatcher implements PatternMatcher {
    private static final ServletPathMatcher INSTANCE = new ServletPathMatcher();
    private static final String SIGN = "*";

    public ServletPathMatcher() {
    }

    public static ServletPathMatcher getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean matches(String pattern, String source) {
        if (pattern != null && source != null) {
            pattern = pattern.trim();
            source = source.trim();
            int start;
            if (pattern.endsWith(SIGN)) {
                start = pattern.length() - 1;
                if (source.length() >= start && pattern.substring(0, start).equals(source.substring(0, start))) {
                    return true;
                }
            } else if (pattern.startsWith(SIGN)) {
                start = pattern.length() - 1;
                if (source.length() >= start && source.endsWith(pattern.substring(1))) {
                    return true;
                }
            } else if (pattern.contains(SIGN)) {
                start = pattern.indexOf(SIGN);
                int end = pattern.lastIndexOf(SIGN);
                if (source.startsWith(pattern.substring(0, start)) && source.endsWith(pattern.substring(end + 1))) {
                    return true;
                }
            } else if (pattern.equals(source)) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }
}

