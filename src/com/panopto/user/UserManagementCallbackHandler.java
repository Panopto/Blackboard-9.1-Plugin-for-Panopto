
/**
 * UserManagementCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.panopto.user;

    /**
     *  UserManagementCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class UserManagementCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public UserManagementCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public UserManagementCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for createExternalGroup method
            * override this method for handling normal response from createExternalGroup operation
            */
           public void receiveResultcreateExternalGroup(
                    com.panopto.user.UserManagementStub.CreateExternalGroupResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createExternalGroup operation
           */
            public void receiveErrorcreateExternalGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updatePassword method
            * override this method for handling normal response from updatePassword operation
            */
           public void receiveResultupdatePassword(
                    com.panopto.user.UserManagementStub.UpdatePasswordResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updatePassword operation
           */
            public void receiveErrorupdatePassword(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteUsers method
            * override this method for handling normal response from deleteUsers operation
            */
           public void receiveResultdeleteUsers(
                    com.panopto.user.UserManagementStub.DeleteUsersResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteUsers operation
           */
            public void receiveErrordeleteUsers(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createInternalGroup method
            * override this method for handling normal response from createInternalGroup operation
            */
           public void receiveResultcreateInternalGroup(
                    com.panopto.user.UserManagementStub.CreateInternalGroupResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createInternalGroup operation
           */
            public void receiveErrorcreateInternalGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getGroupIsPublic method
            * override this method for handling normal response from getGroupIsPublic operation
            */
           public void receiveResultgetGroupIsPublic(
                    com.panopto.user.UserManagementStub.GetGroupIsPublicResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getGroupIsPublic operation
           */
            public void receiveErrorgetGroupIsPublic(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for syncExternalUser method
            * override this method for handling normal response from syncExternalUser operation
            */
           public void receiveResultsyncExternalUser(
                    com.panopto.user.UserManagementStub.SyncExternalUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from syncExternalUser operation
           */
            public void receiveErrorsyncExternalUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for setSystemRole method
            * override this method for handling normal response from setSystemRole operation
            */
           public void receiveResultsetSystemRole(
                    com.panopto.user.UserManagementStub.SetSystemRoleResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from setSystemRole operation
           */
            public void receiveErrorsetSystemRole(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for unlockAccount method
            * override this method for handling normal response from unlockAccount operation
            */
           public void receiveResultunlockAccount(
                    com.panopto.user.UserManagementStub.UnlockAccountResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from unlockAccount operation
           */
            public void receiveErrorunlockAccount(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getUserByKey method
            * override this method for handling normal response from getUserByKey operation
            */
           public void receiveResultgetUserByKey(
                    com.panopto.user.UserManagementStub.GetUserByKeyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getUserByKey operation
           */
            public void receiveErrorgetUserByKey(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getGroup method
            * override this method for handling normal response from getGroup operation
            */
           public void receiveResultgetGroup(
                    com.panopto.user.UserManagementStub.GetGroupResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getGroup operation
           */
            public void receiveErrorgetGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addMembersToInternalGroup method
            * override this method for handling normal response from addMembersToInternalGroup operation
            */
           public void receiveResultaddMembersToInternalGroup(
                    com.panopto.user.UserManagementStub.AddMembersToInternalGroupResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addMembersToInternalGroup operation
           */
            public void receiveErroraddMembersToInternalGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createUser method
            * override this method for handling normal response from createUser operation
            */
           public void receiveResultcreateUser(
                    com.panopto.user.UserManagementStub.CreateUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createUser operation
           */
            public void receiveErrorcreateUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateUserBio method
            * override this method for handling normal response from updateUserBio operation
            */
           public void receiveResultupdateUserBio(
                    com.panopto.user.UserManagementStub.UpdateUserBioResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateUserBio operation
           */
            public void receiveErrorupdateUserBio(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteGroup method
            * override this method for handling normal response from deleteGroup operation
            */
           public void receiveResultdeleteGroup(
                    com.panopto.user.UserManagementStub.DeleteGroupResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteGroup operation
           */
            public void receiveErrordeleteGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for listUsers method
            * override this method for handling normal response from listUsers operation
            */
           public void receiveResultlistUsers(
                    com.panopto.user.UserManagementStub.ListUsersResponse1 result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from listUsers operation
           */
            public void receiveErrorlistUsers(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for removeMembersFromInternalGroup method
            * override this method for handling normal response from removeMembersFromInternalGroup operation
            */
           public void receiveResultremoveMembersFromInternalGroup(
                    com.panopto.user.UserManagementStub.RemoveMembersFromInternalGroupResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from removeMembersFromInternalGroup operation
           */
            public void receiveErrorremoveMembersFromInternalGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for setGroupIsPublic method
            * override this method for handling normal response from setGroupIsPublic operation
            */
           public void receiveResultsetGroupIsPublic(
                    com.panopto.user.UserManagementStub.SetGroupIsPublicResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from setGroupIsPublic operation
           */
            public void receiveErrorsetGroupIsPublic(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateContactInfo method
            * override this method for handling normal response from updateContactInfo operation
            */
           public void receiveResultupdateContactInfo(
                    com.panopto.user.UserManagementStub.UpdateContactInfoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateContactInfo operation
           */
            public void receiveErrorupdateContactInfo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getUsers method
            * override this method for handling normal response from getUsers operation
            */
           public void receiveResultgetUsers(
                    com.panopto.user.UserManagementStub.GetUsersResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getUsers operation
           */
            public void receiveErrorgetUsers(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for resetPassword method
            * override this method for handling normal response from resetPassword operation
            */
           public void receiveResultresetPassword(
                    com.panopto.user.UserManagementStub.ResetPasswordResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from resetPassword operation
           */
            public void receiveErrorresetPassword(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createUsers method
            * override this method for handling normal response from createUsers operation
            */
           public void receiveResultcreateUsers(
                    com.panopto.user.UserManagementStub.CreateUsersResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createUsers operation
           */
            public void receiveErrorcreateUsers(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for listGroups method
            * override this method for handling normal response from listGroups operation
            */
           public void receiveResultlistGroups(
                    com.panopto.user.UserManagementStub.ListGroupsResponse0 result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from listGroups operation
           */
            public void receiveErrorlistGroups(java.lang.Exception e) {
            }
                


    }
    