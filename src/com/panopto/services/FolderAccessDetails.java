/**
 * FolderAccessDetails.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public class FolderAccessDetails  implements java.io.Serializable {
    private java.lang.String folderId;

    private java.lang.String[] groupsWithCreatorAccess;

    private java.lang.String[] groupsWithViewerAccess;

    private java.lang.Boolean isPublic;

    private java.lang.String[] usersWithCreatorAccess;

    private java.lang.String[] usersWithViewerAccess;

    public FolderAccessDetails() {
    }

    public FolderAccessDetails(
           java.lang.String folderId,
           java.lang.String[] groupsWithCreatorAccess,
           java.lang.String[] groupsWithViewerAccess,
           java.lang.Boolean isPublic,
           java.lang.String[] usersWithCreatorAccess,
           java.lang.String[] usersWithViewerAccess) {
           this.folderId = folderId;
           this.groupsWithCreatorAccess = groupsWithCreatorAccess;
           this.groupsWithViewerAccess = groupsWithViewerAccess;
           this.isPublic = isPublic;
           this.usersWithCreatorAccess = usersWithCreatorAccess;
           this.usersWithViewerAccess = usersWithViewerAccess;
    }


    /**
     * Gets the folderId value for this FolderAccessDetails.
     * 
     * @return folderId
     */
    public java.lang.String getFolderId() {
        return folderId;
    }


    /**
     * Sets the folderId value for this FolderAccessDetails.
     * 
     * @param folderId
     */
    public void setFolderId(java.lang.String folderId) {
        this.folderId = folderId;
    }


    /**
     * Gets the groupsWithCreatorAccess value for this FolderAccessDetails.
     * 
     * @return groupsWithCreatorAccess
     */
    public java.lang.String[] getGroupsWithCreatorAccess() {
        return groupsWithCreatorAccess;
    }


    /**
     * Sets the groupsWithCreatorAccess value for this FolderAccessDetails.
     * 
     * @param groupsWithCreatorAccess
     */
    public void setGroupsWithCreatorAccess(java.lang.String[] groupsWithCreatorAccess) {
        this.groupsWithCreatorAccess = groupsWithCreatorAccess;
    }


    /**
     * Gets the groupsWithViewerAccess value for this FolderAccessDetails.
     * 
     * @return groupsWithViewerAccess
     */
    public java.lang.String[] getGroupsWithViewerAccess() {
        return groupsWithViewerAccess;
    }


    /**
     * Sets the groupsWithViewerAccess value for this FolderAccessDetails.
     * 
     * @param groupsWithViewerAccess
     */
    public void setGroupsWithViewerAccess(java.lang.String[] groupsWithViewerAccess) {
        this.groupsWithViewerAccess = groupsWithViewerAccess;
    }


    /**
     * Gets the isPublic value for this FolderAccessDetails.
     * 
     * @return isPublic
     */
    public java.lang.Boolean getIsPublic() {
        return isPublic;
    }


    /**
     * Sets the isPublic value for this FolderAccessDetails.
     * 
     * @param isPublic
     */
    public void setIsPublic(java.lang.Boolean isPublic) {
        this.isPublic = isPublic;
    }


    /**
     * Gets the usersWithCreatorAccess value for this FolderAccessDetails.
     * 
     * @return usersWithCreatorAccess
     */
    public java.lang.String[] getUsersWithCreatorAccess() {
        return usersWithCreatorAccess;
    }


    /**
     * Sets the usersWithCreatorAccess value for this FolderAccessDetails.
     * 
     * @param usersWithCreatorAccess
     */
    public void setUsersWithCreatorAccess(java.lang.String[] usersWithCreatorAccess) {
        this.usersWithCreatorAccess = usersWithCreatorAccess;
    }


    /**
     * Gets the usersWithViewerAccess value for this FolderAccessDetails.
     * 
     * @return usersWithViewerAccess
     */
    public java.lang.String[] getUsersWithViewerAccess() {
        return usersWithViewerAccess;
    }


    /**
     * Sets the usersWithViewerAccess value for this FolderAccessDetails.
     * 
     * @param usersWithViewerAccess
     */
    public void setUsersWithViewerAccess(java.lang.String[] usersWithViewerAccess) {
        this.usersWithViewerAccess = usersWithViewerAccess;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FolderAccessDetails)) return false;
        FolderAccessDetails other = (FolderAccessDetails) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.folderId==null && other.getFolderId()==null) || 
             (this.folderId!=null &&
              this.folderId.equals(other.getFolderId()))) &&
            ((this.groupsWithCreatorAccess==null && other.getGroupsWithCreatorAccess()==null) || 
             (this.groupsWithCreatorAccess!=null &&
              java.util.Arrays.equals(this.groupsWithCreatorAccess, other.getGroupsWithCreatorAccess()))) &&
            ((this.groupsWithViewerAccess==null && other.getGroupsWithViewerAccess()==null) || 
             (this.groupsWithViewerAccess!=null &&
              java.util.Arrays.equals(this.groupsWithViewerAccess, other.getGroupsWithViewerAccess()))) &&
            ((this.isPublic==null && other.getIsPublic()==null) || 
             (this.isPublic!=null &&
              this.isPublic.equals(other.getIsPublic()))) &&
            ((this.usersWithCreatorAccess==null && other.getUsersWithCreatorAccess()==null) || 
             (this.usersWithCreatorAccess!=null &&
              java.util.Arrays.equals(this.usersWithCreatorAccess, other.getUsersWithCreatorAccess()))) &&
            ((this.usersWithViewerAccess==null && other.getUsersWithViewerAccess()==null) || 
             (this.usersWithViewerAccess!=null &&
              java.util.Arrays.equals(this.usersWithViewerAccess, other.getUsersWithViewerAccess())));
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
        if (getFolderId() != null) {
            _hashCode += getFolderId().hashCode();
        }
        if (getGroupsWithCreatorAccess() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGroupsWithCreatorAccess());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGroupsWithCreatorAccess(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getGroupsWithViewerAccess() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGroupsWithViewerAccess());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGroupsWithViewerAccess(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIsPublic() != null) {
            _hashCode += getIsPublic().hashCode();
        }
        if (getUsersWithCreatorAccess() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUsersWithCreatorAccess());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUsersWithCreatorAccess(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUsersWithViewerAccess() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUsersWithViewerAccess());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUsersWithViewerAccess(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FolderAccessDetails.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "FolderAccessDetails"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("folderId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "FolderId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupsWithCreatorAccess");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "GroupsWithCreatorAccess"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "guid"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupsWithViewerAccess");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "GroupsWithViewerAccess"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "guid"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isPublic");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "IsPublic"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usersWithCreatorAccess");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "UsersWithCreatorAccess"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "guid"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usersWithViewerAccess");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "UsersWithViewerAccess"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "guid"));
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
