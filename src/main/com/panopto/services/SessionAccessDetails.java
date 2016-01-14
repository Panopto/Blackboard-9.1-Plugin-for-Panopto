/**
 * SessionAccessDetails.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public class SessionAccessDetails  implements java.io.Serializable {
    private com.panopto.services.FolderAccessDetails folderAccess;

    private java.lang.String[] groupsWithDirectViewerAccess;

    private java.lang.Boolean inheritViewerAccess;

    private java.lang.Boolean isPublic;

    private java.lang.String sessionId;

    private java.lang.String[] usersWithDirectViewerAccess;

    public SessionAccessDetails() {
    }

    public SessionAccessDetails(
           com.panopto.services.FolderAccessDetails folderAccess,
           java.lang.String[] groupsWithDirectViewerAccess,
           java.lang.Boolean inheritViewerAccess,
           java.lang.Boolean isPublic,
           java.lang.String sessionId,
           java.lang.String[] usersWithDirectViewerAccess) {
           this.folderAccess = folderAccess;
           this.groupsWithDirectViewerAccess = groupsWithDirectViewerAccess;
           this.inheritViewerAccess = inheritViewerAccess;
           this.isPublic = isPublic;
           this.sessionId = sessionId;
           this.usersWithDirectViewerAccess = usersWithDirectViewerAccess;
    }


    /**
     * Gets the folderAccess value for this SessionAccessDetails.
     * 
     * @return folderAccess
     */
    public com.panopto.services.FolderAccessDetails getFolderAccess() {
        return folderAccess;
    }


    /**
     * Sets the folderAccess value for this SessionAccessDetails.
     * 
     * @param folderAccess
     */
    public void setFolderAccess(com.panopto.services.FolderAccessDetails folderAccess) {
        this.folderAccess = folderAccess;
    }


    /**
     * Gets the groupsWithDirectViewerAccess value for this SessionAccessDetails.
     * 
     * @return groupsWithDirectViewerAccess
     */
    public java.lang.String[] getGroupsWithDirectViewerAccess() {
        return groupsWithDirectViewerAccess;
    }


    /**
     * Sets the groupsWithDirectViewerAccess value for this SessionAccessDetails.
     * 
     * @param groupsWithDirectViewerAccess
     */
    public void setGroupsWithDirectViewerAccess(java.lang.String[] groupsWithDirectViewerAccess) {
        this.groupsWithDirectViewerAccess = groupsWithDirectViewerAccess;
    }


    /**
     * Gets the inheritViewerAccess value for this SessionAccessDetails.
     * 
     * @return inheritViewerAccess
     */
    public java.lang.Boolean getInheritViewerAccess() {
        return inheritViewerAccess;
    }


    /**
     * Sets the inheritViewerAccess value for this SessionAccessDetails.
     * 
     * @param inheritViewerAccess
     */
    public void setInheritViewerAccess(java.lang.Boolean inheritViewerAccess) {
        this.inheritViewerAccess = inheritViewerAccess;
    }


    /**
     * Gets the isPublic value for this SessionAccessDetails.
     * 
     * @return isPublic
     */
    public java.lang.Boolean getIsPublic() {
        return isPublic;
    }


    /**
     * Sets the isPublic value for this SessionAccessDetails.
     * 
     * @param isPublic
     */
    public void setIsPublic(java.lang.Boolean isPublic) {
        this.isPublic = isPublic;
    }


    /**
     * Gets the sessionId value for this SessionAccessDetails.
     * 
     * @return sessionId
     */
    public java.lang.String getSessionId() {
        return sessionId;
    }


    /**
     * Sets the sessionId value for this SessionAccessDetails.
     * 
     * @param sessionId
     */
    public void setSessionId(java.lang.String sessionId) {
        this.sessionId = sessionId;
    }


    /**
     * Gets the usersWithDirectViewerAccess value for this SessionAccessDetails.
     * 
     * @return usersWithDirectViewerAccess
     */
    public java.lang.String[] getUsersWithDirectViewerAccess() {
        return usersWithDirectViewerAccess;
    }


    /**
     * Sets the usersWithDirectViewerAccess value for this SessionAccessDetails.
     * 
     * @param usersWithDirectViewerAccess
     */
    public void setUsersWithDirectViewerAccess(java.lang.String[] usersWithDirectViewerAccess) {
        this.usersWithDirectViewerAccess = usersWithDirectViewerAccess;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SessionAccessDetails)) return false;
        SessionAccessDetails other = (SessionAccessDetails) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.folderAccess==null && other.getFolderAccess()==null) || 
             (this.folderAccess!=null &&
              this.folderAccess.equals(other.getFolderAccess()))) &&
            ((this.groupsWithDirectViewerAccess==null && other.getGroupsWithDirectViewerAccess()==null) || 
             (this.groupsWithDirectViewerAccess!=null &&
              java.util.Arrays.equals(this.groupsWithDirectViewerAccess, other.getGroupsWithDirectViewerAccess()))) &&
            ((this.inheritViewerAccess==null && other.getInheritViewerAccess()==null) || 
             (this.inheritViewerAccess!=null &&
              this.inheritViewerAccess.equals(other.getInheritViewerAccess()))) &&
            ((this.isPublic==null && other.getIsPublic()==null) || 
             (this.isPublic!=null &&
              this.isPublic.equals(other.getIsPublic()))) &&
            ((this.sessionId==null && other.getSessionId()==null) || 
             (this.sessionId!=null &&
              this.sessionId.equals(other.getSessionId()))) &&
            ((this.usersWithDirectViewerAccess==null && other.getUsersWithDirectViewerAccess()==null) || 
             (this.usersWithDirectViewerAccess!=null &&
              java.util.Arrays.equals(this.usersWithDirectViewerAccess, other.getUsersWithDirectViewerAccess())));
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
        if (getFolderAccess() != null) {
            _hashCode += getFolderAccess().hashCode();
        }
        if (getGroupsWithDirectViewerAccess() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGroupsWithDirectViewerAccess());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGroupsWithDirectViewerAccess(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getInheritViewerAccess() != null) {
            _hashCode += getInheritViewerAccess().hashCode();
        }
        if (getIsPublic() != null) {
            _hashCode += getIsPublic().hashCode();
        }
        if (getSessionId() != null) {
            _hashCode += getSessionId().hashCode();
        }
        if (getUsersWithDirectViewerAccess() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUsersWithDirectViewerAccess());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUsersWithDirectViewerAccess(), i);
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
        new org.apache.axis.description.TypeDesc(SessionAccessDetails.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "SessionAccessDetails"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("folderAccess");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "FolderAccess"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "FolderAccessDetails"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupsWithDirectViewerAccess");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "GroupsWithDirectViewerAccess"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "guid"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inheritViewerAccess");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "InheritViewerAccess"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isPublic");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "IsPublic"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "SessionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usersWithDirectViewerAccess");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "UsersWithDirectViewerAccess"));
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
