package com.deere.api.pagination;

import com.deere.api.axiom.generated.v3.Link;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.deser.std.ContainerDeserializerBase;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.codehaus.jackson.JsonToken.FIELD_NAME;
import static org.codehaus.jackson.JsonToken.START_ARRAY;
import static org.codehaus.jackson.JsonToken.START_OBJECT;

public class CollectionPageDeserializer extends ContainerDeserializerBase<CollectionPage<?>> {

    private final JavaType collectionType;
    private final JsonDeserializer<Object> valueDeserializer;

    public CollectionPageDeserializer(final JavaType collectionType,
                                      final JsonDeserializer<Object> valueDeserializer) {
        super(collectionType.getRawClass());
        this.collectionType = collectionType;
        this.valueDeserializer = valueDeserializer;
    }

    @Override public CollectionPage<?> deserialize(final JsonParser jp,
                                                   final DeserializationContext ctxt) throws IOException {
        final ObjectMapper codec = (ObjectMapper) jp.getCodec();

        final List<Object> objects = new ArrayList<Object>();
        final List<Link> links = new ArrayList<Link>();
        Integer total = null;

        JsonToken jsonToken = jp.getCurrentToken();

        if (START_OBJECT.equals(jsonToken)) {
            while (null != (jsonToken = jp.nextToken())) {
                if (FIELD_NAME.equals(jsonToken)) {
                    final String currentName = jp.getCurrentName();
                    if ("links".equals(currentName)) {
                        jsonToken = jp.nextToken();

                        if (START_ARRAY.equals(jsonToken)) {
                            while (START_OBJECT.equals(jp.nextToken())) {
                                links.add(codec.readValue(jp, Link.class));
                            }
                        }

                    } else if ("total".equals(currentName)) {
                        jp.nextToken();
                        total = codec.readValue(jp, Integer.class);
                    } else if ("values".equals(currentName)) {
                        jp.nextToken();

                        final Collection<Object> deserializedObjects = deserializeValues(jp, codec);
                        objects.addAll(deserializedObjects);
                    }
                }
            }

            return new CollectionPage<Object>(objects,
                                              getSelf(links),
                                              getNextPage(links),
                                              getPreviousPage(links),
                                              total);
        }

        throw ctxt.wrongTokenException(jp, START_OBJECT, "Input JSON could not be deserialized to CollectionPage");
    }

    @SuppressWarnings("unchecked")
    private Collection<Object> deserializeValues(final JsonParser jp, final ObjectMapper codec) throws IOException {
        final TypeFactory typeFactory = codec.getTypeFactory();
        final JavaType parameterizedType = typeFactory.constructCollectionType(List.class,
                                                                               collectionType.containedType(0));
        return (Collection<Object>) codec.readValue(jp, parameterizedType);
    }

    private URI getSelf(final List<Link> links) {
        return findLinkUriByRel(links, "self");
    }

    private URI getPreviousPage(final List<Link> links) {
        return findLinkUriByRel(links, "previousPage");
    }

    private URI getNextPage(final List<Link> links) {
        return findLinkUriByRel(links, "nextPage");
    }

    private URI findLinkUriByRel(final List<Link> links, final String rel) {
        for (final Link link : links) {
            if (rel.equals(link.getRel())) {
                try {
                    return new URI(link.getUri());
                } catch (final URISyntaxException urisEx) {
                    throw new RuntimeException(urisEx);
                }
            }
        }
        return null;
    }

    @Override public JavaType getContentType() {
        return collectionType;
    }

    @Override public JsonDeserializer<Object> getContentDeserializer() {
        return valueDeserializer;
    }
}

