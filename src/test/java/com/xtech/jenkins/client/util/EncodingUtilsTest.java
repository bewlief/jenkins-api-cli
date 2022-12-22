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
}
