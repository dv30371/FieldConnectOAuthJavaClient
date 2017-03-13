package com.deere.rest;

public class OAuthToken {
    private final String token;
    private final String secret;

    public OAuthToken(final String token, final String secret) {
        this.token = token;
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public String getSecret() {
        return secret;
    }
}
