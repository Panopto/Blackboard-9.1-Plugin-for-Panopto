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
    public com.panopto.services.Session[] getSessionsById(com.panopto.services.AuthenticationInfo auth, java.lang.String[] sessionIds) throws java.rmi.RemoteException;
    public com.panopto.services.ListSessionsResponse getSessionsList(com.panopto.services.AuthenticationInfo auth, com.panopto.services.ListSessionsRequest request, java.lang.String searchQuery) throws java.rmi.RemoteException;
    public com.panopto.services.ListFoldersResponse getFoldersList(com.panopto.services.AuthenticationInfo auth, com.panopto.services.ListFoldersRequest request, java.lang.String searchQuery) throws java.rmi.RemoteException;
    public void updateSessionName(com.panopto.services.AuthenticationInfo auth, java.lang.String sessionId, java.lang.String name) throws java.rmi.RemoteException;
    public void updateSessionDescription(com.panopto.services.AuthenticationInfo auth, java.lang.String sessionId, java.lang.String description) throws java.rmi.RemoteException;
    public void updateSessionIsBroadcast(com.panopto.services.AuthenticationInfo auth, java.lang.String sessionId, java.lang.Boolean isBroadcast) throws java.rmi.RemoteException;
    public void moveSessions(com.panopto.services.AuthenticationInfo auth, java.lang.String[] sessionIds, java.lang.String folderId) throws java.rmi.RemoteException;
    public void updateFolderName(com.panopto.services.AuthenticationInfo auth, java.lang.String folderId, java.lang.String name) throws java.rmi.RemoteException;
    public void updateFolderDescription(com.panopto.services.AuthenticationInfo auth, java.lang.String folderId, java.lang.String description) throws java.rmi.RemoteException;
    public void updateFolderEnablePodcast(com.panopto.services.AuthenticationInfo auth, java.lang.String folderId, java.lang.Boolean enablePodcast) throws java.rmi.RemoteException;
    public void updateFolderAllowPublicNotes(com.panopto.services.AuthenticationInfo auth, java.lang.String folderId, java.lang.Boolean allowPublicNotes) throws java.rmi.RemoteException;
    public void updateFolderAllowSessionDownload(com.panopto.services.AuthenticationInfo auth, java.lang.String folderId, java.lang.Boolean allowSessionDownload) throws java.rmi.RemoteException;
    public void updateFolderParent(com.panopto.services.AuthenticationInfo auth, java.lang.String folderId, java.lang.String parentId) throws java.rmi.RemoteException;
    public void deleteSessions(com.panopto.services.AuthenticationInfo auth, java.lang.String[] sessionIds) throws java.rmi.RemoteException;
    public void deleteFolders(com.panopto.services.AuthenticationInfo auth, java.lang.String[] folderIds) throws java.rmi.RemoteException;
    public com.panopto.services.Folder provisionExternalCourse(com.panopto.services.AuthenticationInfo auth, java.lang.String name, java.lang.String externalId) throws java.rmi.RemoteException;
    public com.panopto.services.Folder[] setExternalCourseAccess(com.panopto.services.AuthenticationInfo auth, java.lang.String name, java.lang.String externalId, java.lang.String[] folderIds) throws java.rmi.RemoteException;
    public com.panopto.services.RecorderDownloadUrlResponse getRecorderDownloadUrls() throws java.rmi.RemoteException;
}
