
package com.deere.api.axiom.generated.v3;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Notes
{

    private final static long serialVersionUID = 1L;
    @JsonProperty("values")
    protected List<FieldconnectNote> fieldconnectNotes;

    public List<FieldconnectNote> getFieldconnectNotes() {
        return fieldconnectNotes;
    }

    public void setFieldconnectNotes(List<FieldconnectNote> fieldconnectNotes) {
        this.fieldconnectNotes = fieldconnectNotes;
    }
}
