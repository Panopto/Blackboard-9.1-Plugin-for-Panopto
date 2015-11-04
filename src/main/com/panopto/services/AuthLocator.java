/**
 * AuthLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public class AuthLocator extends org.apache.axis.client.Service implements com.panopto.services.Auth {

    public AuthLocator() {
    }


    public AuthLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AuthLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_IAuth
    private java.lang.String BasicHttpBinding_IAuth_address = "http://pantest01.panopto.local/Panopto/PublicAPI/4.6/Auth.svc";

    public java.lang.String getBasicHttpBinding_IAuthAddress() {
        return BasicHttpBinding_IAuth_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpBinding_IAuthWSDDServiceName = "BasicHttpBinding_IAuth";

    public java.lang.String getBasicHttpBinding_IAuthWSDDServiceName() {
        return BasicHttpBinding_IAuthWSDDServiceName;
    }

    public void setBasicHttpBinding_IAuthWSDDServiceName(java.lang.String name) {
        BasicHttpBinding_IAuthWSDDServiceName = name;
    }

    public com.panopto.services.IAuth getBasicHttpBinding_IAuth() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_IAuth_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_IAuth(endpoint);
    }

    public com.panopto.services.IAuth getBasicHttpBinding_IAuth(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.panopto.services.BasicHttpBinding_IAuthStub _stub = new com.panopto.services.BasicHttpBinding_IAuthStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_IAuthWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_IAuthEndpointAddress(java.lang.String address) {
        BasicHttpBinding_IAuth_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.panopto.services.IAuth.class.isAssignableFrom(serviceEndpointInterface)) {
                com.panopto.services.BasicHttpBinding_IAuthStub _stub = new com.panopto.services.BasicHttpBinding_IAuthStub(new java.net.URL(BasicHttpBinding_IAuth_address), this);
                _stub.setPortName(getBasicHttpBinding_IAuthWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("BasicHttpBinding_IAuth".equals(inputPortName)) {
            return getBasicHttpBinding_IAuth();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "Auth");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_IAuth"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_IAuth".equals(portName)) {
            setBasicHttpBinding_IAuthEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
