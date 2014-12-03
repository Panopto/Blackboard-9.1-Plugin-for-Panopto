/**
 * IUserManagement.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public interface IUserManagement extends java.rmi.Remote {
    public java.lang.String createUser(com.panopto.services.AuthenticationInfo auth, com.panopto.services.User user, java.lang.String initialPassword) throws java.rmi.RemoteException;
    public com.panopto.services.User[] createUsers(com.panopto.services.AuthenticationInfo auth, com.panopto.services.User[] users) throws java.rmi.RemoteException;
    public com.panopto.services.User getUserByKey(com.panopto.services.AuthenticationInfo auth, java.lang.String userKey) throws java.rmi.RemoteException;
    public com.panopto.services.User[] getUsers(com.panopto.services.AuthenticationInfo auth, java.lang.String[] userIds) throws java.rmi.RemoteException;
    public com.panopto.services.ListUsersResponse listUsers(com.panopto.services.AuthenticationInfo auth, com.panopto.services.ListUsersRequest parameters, java.lang.String searchQuery) throws java.rmi.RemoteException;
    public void updateContactInfo(com.panopto.services.AuthenticationInfo auth, java.lang.String userId, java.lang.String firstName, java.lang.String lastName, java.lang.String email, java.lang.Boolean sendNotifications) throws java.rmi.RemoteException;
    public void updateUserBio(com.panopto.services.AuthenticationInfo auth, java.lang.String userId, java.lang.String bio) throws java.rmi.RemoteException;
    public void updatePassword(com.panopto.services.AuthenticationInfo auth, java.lang.String userId, java.lang.String newPassword) throws java.rmi.RemoteException;
    public void resetPassword(com.panopto.services.AuthenticationInfo auth, java.lang.String userId) throws java.rmi.RemoteException;
    public void unlockAccount(com.panopto.services.AuthenticationInfo auth, java.lang.String userId) throws java.rmi.RemoteException;
    public void setSystemRole(com.panopto.services.AuthenticationInfo auth, java.lang.String userId, com.panopto.services.SystemRole role) throws java.rmi.RemoteException;
    public void deleteUsers(com.panopto.services.AuthenticationInfo auth, java.lang.String[] userIds) throws java.rmi.RemoteException;
    public com.panopto.services.Group createInternalGroup(com.panopto.services.AuthenticationInfo auth, java.lang.String groupName, java.lang.String[] memberIds) throws java.rmi.RemoteException;
    public java.lang.Boolean getGroupIsPublic(com.panopto.services.AuthenticationInfo auth, java.lang.String groupId) throws java.rmi.RemoteException;
    public void setGroupIsPublic(com.panopto.services.AuthenticationInfo auth, java.lang.String groupId, java.lang.Boolean isPublic) throws java.rmi.RemoteException;
    public com.panopto.services.Group createExternalGroup(com.panopto.services.AuthenticationInfo auth, java.lang.String groupName, java.lang.String externalProvider, java.lang.String externalId, java.lang.String[] memberIds) throws java.rmi.RemoteException;
    public void addMembersToInternalGroup(com.panopto.services.AuthenticationInfo auth, java.lang.String groupId, java.lang.String[] memberIds) throws java.rmi.RemoteException;
    public void removeMembersFromInternalGroup(com.panopto.services.AuthenticationInfo auth, java.lang.String groupId, java.lang.String[] memberIds) throws java.rmi.RemoteException;
    public void syncExternalUser(com.panopto.services.AuthenticationInfo auth, java.lang.String firstName, java.lang.String lastName, java.lang.String email, java.lang.Boolean emailSessionNotifications, java.lang.String[] externalGroupIds) throws java.rmi.RemoteException;
    public void deleteGroup(com.panopto.services.AuthenticationInfo auth, java.lang.String groupId) throws java.rmi.RemoteException;
    public com.panopto.services.Group getGroup(com.panopto.services.AuthenticationInfo auth, java.lang.String groupId) throws java.rmi.RemoteException;
    public com.panopto.services.ListGroupsResponse listGroups(com.panopto.services.AuthenticationInfo auth, com.panopto.services.Pagination pagination) throws java.rmi.RemoteException;
    public com.panopto.services.Group[] getGroupsByName(com.panopto.services.AuthenticationInfo auth, java.lang.String groupName) throws java.rmi.RemoteException;
}
