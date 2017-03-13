
package com.deere.api.axiom.generated.v3;

import java.util.List;

public abstract class Resource
{
    private final static long serialVersionUID = 1L;
    protected List<Link> links;
    protected String id;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
