
package com.deere.api.axiom.generated.v3;

public class SoilMoisture
    extends Resource

{

    private final static long serialVersionUID = 1L;

    protected Integer managementZoneId;
    protected String probeSerialNumber;
    protected String status;

    /**
     * Gets the value of the managementZoneId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Integer getManagementZoneId() {
        return managementZoneId;
    }

    /**
     * Sets the value of the managementZoneId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManagementZoneId(Integer value) {
        this.managementZoneId = value;
    }

    /**
     * Gets the value of the probeSerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProbeSerialNumber() {
        return probeSerialNumber;
    }

    /**
     * Sets the value of the probeSerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProbeSerialNumber(String value) {
        this.probeSerialNumber = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }


}
