/**
 * UserSortField.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public class UserSortField implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected UserSortField(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _UserKey = "UserKey";
    public static final java.lang.String _Role = "Role";
    public static final java.lang.String _Added = "Added";
    public static final java.lang.String _LastLogOn = "LastLogOn";
    public static final java.lang.String _Email = "Email";
    public static final java.lang.String _FullName = "FullName";
    public static final UserSortField UserKey = new UserSortField(_UserKey);
    public static final UserSortField Role = new UserSortField(_Role);
    public static final UserSortField Added = new UserSortField(_Added);
    public static final UserSortField LastLogOn = new UserSortField(_LastLogOn);
    public static final UserSortField Email = new UserSortField(_Email);
    public static final UserSortField FullName = new UserSortField(_FullName);
    public java.lang.String getValue() { return _value_;}
    public static UserSortField fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        UserSortField enumeration = (UserSortField)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static UserSortField fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(UserSortField.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "UserSortField"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
