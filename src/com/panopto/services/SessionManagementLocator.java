/**
 * SessionManagementLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public class SessionManagementLocator extends org.apache.axis.client.Service implements com.panopto.services.SessionManagement {

    public SessionManagementLocator() {
    }


    public SessionManagementLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SessionManagementLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_ISessionManagement
    private java.lang.String BasicHttpBinding_ISessionManagement_address = "http://10.1.11.27/Panopto/PublicAPI/4.0/SessionManagement.svc";

    public java.lang.String getBasicHttpBinding_ISessionManagementAddress() {
        return BasicHttpBinding_ISessionManagement_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpBinding_ISessionManagementWSDDServiceName = "BasicHttpBinding_ISessionManagement";

    public java.lang.String getBasicHttpBinding_ISessionManagementWSDDServiceName() {
        return BasicHttpBinding_ISessionManagementWSDDServiceName;
    }

    public void setBasicHttpBinding_ISessionManagementWSDDServiceName(java.lang.String name) {
        BasicHttpBinding_ISessionManagementWSDDServiceName = name;
    }

    public com.panopto.services.ISessionManagement getBasicHttpBinding_ISessionManagement() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_ISessionManagement_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_ISessionManagement(endpoint);
    }

    public com.panopto.services.ISessionManagement getBasicHttpBinding_ISessionManagement(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.panopto.services.BasicHttpBinding_ISessionManagementStub _stub = new com.panopto.services.BasicHttpBinding_ISessionManagementStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_ISessionManagementWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_ISessionManagementEndpointAddress(java.lang.String address) {
        BasicHttpBinding_ISessionManagement_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.panopto.services.ISessionManagement.class.isAssignableFrom(serviceEndpointInterface)) {
                com.panopto.services.BasicHttpBinding_ISessionManagementStub _stub = new com.panopto.services.BasicHttpBinding_ISessionManagementStub(new java.net.URL(BasicHttpBinding_ISessionManagement_address), this);
                _stub.setPortName(getBasicHttpBinding_ISessionManagementWSDDServiceName());
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
        if ("BasicHttpBinding_ISessionManagement".equals(inputPortName)) {
            return getBasicHttpBinding_ISessionManagement();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "SessionManagement");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_ISessionManagement"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_ISessionManagement".equals(portName)) {
            setBasicHttpBinding_ISessionManagementEndpointAddress(address);
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
