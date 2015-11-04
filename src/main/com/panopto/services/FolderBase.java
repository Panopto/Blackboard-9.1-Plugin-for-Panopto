/**
 * FolderBase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public class FolderBase  implements java.io.Serializable {
    private java.lang.Boolean allowPublicNotes;

    private java.lang.Boolean allowSessionDownload;

    private java.lang.String audioPodcastITunesUrl;

    private java.lang.String audioRssUrl;

    private java.lang.String[] childFolders;

    private java.lang.Boolean deliveriesHaveSpecifiedOrder;

    private java.lang.String description;

    private java.lang.String embedUploaderUrl;

    private java.lang.String embedUrl;

    private java.lang.Boolean enablePodcast;

    private java.lang.String id;

    private java.lang.Boolean isPublic;

    private java.lang.String listUrl;

    private java.lang.String name;

    private java.lang.String parentFolder;

    private java.lang.String[] presenters;

    private java.lang.String[] sessions;

    private java.lang.String settingsUrl;

    private java.lang.String videoPodcastITunesUrl;

    private java.lang.String videoRssUrl;

    public FolderBase() {
    }

    public FolderBase(
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
           java.lang.String videoRssUrl) {
           this.allowPublicNotes = allowPublicNotes;
           this.allowSessionDownload = allowSessionDownload;
           this.audioPodcastITunesUrl = audioPodcastITunesUrl;
           this.audioRssUrl = audioRssUrl;
           this.childFolders = childFolders;
           this.deliveriesHaveSpecifiedOrder = deliveriesHaveSpecifiedOrder;
           this.description = description;
           this.embedUploaderUrl = embedUploaderUrl;
           this.embedUrl = embedUrl;
           this.enablePodcast = enablePodcast;
           this.id = id;
           this.isPublic = isPublic;
           this.listUrl = listUrl;
           this.name = name;
           this.parentFolder = parentFolder;
           this.presenters = presenters;
           this.sessions = sessions;
           this.settingsUrl = settingsUrl;
           this.videoPodcastITunesUrl = videoPodcastITunesUrl;
           this.videoRssUrl = videoRssUrl;
    }


    /**
     * Gets the allowPublicNotes value for this FolderBase.
     * 
     * @return allowPublicNotes
     */
    public java.lang.Boolean getAllowPublicNotes() {
        return allowPublicNotes;
    }


    /**
     * Sets the allowPublicNotes value for this FolderBase.
     * 
     * @param allowPublicNotes
     */
    public void setAllowPublicNotes(java.lang.Boolean allowPublicNotes) {
        this.allowPublicNotes = allowPublicNotes;
    }


    /**
     * Gets the allowSessionDownload value for this FolderBase.
     * 
     * @return allowSessionDownload
     */
    public java.lang.Boolean getAllowSessionDownload() {
        return allowSessionDownload;
    }


    /**
     * Sets the allowSessionDownload value for this FolderBase.
     * 
     * @param allowSessionDownload
     */
    public void setAllowSessionDownload(java.lang.Boolean allowSessionDownload) {
        this.allowSessionDownload = allowSessionDownload;
    }


    /**
     * Gets the audioPodcastITunesUrl value for this FolderBase.
     * 
     * @return audioPodcastITunesUrl
     */
    public java.lang.String getAudioPodcastITunesUrl() {
        return audioPodcastITunesUrl;
    }


    /**
     * Sets the audioPodcastITunesUrl value for this FolderBase.
     * 
     * @param audioPodcastITunesUrl
     */
    public void setAudioPodcastITunesUrl(java.lang.String audioPodcastITunesUrl) {
        this.audioPodcastITunesUrl = audioPodcastITunesUrl;
    }


    /**
     * Gets the audioRssUrl value for this FolderBase.
     * 
     * @return audioRssUrl
     */
    public java.lang.String getAudioRssUrl() {
        return audioRssUrl;
    }


    /**
     * Sets the audioRssUrl value for this FolderBase.
     * 
     * @param audioRssUrl
     */
    public void setAudioRssUrl(java.lang.String audioRssUrl) {
        this.audioRssUrl = audioRssUrl;
    }


    /**
     * Gets the childFolders value for this FolderBase.
     * 
     * @return childFolders
     */
    public java.lang.String[] getChildFolders() {
        return childFolders;
    }


    /**
     * Sets the childFolders value for this FolderBase.
     * 
     * @param childFolders
     */
    public void setChildFolders(java.lang.String[] childFolders) {
        this.childFolders = childFolders;
    }


    /**
     * Gets the deliveriesHaveSpecifiedOrder value for this FolderBase.
     * 
     * @return deliveriesHaveSpecifiedOrder
     */
    public java.lang.Boolean getDeliveriesHaveSpecifiedOrder() {
        return deliveriesHaveSpecifiedOrder;
    }


    /**
     * Sets the deliveriesHaveSpecifiedOrder value for this FolderBase.
     * 
     * @param deliveriesHaveSpecifiedOrder
     */
    public void setDeliveriesHaveSpecifiedOrder(java.lang.Boolean deliveriesHaveSpecifiedOrder) {
        this.deliveriesHaveSpecifiedOrder = deliveriesHaveSpecifiedOrder;
    }


    /**
     * Gets the description value for this FolderBase.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this FolderBase.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the embedUploaderUrl value for this FolderBase.
     * 
     * @return embedUploaderUrl
     */
    public java.lang.String getEmbedUploaderUrl() {
        return embedUploaderUrl;
    }


    /**
     * Sets the embedUploaderUrl value for this FolderBase.
     * 
     * @param embedUploaderUrl
     */
    public void setEmbedUploaderUrl(java.lang.String embedUploaderUrl) {
        this.embedUploaderUrl = embedUploaderUrl;
    }


    /**
     * Gets the embedUrl value for this FolderBase.
     * 
     * @return embedUrl
     */
    public java.lang.String getEmbedUrl() {
        return embedUrl;
    }


    /**
     * Sets the embedUrl value for this FolderBase.
     * 
     * @param embedUrl
     */
    public void setEmbedUrl(java.lang.String embedUrl) {
        this.embedUrl = embedUrl;
    }


    /**
     * Gets the enablePodcast value for this FolderBase.
     * 
     * @return enablePodcast
     */
    public java.lang.Boolean getEnablePodcast() {
        return enablePodcast;
    }


    /**
     * Sets the enablePodcast value for this FolderBase.
     * 
     * @param enablePodcast
     */
    public void setEnablePodcast(java.lang.Boolean enablePodcast) {
        this.enablePodcast = enablePodcast;
    }


    /**
     * Gets the id value for this FolderBase.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this FolderBase.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the isPublic value for this FolderBase.
     * 
     * @return isPublic
     */
    public java.lang.Boolean getIsPublic() {
        return isPublic;
    }


    /**
     * Sets the isPublic value for this FolderBase.
     * 
     * @param isPublic
     */
    public void setIsPublic(java.lang.Boolean isPublic) {
        this.isPublic = isPublic;
    }


    /**
     * Gets the listUrl value for this FolderBase.
     * 
     * @return listUrl
     */
    public java.lang.String getListUrl() {
        return listUrl;
    }


    /**
     * Sets the listUrl value for this FolderBase.
     * 
     * @param listUrl
     */
    public void setListUrl(java.lang.String listUrl) {
        this.listUrl = listUrl;
    }


    /**
     * Gets the name value for this FolderBase.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this FolderBase.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the parentFolder value for this FolderBase.
     * 
     * @return parentFolder
     */
    public java.lang.String getParentFolder() {
        return parentFolder;
    }


    /**
     * Sets the parentFolder value for this FolderBase.
     * 
     * @param parentFolder
     */
    public void setParentFolder(java.lang.String parentFolder) {
        this.parentFolder = parentFolder;
    }


    /**
     * Gets the presenters value for this FolderBase.
     * 
     * @return presenters
     */
    public java.lang.String[] getPresenters() {
        return presenters;
    }


    /**
     * Sets the presenters value for this FolderBase.
     * 
     * @param presenters
     */
    public void setPresenters(java.lang.String[] presenters) {
        this.presenters = presenters;
    }


    /**
     * Gets the sessions value for this FolderBase.
     * 
     * @return sessions
     */
    public java.lang.String[] getSessions() {
        return sessions;
    }


    /**
     * Sets the sessions value for this FolderBase.
     * 
     * @param sessions
     */
    public void setSessions(java.lang.String[] sessions) {
        this.sessions = sessions;
    }


    /**
     * Gets the settingsUrl value for this FolderBase.
     * 
     * @return settingsUrl
     */
    public java.lang.String getSettingsUrl() {
        return settingsUrl;
    }


    /**
     * Sets the settingsUrl value for this FolderBase.
     * 
     * @param settingsUrl
     */
    public void setSettingsUrl(java.lang.String settingsUrl) {
        this.settingsUrl = settingsUrl;
    }


    /**
     * Gets the videoPodcastITunesUrl value for this FolderBase.
     * 
     * @return videoPodcastITunesUrl
     */
    public java.lang.String getVideoPodcastITunesUrl() {
        return videoPodcastITunesUrl;
    }


    /**
     * Sets the videoPodcastITunesUrl value for this FolderBase.
     * 
     * @param videoPodcastITunesUrl
     */
    public void setVideoPodcastITunesUrl(java.lang.String videoPodcastITunesUrl) {
        this.videoPodcastITunesUrl = videoPodcastITunesUrl;
    }


    /**
     * Gets the videoRssUrl value for this FolderBase.
     * 
     * @return videoRssUrl
     */
    public java.lang.String getVideoRssUrl() {
        return videoRssUrl;
    }


    /**
     * Sets the videoRssUrl value for this FolderBase.
     * 
     * @param videoRssUrl
     */
    public void setVideoRssUrl(java.lang.String videoRssUrl) {
        this.videoRssUrl = videoRssUrl;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FolderBase)) return false;
        FolderBase other = (FolderBase) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.allowPublicNotes==null && other.getAllowPublicNotes()==null) || 
             (this.allowPublicNotes!=null &&
              this.allowPublicNotes.equals(other.getAllowPublicNotes()))) &&
            ((this.allowSessionDownload==null && other.getAllowSessionDownload()==null) || 
             (this.allowSessionDownload!=null &&
              this.allowSessionDownload.equals(other.getAllowSessionDownload()))) &&
            ((this.audioPodcastITunesUrl==null && other.getAudioPodcastITunesUrl()==null) || 
             (this.audioPodcastITunesUrl!=null &&
              this.audioPodcastITunesUrl.equals(other.getAudioPodcastITunesUrl()))) &&
            ((this.audioRssUrl==null && other.getAudioRssUrl()==null) || 
             (this.audioRssUrl!=null &&
              this.audioRssUrl.equals(other.getAudioRssUrl()))) &&
            ((this.childFolders==null && other.getChildFolders()==null) || 
             (this.childFolders!=null &&
              java.util.Arrays.equals(this.childFolders, other.getChildFolders()))) &&
            ((this.deliveriesHaveSpecifiedOrder==null && other.getDeliveriesHaveSpecifiedOrder()==null) || 
             (this.deliveriesHaveSpecifiedOrder!=null &&
              this.deliveriesHaveSpecifiedOrder.equals(other.getDeliveriesHaveSpecifiedOrder()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.embedUploaderUrl==null && other.getEmbedUploaderUrl()==null) || 
             (this.embedUploaderUrl!=null &&
              this.embedUploaderUrl.equals(other.getEmbedUploaderUrl()))) &&
            ((this.embedUrl==null && other.getEmbedUrl()==null) || 
             (this.embedUrl!=null &&
              this.embedUrl.equals(other.getEmbedUrl()))) &&
            ((this.enablePodcast==null && other.getEnablePodcast()==null) || 
             (this.enablePodcast!=null &&
              this.enablePodcast.equals(other.getEnablePodcast()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.isPublic==null && other.getIsPublic()==null) || 
             (this.isPublic!=null &&
              this.isPublic.equals(other.getIsPublic()))) &&
            ((this.listUrl==null && other.getListUrl()==null) || 
             (this.listUrl!=null &&
              this.listUrl.equals(other.getListUrl()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.parentFolder==null && other.getParentFolder()==null) || 
             (this.parentFolder!=null &&
              this.parentFolder.equals(other.getParentFolder()))) &&
            ((this.presenters==null && other.getPresenters()==null) || 
             (this.presenters!=null &&
              java.util.Arrays.equals(this.presenters, other.getPresenters()))) &&
            ((this.sessions==null && other.getSessions()==null) || 
             (this.sessions!=null &&
              java.util.Arrays.equals(this.sessions, other.getSessions()))) &&
            ((this.settingsUrl==null && other.getSettingsUrl()==null) || 
             (this.settingsUrl!=null &&
              this.settingsUrl.equals(other.getSettingsUrl()))) &&
            ((this.videoPodcastITunesUrl==null && other.getVideoPodcastITunesUrl()==null) || 
             (this.videoPodcastITunesUrl!=null &&
              this.videoPodcastITunesUrl.equals(other.getVideoPodcastITunesUrl()))) &&
            ((this.videoRssUrl==null && other.getVideoRssUrl()==null) || 
             (this.videoRssUrl!=null &&
              this.videoRssUrl.equals(other.getVideoRssUrl())));
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
        if (getAllowPublicNotes() != null) {
            _hashCode += getAllowPublicNotes().hashCode();
        }
        if (getAllowSessionDownload() != null) {
            _hashCode += getAllowSessionDownload().hashCode();
        }
        if (getAudioPodcastITunesUrl() != null) {
            _hashCode += getAudioPodcastITunesUrl().hashCode();
        }
        if (getAudioRssUrl() != null) {
            _hashCode += getAudioRssUrl().hashCode();
        }
        if (getChildFolders() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getChildFolders());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getChildFolders(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDeliveriesHaveSpecifiedOrder() != null) {
            _hashCode += getDeliveriesHaveSpecifiedOrder().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getEmbedUploaderUrl() != null) {
            _hashCode += getEmbedUploaderUrl().hashCode();
        }
        if (getEmbedUrl() != null) {
            _hashCode += getEmbedUrl().hashCode();
        }
        if (getEnablePodcast() != null) {
            _hashCode += getEnablePodcast().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getIsPublic() != null) {
            _hashCode += getIsPublic().hashCode();
        }
        if (getListUrl() != null) {
            _hashCode += getListUrl().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getParentFolder() != null) {
            _hashCode += getParentFolder().hashCode();
        }
        if (getPresenters() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPresenters());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPresenters(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSessions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSessions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSessions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSettingsUrl() != null) {
            _hashCode += getSettingsUrl().hashCode();
        }
        if (getVideoPodcastITunesUrl() != null) {
            _hashCode += getVideoPodcastITunesUrl().hashCode();
        }
        if (getVideoRssUrl() != null) {
            _hashCode += getVideoRssUrl().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FolderBase.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "FolderBase"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allowPublicNotes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "AllowPublicNotes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allowSessionDownload");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "AllowSessionDownload"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("audioPodcastITunesUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "AudioPodcastITunesUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("audioRssUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "AudioRssUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("childFolders");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "ChildFolders"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "guid"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveriesHaveSpecifiedOrder");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "DeliveriesHaveSpecifiedOrder"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "Description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("embedUploaderUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "EmbedUploaderUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("embedUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "EmbedUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enablePodcast");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "EnablePodcast"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "Id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isPublic");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "IsPublic"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "ListUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parentFolder");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "ParentFolder"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("presenters");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "Presenters"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "Sessions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "guid"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("settingsUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "SettingsUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("videoPodcastITunesUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "VideoPodcastITunesUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("videoRssUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V46.Soap", "VideoRssUrl"));
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
