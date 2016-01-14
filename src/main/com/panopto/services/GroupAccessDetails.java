/**
 * GroupAccessDetails.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public class GroupAccessDetails  implements java.io.Serializable {
    private java.lang.String[] foldersWithCreatorAccess;

    private java.lang.String[] foldersWithViewerAccess;

    private java.lang.String groupId;

    private java.lang.String[] sessionsWithViewerAccess;

    private com.panopto.services.SystemRole systemRole;

    public GroupAccessDetails() {
    }

    public GroupAccessDetails(
           java.lang.String[] foldersWithCreatorAccess,
           java.lang.String[] foldersWithViewerAccess,
           java.lang.String groupId,
           java.lang.String[] sessionsWithViewerAccess,
           com.panopto.services.SystemRole systemRole) {
           this.foldersWithCreatorAccess = foldersWithCreatorAccess;
           this.foldersWithViewerAccess = foldersWithViewerAccess;
           this.groupId = groupId;
           this.sessionsWithViewerAccess = sessionsWithViewerAccess;
           this.systemRole = systemRole;
    }


    /**
     * Gets the foldersWithCreatorAccess value for this GroupAccessDetails.
     * 
     * @return foldersWithCreatorAccess
     */
    public java.lang.String[] getFoldersWithCreatorAccess() {
        return foldersWithCreatorAccess;
    }


    /**
     * Sets the foldersWithCreatorAccess value for this GroupAccessDetails.
     * 
     * @param foldersWithCreatorAccess
     */
    public void setFoldersWithCreatorAccess(java.lang.String[] foldersWithCreatorAccess) {
        this.foldersWithCreatorAccess = foldersWithCreatorAccess;
    }


    /**
     * Gets the foldersWithViewerAccess value for this GroupAccessDetails.
     * 
     * @return foldersWithViewerAccess
     */
    public java.lang.String[] getFoldersWithViewerAccess() {
        return foldersWithViewerAccess;
    }


    /**
     * Sets the foldersWithViewerAccess value for this GroupAccessDetails.
     * 
     * @param foldersWithViewerAccess
     */
    public void setFoldersWithViewerAccess(java.lang.String[] foldersWithViewerAccess) {
        this.foldersWithViewerAccess = foldersWithViewerAccess;
    }


    /**
     * Gets the groupId value for this GroupAccessDetails.
     * 
     * @return groupId
     */
    public java.lang.String getGroupId() {
        return groupId;
    }


    /**
     * Sets the groupId value for this GroupAccessDetails.
     * 
     * @param groupId
     */
    public void setGroupId(java.lang.String groupId) {
        this.groupId = groupId;
    }


    /**
     * Gets the sessionsWithViewerAccess value for this GroupAccessDetails.
     * 
     * @return sessionsWithViewerAccess
     */
    public java.lang.String[] getSessionsWithViewerAccess() {
        return sessionsWithViewerAccess;
    }


    /**
     * Sets the sessionsWithViewerAccess value for this GroupAccessDetails.
     * 
     * @param sessionsWithViewerAccess
     */
    public void setSessionsWithViewerAccess(java.lang.String[] sessionsWithViewerAccess) {
        this.sessionsWithViewerAccess = sessionsWithViewerAccess;
    }


    /**
     * Gets the systemRole value for this GroupAccessDetails.
     * 
     * @return systemRole
     */
    public com.panopto.services.SystemRole getSystemRole() {
        return systemRole;
    }


    /**
     * Sets the systemRole value for this GroupAccessDetails.
     * 
     * @param systemRole
     */
    public void setSystemRole(com.panopto.services.SystemRole systemRole) {
        this.systemRole = systemRole;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GroupAccessDetails)) return false;
        GroupAccessDetails other = (GroupAccessDetails) obj;
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
            ((this.groupId==null && other.getGroupId()==null) || 
             (this.groupId!=null &&
              this.groupId.equals(other.getGroupId()))) &&
            ((this.sessionsWithViewerAccess==null && other.getSessionsWithViewerAccess()==null) || 
             (this.sessionsWithViewerAccess!=null &&
              java.util.Arrays.equals(this.sessionsWithViewerAccess, other.getSessionsWithViewerAccess()))) &&
            ((this.systemRole==null && other.getSystemRole()==null) || 
             (this.systemRole!=null &&
              this.systemRole.equals(other.getSystemRole())));
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
        if (getGroupId() != null) {
            _hashCode += getGroupId().hashCode();
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GroupAccessDetails.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "GroupAccessDetails"));
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
        elemField.setFieldName("groupId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "GroupId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
