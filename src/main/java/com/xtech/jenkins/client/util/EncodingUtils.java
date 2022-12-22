package com.xtech.jenkins.client.util;

import com.google.common.net.UrlEscapers;

public abstract class EncodingUtils {

    public static String encode(String pathPart) {
        // jenkins doesn't like the + for space, use %20 instead
        return UrlEscapers.urlPathSegmentEscaper().escape(pathPart);
    }

    public static String encodeParam(String pathPart) {
        // jenkins doesn't like the + for space, use %20 instead
        return UrlEscapers.urlFormParameterEscaper().escape(pathPart);
    }

}
