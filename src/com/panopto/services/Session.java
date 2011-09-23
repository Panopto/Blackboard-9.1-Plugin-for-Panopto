/**
 * Session.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public class Session  implements java.io.Serializable {
    private java.lang.String creatorId;

    private java.lang.String description;

    private java.lang.Double duration;

    private java.lang.String editorUrl;

    private java.lang.String folderId;

    private java.lang.String id;

    private java.lang.Boolean isBroadcast;

    private java.lang.Boolean isDownloadable;

    private java.lang.String MP3Url;

    private java.lang.String MP4Url;

    private java.lang.String name;

    private java.lang.String notesURL;

    private java.lang.String outputsPageUrl;

    private java.lang.String[] remoteRecorderIds;

    private java.lang.String sharePageUrl;

    private java.util.Calendar startTime;

    private com.panopto.services.SessionState state;

    private java.lang.String statusMessage;

    private java.lang.String thumbUrl;

    private java.lang.String viewerUrl;

    public Session() {
    }

    public Session(
           java.lang.String creatorId,
           java.lang.String description,
           java.lang.Double duration,
           java.lang.String editorUrl,
           java.lang.String folderId,
           java.lang.String id,
           java.lang.Boolean isBroadcast,
           java.lang.Boolean isDownloadable,
           java.lang.String MP3Url,
           java.lang.String MP4Url,
           java.lang.String name,
           java.lang.String notesURL,
           java.lang.String outputsPageUrl,
           java.lang.String[] remoteRecorderIds,
           java.lang.String sharePageUrl,
           java.util.Calendar startTime,
           com.panopto.services.SessionState state,
           java.lang.String statusMessage,
           java.lang.String thumbUrl,
           java.lang.String viewerUrl) {
           this.creatorId = creatorId;
           this.description = description;
           this.duration = duration;
           this.editorUrl = editorUrl;
           this.folderId = folderId;
           this.id = id;
           this.isBroadcast = isBroadcast;
           this.isDownloadable = isDownloadable;
           this.MP3Url = MP3Url;
           this.MP4Url = MP4Url;
           this.name = name;
           this.notesURL = notesURL;
           this.outputsPageUrl = outputsPageUrl;
           this.remoteRecorderIds = remoteRecorderIds;
           this.sharePageUrl = sharePageUrl;
           this.startTime = startTime;
           this.state = state;
           this.statusMessage = statusMessage;
           this.thumbUrl = thumbUrl;
           this.viewerUrl = viewerUrl;
    }


    /**
     * Gets the creatorId value for this Session.
     * 
     * @return creatorId
     */
    public java.lang.String getCreatorId() {
        return creatorId;
    }


    /**
     * Sets the creatorId value for this Session.
     * 
     * @param creatorId
     */
    public void setCreatorId(java.lang.String creatorId) {
        this.creatorId = creatorId;
    }


    /**
     * Gets the description value for this Session.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Session.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the duration value for this Session.
     * 
     * @return duration
     */
    public java.lang.Double getDuration() {
        return duration;
    }


    /**
     * Sets the duration value for this Session.
     * 
     * @param duration
     */
    public void setDuration(java.lang.Double duration) {
        this.duration = duration;
    }


    /**
     * Gets the editorUrl value for this Session.
     * 
     * @return editorUrl
     */
    public java.lang.String getEditorUrl() {
        return editorUrl;
    }


    /**
     * Sets the editorUrl value for this Session.
     * 
     * @param editorUrl
     */
    public void setEditorUrl(java.lang.String editorUrl) {
        this.editorUrl = editorUrl;
    }


    /**
     * Gets the folderId value for this Session.
     * 
     * @return folderId
     */
    public java.lang.String getFolderId() {
        return folderId;
    }


    /**
     * Sets the folderId value for this Session.
     * 
     * @param folderId
     */
    public void setFolderId(java.lang.String folderId) {
        this.folderId = folderId;
    }


    /**
     * Gets the id value for this Session.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this Session.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the isBroadcast value for this Session.
     * 
     * @return isBroadcast
     */
    public java.lang.Boolean getIsBroadcast() {
        return isBroadcast;
    }


    /**
     * Sets the isBroadcast value for this Session.
     * 
     * @param isBroadcast
     */
    public void setIsBroadcast(java.lang.Boolean isBroadcast) {
        this.isBroadcast = isBroadcast;
    }


    /**
     * Gets the isDownloadable value for this Session.
     * 
     * @return isDownloadable
     */
    public java.lang.Boolean getIsDownloadable() {
        return isDownloadable;
    }


    /**
     * Sets the isDownloadable value for this Session.
     * 
     * @param isDownloadable
     */
    public void setIsDownloadable(java.lang.Boolean isDownloadable) {
        this.isDownloadable = isDownloadable;
    }


    /**
     * Gets the MP3Url value for this Session.
     * 
     * @return MP3Url
     */
    public java.lang.String getMP3Url() {
        return MP3Url;
    }


    /**
     * Sets the MP3Url value for this Session.
     * 
     * @param MP3Url
     */
    public void setMP3Url(java.lang.String MP3Url) {
        this.MP3Url = MP3Url;
    }


    /**
     * Gets the MP4Url value for this Session.
     * 
     * @return MP4Url
     */
    public java.lang.String getMP4Url() {
        return MP4Url;
    }


    /**
     * Sets the MP4Url value for this Session.
     * 
     * @param MP4Url
     */
    public void setMP4Url(java.lang.String MP4Url) {
        this.MP4Url = MP4Url;
    }


    /**
     * Gets the name value for this Session.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Session.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the notesURL value for this Session.
     * 
     * @return notesURL
     */
    public java.lang.String getNotesURL() {
        return notesURL;
    }


    /**
     * Sets the notesURL value for this Session.
     * 
     * @param notesURL
     */
    public void setNotesURL(java.lang.String notesURL) {
        this.notesURL = notesURL;
    }


    /**
     * Gets the outputsPageUrl value for this Session.
     * 
     * @return outputsPageUrl
     */
    public java.lang.String getOutputsPageUrl() {
        return outputsPageUrl;
    }


    /**
     * Sets the outputsPageUrl value for this Session.
     * 
     * @param outputsPageUrl
     */
    public void setOutputsPageUrl(java.lang.String outputsPageUrl) {
        this.outputsPageUrl = outputsPageUrl;
    }


    /**
     * Gets the remoteRecorderIds value for this Session.
     * 
     * @return remoteRecorderIds
     */
    public java.lang.String[] getRemoteRecorderIds() {
        return remoteRecorderIds;
    }


    /**
     * Sets the remoteRecorderIds value for this Session.
     * 
     * @param remoteRecorderIds
     */
    public void setRemoteRecorderIds(java.lang.String[] remoteRecorderIds) {
        this.remoteRecorderIds = remoteRecorderIds;
    }


    /**
     * Gets the sharePageUrl value for this Session.
     * 
     * @return sharePageUrl
     */
    public java.lang.String getSharePageUrl() {
        return sharePageUrl;
    }


    /**
     * Sets the sharePageUrl value for this Session.
     * 
     * @param sharePageUrl
     */
    public void setSharePageUrl(java.lang.String sharePageUrl) {
        this.sharePageUrl = sharePageUrl;
    }


    /**
     * Gets the startTime value for this Session.
     * 
     * @return startTime
     */
    public java.util.Calendar getStartTime() {
        return startTime;
    }


    /**
     * Sets the startTime value for this Session.
     * 
     * @param startTime
     */
    public void setStartTime(java.util.Calendar startTime) {
        this.startTime = startTime;
    }


    /**
     * Gets the state value for this Session.
     * 
     * @return state
     */
    public com.panopto.services.SessionState getState() {
        return state;
    }


    /**
     * Sets the state value for this Session.
     * 
     * @param state
     */
    public void setState(com.panopto.services.SessionState state) {
        this.state = state;
    }


    /**
     * Gets the statusMessage value for this Session.
     * 
     * @return statusMessage
     */
    public java.lang.String getStatusMessage() {
        return statusMessage;
    }


    /**
     * Sets the statusMessage value for this Session.
     * 
     * @param statusMessage
     */
    public void setStatusMessage(java.lang.String statusMessage) {
        this.statusMessage = statusMessage;
    }


    /**
     * Gets the thumbUrl value for this Session.
     * 
     * @return thumbUrl
     */
    public java.lang.String getThumbUrl() {
        return thumbUrl;
    }


    /**
     * Sets the thumbUrl value for this Session.
     * 
     * @param thumbUrl
     */
    public void setThumbUrl(java.lang.String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }


    /**
     * Gets the viewerUrl value for this Session.
     * 
     * @return viewerUrl
     */
    public java.lang.String getViewerUrl() {
        return viewerUrl;
    }


    /**
     * Sets the viewerUrl value for this Session.
     * 
     * @param viewerUrl
     */
    public void setViewerUrl(java.lang.String viewerUrl) {
        this.viewerUrl = viewerUrl;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Session)) return false;
        Session other = (Session) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.creatorId==null && other.getCreatorId()==null) || 
             (this.creatorId!=null &&
              this.creatorId.equals(other.getCreatorId()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.duration==null && other.getDuration()==null) || 
             (this.duration!=null &&
              this.duration.equals(other.getDuration()))) &&
            ((this.editorUrl==null && other.getEditorUrl()==null) || 
             (this.editorUrl!=null &&
              this.editorUrl.equals(other.getEditorUrl()))) &&
            ((this.folderId==null && other.getFolderId()==null) || 
             (this.folderId!=null &&
              this.folderId.equals(other.getFolderId()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.isBroadcast==null && other.getIsBroadcast()==null) || 
             (this.isBroadcast!=null &&
              this.isBroadcast.equals(other.getIsBroadcast()))) &&
            ((this.isDownloadable==null && other.getIsDownloadable()==null) || 
             (this.isDownloadable!=null &&
              this.isDownloadable.equals(other.getIsDownloadable()))) &&
            ((this.MP3Url==null && other.getMP3Url()==null) || 
             (this.MP3Url!=null &&
              this.MP3Url.equals(other.getMP3Url()))) &&
            ((this.MP4Url==null && other.getMP4Url()==null) || 
             (this.MP4Url!=null &&
              this.MP4Url.equals(other.getMP4Url()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.notesURL==null && other.getNotesURL()==null) || 
             (this.notesURL!=null &&
              this.notesURL.equals(other.getNotesURL()))) &&
            ((this.outputsPageUrl==null && other.getOutputsPageUrl()==null) || 
             (this.outputsPageUrl!=null &&
              this.outputsPageUrl.equals(other.getOutputsPageUrl()))) &&
            ((this.remoteRecorderIds==null && other.getRemoteRecorderIds()==null) || 
             (this.remoteRecorderIds!=null &&
              java.util.Arrays.equals(this.remoteRecorderIds, other.getRemoteRecorderIds()))) &&
            ((this.sharePageUrl==null && other.getSharePageUrl()==null) || 
             (this.sharePageUrl!=null &&
              this.sharePageUrl.equals(other.getSharePageUrl()))) &&
            ((this.startTime==null && other.getStartTime()==null) || 
             (this.startTime!=null &&
              this.startTime.equals(other.getStartTime()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            ((this.statusMessage==null && other.getStatusMessage()==null) || 
             (this.statusMessage!=null &&
              this.statusMessage.equals(other.getStatusMessage()))) &&
            ((this.thumbUrl==null && other.getThumbUrl()==null) || 
             (this.thumbUrl!=null &&
              this.thumbUrl.equals(other.getThumbUrl()))) &&
            ((this.viewerUrl==null && other.getViewerUrl()==null) || 
             (this.viewerUrl!=null &&
              this.viewerUrl.equals(other.getViewerUrl())));
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
        if (getCreatorId() != null) {
            _hashCode += getCreatorId().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getDuration() != null) {
            _hashCode += getDuration().hashCode();
        }
        if (getEditorUrl() != null) {
            _hashCode += getEditorUrl().hashCode();
        }
        if (getFolderId() != null) {
            _hashCode += getFolderId().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getIsBroadcast() != null) {
            _hashCode += getIsBroadcast().hashCode();
        }
        if (getIsDownloadable() != null) {
            _hashCode += getIsDownloadable().hashCode();
        }
        if (getMP3Url() != null) {
            _hashCode += getMP3Url().hashCode();
        }
        if (getMP4Url() != null) {
            _hashCode += getMP4Url().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getNotesURL() != null) {
            _hashCode += getNotesURL().hashCode();
        }
        if (getOutputsPageUrl() != null) {
            _hashCode += getOutputsPageUrl().hashCode();
        }
        if (getRemoteRecorderIds() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRemoteRecorderIds());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRemoteRecorderIds(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSharePageUrl() != null) {
            _hashCode += getSharePageUrl().hashCode();
        }
        if (getStartTime() != null) {
            _hashCode += getStartTime().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        if (getStatusMessage() != null) {
            _hashCode += getStatusMessage().hashCode();
        }
        if (getThumbUrl() != null) {
            _hashCode += getThumbUrl().hashCode();
        }
        if (getViewerUrl() != null) {
            _hashCode += getViewerUrl().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Session.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "Session"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creatorId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "CreatorId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "Description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("duration");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "Duration"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("editorUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "EditorUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("folderId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "FolderId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "Id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isBroadcast");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "IsBroadcast"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isDownloadable");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "IsDownloadable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MP3Url");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "MP3Url"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MP4Url");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "MP4Url"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notesURL");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "NotesURL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outputsPageUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "OutputsPageUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("remoteRecorderIds");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "RemoteRecorderIds"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "guid"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sharePageUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "SharePageUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "StartTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "State"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "SessionState"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "StatusMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("thumbUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "ThumbUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("viewerUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Panopto.Server.Services.PublicAPI.V40", "ViewerUrl"));
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
