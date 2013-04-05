
/**
 * SessionManagementCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.panopto.session;

    /**
     *  SessionManagementCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class SessionManagementCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public SessionManagementCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public SessionManagementCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for updateFolderName method
            * override this method for handling normal response from updateFolderName operation
            */
           public void receiveResultupdateFolderName(
                    com.panopto.session.SessionManagementStub.UpdateFolderNameResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateFolderName operation
           */
            public void receiveErrorupdateFolderName(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateFolderExternalId method
            * override this method for handling normal response from updateFolderExternalId operation
            */
           public void receiveResultupdateFolderExternalId(
                    com.panopto.session.SessionManagementStub.UpdateFolderExternalIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateFolderExternalId operation
           */
            public void receiveErrorupdateFolderExternalId(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateSessionDescription method
            * override this method for handling normal response from updateSessionDescription operation
            */
           public void receiveResultupdateSessionDescription(
                    com.panopto.session.SessionManagementStub.UpdateSessionDescriptionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateSessionDescription operation
           */
            public void receiveErrorupdateSessionDescription(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for setNotesPublic method
            * override this method for handling normal response from setNotesPublic operation
            */
           public void receiveResultsetNotesPublic(
                    com.panopto.session.SessionManagementStub.SetNotesPublicResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from setNotesPublic operation
           */
            public void receiveErrorsetNotesPublic(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createCaptionByAbsoluteTime method
            * override this method for handling normal response from createCaptionByAbsoluteTime operation
            */
           public void receiveResultcreateCaptionByAbsoluteTime(
                    com.panopto.session.SessionManagementStub.CreateCaptionByAbsoluteTimeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createCaptionByAbsoluteTime operation
           */
            public void receiveErrorcreateCaptionByAbsoluteTime(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for areUsersNotesPublic method
            * override this method for handling normal response from areUsersNotesPublic operation
            */
           public void receiveResultareUsersNotesPublic(
                    com.panopto.session.SessionManagementStub.AreUsersNotesPublicResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from areUsersNotesPublic operation
           */
            public void receiveErrorareUsersNotesPublic(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteSessions method
            * override this method for handling normal response from deleteSessions operation
            */
           public void receiveResultdeleteSessions(
                    com.panopto.session.SessionManagementStub.DeleteSessionsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteSessions operation
           */
            public void receiveErrordeleteSessions(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateFolderAllowSessionDownload method
            * override this method for handling normal response from updateFolderAllowSessionDownload operation
            */
           public void receiveResultupdateFolderAllowSessionDownload(
                    com.panopto.session.SessionManagementStub.UpdateFolderAllowSessionDownloadResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateFolderAllowSessionDownload operation
           */
            public void receiveErrorupdateFolderAllowSessionDownload(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateFolderAllowPublicNotes method
            * override this method for handling normal response from updateFolderAllowPublicNotes operation
            */
           public void receiveResultupdateFolderAllowPublicNotes(
                    com.panopto.session.SessionManagementStub.UpdateFolderAllowPublicNotesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateFolderAllowPublicNotes operation
           */
            public void receiveErrorupdateFolderAllowPublicNotes(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for provisionExternalCourse method
            * override this method for handling normal response from provisionExternalCourse operation
            */
           public void receiveResultprovisionExternalCourse(
                    com.panopto.session.SessionManagementStub.ProvisionExternalCourseResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from provisionExternalCourse operation
           */
            public void receiveErrorprovisionExternalCourse(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateFolderParent method
            * override this method for handling normal response from updateFolderParent operation
            */
           public void receiveResultupdateFolderParent(
                    com.panopto.session.SessionManagementStub.UpdateFolderParentResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateFolderParent operation
           */
            public void receiveErrorupdateFolderParent(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addFolder method
            * override this method for handling normal response from addFolder operation
            */
           public void receiveResultaddFolder(
                    com.panopto.session.SessionManagementStub.AddFolderResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addFolder operation
           */
            public void receiveErroraddFolder(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateFolderEnablePodcast method
            * override this method for handling normal response from updateFolderEnablePodcast operation
            */
           public void receiveResultupdateFolderEnablePodcast(
                    com.panopto.session.SessionManagementStub.UpdateFolderEnablePodcastResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateFolderEnablePodcast operation
           */
            public void receiveErrorupdateFolderEnablePodcast(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateSessionIsBroadcast method
            * override this method for handling normal response from updateSessionIsBroadcast operation
            */
           public void receiveResultupdateSessionIsBroadcast(
                    com.panopto.session.SessionManagementStub.UpdateSessionIsBroadcastResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateSessionIsBroadcast operation
           */
            public void receiveErrorupdateSessionIsBroadcast(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getRecorderDownloadUrls method
            * override this method for handling normal response from getRecorderDownloadUrls operation
            */
           public void receiveResultgetRecorderDownloadUrls(
                    com.panopto.session.SessionManagementStub.GetRecorderDownloadUrlsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getRecorderDownloadUrls operation
           */
            public void receiveErrorgetRecorderDownloadUrls(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteNote method
            * override this method for handling normal response from deleteNote operation
            */
           public void receiveResultdeleteNote(
                    com.panopto.session.SessionManagementStub.DeleteNoteResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteNote operation
           */
            public void receiveErrordeleteNote(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createNoteByAbsoluteTime method
            * override this method for handling normal response from createNoteByAbsoluteTime operation
            */
           public void receiveResultcreateNoteByAbsoluteTime(
                    com.panopto.session.SessionManagementStub.CreateNoteByAbsoluteTimeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createNoteByAbsoluteTime operation
           */
            public void receiveErrorcreateNoteByAbsoluteTime(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for setExternalCourseAccess method
            * override this method for handling normal response from setExternalCourseAccess operation
            */
           public void receiveResultsetExternalCourseAccess(
                    com.panopto.session.SessionManagementStub.SetExternalCourseAccessResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from setExternalCourseAccess operation
           */
            public void receiveErrorsetExternalCourseAccess(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getNote method
            * override this method for handling normal response from getNote operation
            */
           public void receiveResultgetNote(
                    com.panopto.session.SessionManagementStub.GetNoteResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getNote operation
           */
            public void receiveErrorgetNote(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for editNote method
            * override this method for handling normal response from editNote operation
            */
           public void receiveResulteditNote(
                    com.panopto.session.SessionManagementStub.EditNoteResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from editNote operation
           */
            public void receiveErroreditNote(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addSession method
            * override this method for handling normal response from addSession operation
            */
           public void receiveResultaddSession(
                    com.panopto.session.SessionManagementStub.AddSessionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addSession operation
           */
            public void receiveErroraddSession(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getFoldersList method
            * override this method for handling normal response from getFoldersList operation
            */
           public void receiveResultgetFoldersList(
                    com.panopto.session.SessionManagementStub.GetFoldersListResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getFoldersList operation
           */
            public void receiveErrorgetFoldersList(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateSessionExternalId method
            * override this method for handling normal response from updateSessionExternalId operation
            */
           public void receiveResultupdateSessionExternalId(
                    com.panopto.session.SessionManagementStub.UpdateSessionExternalIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateSessionExternalId operation
           */
            public void receiveErrorupdateSessionExternalId(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getSessionsByExternalId method
            * override this method for handling normal response from getSessionsByExternalId operation
            */
           public void receiveResultgetSessionsByExternalId(
                    com.panopto.session.SessionManagementStub.GetSessionsByExternalIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getSessionsByExternalId operation
           */
            public void receiveErrorgetSessionsByExternalId(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getFoldersById method
            * override this method for handling normal response from getFoldersById operation
            */
           public void receiveResultgetFoldersById(
                    com.panopto.session.SessionManagementStub.GetFoldersByIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getFoldersById operation
           */
            public void receiveErrorgetFoldersById(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getSessionsById method
            * override this method for handling normal response from getSessionsById operation
            */
           public void receiveResultgetSessionsById(
                    com.panopto.session.SessionManagementStub.GetSessionsByIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getSessionsById operation
           */
            public void receiveErrorgetSessionsById(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for uploadTranscript method
            * override this method for handling normal response from uploadTranscript operation
            */
           public void receiveResultuploadTranscript(
                    com.panopto.session.SessionManagementStub.UploadTranscriptResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from uploadTranscript operation
           */
            public void receiveErroruploadTranscript(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createCaptionByRelativeTime method
            * override this method for handling normal response from createCaptionByRelativeTime operation
            */
           public void receiveResultcreateCaptionByRelativeTime(
                    com.panopto.session.SessionManagementStub.CreateCaptionByRelativeTimeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createCaptionByRelativeTime operation
           */
            public void receiveErrorcreateCaptionByRelativeTime(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createNoteByRelativeTime method
            * override this method for handling normal response from createNoteByRelativeTime operation
            */
           public void receiveResultcreateNoteByRelativeTime(
                    com.panopto.session.SessionManagementStub.CreateNoteByRelativeTimeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createNoteByRelativeTime operation
           */
            public void receiveErrorcreateNoteByRelativeTime(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getFoldersByExternalId method
            * override this method for handling normal response from getFoldersByExternalId operation
            */
           public void receiveResultgetFoldersByExternalId(
                    com.panopto.session.SessionManagementStub.GetFoldersByExternalIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getFoldersByExternalId operation
           */
            public void receiveErrorgetFoldersByExternalId(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateSessionName method
            * override this method for handling normal response from updateSessionName operation
            */
           public void receiveResultupdateSessionName(
                    com.panopto.session.SessionManagementStub.UpdateSessionNameResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateSessionName operation
           */
            public void receiveErrorupdateSessionName(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for listNotes method
            * override this method for handling normal response from listNotes operation
            */
           public void receiveResultlistNotes(
                    com.panopto.session.SessionManagementStub.ListNotesResponse0 result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from listNotes operation
           */
            public void receiveErrorlistNotes(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for moveSessions method
            * override this method for handling normal response from moveSessions operation
            */
           public void receiveResultmoveSessions(
                    com.panopto.session.SessionManagementStub.MoveSessionsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from moveSessions operation
           */
            public void receiveErrormoveSessions(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for isDropbox method
            * override this method for handling normal response from isDropbox operation
            */
           public void receiveResultisDropbox(
                    com.panopto.session.SessionManagementStub.IsDropboxResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from isDropbox operation
           */
            public void receiveErrorisDropbox(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getSessionsList method
            * override this method for handling normal response from getSessionsList operation
            */
           public void receiveResultgetSessionsList(
                    com.panopto.session.SessionManagementStub.GetSessionsListResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getSessionsList operation
           */
            public void receiveErrorgetSessionsList(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteFolders method
            * override this method for handling normal response from deleteFolders operation
            */
           public void receiveResultdeleteFolders(
                    com.panopto.session.SessionManagementStub.DeleteFoldersResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteFolders operation
           */
            public void receiveErrordeleteFolders(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateFolderDescription method
            * override this method for handling normal response from updateFolderDescription operation
            */
           public void receiveResultupdateFolderDescription(
                    com.panopto.session.SessionManagementStub.UpdateFolderDescriptionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateFolderDescription operation
           */
            public void receiveErrorupdateFolderDescription(java.lang.Exception e) {
            }
                


    }
    