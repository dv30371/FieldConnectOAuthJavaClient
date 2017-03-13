package com.deere.democlient;

import com.deere.api.axiom.generated.v3.Link;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static com.deere.democlient.ApiCredentials.CLIENT;
import static java.lang.String.format;

public class OAuthWorkFlow {

    private DefaultOAuthProvider provider = null;
    private String authUri;
    private String verifier;
    private DefaultOAuthConsumer consumer;

    public void retrieveOAuthProviderDetails() {

        final Map<String, Link> links = new HashMap<String, Link>();
        Link requestLink = new Link();
        requestLink.setUri("https://developer.deere.com/oauth/oauth10/initiate");
        links.put("oauthRequestToken", requestLink);
        Link accessTokenLink = new Link();
        accessTokenLink.setUri("https://developer.deere.com/oauth/oauth10/token");
        links.put("oauthAccessToken", accessTokenLink);
        Link authorizeTokenLink = new Link();
        authorizeTokenLink.setUri("https://developer.deere.com/oauth/auz/authorize");
        links.put("oauthAuthorizeRequestToken", authorizeTokenLink);

        provider = new ProxyAwareOAuthProvider(links);

    }

    public void getRequestToken() throws Exception {
        consumer = new DefaultOAuthConsumer(CLIENT.getKey(), CLIENT.getSecret());
        authUri = provider.retrieveRequestToken(consumer, "https://developer.deere.com/oauth/auz/grants/provider/authcomplete");
    }

    public void authorizeRequestToken() throws IOException {
        System.out.println(format("Please provide the verifier from %s", authUri));
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        verifier = reader.readLine();
    }

    public void exchangeRequestTokenForAccessToken() throws Exception {
        provider.retrieveAccessToken(consumer, verifier);
        System.out.println(format("Token: %s\nToken Secret: %s", consumer.getToken(), consumer.getTokenSecret()));
    }

}
