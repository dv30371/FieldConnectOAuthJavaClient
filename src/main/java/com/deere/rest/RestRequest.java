package com.deere.rest;

import com.google.common.io.InputSupplier;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.security.cert.X509Certificate;
import java.util.List;

import static com.google.common.io.ByteStreams.copy;
import static java.lang.Integer.valueOf;
import static java.lang.System.getProperty;
import static java.util.Arrays.asList;

public class RestRequest {
    private final String method;
    private final String uri;
    private final OAuthClient client;
    private final OAuthToken token;
    private final List<HttpHeader> headers;
    private final InputSupplier<? extends InputStream> body;

    public RestRequest(final String method,
                       final String uri,
                       final OAuthClient client,
                       final OAuthToken token,
                       final InputSupplier<? extends InputStream> body,
                       final HttpHeader... headers) {
        this(method, uri, client, token, body, asList(headers));
    }

    public RestRequest(final String method,
                       final String uri,
                       final OAuthClient client,
                       final OAuthToken token,
                       final InputSupplier<? extends InputStream> body,
                       final List<HttpHeader> headers) {
        this.method = method;
        this.uri = uri;
        this.client = client;
        this.token = token;
        this.headers = headers;
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public OAuthClient getClient() {
        return client;
    }

    public OAuthToken getToken() {
        return token;
    }

    public List<HttpHeader> getHeaders() {
        return headers;
    }

    public InputSupplier<? extends InputStream> getBody() {
        return body;
    }

    public RestResponse fetchResponse() {
        final HttpURLConnection req;
        final HttpHeadersMap responseHeaders;
        final int responseCode;
        final String responseMessage;

        try {
            req = prepareConnection();
            req.connect();

            if (req.getDoOutput()) {
                copy(getBody(), req.getOutputStream());
            }

            responseHeaders = new HttpHeadersMap();

            String field;
            for (int i = 1; null != (field = req.getHeaderFieldKey(i)); i++) {
                responseHeaders.add(field, req.getHeaderField(i));
            }

            responseCode = req.getResponseCode();
            responseMessage = req.getResponseMessage();

            // it's probably irresponsible not to try to detect infinite redirect loops
            if (followRedirectsForMethod() && responseCodeIsPermanentlyMoved(responseCode)) {
                final RestRequest redirectedRequest = new RestRequest("GET",
                                                                      responseHeaders.valueOf("Location"),
                                                                      getClient(),
                                                                      getToken(),
                                                                      getBody(),
                                                                      getHeaders());
                return redirectedRequest.fetchResponse();
            }

        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }

        try {
            return new RestResponse(responseCode, responseMessage, responseHeaders, req.getInputStream());
        } catch (final IOException ioEx) {
            return new RestResponse(responseCode, responseMessage, responseHeaders, req.getErrorStream());
        }
    }

    private boolean followRedirectsForMethod() {
        return "GET".equalsIgnoreCase(getMethod());
    }

    private boolean responseCodeIsPermanentlyMoved(final int responseCode) {
        return 301 == responseCode;
    }

    private HttpURLConnection prepareConnection()  {
        final OAuthConsumer consumer = createOAuthConsumer();

        final URL url = buildUrlFromUri();
        final boolean useProxy = isProxySetInSystemProperties();
        final HttpURLConnection req = openConnection(url, useProxy ? proxyFromSystemProperties() : null);

        req.setDoOutput(null != body);

        setRequestMethod(req);

        for (final HttpHeader header : headers) {
            req.addRequestProperty(header.getName(), header.getValue());
        }

        signRequestWithConsumer(req, consumer);
        disableRedirectsSoTheyCanBeResignedWithOAuth(req);

        if (useProxy) {
            makeSslInsecureFor(req);
        }

        return req;
    }

    private void disableRedirectsSoTheyCanBeResignedWithOAuth(final HttpURLConnection req) {
        req.setInstanceFollowRedirects(false);
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

    private boolean isProxySetInSystemProperties() {
        return Boolean.valueOf(getProperty("http.proxySet", "false"));
    }

    private Proxy proxyFromSystemProperties() {
        final String proxyHost = getProperty("host", "host_url");
        final Integer proxyPort = valueOf(getProperty("http.proxyPort", "port"));
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
    }

    private void signRequestWithConsumer(final HttpURLConnection req, final OAuthConsumer consumer) {
        try {
            consumer.sign(req);
        } catch (final OAuthException oaEx) {
            throw new RuntimeException(oaEx);
        }
    }

    private void setRequestMethod(final HttpURLConnection req) {
        try {
            req.setRequestMethod(method);
        } catch (final ProtocolException pEx) {
            throw new RuntimeException(pEx);
        }
    }

    private HttpURLConnection openConnection(final URL url, final Proxy localhost) {
        try {
            if (null == localhost) {
                return (HttpURLConnection) url.openConnection();
            } else {
                return (HttpURLConnection) url.openConnection(localhost);
            }
        } catch (final IOException ioEx) {
            throw new RuntimeException(ioEx);
        }
    }

    private URL buildUrlFromUri() {
        try {
            return new URL(uri);
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private OAuthConsumer createOAuthConsumer() {
        final OAuthConsumer consumer = new DefaultOAuthConsumer(client.getKey(), client.getSecret());
        if (null != token) {
            consumer.setTokenWithSecret(token.getToken(), token.getSecret());
        }
        return consumer;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        final RestRequest that = (RestRequest) o;

        return !(body != null ? !body.equals(that.body) : that.body != null)
               && client.equals(that.client)
               && headers.equals(that.headers)
               && method.equals(that.method)
               && token.equals(that.token)
               && uri.equals(that.uri);

    }

    @Override
    public int hashCode() {
        int result = method.hashCode();
        result = 31 * result + uri.hashCode();
        result = 31 * result + client.hashCode();
        result = 31 * result + token.hashCode();
        result = 31 * result + headers.hashCode();
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }
}
