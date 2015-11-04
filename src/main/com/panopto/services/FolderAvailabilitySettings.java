/**
 * FolderAvailabilitySettings.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public class FolderAvailabilitySettings  implements java.io.Serializable {
    private com.panopto.services.DateTimeOffset endSettingDate;

    private com.panopto.services.FolderEndSettingType endSettingType;

    private java.lang.String folderId;

    private com.panopto.services.DateTimeOffset startSettingDate;

    private com.panopto.services.FolderStartSettingType startSettingType;

    public FolderAvailabilitySettings() {
    }

    public FolderAvailabilitySettings(
           com.panopto.services.DateTimeOffset endSettingDate,
           com.panopto.services.FolderEndSettingType endSettingType,
           java.lang.String folderId,
           com.panopto.services.DateTimeOffset startSettingDate,
           com.panopto.services.FolderStartSettingType startSettingType) {
           this.endSettingDate = endSettingDate;
           this.endSettingType = endSettingType;
           this.folderId = folderId;
           this.startSettingDate = startSettingDate;
           this.startSettingType = startSettingType;
    }


    /**
     * Gets the endSettingDate value for this FolderAvailabilitySettings.
     * 
     * @return endSettingDate
     */
    public com.panopto.services.DateTimeOffset getEndSettingDate() {
        return endSettingDate;
    }


    /**
     * Sets the endSettingDate value for this FolderAvailabilitySettings.
     * 
     * @param endSettingDate
     */
    public void setEndSettingDate(com.panopto.services.DateTimeOffset endSettingDate) {
        this.endSettingDate = endSettingDate;
    }


    /**
     * Gets the endSettingType value for this FolderAvailabilitySettings.
     * 
     * @return endSettingType
     */
    public com.panopto.services.FolderEndSettingType getEndSettingType() {
        return endSettingType;
    }


    /**
     * Sets the endSettingType value for this FolderAvailabilitySettings.
     * 
     * @param endSettingType
     */
    public void setEndSettingType(com.panopto.services.FolderEndSettingType endSettingType) {
        this.endSettingType = endSettingType;
    }


    /**
     * Gets the folderId value for this FolderAvailabilitySettings.
     * 
     * @return folderId
     */
    public java.lang.String getFolderId() {
        return folderId;
    }


    /**
     * Sets the folderId value for this FolderAvailabilitySettings.
     * 
     * @param folderId
     */
    public void setFolderId(java.lang.String folderId) {
        this.folderId = folderId;
    }


    /**
     * Gets the startSettingDate value for this FolderAvailabilitySettings.
     * 
     * @return startSettingDate
     */
    public com.panopto.services.DateTimeOffset getStartSettingDate() {
        return startSettingDate;
    }


    /**
     * Sets the startSettingDate value for this FolderAvailabilitySettings.
     * 
     * @param startSettingDate
     */
    public void setStartSettingDate(com.panopto.services.DateTimeOffset startSettingDate) {
        this.startSettingDate = startSettingDate;
    }


    /**
     * Gets the startSettingType value for this FolderAvailabilitySettings.
     * 
     * @return startSettingType
     */
    public com.panopto.services.FolderStartSettingType getStartSettingType() {
        return startSettingType;
    }


    /**
     * Sets the startSettingType value for this FolderAvailabilitySettings.
     * 
     * @param startSettingType
     */
    public void setStartSettingType(com.panopto.services.FolderStartSettingType startSettingType) {
        this.startSettingType = startSettingType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FolderAvailabilitySettings)) return false;
        FolderAvailabilitySettings other = (FolderAvailabilitySettings) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.endSettingDate==null && other.getEndSettingDate()==null) || 
             (this.endSettingDate!=null &&
              this.endSettingDate.equals(other.getEndSettingDate()))) &&
            ((this.endSettingType==null && other.getEndSettingType()==null) || 
             (this.endSettingType!=null &&
              this.endSettingType.equals(other.getEndSettingType()))) &&
            ((this.folderId==null && other.getFolderId()==null) || 
             (this.folderId!=null &&
              this.folderId.equals(other.getFolderId()))) &&
            ((this.startSettingDate==null && other.getStartSettingDate()==null) || 
             (this.startSettingDate!=null &&
              this.startSettingDate.equals(other.getStartSettingDate()))) &&
            ((this.startSettingType==null && other.getStartSettingType()==null) || 
             (this.startSettingType!=null &&
              this.startSettingType.equals(other.getStartSettingType())));
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
        if (getEndSettingDate() != null) {
            _hashCode += getEndSettingDate().hashCode();
        }
        if (getEndSettingType() != null) {
            _hashCode += getEndSettingType().hashCode();
        }
        if (getFolderId() != null) {
            _hashCode += getFolderId().hashCode();
        }
        if (getStartSettingDate() != null) {
            _hashCode += getStartSettingDate().hashCode();
        }
        if (getStartSettingType() != null) {
            _hashCode += getStartSettingType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FolderAvailabilitySettings.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "FolderAvailabilitySettings"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endSettingDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "EndSettingDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/System", "DateTimeOffset"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endSettingType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "EndSettingType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "FolderEndSettingType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("folderId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "FolderId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startSettingDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "StartSettingDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/System", "DateTimeOffset"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startSettingType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "StartSettingType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "FolderStartSettingType"));
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
