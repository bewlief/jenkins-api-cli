package com.xtech.jenkins.client;

import com.xtech.jenkins.client.helper.Plugins;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JenkinsTest {

    private JenkinsHttpClient client = mock(JenkinsHttpClient.class);
    private Jenkins jenkins = new Jenkins(client);

    @Test
    void testShouldReturnFalseIfNoResponse() throws IOException {
        // GIVEN
        when(client.get(anyString())).thenThrow(new IOException());

        // WHEN
        boolean target = jenkins.isRunning();

        // THEN
        assertEquals(false, target);
    }

    @Test
    void testShouldReturnTrueIfClientGetResponse() throws IOException {
        // GIVEN
        when(client.get(anyString())).thenReturn("someResponse");

        // WHEN
        boolean target = jenkins.isRunning();

        // THEN
        assertEquals(true, target);
    }

    @Test
    void testShouldReturnPlugins(){
        // GIVEN

        // WHEN
        Plugins target=jenkins.getPlugins();

        // THEN
        assertEquals(client, target.getClient());
    }
}
