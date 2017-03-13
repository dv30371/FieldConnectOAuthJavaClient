package com.deere.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.google.common.base.Joiner.on;
import static java.lang.String.CASE_INSENSITIVE_ORDER;

public class HttpHeadersMap implements Iterable<HttpHeader> {

    private final LinkedList<HttpHeader> headerQueue = new LinkedList<HttpHeader>();
    private final Map<String, List<String>> map = new TreeMap<String, List<String>>(CASE_INSENSITIVE_ORDER);

    public void add(final String header, final String value) {
        headerQueue.add(new HttpHeader(header, value));
        if (map.containsKey(header)) {
            map.get(header).add(value);
        } else {
            final ArrayList<String> values = new ArrayList<String>();
            values.add(value);
            map.put(header, values);
        }
    }

    public List<String> valueListOf(final String header) {
        return map.get(header);
    }

    public String valueOf(final String header) {
        final List<String> parts = valueListOf(header);
        return null == parts ? null : on(", ").join(parts);
    }

    @Override public Iterator<HttpHeader> iterator() {
        return headerQueue.iterator();
    }

    public boolean contains(final String header) {
        return map.containsKey(header);
    }
}
