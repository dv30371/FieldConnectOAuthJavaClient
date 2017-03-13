package com.deere.rest;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
//import org.springframework.http.HttpStatus;

public class ResponseCodeMatcher
        extends BaseMatcher<HttpStatus> {
    private final HttpStatus status;

    public ResponseCodeMatcher(final HttpStatus status) {
        this.status = status;
    }

    @Override public boolean matches(final Object item) {
        return status.equals(item instanceof RestResponse ? ((RestResponse) item).getResponseCode() : item);
    }

    @Override public void describeTo(final Description description) {
        description.appendText("response with HTTP Status Code ").appendValue(status);
    }

    public static ResponseCodeMatcher hasResponseCode(final HttpStatus status) {
        return new ResponseCodeMatcher(status);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        final ResponseCodeMatcher that = (ResponseCodeMatcher) o;

        return status == that.status;
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }
}
