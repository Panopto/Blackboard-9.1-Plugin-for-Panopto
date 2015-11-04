/**
 * UserAccessDetails.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public class UserAccessDetails  implements java.io.Serializable {
    private java.lang.String[] foldersWithCreatorAccess;

    private java.lang.String[] foldersWithViewerAccess;

    private com.panopto.services.GroupAccessDetails[] groupMembershipAccess;

    private java.lang.String[] sessionsWithViewerAccess;

    private com.panopto.services.SystemRole systemRole;

    private java.lang.String userId;

    public UserAccessDetails() {
    }

    public UserAccessDetails(
           java.lang.String[] foldersWithCreatorAccess,
           java.lang.String[] foldersWithViewerAccess,
           com.panopto.services.GroupAccessDetails[] groupMembershipAccess,
           java.lang.String[] sessionsWithViewerAccess,
           com.panopto.services.SystemRole systemRole,
           java.lang.String userId) {
           this.foldersWithCreatorAccess = foldersWithCreatorAccess;
           this.foldersWithViewerAccess = foldersWithViewerAccess;
           this.groupMembershipAccess = groupMembershipAccess;
           this.sessionsWithViewerAccess = sessionsWithViewerAccess;
           this.systemRole = systemRole;
           this.userId = userId;
    }


    /**
     * Gets the foldersWithCreatorAccess value for this UserAccessDetails.
     * 
     * @return foldersWithCreatorAccess
     */
    public java.lang.String[] getFoldersWithCreatorAccess() {
        return foldersWithCreatorAccess;
    }


    /**
     * Sets the foldersWithCreatorAccess value for this UserAccessDetails.
     * 
     * @param foldersWithCreatorAccess
     */
    public void setFoldersWithCreatorAccess(java.lang.String[] foldersWithCreatorAccess) {
        this.foldersWithCreatorAccess = foldersWithCreatorAccess;
    }


    /**
     * Gets the foldersWithViewerAccess value for this UserAccessDetails.
     * 
     * @return foldersWithViewerAccess
     */
    public java.lang.String[] getFoldersWithViewerAccess() {
        return foldersWithViewerAccess;
    }


    /**
     * Sets the foldersWithViewerAccess value for this UserAccessDetails.
     * 
     * @param foldersWithViewerAccess
     */
    public void setFoldersWithViewerAccess(java.lang.String[] foldersWithViewerAccess) {
        this.foldersWithViewerAccess = foldersWithViewerAccess;
    }


    /**
     * Gets the groupMembershipAccess value for this UserAccessDetails.
     * 
     * @return groupMembershipAccess
     */
    public com.panopto.services.GroupAccessDetails[] getGroupMembershipAccess() {
        return groupMembershipAccess;
    }


    /**
     * Sets the groupMembershipAccess value for this UserAccessDetails.
     * 
     * @param groupMembershipAccess
     */
    public void setGroupMembershipAccess(com.panopto.services.GroupAccessDetails[] groupMembershipAccess) {
        this.groupMembershipAccess = groupMembershipAccess;
    }


    /**
     * Gets the sessionsWithViewerAccess value for this UserAccessDetails.
     * 
     * @return sessionsWithViewerAccess
     */
    public java.lang.String[] getSessionsWithViewerAccess() {
        return sessionsWithViewerAccess;
    }


    /**
     * Sets the sessionsWithViewerAccess value for this UserAccessDetails.
     * 
     * @param sessionsWithViewerAccess
     */
    public void setSessionsWithViewerAccess(java.lang.String[] sessionsWithViewerAccess) {
        this.sessionsWithViewerAccess = sessionsWithViewerAccess;
    }


    /**
     * Gets the systemRole value for this UserAccessDetails.
     * 
     * @return systemRole
     */
    public com.panopto.services.SystemRole getSystemRole() {
        return systemRole;
    }


    /**
     * Sets the systemRole value for this UserAccessDetails.
     * 
     * @param systemRole
     */
    public void setSystemRole(com.panopto.services.SystemRole systemRole) {
        this.systemRole = systemRole;
    }


    /**
     * Gets the userId value for this UserAccessDetails.
     * 
     * @return userId
     */
    public java.lang.String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this UserAccessDetails.
     * 
     * @param userId
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UserAccessDetails)) return false;
        UserAccessDetails other = (UserAccessDetails) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.foldersWithCreatorAccess==null && other.getFoldersWithCreatorAccess()==null) || 
             (this.foldersWithCreatorAccess!=null &&
              java.util.Arrays.equals(this.foldersWithCreatorAccess, other.getFoldersWithCreatorAccess()))) &&
            ((this.foldersWithViewerAccess==null && other.getFoldersWithViewerAccess()==null) || 
             (this.foldersWithViewerAccess!=null &&
              java.util.Arrays.equals(this.foldersWithViewerAccess, other.getFoldersWithViewerAccess()))) &&
            ((this.groupMembershipAccess==null && other.getGroupMembershipAccess()==null) || 
             (this.groupMembershipAccess!=null &&
              java.util.Arrays.equals(this.groupMembershipAccess, other.getGroupMembershipAccess()))) &&
            ((this.sessionsWithViewerAccess==null && other.getSessionsWithViewerAccess()==null) || 
             (this.sessionsWithViewerAccess!=null &&
              java.util.Arrays.equals(this.sessionsWithViewerAccess, other.getSessionsWithViewerAccess()))) &&
            ((this.systemRole==null && other.getSystemRole()==null) || 
             (this.systemRole!=null &&
              this.systemRole.equals(other.getSystemRole()))) &&
            ((this.userId==null && other.getUserId()==null) || 
             (this.userId!=null &&
              this.userId.equals(other.getUserId())));
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
        if (getFoldersWithCreatorAccess() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFoldersWithCreatorAccess());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFoldersWithCreatorAccess(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFoldersWithViewerAccess() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFoldersWithViewerAccess());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFoldersWithViewerAccess(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getGroupMembershipAccess() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGroupMembershipAccess());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGroupMembershipAccess(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSessionsWithViewerAccess() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSessionsWithViewerAccess());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSessionsWithViewerAccess(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSystemRole() != null) {
            _hashCode += getSystemRole().hashCode();
        }
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UserAccessDetails.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "UserAccessDetails"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("foldersWithCreatorAccess");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "FoldersWithCreatorAccess"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "guid"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("foldersWithViewerAccess");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "FoldersWithViewerAccess"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "guid"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupMembershipAccess");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "GroupMembershipAccess"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "GroupAccessDetails"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "GroupAccessDetails"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionsWithViewerAccess");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "SessionsWithViewerAccess"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "guid"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemRole");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "SystemRole"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "SystemRole"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "UserId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
