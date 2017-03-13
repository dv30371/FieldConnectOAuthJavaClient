package com.deere.rest;

import com.google.common.io.InputSupplier;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RestRequestBuilder {

    private String method;
    private List<HttpHeader> headers = new ArrayList<HttpHeader>();
    private OAuthClient oauthClient;
    private OAuthToken oauthToken;
    private InputSupplier<? extends InputStream> body;
    private String baseUri;
    private String relativeUri;

    public static RestRequestBuilder request() {
        return new RestRequestBuilder();
    }

    public RestRequestBuilder method(final String method) {
        this.method = method;
        return this;
    }

    public RestRequestBuilder addHeader(final HttpHeader header) {
        this.headers.add(header);
        return this;
    }

    public RestRequestBuilder oauthClient(final OAuthClient client) {
        this.oauthClient = client;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public List<HttpHeader> getHeaders() {
        return headers;
    }

    public OAuthClient getOAuthClient() {
        return oauthClient;
    }

    public RestRequestBuilder oauthToken(final OAuthToken token) {
        this.oauthToken = token;
        return this;
    }

    public OAuthToken getOAuthToken() {
        return oauthToken;
    }

    public RestRequest build() {
        return new RestRequest(method, baseUri + ns(relativeUri), oauthClient, oauthToken, body, headers);
    }

    private static String ns(final String n) {
        return null == n ? "" : n;
    }

    public RestRequestBuilder body(final InputSupplier<? extends InputStream> body) {
        this.body = body;
        return this;
    }

    public InputSupplier<? extends InputStream> getBody() {
        return body;
    }

    public RestRequestBuilder baseUri(final String baseUri) {
        this.baseUri = baseUri;
        return this;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public RestRequestBuilder relativeUri(final String relativeUri) {
        this.relativeUri = relativeUri;
        return this;
    }

    public String getRelativeUri() {
        return relativeUri;
    }
}
