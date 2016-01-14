/**
 * ListFoldersResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public class ListFoldersResponse  implements java.io.Serializable {
    private com.panopto.services.Folder[] results;

    private java.lang.Integer totalNumberResults;

    public ListFoldersResponse() {
    }

    public ListFoldersResponse(
           com.panopto.services.Folder[] results,
           java.lang.Integer totalNumberResults) {
           this.results = results;
           this.totalNumberResults = totalNumberResults;
    }


    /**
     * Gets the results value for this ListFoldersResponse.
     * 
     * @return results
     */
    public com.panopto.services.Folder[] getResults() {
        return results;
    }


    /**
     * Sets the results value for this ListFoldersResponse.
     * 
     * @param results
     */
    public void setResults(com.panopto.services.Folder[] results) {
        this.results = results;
    }


    /**
     * Gets the totalNumberResults value for this ListFoldersResponse.
     * 
     * @return totalNumberResults
     */
    public java.lang.Integer getTotalNumberResults() {
        return totalNumberResults;
    }


    /**
     * Sets the totalNumberResults value for this ListFoldersResponse.
     * 
     * @param totalNumberResults
     */
    public void setTotalNumberResults(java.lang.Integer totalNumberResults) {
        this.totalNumberResults = totalNumberResults;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListFoldersResponse)) return false;
        ListFoldersResponse other = (ListFoldersResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.results==null && other.getResults()==null) || 
             (this.results!=null &&
              java.util.Arrays.equals(this.results, other.getResults()))) &&
            ((this.totalNumberResults==null && other.getTotalNumberResults()==null) || 
             (this.totalNumberResults!=null &&
              this.totalNumberResults.equals(other.getTotalNumberResults())));
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
        if (getResults() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getResults());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getResults(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTotalNumberResults() != null) {
            _hashCode += getTotalNumberResults().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ListFoldersResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "ListFoldersResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("results");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "Results"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "Folder"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "Folder"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalNumberResults");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "TotalNumberResults"));
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
