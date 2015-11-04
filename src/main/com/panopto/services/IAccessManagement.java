/**
 * IAccessManagement.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public interface IAccessManagement extends java.rmi.Remote {
    public com.panopto.services.UserAccessDetails getUserAccessDetails(com.panopto.services.AuthenticationInfo auth, java.lang.String userId) throws java.rmi.RemoteException;
    public com.panopto.services.UserAccessDetails getSelfUserAccessDetails(com.panopto.services.AuthenticationInfo auth) throws java.rmi.RemoteException;
    public com.panopto.services.SessionAccessDetails getSessionAccessDetails(com.panopto.services.AuthenticationInfo auth, java.lang.String sessionId) throws java.rmi.RemoteException;
    public com.panopto.services.FolderAccessDetails getFolderAccessDetails(com.panopto.services.AuthenticationInfo auth, java.lang.String folderId) throws java.rmi.RemoteException;
    public com.panopto.services.GroupAccessDetails getGroupAccessDetails(com.panopto.services.AuthenticationInfo auth, java.lang.String groupId) throws java.rmi.RemoteException;
    public void grantUsersAccessToFolder(com.panopto.services.AuthenticationInfo auth, java.lang.String folderId, java.lang.String[] userIds, com.panopto.services.AccessRole role) throws java.rmi.RemoteException;
    public void grantUsersViewerAccessToSession(com.panopto.services.AuthenticationInfo auth, java.lang.String sessionId, java.lang.String[] userIds) throws java.rmi.RemoteException;
    public void grantGroupAccessToFolder(com.panopto.services.AuthenticationInfo auth, java.lang.String folderId, java.lang.String groupId, com.panopto.services.AccessRole role) throws java.rmi.RemoteException;
    public void grantGroupViewerAccessToSession(com.panopto.services.AuthenticationInfo auth, java.lang.String sessionId, java.lang.String groupId) throws java.rmi.RemoteException;
    public void updateFolderIsPublic(com.panopto.services.AuthenticationInfo auth, java.lang.String folderId, java.lang.Boolean isPublic) throws java.rmi.RemoteException;
    public void updateSessionIsPublic(com.panopto.services.AuthenticationInfo auth, java.lang.String sessionId, java.lang.Boolean isPublic) throws java.rmi.RemoteException;
    public void updateSessionInheritViewerAccess(com.panopto.services.AuthenticationInfo auth, java.lang.String sessionId, java.lang.Boolean inheritViewerAccess) throws java.rmi.RemoteException;
    public void revokeUsersAccessFromFolder(com.panopto.services.AuthenticationInfo auth, java.lang.String folderId, java.lang.String[] userIds, com.panopto.services.AccessRole role) throws java.rmi.RemoteException;
    public void revokeUsersViewerAccessFromSession(com.panopto.services.AuthenticationInfo auth, java.lang.String sessionId, java.lang.String[] userIds) throws java.rmi.RemoteException;
    public void revokeGroupAccessFromFolder(com.panopto.services.AuthenticationInfo auth, java.lang.String folderId, java.lang.String groupId, com.panopto.services.AccessRole role) throws java.rmi.RemoteException;
    public void revokeGroupViewerAccessFromSession(com.panopto.services.AuthenticationInfo auth, java.lang.String sessionId, java.lang.String groupId) throws java.rmi.RemoteException;
}
