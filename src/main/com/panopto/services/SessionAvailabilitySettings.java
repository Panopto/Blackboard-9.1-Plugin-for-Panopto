/**
 * SessionAvailabilitySettings.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public class SessionAvailabilitySettings  implements java.io.Serializable {
    private com.panopto.services.DateTimeOffset endSettingDate;

    private com.panopto.services.SessionEndSettingType endSettingType;

    private java.lang.String sessionId;

    private com.panopto.services.DateTimeOffset startSettingDate;

    private com.panopto.services.SessionStartSettingType startSettingType;

    public SessionAvailabilitySettings() {
    }

    public SessionAvailabilitySettings(
           com.panopto.services.DateTimeOffset endSettingDate,
           com.panopto.services.SessionEndSettingType endSettingType,
           java.lang.String sessionId,
           com.panopto.services.DateTimeOffset startSettingDate,
           com.panopto.services.SessionStartSettingType startSettingType) {
           this.endSettingDate = endSettingDate;
           this.endSettingType = endSettingType;
           this.sessionId = sessionId;
           this.startSettingDate = startSettingDate;
           this.startSettingType = startSettingType;
    }


    /**
     * Gets the endSettingDate value for this SessionAvailabilitySettings.
     * 
     * @return endSettingDate
     */
    public com.panopto.services.DateTimeOffset getEndSettingDate() {
        return endSettingDate;
    }


    /**
     * Sets the endSettingDate value for this SessionAvailabilitySettings.
     * 
     * @param endSettingDate
     */
    public void setEndSettingDate(com.panopto.services.DateTimeOffset endSettingDate) {
        this.endSettingDate = endSettingDate;
    }


    /**
     * Gets the endSettingType value for this SessionAvailabilitySettings.
     * 
     * @return endSettingType
     */
    public com.panopto.services.SessionEndSettingType getEndSettingType() {
        return endSettingType;
    }


    /**
     * Sets the endSettingType value for this SessionAvailabilitySettings.
     * 
     * @param endSettingType
     */
    public void setEndSettingType(com.panopto.services.SessionEndSettingType endSettingType) {
        this.endSettingType = endSettingType;
    }


    /**
     * Gets the sessionId value for this SessionAvailabilitySettings.
     * 
     * @return sessionId
     */
    public java.lang.String getSessionId() {
        return sessionId;
    }


    /**
     * Sets the sessionId value for this SessionAvailabilitySettings.
     * 
     * @param sessionId
     */
    public void setSessionId(java.lang.String sessionId) {
        this.sessionId = sessionId;
    }


    /**
     * Gets the startSettingDate value for this SessionAvailabilitySettings.
     * 
     * @return startSettingDate
     */
    public com.panopto.services.DateTimeOffset getStartSettingDate() {
        return startSettingDate;
    }


    /**
     * Sets the startSettingDate value for this SessionAvailabilitySettings.
     * 
     * @param startSettingDate
     */
    public void setStartSettingDate(com.panopto.services.DateTimeOffset startSettingDate) {
        this.startSettingDate = startSettingDate;
    }


    /**
     * Gets the startSettingType value for this SessionAvailabilitySettings.
     * 
     * @return startSettingType
     */
    public com.panopto.services.SessionStartSettingType getStartSettingType() {
        return startSettingType;
    }


    /**
     * Sets the startSettingType value for this SessionAvailabilitySettings.
     * 
     * @param startSettingType
     */
    public void setStartSettingType(com.panopto.services.SessionStartSettingType startSettingType) {
        this.startSettingType = startSettingType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SessionAvailabilitySettings)) return false;
        SessionAvailabilitySettings other = (SessionAvailabilitySettings) obj;
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
            ((this.sessionId==null && other.getSessionId()==null) || 
             (this.sessionId!=null &&
              this.sessionId.equals(other.getSessionId()))) &&
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
        if (getSessionId() != null) {
            _hashCode += getSessionId().hashCode();
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
        new org.apache.axis.description.TypeDesc(SessionAvailabilitySettings.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "SessionAvailabilitySettings"));
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "SessionEndSettingType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "SessionId"));
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "SessionStartSettingType"));
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
