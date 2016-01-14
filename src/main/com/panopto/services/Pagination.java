/**
 * Pagination.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public class Pagination  implements java.io.Serializable {
    private java.lang.Integer maxNumberResults;

    private java.lang.Integer pageNumber;

    public Pagination() {
    }

    public Pagination(
           java.lang.Integer maxNumberResults,
           java.lang.Integer pageNumber) {
           this.maxNumberResults = maxNumberResults;
           this.pageNumber = pageNumber;
    }


    /**
     * Gets the maxNumberResults value for this Pagination.
     * 
     * @return maxNumberResults
     */
    public java.lang.Integer getMaxNumberResults() {
        return maxNumberResults;
    }


    /**
     * Sets the maxNumberResults value for this Pagination.
     * 
     * @param maxNumberResults
     */
    public void setMaxNumberResults(java.lang.Integer maxNumberResults) {
        this.maxNumberResults = maxNumberResults;
    }


    /**
     * Gets the pageNumber value for this Pagination.
     * 
     * @return pageNumber
     */
    public java.lang.Integer getPageNumber() {
        return pageNumber;
    }


    /**
     * Sets the pageNumber value for this Pagination.
     * 
     * @param pageNumber
     */
    public void setPageNumber(java.lang.Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Pagination)) return false;
        Pagination other = (Pagination) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.maxNumberResults==null && other.getMaxNumberResults()==null) || 
             (this.maxNumberResults!=null &&
              this.maxNumberResults.equals(other.getMaxNumberResults()))) &&
            ((this.pageNumber==null && other.getPageNumber()==null) || 
             (this.pageNumber!=null &&
              this.pageNumber.equals(other.getPageNumber())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getMaxNumberResults() != null) {
            _hashCode += getMaxNumberResults().hashCode();
        }
        if (getPageNumber() != null) {
            _hashCode += getPageNumber().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Pagination.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "Pagination"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxNumberResults");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "MaxNumberResults"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "PageNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
