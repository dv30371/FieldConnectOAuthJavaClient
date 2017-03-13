package com.deere.democlient;

import com.deere.rest.OAuthClient;
import com.deere.rest.OAuthToken;

public abstract class ApiCredentials {
    public static final OAuthClient CLIENT = new OAuthClient("OAuth client", "OAuth secret");
    public static final OAuthToken TOKEN = new OAuthToken("generated token", "generated secret");
}
