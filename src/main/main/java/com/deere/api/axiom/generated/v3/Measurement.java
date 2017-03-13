
package com.deere.api.axiom.generated.v3;

public class Measurement
    extends Resource

{

    private final static long serialVersionUID = 1L;
    protected String measurementTypeName;
    protected Float measurementValue;
    protected String measurementTime;
    protected String unitOfMeasure;

    /**
     * Gets the value of the measurementTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasurementTypeName() {
        return measurementTypeName;
    }

    /**
     * Sets the value of the measurementTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasurementTypeName(String value) {
        this.measurementTypeName = value;
    }

    /**
     * Gets the value of the measurementValue property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getMeasurementValue() {
        return measurementValue;
    }

    /**
     * Sets the value of the measurementValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setMeasurementValue(Float value) {
        this.measurementValue = value;
    }

    /**
     * Gets the value of the measurementTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasurementTime() {
        return measurementTime;
    }

    /**
     * Sets the value of the measurementTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasurementTime(String value) {
        this.measurementTime = value;
    }

    /**
     * Gets the value of the unitOfMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Sets the value of the unitOfMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitOfMeasure(String value) {
        this.unitOfMeasure = value;
    }



}
