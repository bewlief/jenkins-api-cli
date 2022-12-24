package com.xtech.jenkins.client.validator;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

//@ExtendWith(MockitoExtension.class)
class HttpResponseValidatorTest {
    private HttpResponse httpResponse;
    private boolean httpException = false;

    private HttpResponseValidator validator = new HttpResponseValidator();

    @Test
    void testShouldThrowHttpResponseExceptionWhenStatusIsLowerThan200() {
        // GIVEN
        httpResponse = getResponseWithCode(170);

        // WHEN
        httpException = validateResponse(httpResponse);

        // THEN
        Assertions.assertTrue(httpException);
    }

    @Test
    void testShouldThrowHttpResponseExceptionWhenStatusIsHigherThan400(){
        // GIVEN
        httpResponse = getResponseWithCode(403);

        // WHEN
        httpException = validateResponse(httpResponse);

        // THEN
        Assertions.assertTrue(httpException);
    }

    @Test
    void testShouldThrowHttpResponseExceptionWhenStatusIs400(){
        // GIVEN
        httpResponse = getResponseWithCode(400);

        // WHEN
        httpException = validateResponse(httpResponse);

        // THEN
        Assertions.assertTrue(httpException);
    }


    @Test
    void testShouldNotThrowHttpResponseExceptionWhenStatusBetween200And400(){
        // GIVEN
        httpResponse = getResponseWithCode(220);

        // WHEN
        httpException = validateResponse(httpResponse);

        // THEN
        Assertions.assertFalse(httpException);
    }

    private HttpResponse getResponseWithCode(Integer statusCode) {
        HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
        StatusLine statusLine = Mockito.mock(StatusLine.class);

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(statusCode);

        return httpResponse;
    }

    private boolean validateResponse(HttpResponse httpResponse) {
        boolean result = false;
        try {
            validator.validateResponse(httpResponse);
        } catch (HttpResponseException e) {
            result = true;
        }
        return result;
    }
}
