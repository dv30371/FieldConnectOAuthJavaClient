package com.deere.rest;

import com.google.common.base.Optional;
import com.google.common.net.MediaType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static com.google.common.base.Optional.absent;
import static com.google.common.net.MediaType.parse;
import static java.nio.charset.Charset.forName;

public class RestResponse {
    private final HttpHeadersMap headerFields;
    private final HttpStatus responseCode;
    private final InputStream body;
    private BufferedReader bufferedReader;
    private final String responseMessage;

    public RestResponse(final int responseCode,
                        final String responseMessage,
                        final HttpHeadersMap headerFields,
                        final InputStream body) {
        this.responseCode = HttpStatus.valueOf(responseCode);
        this.responseMessage = responseMessage;
        this.headerFields = headerFields;
        this.body = body;
    }

    public HttpStatus getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public HttpHeadersMap getHeaderFields() {
        return headerFields;
    }

    public BufferedReader getBodyAsReader() {
        final Optional<Charset> characterEncoding = getCharacterEncoding();
        if (null == bufferedReader) {
            bufferedReader = new BufferedReader(new InputStreamReader(this.body,
                                                                      characterEncoding.or(forName("UTF-8"))));

        }

        return bufferedReader;
    }

    public InputStream getBody() {
        return this.body;
    }

    private Optional<Charset> getCharacterEncoding() {
        final String contentTypes = headerFields.valueOf("Content-Type");
        if (null != contentTypes) {
            final MediaType mediaType = parse(contentTypes);
            return mediaType.charset();
        }
        return absent();
    }

    public String readResponseAsString() throws IOException {
        StringBuilder sb = new StringBuilder();

        sb.append(getHeadersAsString());
        sb.append("\n");
        String line;
        while (null != (line = getBodyAsReader().readLine())) {
            sb.append(line);
        }

        return sb.toString();
    }

    private String getHeadersAsString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getResponseCode()).append(" ").append(getResponseMessage()).append("\n");

        for (HttpHeader header  : getHeaderFields()) {
            sb.append(header.getName()).append(": ").append(header.getValue());
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override public String toString() {
        return "HTTP Response: " + getHeadersAsString();
    }
}
