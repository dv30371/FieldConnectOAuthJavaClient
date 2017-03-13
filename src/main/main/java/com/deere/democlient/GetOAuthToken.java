package com.deere.democlient;

import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import static java.lang.Integer.valueOf;
import static java.lang.System.getProperty;

public class GetOAuthToken {


    public static void main(String[] args) throws Exception {
        final OAuthWorkFlow flow = new OAuthWorkFlow();

        flow.retrieveOAuthProviderDetails();

        flow.getRequestToken();
        flow.authorizeRequestToken();
        flow.exchangeRequestTokenForAccessToken();
        /*String dataFromServer = new GetOAuthToken().getDataFromServer("");
        System.out.println(dataFromServer);*/
    }

}
