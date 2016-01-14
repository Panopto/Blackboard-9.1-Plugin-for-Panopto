/**
 * SessionState.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public class SessionState implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected SessionState(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Created = "Created";
    public static final java.lang.String _Scheduled = "Scheduled";
    public static final java.lang.String _Recording = "Recording";
    public static final java.lang.String _Broadcasting = "Broadcasting";
    public static final java.lang.String _Processing = "Processing";
    public static final java.lang.String _Complete = "Complete";
    public static final SessionState Created = new SessionState(_Created);
    public static final SessionState Scheduled = new SessionState(_Scheduled);
    public static final SessionState Recording = new SessionState(_Recording);
    public static final SessionState Broadcasting = new SessionState(_Broadcasting);
    public static final SessionState Processing = new SessionState(_Processing);
    public static final SessionState Complete = new SessionState(_Complete);
    public java.lang.String getValue() { return _value_;}
    public static SessionState fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        SessionState enumeration = (SessionState)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static SessionState fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SessionState.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "SessionState"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
