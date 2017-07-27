/**
 * IAuth.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public interface IAuth extends java.rmi.Remote {
    public java.lang.Boolean logOnWithPassword(java.lang.String userKey, java.lang.String password) throws java.rmi.RemoteException;
    public java.lang.Boolean logOnWithExternalProvider(java.lang.String userKey, java.lang.String authCode) throws java.rmi.RemoteException;
    public java.lang.String getServerVersion() throws java.rmi.RemoteException;
    public java.lang.String getAuthenticatedUrl(com.panopto.services.AuthenticationInfo auth, java.lang.String targetUrl) throws java.rmi.RemoteException;
    public void reportIntegrationInfo(com.panopto.services.AuthenticationInfo auth, java.lang.String idProviderName, java.lang.String moduleVersion, java.lang.String targetPlatformVersion) throws java.rmi.RemoteException;
}
