package com.deere.democlient;

import com.deere.api.axiom.generated.v3.Link;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.basic.HttpURLConnectionRequestAdapter;
import oauth.signpost.http.HttpRequest;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Map;

import static java.lang.Integer.valueOf;
import static java.lang.System.getProperty;

public class ProxyAwareOAuthProvider extends DefaultOAuthProvider {
    public ProxyAwareOAuthProvider(final Map<String, Link> links) {
        super(links.get("oauthRequestToken").getUri(),
              links.get("oauthAccessToken").getUri(),
             (links.get("oauthAuthorizeRequestToken").getUri()));

    }

    @Override
    protected HttpRequest createRequest(String endpointUrl) throws IOException {
        final HttpURLConnection connection;
        if (isProxySet()) {
            connection = (HttpURLConnection) new URL(endpointUrl).openConnection(proxyFromSystemProperties());
            makeSslInsecureFor(connection);
        } else {
            connection = (HttpURLConnection) new URL(endpointUrl).openConnection();
        }
        connection.setRequestMethod("POST");
        connection.setAllowUserInteraction(false);
        connection.setRequestProperty("Content-Length", "0");
        connection.setRequestProperty("Content-Type", "*/*");
        connection.setRequestProperty("Accept", "application/json");


        return new HttpURLConnectionRequestAdapter(connection);
    }

    private Boolean isProxySet() {
        return Boolean.valueOf(System.getProperty("http.proxySet", "true"));
    }

    private Proxy proxyFromSystemProperties() {
        final String proxyHost = getProperty("host", "host_url");
        final Integer proxyPort = valueOf(getProperty("http.proxyPort", "port"));
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
    }

    private static String cleanAuthorizationUriForSignPost(final String uri) {
        return uri.substring(0, uri.indexOf("?"));
    }

    private void makeSslInsecureFor(final HttpURLConnection req) {
        if (req instanceof HttpsURLConnection) {
            final X509TrustManager x509TrustManager = new X509TrustManager() {
                @Override public void checkClientTrusted(final X509Certificate[] x509Certificates, final String s) { }

                @Override public void checkServerTrusted(final X509Certificate[] x509Certificates, final String s) { }

                @Override public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };

            try {
                final SSLContext fakeSsl = SSLContext.getInstance("SSL");
                fakeSsl.init(null, new TrustManager[]{x509TrustManager}, null);
                ((HttpsURLConnection) req).setSSLSocketFactory(fakeSsl.getSocketFactory());
            } catch (final Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }


}
