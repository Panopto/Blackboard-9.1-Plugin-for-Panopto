
/**
 * RemoteRecorderManagementCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.panopto.remoterecorder;

    /**
     *  RemoteRecorderManagementCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class RemoteRecorderManagementCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public RemoteRecorderManagementCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public RemoteRecorderManagementCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for scheduleRecording method
            * override this method for handling normal response from scheduleRecording operation
            */
           public void receiveResultscheduleRecording(
                    com.panopto.remoterecorder.RemoteRecorderManagementStub.ScheduleRecordingResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from scheduleRecording operation
           */
            public void receiveErrorscheduleRecording(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for scheduleRecurringRecording method
            * override this method for handling normal response from scheduleRecurringRecording operation
            */
           public void receiveResultscheduleRecurringRecording(
                    com.panopto.remoterecorder.RemoteRecorderManagementStub.ScheduleRecurringRecordingResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from scheduleRecurringRecording operation
           */
            public void receiveErrorscheduleRecurringRecording(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getRemoteRecordersByExternalId method
            * override this method for handling normal response from getRemoteRecordersByExternalId operation
            */
           public void receiveResultgetRemoteRecordersByExternalId(
                    com.panopto.remoterecorder.RemoteRecorderManagementStub.GetRemoteRecordersByExternalIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getRemoteRecordersByExternalId operation
           */
            public void receiveErrorgetRemoteRecordersByExternalId(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateRemoteRecorderExternalId method
            * override this method for handling normal response from updateRemoteRecorderExternalId operation
            */
           public void receiveResultupdateRemoteRecorderExternalId(
                    com.panopto.remoterecorder.RemoteRecorderManagementStub.UpdateRemoteRecorderExternalIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateRemoteRecorderExternalId operation
           */
            public void receiveErrorupdateRemoteRecorderExternalId(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for listRecorders method
            * override this method for handling normal response from listRecorders operation
            */
           public void receiveResultlistRecorders(
                    com.panopto.remoterecorder.RemoteRecorderManagementStub.ListRecordersResponse0 result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from listRecorders operation
           */
            public void receiveErrorlistRecorders(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateRecordingTime method
            * override this method for handling normal response from updateRecordingTime operation
            */
           public void receiveResultupdateRecordingTime(
                    com.panopto.remoterecorder.RemoteRecorderManagementStub.UpdateRecordingTimeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateRecordingTime operation
           */
            public void receiveErrorupdateRecordingTime(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getRemoteRecordersById method
            * override this method for handling normal response from getRemoteRecordersById operation
            */
           public void receiveResultgetRemoteRecordersById(
                    com.panopto.remoterecorder.RemoteRecorderManagementStub.GetRemoteRecordersByIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getRemoteRecordersById operation
           */
            public void receiveErrorgetRemoteRecordersById(java.lang.Exception e) {
            }
                


    }
    