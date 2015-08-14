/**
 * Folder.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public class Folder  extends com.panopto.services.FolderBase  implements java.io.Serializable {
    private java.lang.String externalId;

    public Folder() {
    }

    public Folder(
           java.lang.Boolean allowPublicNotes,
           java.lang.Boolean allowSessionDownload,
           java.lang.String audioPodcastITunesUrl,
           java.lang.String audioRssUrl,
           java.lang.String[] childFolders,
           java.lang.Boolean deliveriesHaveSpecifiedOrder,
           java.lang.String description,
           java.lang.String embedUploaderUrl,
           java.lang.String embedUrl,
           java.lang.Boolean enablePodcast,
           java.lang.String id,
           java.lang.Boolean isPublic,
           java.lang.String listUrl,
           java.lang.String name,
           java.lang.String parentFolder,
           java.lang.String[] presenters,
           java.lang.String[] sessions,
           java.lang.String settingsUrl,
           java.lang.String videoPodcastITunesUrl,
           java.lang.String videoRssUrl,
           java.lang.String externalId) {
        super(
            allowPublicNotes,
            allowSessionDownload,
            audioPodcastITunesUrl,
            audioRssUrl,
            childFolders,
            deliveriesHaveSpecifiedOrder,
            description,
            embedUploaderUrl,
            embedUrl,
            enablePodcast,
            id,
            isPublic,
            listUrl,
            name,
            parentFolder,
            presenters,
            sessions,
            settingsUrl,
            videoPodcastITunesUrl,
            videoRssUrl);
        this.externalId = externalId;
    }


    /**
     * Gets the externalId value for this Folder.
     * 
     * @return externalId
     */
    public java.lang.String getExternalId() {
        return externalId;
    }


    /**
     * Sets the externalId value for this Folder.
     * 
     * @param externalId
     */
    public void setExternalId(java.lang.String externalId) {
        this.externalId = externalId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Folder)) return false;
        Folder other = (Folder) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.externalId==null && other.getExternalId()==null) || 
             (this.externalId!=null &&
              this.externalId.equals(other.getExternalId())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getExternalId() != null) {
            _hashCode += getExternalId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Folder.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "Folder"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "ExternalId"));
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
