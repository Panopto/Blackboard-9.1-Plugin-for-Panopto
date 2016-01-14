/**
 * User.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public class User  implements java.io.Serializable {
    private java.lang.String email;

    private java.lang.Boolean emailSessionNotifications;

    private java.lang.String firstName;

    private java.lang.String[] groupMemberships;

    private java.lang.String lastName;

    private com.panopto.services.SystemRole systemRole;

    private java.lang.String userBio;

    private java.lang.String userId;

    private java.lang.String userKey;

    private java.lang.String userSettingsUrl;

    public User() {
    }

    public User(
           java.lang.String email,
           java.lang.Boolean emailSessionNotifications,
           java.lang.String firstName,
           java.lang.String[] groupMemberships,
           java.lang.String lastName,
           com.panopto.services.SystemRole systemRole,
           java.lang.String userBio,
           java.lang.String userId,
           java.lang.String userKey,
           java.lang.String userSettingsUrl) {
           this.email = email;
           this.emailSessionNotifications = emailSessionNotifications;
           this.firstName = firstName;
           this.groupMemberships = groupMemberships;
           this.lastName = lastName;
           this.systemRole = systemRole;
           this.userBio = userBio;
           this.userId = userId;
           this.userKey = userKey;
           this.userSettingsUrl = userSettingsUrl;
    }


    /**
     * Gets the email value for this User.
     * 
     * @return email
     */
    public java.lang.String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this User.
     * 
     * @param email
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }


    /**
     * Gets the emailSessionNotifications value for this User.
     * 
     * @return emailSessionNotifications
     */
    public java.lang.Boolean getEmailSessionNotifications() {
        return emailSessionNotifications;
    }


    /**
     * Sets the emailSessionNotifications value for this User.
     * 
     * @param emailSessionNotifications
     */
    public void setEmailSessionNotifications(java.lang.Boolean emailSessionNotifications) {
        this.emailSessionNotifications = emailSessionNotifications;
    }


    /**
     * Gets the firstName value for this User.
     * 
     * @return firstName
     */
    public java.lang.String getFirstName() {
        return firstName;
    }


    /**
     * Sets the firstName value for this User.
     * 
     * @param firstName
     */
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }


    /**
     * Gets the groupMemberships value for this User.
     * 
     * @return groupMemberships
     */
    public java.lang.String[] getGroupMemberships() {
        return groupMemberships;
    }


    /**
     * Sets the groupMemberships value for this User.
     * 
     * @param groupMemberships
     */
    public void setGroupMemberships(java.lang.String[] groupMemberships) {
        this.groupMemberships = groupMemberships;
    }


    /**
     * Gets the lastName value for this User.
     * 
     * @return lastName
     */
    public java.lang.String getLastName() {
        return lastName;
    }


    /**
     * Sets the lastName value for this User.
     * 
     * @param lastName
     */
    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }


    /**
     * Gets the systemRole value for this User.
     * 
     * @return systemRole
     */
    public com.panopto.services.SystemRole getSystemRole() {
        return systemRole;
    }


    /**
     * Sets the systemRole value for this User.
     * 
     * @param systemRole
     */
    public void setSystemRole(com.panopto.services.SystemRole systemRole) {
        this.systemRole = systemRole;
    }


    /**
     * Gets the userBio value for this User.
     * 
     * @return userBio
     */
    public java.lang.String getUserBio() {
        return userBio;
    }


    /**
     * Sets the userBio value for this User.
     * 
     * @param userBio
     */
    public void setUserBio(java.lang.String userBio) {
        this.userBio = userBio;
    }


    /**
     * Gets the userId value for this User.
     * 
     * @return userId
     */
    public java.lang.String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this User.
     * 
     * @param userId
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }


    /**
     * Gets the userKey value for this User.
     * 
     * @return userKey
     */
    public java.lang.String getUserKey() {
        return userKey;
    }


    /**
     * Sets the userKey value for this User.
     * 
     * @param userKey
     */
    public void setUserKey(java.lang.String userKey) {
        this.userKey = userKey;
    }


    /**
     * Gets the userSettingsUrl value for this User.
     * 
     * @return userSettingsUrl
     */
    public java.lang.String getUserSettingsUrl() {
        return userSettingsUrl;
    }


    /**
     * Sets the userSettingsUrl value for this User.
     * 
     * @param userSettingsUrl
     */
    public void setUserSettingsUrl(java.lang.String userSettingsUrl) {
        this.userSettingsUrl = userSettingsUrl;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof User)) return false;
        User other = (User) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.email==null && other.getEmail()==null) || 
             (this.email!=null &&
              this.email.equals(other.getEmail()))) &&
            ((this.emailSessionNotifications==null && other.getEmailSessionNotifications()==null) || 
             (this.emailSessionNotifications!=null &&
              this.emailSessionNotifications.equals(other.getEmailSessionNotifications()))) &&
            ((this.firstName==null && other.getFirstName()==null) || 
             (this.firstName!=null &&
              this.firstName.equals(other.getFirstName()))) &&
            ((this.groupMemberships==null && other.getGroupMemberships()==null) || 
             (this.groupMemberships!=null &&
              java.util.Arrays.equals(this.groupMemberships, other.getGroupMemberships()))) &&
            ((this.lastName==null && other.getLastName()==null) || 
             (this.lastName!=null &&
              this.lastName.equals(other.getLastName()))) &&
            ((this.systemRole==null && other.getSystemRole()==null) || 
             (this.systemRole!=null &&
              this.systemRole.equals(other.getSystemRole()))) &&
            ((this.userBio==null && other.getUserBio()==null) || 
             (this.userBio!=null &&
              this.userBio.equals(other.getUserBio()))) &&
            ((this.userId==null && other.getUserId()==null) || 
             (this.userId!=null &&
              this.userId.equals(other.getUserId()))) &&
            ((this.userKey==null && other.getUserKey()==null) || 
             (this.userKey!=null &&
              this.userKey.equals(other.getUserKey()))) &&
            ((this.userSettingsUrl==null && other.getUserSettingsUrl()==null) || 
             (this.userSettingsUrl!=null &&
              this.userSettingsUrl.equals(other.getUserSettingsUrl())));
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
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getEmailSessionNotifications() != null) {
            _hashCode += getEmailSessionNotifications().hashCode();
        }
        if (getFirstName() != null) {
            _hashCode += getFirstName().hashCode();
        }
        if (getGroupMemberships() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGroupMemberships());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGroupMemberships(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getLastName() != null) {
            _hashCode += getLastName().hashCode();
        }
        if (getSystemRole() != null) {
            _hashCode += getSystemRole().hashCode();
        }
        if (getUserBio() != null) {
            _hashCode += getUserBio().hashCode();
        }
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        if (getUserKey() != null) {
            _hashCode += getUserKey().hashCode();
        }
        if (getUserSettingsUrl() != null) {
            _hashCode += getUserSettingsUrl().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(User.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "User"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "Email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailSessionNotifications");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "EmailSessionNotifications"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "FirstName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupMemberships");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "GroupMemberships"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "guid"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "LastName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemRole");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "SystemRole"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "SystemRole"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userBio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "UserBio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "UserId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userKey");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "UserKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userSettingsUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "UserSettingsUrl"));
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
