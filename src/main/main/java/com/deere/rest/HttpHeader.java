package com.deere.rest;

public class HttpHeader {
    private final String name;
    private final String value;

    public HttpHeader(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        final HttpHeader that = (HttpHeader) o;

        return name.equalsIgnoreCase(that.name) && value.equals(that.value);
    }

    @Override public int hashCode() {
        int result = name.toLowerCase().hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
