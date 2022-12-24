package com.xtech.jenkins.client;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BaseTest {

    private static Jenkins jenkins;

    @BeforeAll
    public static void setup() {
        jenkins = new Jenkins("", "", "");
    }
}
