package com.madbid.web.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dimer on 11/23/14.
 */
public class UrlUtils {

    public static String generateBaseUrl(HttpServletRequest request) {
        // Generate response URLs
        StringBuilder url = new StringBuilder();
        url.append("http://");
        url.append(request.getServerName());
        url.append(":");
        url.append(request.getServerPort());
        url.append(request.getContextPath());
        return url.toString();
    }
}
