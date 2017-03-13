
package com.deere.api.axiom.generated.v3;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ManagementZones
{

    private final static long serialVersionUID = 1L;
    @JsonProperty("values")
    protected List<ManagementZone> managementZones;

    public List<ManagementZone> getManagementZones() {
        return managementZones;
    }

    public void setManagementZones(List<ManagementZone> managementZones) {
        this.managementZones = managementZones;
    }
}
