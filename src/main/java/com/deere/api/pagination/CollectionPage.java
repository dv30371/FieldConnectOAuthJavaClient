package com.deere.api.pagination;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

@JsonSerialize(using = CollectionPageSerializer.class)
public class CollectionPage<E> extends ArrayList<E> {
    private Integer totalSize;
    private URI nextPage;
    private URI prevPage;
    private URI self;

    @Deprecated
    public CollectionPage(
            final Collection<? extends E> page,
            final URI nextPage,
            final URI prevPage,
            final Integer totalSize
    ) {
        this(page, null, nextPage, prevPage, totalSize);
    }

    public CollectionPage(
            final Collection<? extends E> page,
            final URI self,
            final URI nextPage,
            final URI prevPage,
            final Integer totalSize
                         ) {
        super(page);
        this.totalSize = totalSize;
        this.nextPage = nextPage;
        this.prevPage = prevPage;
        this.self = self;
    }


    public Integer getTotalSize() {
        return totalSize;
    }

    public URI getNextPage() {
        return nextPage;
    }

    public URI getPrevPage() {
        return prevPage;
    }

    public URI getSelf() {
        return self;
    }
}
