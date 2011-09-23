/**
 * ListGroupsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public class ListGroupsResponse  implements java.io.Serializable {
    private com.panopto.services.Group[] pagedResults;

    private java.lang.Integer totalResultCount;

    public ListGroupsResponse() {
    }

    public ListGroupsResponse(
           com.panopto.services.Group[] pagedResults,
           java.lang.Integer totalResultCount) {
           this.pagedResults = pagedResults;
           this.totalResultCount = totalResultCount;
    }


    /**
     * Gets the pagedResults value for this ListGroupsResponse.
     * 
     * @return pagedResults
     */
    public com.panopto.services.Group[] getPagedResults() {
        return pagedResults;
    }


    /**
     * Sets the pagedResults value for this ListGroupsResponse.
     * 
     * @param pagedResults
     */
    public void setPagedResults(com.panopto.services.Group[] pagedResults) {
        this.pagedResults = pagedResults;
    }


    /**
     * Gets the totalResultCount value for this ListGroupsResponse.
     * 
     * @return totalResultCount
     */
    public java.lang.Integer getTotalResultCount() {
        return totalResultCount;
    }


    /**
     * Sets the totalResultCount value for this ListGroupsResponse.
     * 
     * @param totalResultCount
     */
    public void setTotalResultCount(java.lang.Integer totalResultCount) {
        this.totalResultCount = totalResultCount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListGroupsResponse)) return false;
        ListGroupsResponse other = (ListGroupsResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.pagedResults==null && other.getPagedResults()==null) || 
             (this.pagedResults!=null &&
              java.util.Arrays.equals(this.pagedResults, other.getPagedResults()))) &&
            ((this.totalResultCount==null && other.getTotalResultCount()==null) || 
             (this.totalResultCount!=null &&
              this.totalResultCount.equals(other.getTotalResultCount())));
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
        if (getPagedResults() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPagedResults());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPagedResults(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTotalResultCount() != null) {
            _hashCode += getTotalResultCount().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ListGroupsResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "ListGroupsResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pagedResults");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "PagedResults"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "Group"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "Group"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalResultCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "TotalResultCount"));
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
