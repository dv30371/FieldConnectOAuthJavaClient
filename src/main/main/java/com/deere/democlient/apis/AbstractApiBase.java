package com.deere.democlient.apis;

import com.deere.api.axiom.generated.v3.Link;
import com.deere.api.pagination.CollectionPageDeserializerFactory;
import com.deere.democlient.ApiCredentials;
import com.deere.rest.ContentExchanger;
import com.deere.rest.RestRequestBuilder;
import com.deere.rest.RestResponse;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.hamcrest.Matcher;

import java.net.URISyntaxException;

import static com.deere.rest.RestRequestBuilder.request;

public abstract class AbstractApiBase {

    private static final ClassLoader CLASS_LOADER = AbstractApiBase.class.getClassLoader();
    protected static ContentExchanger read(final RestResponse restResponse) {
        final ContentExchanger read = ContentExchanger.read(restResponse);
        final ObjectMapper objectMapper = read.getObjectMapper();

        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        objectMapper.setDeserializerProvider(objectMapper
                                                     .getDeserializerProvider()
                                                     .withFactory(new CollectionPageDeserializerFactory(null)));
        return read;
    }

    public static <T> void  assertThat(String reason, T actual, Matcher<T> matcher) {
        if (!matcher.matches(actual)) {
            throw new java.lang.AssertionError("No Match");

        }
    }


    public static java.io.File findFile(final String name) {
        try {
            return new java.io.File(CLASS_LOADER.getResource(name).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static RestRequestBuilder oauthRequestTo(final String baseUri) {
        return request()
                .baseUri(baseUri)
                .oauthClient(ApiCredentials.CLIENT)
                .oauthToken(ApiCredentials.TOKEN);
    }

    public static Link linkWith(final String rel, final String uri) {
        final Link link = new Link();
        link.setRel(rel);
        link.setUri(uri);
        return link;
    }

}
