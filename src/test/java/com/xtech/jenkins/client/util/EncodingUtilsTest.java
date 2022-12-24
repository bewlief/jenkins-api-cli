package com.xtech.jenkins.client.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EncodingUtilsTest {

    @Test
    void shouldReturnEncodedPath() {
        // GIVEN
        String path = "/a/b c";

        // WHEN
        String target = EncodingUtils.encode(path);

        // THEN
        Assertions.assertEquals("%2Fa%2Fb%20c", target);
    }

    @Test
    void shouldReturnEncodedParam() {
        // GIVEN
        String path = "/a/b c";

        // WHEN
        String target = EncodingUtils.encodeParam(path);

        // THEN
        Assertions.assertEquals("%2Fa%2Fb+c", target);
    }

    @Test
    void encodeShouldReturnEncodedDoubleQuoteAndSpace() {
        String result = EncodingUtils.encode("!\"& ");
        // THEN
        Assertions.assertEquals("!%22&%20", result);
    }

    @Test
    void encodeShouldReturnNotEncodeSafeChars() {
        String result = EncodingUtils.encode("-._~!$'()*,;&=@:+");
        // THEN
        Assertions.assertEquals("-._~!$'()*,;&=@:+", result);
    }

    @Test
    void encodeShouldReturnNotEncodeAlpha() {
        String result = EncodingUtils.encode("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
        // THEN
        Assertions.assertEquals("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", result);
    }

    @Test
    void encodeShouldReturnEncodingUmlautAndOthers() {
        String result = EncodingUtils.encode("äöü#{}");
        // THEN
        Assertions.assertEquals("%C3%A4%C3%B6%C3%BC%23%7B%7D", result);
    }

    @Test
    void encodeParamShouldReturnEncodedExclamationMarkDoubleQuoteAmpersampSpace() {
        String result = EncodingUtils.encodeParam("!\"& ");
        // THEN
        Assertions.assertEquals("%21%22%26+", result);
    }

    @Test
    void encodeParamShouldReturnNotEncodeSafeChars() {
        String result = EncodingUtils.encodeParam("-_.*");
        // THEN
        Assertions.assertEquals("-_.*", result);
    }

    @Test
    void encodeParamShouldReturnEncodedUmlautAndOthers() {
        String result = EncodingUtils.encodeParam("äöü#{}");
        // THEN
        Assertions.assertEquals("%C3%A4%C3%B6%C3%BC%23%7B%7D", result);
    }

    @Test
    void encodeParamShouldReturnEncodedCharacters() {
        String result = EncodingUtils.encodeParam("-._~!$'()*,;&=@:+");
        // THEN
        Assertions.assertEquals("-._%7E%21%24%27%28%29*%2C%3B%26%3D%40%3A%2B", result);
    }
}
