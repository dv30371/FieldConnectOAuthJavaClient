package com.deere.rest;

public class OAuthClient {
    private final String key;
    private final String secret;

    public OAuthClient(final String key, final String secret) {
        this.key = key;
        this.secret = secret;
    }

    public String getKey() {
        return key;
    }

    public String getSecret() {
        return secret;
    }

    @Override public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        final OAuthClient that = (OAuthClient) o;

        return key.equals(that.key) && secret.equals(that.secret);

    }

    @Override public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + secret.hashCode();
        return result;
    }
}
