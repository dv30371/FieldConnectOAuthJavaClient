package com.deere.rest;

import com.google.common.net.MediaType;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;

import static com.google.common.net.MediaType.parse;

public class ContentExchanger {

    private static final MediaType v3Json = parse("application/vnd.deere.axiom.v3+json");
    private static final MediaType v3Xml = parse("application/xml");
    private final RestResponse restResponse;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Unmarshaller unmarshaller;

    protected ContentExchanger(final RestResponse restRequest) {
        this.restResponse = restRequest;
        try {
            unmarshaller = JAXBContext.newInstance("com.deere.api.axiom.generated.v3").createUnmarshaller();

        } catch (final JAXBException jEx) {
            throw new RuntimeException(jEx);
        }
    }

    public static ContentExchanger read(final RestResponse restRequest) {
        return new ContentExchanger(restRequest);
    }

    public <T> T as(final Class<T> clazz) {
        final MediaType mediaType = parse(restResponse.getHeaderFields().valueOf("Content-Type"));

        if (mediaType.is(v3Json)) {
            return parseAsJson(clazz);
        } else if (mediaType.is(v3Xml)) {
            return parseAsXml();
        } else {
            throw fail(mediaType.toString() + " is not parsable as JSON or XML.");
        }
    }

    public <T> T as(final TypeReference<T> clazz) {
        final MediaType mediaType = parse(restResponse.getHeaderFields().valueOf("Content-Type"));

        if (mediaType.is(v3Json)) {
            return parseAsJson(clazz);
        } else if (mediaType.is(v3Xml)) {
            return parseAsXml();
        } else {
            throw fail(mediaType.toString() + " is not parsable as JSON or XML.");
        }
    }

    public <T> T parseAsJson(final TypeReference<T> clazz) {
        try {
            return objectMapper.readValue(restResponse.getBodyAsReader(), clazz);
        } catch (IOException ioEx) {
            throw new RuntimeException(ioEx);
        }
    }

    public <T> T parseAsJson(final Class<T> clazz) {
        try {
            return objectMapper.readValue(restResponse.getBodyAsReader(), clazz);
        } catch (IOException ioEx) {
            throw new RuntimeException(ioEx);
        }
    }

    @SuppressWarnings("unchecked") private <T> T parseAsXml() {
        try {
            final Object obj = unmarshaller.unmarshal(restResponse.getBodyAsReader());

            if (obj instanceof JAXBElement) {
                return (T) ((JAXBElement<?>) obj).getValue();
            } else {
                return (T) obj;
            }
        } catch (final JAXBException jEx) {
            throw new RuntimeException(jEx);
        }
    }

    private AssertionError fail(final String msg) {
        try {
            org.junit.Assert.fail(msg);
        } catch (final AssertionError aEx) {
            return aEx;
        }

        throw new IllegalStateException();
    }

    public RestResponse getRestResponse() {
        return restResponse;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
