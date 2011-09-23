/**
 * AccessManagementLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.panopto.services;

public class AccessManagementLocator extends org.apache.axis.client.Service implements com.panopto.services.AccessManagement {

    public AccessManagementLocator() {
    }


    public AccessManagementLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AccessManagementLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_IAccessManagement
    private java.lang.String BasicHttpBinding_IAccessManagement_address = "http://10.1.11.27/Panopto/PublicAPI/4.0/AccessManagement.svc";

    public java.lang.String getBasicHttpBinding_IAccessManagementAddress() {
        return BasicHttpBinding_IAccessManagement_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpBinding_IAccessManagementWSDDServiceName = "BasicHttpBinding_IAccessManagement";

    public java.lang.String getBasicHttpBinding_IAccessManagementWSDDServiceName() {
        return BasicHttpBinding_IAccessManagementWSDDServiceName;
    }

    public void setBasicHttpBinding_IAccessManagementWSDDServiceName(java.lang.String name) {
        BasicHttpBinding_IAccessManagementWSDDServiceName = name;
    }

    public com.panopto.services.IAccessManagement getBasicHttpBinding_IAccessManagement() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_IAccessManagement_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_IAccessManagement(endpoint);
    }

    public com.panopto.services.IAccessManagement getBasicHttpBinding_IAccessManagement(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.panopto.services.BasicHttpBinding_IAccessManagementStub _stub = new com.panopto.services.BasicHttpBinding_IAccessManagementStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_IAccessManagementWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_IAccessManagementEndpointAddress(java.lang.String address) {
        BasicHttpBinding_IAccessManagement_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.panopto.services.IAccessManagement.class.isAssignableFrom(serviceEndpointInterface)) {
                com.panopto.services.BasicHttpBinding_IAccessManagementStub _stub = new com.panopto.services.BasicHttpBinding_IAccessManagementStub(new java.net.URL(BasicHttpBinding_IAccessManagement_address), this);
                _stub.setPortName(getBasicHttpBinding_IAccessManagementWSDDServiceName());
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
        if ("BasicHttpBinding_IAccessManagement".equals(inputPortName)) {
            return getBasicHttpBinding_IAccessManagement();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "AccessManagement");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_IAccessManagement"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_IAccessManagement".equals(portName)) {
            setBasicHttpBinding_IAccessManagementEndpointAddress(address);
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
