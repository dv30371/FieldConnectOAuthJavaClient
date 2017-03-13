package com.deere.api.pagination;

import com.deere.api.axiom.generated.v3.Link;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class CollectionPageSerializer extends SerializerBase<CollectionPage<Object>> {

    public CollectionPageSerializer() {
        super(CollectionPage.class, true);
    }

    @Override
    public void serialize(final CollectionPage<Object> value,
                          final JsonGenerator jgen,
                          final SerializerProvider provider)
            throws IOException {
        jgen.writeStartObject();
        writeLinks(value, jgen);
        writeTotalSize(value, jgen);
        writeValues(value, jgen);
        jgen.writeEndObject();
    }

    private void writeLinks(final CollectionPage<Object> value, final JsonGenerator jgen) throws IOException {
        jgen.writeObjectField("links", getLinks(value));
    }

    private void writeTotalSize(final CollectionPage<Object> value, final JsonGenerator jgen) throws IOException {
        if (null != value.getTotalSize()) {
            jgen.writeNumberField("total", value.getTotalSize());
        }
    }

    private void writeValues(final CollectionPage<Object> value, final JsonGenerator jgen) throws IOException {
        jgen.writeArrayFieldStart("values");
        for (final Object obj : value) {
            jgen.writeObject(obj);
        }
        jgen.writeEndArray();
    }

    private List<Link> getLinks(final CollectionPage<Object> value) {
        final List<Link> links = new ArrayList<Link>(3);

        if (null != value.getSelf()) {
            links.add(buildLink("self", value.getSelf()));
        }

        if (null != value.getNextPage()) {
            links.add(buildLink("nextPage", value.getNextPage()));
        }

        if (null != value.getPrevPage()) {
            links.add(buildLink("previousPage", value.getPrevPage()));
        }

        return links;
    }

    private Link buildLink(final String rel, final URI uri) {
        final Link Link = new Link();
        Link.setRel(rel);
        Link.setUri(uri.toString());
        return Link;
    }
}
