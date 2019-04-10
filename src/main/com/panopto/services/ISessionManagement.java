/**
 * ISessionManagement.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public interface ISessionManagement extends java.rmi.Remote {
    public com.panopto.services.Folder addFolder(com.panopto.services.AuthenticationInfo auth, java.lang.String name, java.lang.String parentFolder, java.lang.Boolean isPublic) throws java.rmi.RemoteException;
    public com.panopto.services.Session addSession(com.panopto.services.AuthenticationInfo auth, java.lang.String name, java.lang.String folderId, java.lang.Boolean isBroadcast) throws java.rmi.RemoteException;
    public com.panopto.services.Folder[] getFoldersById(com.panopto.services.AuthenticationInfo auth, java.lang.String[] folderIds) throws java.rmi.RemoteException;
    public com.panopto.services.FolderWithExternalContext[] getFoldersWithExternalContextById(com.panopto.services.AuthenticationInfo auth, java.lang.String[] folderIds) throws java.rmi.RemoteException;
    public com.panopto.services.Folder[] getFoldersByExternalId(com.panopto.services.AuthenticationInfo auth, java.lang.String[] folderExternalIds) throws java.rmi.RemoteException;
    public com.panopto.services.FolderWithExternalContext[] getFoldersWithExternalContextByExternalId(com.panopto.services.AuthenticationInfo auth, java.lang.String[] folderExternalIds) throws java.rmi.RemoteException;
    public com.panopto.services.Folder[] getAllFoldersByExternalId(com.panopto.services.AuthenticationInfo auth, java.lang.String[] folderExternalIds, java.lang.String[] providerNames) throws java.rmi.RemoteException;
    public com.panopto.services.FolderWithExternalContext[] getAllFoldersWithExternalContextByExternalId(com.panopto.services.AuthenticationInfo auth, java.lang.String[] folderExternalIds, java.lang.String[] providerNames) throws java.rmi.RemoteException;
    public com.panopto.services.Session[] getSessionsById(com.panopto.services.AuthenticationInfo auth, java.lang.String[] sessionIds) throws java.rmi.RemoteException;
    public com.panopto.services.Session[] getSessionsByExternalId(com.panopto.services.AuthenticationInfo auth, java.lang.String[] sessionExternalIds) throws java.rmi.RemoteException;
    public com.panopto.services.ListSessionsResponse getSessionsList(com.panopto.services.AuthenticationInfo auth, com.panopto.services.ListSessionsRequest request, java.lang.String searchQuery) throws java.rmi.RemoteException;
    public com.panopto.services.ListFoldersResponse getFoldersList(com.panopto.services.AuthenticationInfo auth, com.panopto.services.ListFoldersRequest request, java.lang.String searchQuery) throws java.rmi.RemoteException;
    public com.panopto.services.ListFoldersResponseWithExternalContext getFoldersWithExternalContextList(com.panopto.services.AuthenticationInfo auth, com.panopto.services.ListFoldersRequest request, java.lang.String searchQuery) throws java.rmi.RemoteException;
    public com.panopto.services.ListFoldersResponse getCreatorFoldersList(com.panopto.services.AuthenticationInfo auth, com.panopto.services.ListFoldersRequest request, java.lang.String searchQuery) throws java.rmi.RemoteException;
    public com.panopto.services.ListFoldersResponseWithExternalContext getCreatorFoldersWithExternalContextList(com.panopto.services.AuthenticationInfo auth, com.panopto.services.ListFoldersRequest request, java.lang.String searchQuery) throws java.rmi.RemoteException;
    public void updateSessionName(com.panopto.services.AuthenticationInfo auth, java.lang.String sessionId, java.lang.String name) throws java.rmi.RemoteException;
    public void updateSessionDescription(com.panopto.services.AuthenticationInfo auth, java.lang.String sessionId, java.lang.String description) throws java.rmi.RemoteException;
    public void updateSessionIsBroadcast(com.panopto.services.AuthenticationInfo auth, java.lang.String sessionId, java.lang.Boolean isBroadcast) throws java.rmi.RemoteException;
    public void updateSessionOwner(com.panopto.services.AuthenticationInfo auth, java.lang.String[] sessionIds, java.lang.String newOwnerUserKey) throws java.rmi.RemoteException;
    public void moveSessions(com.panopto.services.AuthenticationInfo auth, java.lang.String[] sessionIds, java.lang.String folderId) throws java.rmi.RemoteException;
    public void updateSessionExternalId(com.panopto.services.AuthenticationInfo auth, java.lang.String sessionId, java.lang.String externalId) throws java.rmi.RemoteException;
    public void updateFolderName(com.panopto.services.AuthenticationInfo auth, java.lang.String folderId, java.lang.String name) throws java.rmi.RemoteException;
    public void updateFolderDescription(com.panopto.services.AuthenticationInfo auth, java.lang.String folderId, java.lang.String description) throws java.rmi.RemoteException;
    public void updateFolderEnablePodcast(com.panopto.services.AuthenticationInfo auth, java.lang.String folderId, java.lang.Boolean enablePodcast) throws java.rmi.RemoteException;
    public void updateFolderAllowPublicNotes(com.panopto.services.AuthenticationInfo auth, java.lang.String folderId, java.lang.Boolean allowPublicNotes) throws java.rmi.RemoteException;
    public void updateFolderAllowSessionDownload(com.panopto.services.AuthenticationInfo auth, java.lang.String folderId, java.lang.Boolean allowSessionDownload) throws java.rmi.RemoteException;
    public void updateFolderParent(com.panopto.services.AuthenticationInfo auth, java.lang.String folderId, java.lang.String parentId) throws java.rmi.RemoteException;
    public void updateFolderExternalId(com.panopto.services.AuthenticationInfo auth, java.lang.String folderId, java.lang.String externalId) throws java.rmi.RemoteException;
    public void updateFolderExternalIdWithProvider(com.panopto.services.AuthenticationInfo auth, java.lang.String folderId, java.lang.String externalId, java.lang.String siteMembershipProviderName) throws java.rmi.RemoteException;
    public void deleteSessions(com.panopto.services.AuthenticationInfo auth, java.lang.String[] sessionIds) throws java.rmi.RemoteException;
    public void deleteFolders(com.panopto.services.AuthenticationInfo auth, java.lang.String[] folderIds) throws java.rmi.RemoteException;
    public com.panopto.services.Folder provisionExternalCourse(com.panopto.services.AuthenticationInfo auth, java.lang.String name, java.lang.String externalId) throws java.rmi.RemoteException;
    public java.lang.Boolean unprovisionExternalCourse(com.panopto.services.AuthenticationInfo auth, java.lang.String externalContextId) throws java.rmi.RemoteException;
    public com.panopto.services.Folder[] setExternalCourseAccess(com.panopto.services.AuthenticationInfo auth, java.lang.String name, java.lang.String externalId, java.lang.String[] folderIds) throws java.rmi.RemoteException;
    public com.panopto.services.Folder[] setCopiedExternalCourseAccess(com.panopto.services.AuthenticationInfo auth, java.lang.String name, java.lang.String externalId, java.lang.String[] folderIds) throws java.rmi.RemoteException;
    public com.panopto.services.RecorderDownloadUrlResponse getRecorderDownloadUrls() throws java.rmi.RemoteException;
    public java.lang.String createNoteByRelativeTime(com.panopto.services.AuthenticationInfo auth, java.lang.String sessionId, java.lang.String text, java.lang.String channel, java.lang.Double timestamp) throws java.rmi.RemoteException;
    public java.lang.String createNoteByAbsoluteTime(com.panopto.services.AuthenticationInfo auth, java.lang.String sessionId, java.lang.String text, java.lang.String channel, java.util.Calendar timestamp) throws java.rmi.RemoteException;
    public void editNote(com.panopto.services.AuthenticationInfo auth, java.lang.String noteId, java.lang.String sessionId, java.lang.String newText) throws java.rmi.RemoteException;
    public void deleteNote(com.panopto.services.AuthenticationInfo auth, java.lang.String noteId, java.lang.String sessionId) throws java.rmi.RemoteException;
    public com.panopto.services.Note getNote(com.panopto.services.AuthenticationInfo auth, java.lang.String noteId, java.lang.String sessionId) throws java.rmi.RemoteException;
    public com.panopto.services.ListNotesResponse listNotes(com.panopto.services.AuthenticationInfo auth, java.lang.String sessionId, com.panopto.services.Pagination pagination, java.lang.String creatorId, java.lang.String channel, java.lang.String searchQuery) throws java.rmi.RemoteException;
    public java.lang.Boolean areUsersNotesPublic(com.panopto.services.AuthenticationInfo auth, java.lang.String userId, java.lang.String sessionId) throws java.rmi.RemoteException;
    public void setNotesPublic(com.panopto.services.AuthenticationInfo auth, java.lang.String sessionId, java.lang.Boolean areNotesPublic) throws java.rmi.RemoteException;
    public java.lang.Boolean isDropbox(com.panopto.services.AuthenticationInfo auth, java.lang.String folderId) throws java.rmi.RemoteException;
    public void createCaptionByRelativeTime(com.panopto.services.AuthenticationInfo auth, java.lang.String sessionId, java.lang.String text, java.lang.Double timestamp) throws java.rmi.RemoteException;
    public void createCaptionByAbsoluteTime(com.panopto.services.AuthenticationInfo auth, java.lang.String sessionId, java.lang.String text, java.util.Calendar timestamp) throws java.rmi.RemoteException;
    public void uploadTranscript(com.panopto.services.AuthenticationInfo auth, java.lang.String sessionId, java.lang.String file) throws java.rmi.RemoteException;
    public com.panopto.services.FoldersWithAvailabilitySettings getFoldersAvailabilitySettings(com.panopto.services.AuthenticationInfo auth, java.lang.String[] folderIds) throws java.rmi.RemoteException;
    public com.panopto.services.SessionsWithAvailabilitySettings getSessionsAvailabilitySettings(com.panopto.services.AuthenticationInfo auth, java.lang.String[] sessionIds) throws java.rmi.RemoteException;
    public java.lang.Boolean updateFoldersAvailabilityStartSettings(com.panopto.services.AuthenticationInfo auth, java.lang.String[] folderIds, com.panopto.services.FolderStartSettingType settingType, java.util.Calendar startDate, java.lang.Boolean overrideSessionsSettings) throws java.rmi.RemoteException;
    public java.lang.Boolean updateFoldersAvailabilityEndSettings(com.panopto.services.AuthenticationInfo auth, java.lang.String[] folderIds, com.panopto.services.FolderEndSettingType settingType, java.util.Calendar endDate, java.lang.Boolean overrideSessionsSettings) throws java.rmi.RemoteException;
    public java.lang.Boolean updateSessionsAvailabilityStartSettings(com.panopto.services.AuthenticationInfo auth, java.lang.String[] sessionIds, com.panopto.services.SessionStartSettingType settingType, java.util.Calendar startDate) throws java.rmi.RemoteException;
    public java.lang.Boolean updateSessionsAvailabilityEndSettings(com.panopto.services.AuthenticationInfo auth, java.lang.String[] sessionIds, com.panopto.services.SessionEndSettingType settingType, java.util.Calendar endDate) throws java.rmi.RemoteException;
}
