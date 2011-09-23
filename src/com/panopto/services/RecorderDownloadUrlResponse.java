/**
 * RecorderDownloadUrlResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public class RecorderDownloadUrlResponse  implements java.io.Serializable {
    private java.lang.String macRecorderDownloadUrl;

    private java.lang.String windowsRecorderDownloadUrl;

    private java.lang.String windowsRemoteRecorderDownloadUrl;

    public RecorderDownloadUrlResponse() {
    }

    public RecorderDownloadUrlResponse(
           java.lang.String macRecorderDownloadUrl,
           java.lang.String windowsRecorderDownloadUrl,
           java.lang.String windowsRemoteRecorderDownloadUrl) {
           this.macRecorderDownloadUrl = macRecorderDownloadUrl;
           this.windowsRecorderDownloadUrl = windowsRecorderDownloadUrl;
           this.windowsRemoteRecorderDownloadUrl = windowsRemoteRecorderDownloadUrl;
    }


    /**
     * Gets the macRecorderDownloadUrl value for this RecorderDownloadUrlResponse.
     * 
     * @return macRecorderDownloadUrl
     */
    public java.lang.String getMacRecorderDownloadUrl() {
        return macRecorderDownloadUrl;
    }


    /**
     * Sets the macRecorderDownloadUrl value for this RecorderDownloadUrlResponse.
     * 
     * @param macRecorderDownloadUrl
     */
    public void setMacRecorderDownloadUrl(java.lang.String macRecorderDownloadUrl) {
        this.macRecorderDownloadUrl = macRecorderDownloadUrl;
    }


    /**
     * Gets the windowsRecorderDownloadUrl value for this RecorderDownloadUrlResponse.
     * 
     * @return windowsRecorderDownloadUrl
     */
    public java.lang.String getWindowsRecorderDownloadUrl() {
        return windowsRecorderDownloadUrl;
    }


    /**
     * Sets the windowsRecorderDownloadUrl value for this RecorderDownloadUrlResponse.
     * 
     * @param windowsRecorderDownloadUrl
     */
    public void setWindowsRecorderDownloadUrl(java.lang.String windowsRecorderDownloadUrl) {
        this.windowsRecorderDownloadUrl = windowsRecorderDownloadUrl;
    }


    /**
     * Gets the windowsRemoteRecorderDownloadUrl value for this RecorderDownloadUrlResponse.
     * 
     * @return windowsRemoteRecorderDownloadUrl
     */
    public java.lang.String getWindowsRemoteRecorderDownloadUrl() {
        return windowsRemoteRecorderDownloadUrl;
    }


    /**
     * Sets the windowsRemoteRecorderDownloadUrl value for this RecorderDownloadUrlResponse.
     * 
     * @param windowsRemoteRecorderDownloadUrl
     */
    public void setWindowsRemoteRecorderDownloadUrl(java.lang.String windowsRemoteRecorderDownloadUrl) {
        this.windowsRemoteRecorderDownloadUrl = windowsRemoteRecorderDownloadUrl;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RecorderDownloadUrlResponse)) return false;
        RecorderDownloadUrlResponse other = (RecorderDownloadUrlResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.macRecorderDownloadUrl==null && other.getMacRecorderDownloadUrl()==null) || 
             (this.macRecorderDownloadUrl!=null &&
              this.macRecorderDownloadUrl.equals(other.getMacRecorderDownloadUrl()))) &&
            ((this.windowsRecorderDownloadUrl==null && other.getWindowsRecorderDownloadUrl()==null) || 
             (this.windowsRecorderDownloadUrl!=null &&
              this.windowsRecorderDownloadUrl.equals(other.getWindowsRecorderDownloadUrl()))) &&
            ((this.windowsRemoteRecorderDownloadUrl==null && other.getWindowsRemoteRecorderDownloadUrl()==null) || 
             (this.windowsRemoteRecorderDownloadUrl!=null &&
              this.windowsRemoteRecorderDownloadUrl.equals(other.getWindowsRemoteRecorderDownloadUrl())));
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
        if (getMacRecorderDownloadUrl() != null) {
            _hashCode += getMacRecorderDownloadUrl().hashCode();
        }
        if (getWindowsRecorderDownloadUrl() != null) {
            _hashCode += getWindowsRecorderDownloadUrl().hashCode();
        }
        if (getWindowsRemoteRecorderDownloadUrl() != null) {
            _hashCode += getWindowsRemoteRecorderDownloadUrl().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RecorderDownloadUrlResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "RecorderDownloadUrlResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("macRecorderDownloadUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "MacRecorderDownloadUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("windowsRecorderDownloadUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "WindowsRecorderDownloadUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("windowsRemoteRecorderDownloadUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "WindowsRemoteRecorderDownloadUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
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
