/* Copyright Panopto 2009 - 2015
 *
 * This file is part of the Panopto plugin for Blackboard.
 *
 * The Panopto plugin for Blackboard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Panopto plugin for Blackboard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Panopto plugin for Blackboard.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.panopto.blackboard;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import blackboard.base.FormattedText;
import blackboard.data.ValidationException;
import blackboard.data.content.Content;
import blackboard.data.course.Course;
import blackboard.data.course.CourseMembership;
import blackboard.data.course.CourseMembership.Role;
import blackboard.data.navigation.CourseToc;
import blackboard.data.registry.CourseRegistryEntry;
import blackboard.data.registry.Registry;
import blackboard.data.registry.RegistryEntry;
import blackboard.data.user.User;
import blackboard.persist.BbPersistenceManager;
import blackboard.persist.Id;
import blackboard.persist.KeyNotFoundException;
import blackboard.persist.PersistenceException;
import blackboard.persist.content.ContentDbPersister;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.persist.navigation.CourseTocDbLoader;
import blackboard.persist.navigation.CourseTocDbPersister;
import blackboard.persist.registry.CourseRegistryEntryDbLoader;
import blackboard.persist.registry.CourseRegistryEntryDbPersister;
import blackboard.persist.user.UserDbLoader;
import blackboard.platform.context.Context;
import blackboard.platform.persistence.PersistenceServiceFactory;
import blackboard.platform.plugin.PlugInUtil;
import blackboard.platform.security.CourseRole;

import com.panopto.services.AccessManagementLocator;
import com.panopto.services.AuthLocator;
import com.panopto.services.AuthenticationInfo;
import com.panopto.services.DateTimeOffset;
import com.panopto.services.Folder;
import com.panopto.services.FolderSortField;
import com.panopto.services.IAccessManagement;
import com.panopto.services.IAuth;
import com.panopto.services.ISessionManagement;
import com.panopto.services.IUserManagement;
import com.panopto.services.ListFoldersRequest;
import com.panopto.services.ListFoldersResponse;
import com.panopto.services.ListSessionsRequest;
import com.panopto.services.ListSessionsResponse;
import com.panopto.services.Pagination;
import com.panopto.services.Session;
import com.panopto.services.SessionAvailabilitySettings;
import com.panopto.services.SessionEndSettingType;
import com.panopto.services.SessionManagementLocator;
import com.panopto.services.SessionSortField;
import com.panopto.services.SessionStartSettingType;
import com.panopto.services.SessionState;
import com.panopto.services.UserManagementLocator;

// Wrap interaction with DB and Panopto SOAP services for a particular Blackboard course
public class PanoptoData
{
    // Keys for Blackboard course registry settings
    private static final String hostnameRegistryKey = "CourseCast_Hostname";
    private static final String sessionGroupIDRegistryKey = "CourseCast_SessionGroupID";
    private static final String sessionGroupDisplayNameRegistryKey = "CourseCast_SessionGroupDisplayName";
    private static final String originalContextRegistryKey = "CourseCast_OriginalContext";

    //Constants used for paging.
    private final int maxPages = 100;
    private final int perPage = 100;

    // Blackboard course we are associating with
    private Course bbCourse;
    // Blackboard username of currently logged in user
    private String bbUserName;

    private boolean isInstructor = false;
    private boolean canAddLinks = false;

    // Panopto server to talk to
    private String serverName;
    
    //Version number of the current Panopto server
    private PanoptoVersion serverVersion;
    
    // User key to use when talking to Panopto SOAP services
    // (Instance-decorated username of currently-logged-in Blackboard user)
    private String apiUserKey;
    // Hash of username, server, and shared secret for securing web services
    private String apiUserAuthCode;

    // ID and display name of currently associated Panopto course
    private String[] sessionGroupPublicIDs;
    private String[] sessionGroupDisplayNames;

    // SOAP port for talking to Panopto
    private ISessionManagement sessionManagement;

    
    // Construct the PanoptoData object using the current Blackboard context (e.g. from <bbData:context ...> tag)
    // Pulls in stored property values from BB course registry if available.
    // Ensure that serverName and sessionGroupPublicIDs are set before calling any instance methods that rely on these properties (most).
    public PanoptoData(Context ctx)
    {
        InitPanoptoData(ctx.getCourse(), ctx.getUser().getUserName());
    }

    public PanoptoData(String bbCourseId, String bbUserName)
    {
        BbPersistenceManager bbPm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();
        Course bbCourse = null;
        try
        {

            CourseDbLoader courseLoader = (CourseDbLoader) bbPm.getLoader(CourseDbLoader.TYPE);
            bbCourse = courseLoader.loadByCourseId(bbCourseId);
            InitPanoptoData(bbCourse, bbUserName);
        }
        catch(Exception e)
        {
            Utils.log(e, String.format("Error getting course info (course ID: %s).", bbCourseId));
        }
    }

    public PanoptoData(Course bbCourse, String bbUserName)
    {
        InitPanoptoData(bbCourse, bbUserName);
    }

    private void InitPanoptoData(Course bbCourse, String bbUserName)
    {
        this.bbCourse = bbCourse;
        this.bbUserName = bbUserName;
        this.isInstructor = PanoptoData.isUserInstructor(this.bbCourse.getId(), this.bbUserName, false);
        this.canAddLinks = PanoptoData.canUserAddLinks(this.bbCourse.getId(), this.bbUserName);
        
        List<String> serverList = Utils.pluginSettings.getServerList();
        // If there is only one server available, use it
        if(serverList.size() == 1)
        {
            updateServerName(serverList.get(0));
        }
        else
        {
            updateServerName(getCourseRegistryEntry(hostnameRegistryKey));
        }
        sessionGroupPublicIDs = getCourseRegistryEntries(sessionGroupIDRegistryKey);
        sessionGroupDisplayNames = getCourseRegistryEntries(sessionGroupDisplayNameRegistryKey);
        
        // Check that the list of Ids and names are valid. They must both be the same length
        if ((sessionGroupPublicIDs == null && sessionGroupDisplayNames != null)
                || (sessionGroupPublicIDs != null && sessionGroupDisplayNames == null)
                || (sessionGroupPublicIDs != null && sessionGroupPublicIDs.length != sessionGroupDisplayNames.length))
        {
            Utils.log(String.format("Invalid course registry settings. Reseting both to null. sessionGroupPublicIDs = %s          sessionGroupDisplayNames = %s",
                    Utils.encodeArrayOfStrings(sessionGroupPublicIDs),
                    Utils.encodeArrayOfStrings(sessionGroupDisplayNames)));

            this.sessionGroupPublicIDs = null;
            this.sessionGroupDisplayNames = null;
            setCourseRegistryEntry(hostnameRegistryKey, null);
            setCourseRegistryEntries(sessionGroupIDRegistryKey, null);
            setCourseRegistryEntries(sessionGroupDisplayNameRegistryKey, null);
        }
    }

    public Course getBBCourse()
    {
        return bbCourse;
    }

    public String getProvisionUrl(String serverName, String returnUrl)
    {
        if (serverName == null)
        {
            return "";
        }
        return "Course_Provision.jsp?provisionServerName=" + serverName + "&bbCourses=" + getBBCourse().getCourseId() + "&returnUrl=" + returnUrl;
    }

    public String getServerName()
    {
        return serverName;
    }

    public String[] getFolderDisplayNames()
    {
        return sessionGroupDisplayNames;
    }

    // gets the number of folders associated with this course
    public int getNumberOfFolders()
    {
        if (sessionGroupPublicIDs == null)
        {
            return 0;
        }

        return sessionGroupPublicIDs.length;
    }

    // Update properties that depend on serverName
    private void updateServerName(String serverName)
    {
        this.serverName = serverName;

        if((serverName != null) && !serverName.equals(""))
        {
            apiUserKey = Utils.decorateBlackboardUserName(bbUserName);
            apiUserAuthCode = Utils.generateAuthCode(serverName, apiUserKey + "@" + serverName);
            sessionManagement = getPanoptoSessionManagementSOAPService(serverName);
            serverVersion = getServerVersion(); 
            
        }
        else
        {
            apiUserKey = null;
            apiUserAuthCode = null;
            sessionManagement = null;
            serverVersion = null;
        }
    }

    public boolean isServerSet()
    {
        return (serverName != null);
    }

    // i.e. a Panopto course been selected for this Blackboard course
    public boolean isMapped()
    {
        return (sessionGroupPublicIDs != null);
    }

    // Get the Panopto user being used for SOAP calls
    public String getApiUserKey()
    {
        return apiUserKey;
    }

    // Determine if this course is in the original context and the course has NOT been copied.
    public boolean isOriginalContext()
    {
        boolean isOriginal = true;

        // If the registry does not exist, assume this is original and set the value.
        if (getCourseRegistryEntry(originalContextRegistryKey) == null)
        {
            setCourseRegistryEntry(originalContextRegistryKey, bbCourse.getId().toExternalString());
        }
        else if (!getCourseRegistryEntry(originalContextRegistryKey).equalsIgnoreCase(bbCourse.getId().toExternalString()))
        {
            // There is a context and it doesn't match the current context.
            isOriginal = false;
        }
        return (isOriginal);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    ///// The following "get..." functions are just wrappers for the relevant SOAP calls, /////
    ///// using stored course mapping and credentials.                                    /////
    ///////////////////////////////////////////////////////////////////////////////////////////

    // Gets all the sessions in a folder from the Panopto server. Returns null on error.
    public Session[] getSessions(String folderId)
    {
        Session[] returnValue;
        try
        {
            // Get all the sessions
            int page = 0;
            int responseCount = 0;
            int totalSessionsExpected = -1;
            ListSessionsResponse listResponse;
            List<Session> allSessions = new ArrayList<Session>();
            AuthenticationInfo auth = new AuthenticationInfo(apiUserAuthCode, null, apiUserKey);
            do
            {
                ListSessionsRequest request = new ListSessionsRequest();

                request.setFolderId(folderId);
                request.setPagination(new Pagination(this.perPage, page));
                request.setSortBy(SessionSortField.Date);
                request.setSortIncreasing(true); //sortIncreasing = true
                request.setStates(new SessionState[] { SessionState.Broadcasting, SessionState.Complete, SessionState.Recording });

                listResponse = sessionManagement.getSessionsList(auth, request, null); //searchQuery = null
                allSessions.addAll(Arrays.asList(listResponse.getResults()));
                if (totalSessionsExpected == -1)
                {
                    // First time through, grab the expected total count.
                    totalSessionsExpected = listResponse.getTotalNumberResults();
                }
                Session[] returnedSessions = listResponse.getResults();
                responseCount += returnedSessions.length;
                page++;
            } while ((responseCount < totalSessionsExpected) && (page < this.maxPages));

            returnValue = new Session[allSessions.size()];
            returnValue = allSessions.toArray(returnValue);
        }
        catch(Exception e)
        {
            Utils.log(e, String.format("Error getting sessions (folder ID: %s, api user: %s).", folderId, apiUserKey));
            returnValue = null;
        }
        return  returnValue;
    }

    // Gets all the folders associated with this course. Any folder we can't get will return as null.
    public Folder[] getFolders()
    {
        Folder[] retVal = null;
        if (sessionGroupPublicIDs != null && sessionGroupPublicIDs.length > 0)
        {
            AuthenticationInfo auth = new AuthenticationInfo(apiUserAuthCode, null, apiUserKey);
            try
            {
                retVal = sessionManagement.getFoldersById(auth, sessionGroupPublicIDs);
            }
            catch (Exception e)
            {
                Utils.logVerbose("first attempt at getFoldersById failed, calling syncUser");
                // Got an error from the panopto server. sync the user's credentials and try again
                syncUser(serverName, bbUserName);
                try
                {
                    retVal = sessionManagement.getFoldersById(auth, sessionGroupPublicIDs);
                }
                catch (Exception e2)
                {
                    // Still failed. Could be because one of the folders has been deleted. Get them one at a time.
                    retVal = new Folder[sessionGroupPublicIDs.length];
                    for (int i = 0; i < sessionGroupPublicIDs.length; i++)
                    {
                        try
                        {
                            retVal[i] = sessionManagement.getFoldersById(auth, new String[] { sessionGroupPublicIDs[i] })[0];
                        }
                        catch(Exception e3)
                        {
                            Utils.log(e3, String.format("Error getting folder(courseId: %s, courseTitle %s, folder ID: %s, api user: %s).", bbCourse.getId().toExternalString(), bbCourse.getTitle(), sessionGroupPublicIDs[i], apiUserKey));
                            retVal[i] = null;
                        }
                    }
                }
            }
        }

        return retVal;
    }

    // Gets the urls to download the recorders
    public com.panopto.services.RecorderDownloadUrlResponse getRecorderDownloadUrls()
    {
        try
        {
            return sessionManagement.getRecorderDownloadUrls();
        }
        catch (RemoteException e)
        {
            Utils.log(e, "Error getRecorderDownloadUrls");
            return null;
        }
    }

    // Used to sort folders alphabetically by name
    class FolderComparator implements Comparator<Folder>
    {
        public int compare(Folder f1, Folder f2)
        {
            return f1.getName().toLowerCase().compareTo(f2.getName().toLowerCase());
        }
    }

    // Gets all the Panopto folders the user has creator access to
    private Folder[] getFoldersWithCreatorAccess()
    {
        try
        {
            // First sync the user so his memberships will be up to date
            syncUser(serverName, bbUserName);

            // Next get the user's access details
            AuthenticationInfo auth = new AuthenticationInfo(apiUserAuthCode, null, apiUserKey);
            HashSet<String> foldersWithCreatorAccess = new HashSet<String>();

            // Get all the folders
            int page = 0;
            int responseCount = 0;
            int totalFoldersExpected = -1;
            ListFoldersResponse listResponse;
            List<Folder> allFolders = new ArrayList<Folder>();
            do
            {
                ListFoldersRequest foldersRequest = new ListFoldersRequest( new Pagination(this.perPage, page ), null, false, FolderSortField.Name, true );
                listResponse = getPanoptoSessionManagementSOAPService(serverName).getCreatorFoldersList(auth, foldersRequest, null);
                allFolders.addAll(Arrays.asList(listResponse.getResults()));

                if (totalFoldersExpected == -1)
                {
                    // First time through, grab the expected total count.
                    totalFoldersExpected = listResponse.getTotalNumberResults();
                }
                Folder[] returnedFolders = listResponse.getResults();

                // Log which folders we got back. foldersWithCreatorAccess, folderIdList, and returnedFolders are all just in place for logging.
                foldersWithCreatorAccess = new HashSet<String>();
                for (Folder folder : returnedFolders)
                {
                    foldersWithCreatorAccess.add(folder.getId());
                }
                String[] folderIdList = foldersWithCreatorAccess.toArray(new String[0]);
                Utils.logVerbose(String.format("getFoldersWithCreatorAccess. User: %s, page: %d, returned from getCreatorFoldersList: %s", bbUserName, page, Utils.encodeArrayOfStrings(folderIdList)));

                responseCount += returnedFolders.length;
                page++;
            } while ((responseCount < totalFoldersExpected) && (page < this.maxPages));

            Utils.logVerbose(String.format("Expected %d folders, returned %d folders", totalFoldersExpected, allFolders.size()));

            return allFolders.toArray(new Folder[allFolders.size()]);
        }
        catch (RemoteException e)
        {
            Utils.log(e, String.format("Error getting folders with creator access (server: %s, apiUserKey: %s).", serverName, apiUserKey));
            return null;
        }
    }

    // Gets all the public folders from the server
    private Folder[] getPublicFolders()
    {
        try
        {
            AuthenticationInfo auth = new AuthenticationInfo(apiUserAuthCode, null, apiUserKey);
            HashSet<String> publicFolders = new HashSet<String>();
            // Get all the folders

            int page = 0;
            int responseCount = 0;
            int totalFoldersExpected = -1;
            ListFoldersResponse listResponse;
            List<Folder> allFolders = new ArrayList<Folder>();

            do
            {
                ListFoldersRequest request = new ListFoldersRequest();
                request.setPublicOnly(true);
                request.setPagination(new Pagination(this.perPage, page));
                listResponse =getPanoptoSessionManagementSOAPService(serverName).getCreatorFoldersList(auth, request, null);
                allFolders.addAll(Arrays.asList(listResponse.getResults()));

                if (totalFoldersExpected == -1)
                {
                    // First time through, grab the expected total count.
                    totalFoldersExpected = listResponse.getTotalNumberResults();
                }
                Folder[] returnedFolders = listResponse.getResults();

                // Log which folders we got back. foldersWithCreatorAccess, folderIdList, and returnedFolders are all just in place for logging.
                publicFolders = new HashSet<String>();
                for (Folder folder : returnedFolders)
                {
                    publicFolders.add(folder.getId());
                }
                String[] folderIdList = publicFolders.toArray(new String[0]);
                Utils.logVerbose(String.format("getPublicFolders. User: %s, page: %d, returned from getPublicFolders: %s", bbUserName, page, Utils.encodeArrayOfStrings(folderIdList)));

                responseCount += returnedFolders.length;
                page++;
            } while ((responseCount < totalFoldersExpected) && (page < this.maxPages));
            Utils.logVerbose(String.format("Expected %d folders, returned %d folders", totalFoldersExpected, allFolders.size()));
            return allFolders.toArray(new Folder[allFolders.size()]);

        }
        catch (RemoteException e)
        {
            Utils.log(e, String.format("Error getting public folders (server: %s, apiUserKey: %s).", serverName, apiUserKey));
            return null;
        }
    }

    // Generates the list of selected folders for the course config page's listbox
    public String generateCourseConfigSelectedFoldersOptionsHTML()
    {
        StringBuilder result = new StringBuilder();
        if (sessionGroupDisplayNames != null)
        {
            for (int i = 0; i < sessionGroupDisplayNames.length; i++)
            {
                result.append("<option");
                result.append(" value='").append(sessionGroupPublicIDs[i]).append("'");
                result.append(">");
                result.append(Utils.escapeHTML(sessionGroupDisplayNames[i]));
                result.append("</option>\n");
            }
        }

        return result.toString();
    }

    // Generates the list of available folders for the course config page's listbox
    public String generateCourseConfigAvailableFoldersOptionsHTML()
    {
        StringBuilder result = new StringBuilder();

        // Get all the folder the user has access to
        Folder[] folders = getFoldersWithCreatorAccess();

        // Sort them by name
        ArrayList<Folder> sortedFolders = new ArrayList<Folder>();
        sortedFolders.addAll(Arrays.asList(folders));
        Collections.sort(sortedFolders, new FolderComparator());

        // Build a hash of the currently selected folders so we can quickly exclude them
        HashSet<String> currentFolderIds = new HashSet<String>();
        currentFolderIds.addAll(Arrays.asList(sessionGroupPublicIDs));

        // Finally write out the options in sorted order
        for (Folder folder : sortedFolders)
        {
            if (!currentFolderIds.contains(folder.getId()))
            {
                result.append("<option");
                result.append(" value='").append(folder.getId()).append("'");
                result.append(">");
                result.append(Utils.escapeHTML(folder.getName()));
                result.append("</option>\n");
            }
        }

        return result.toString();
    }

    // Looks up the display string for a given folderID. The folderId must be already mapped with the course
    public String getFolderDisplayString(String folderId)
    {
        if (folderId != null)
        {
            for (int i = 0; i < sessionGroupDisplayNames.length; i++)
            {
                if(sessionGroupPublicIDs[i].equals(folderId))
                {
                    return sessionGroupDisplayNames[i];
                }
            }
        }

        return null;
    }

    // Generate <option>s for available folders. Used by Item_Create to select a folder
    public String generateFolderOptionsHTML(String folderId)
    {
        StringBuilder result = new StringBuilder();

        Folder[] publicFolders = this.getPublicFolders();
        int numFolders = getNumberOfFolders();
        numFolders += publicFolders != null ? publicFolders.length : 0;
        if (numFolders == 0)
        {
            result.append("<option value=''>-- No Folders Available --</option>");
        }
        else
        {
            result.append("<option value=''>-- Select a Folder --</option>");

            // Only use option groups if we have elements in both groups
            boolean useOptionalGroups =    sessionGroupDisplayNames != null
                    && sessionGroupDisplayNames.length > 0
                    && publicFolders != null
                    && publicFolders.length > 0;

                    // Add all the mapped folders
                    if (sessionGroupDisplayNames != null && sessionGroupDisplayNames.length > 0)
                    {
                        if (useOptionalGroups)
                        {
                            result.append("<optgroup label='Mapped Folders'>\n");
                        }
                        for (int i = 0; i < sessionGroupDisplayNames.length; i++)
                        {
                            String strDisplayName = Utils.escapeHTML(sessionGroupDisplayNames[i]);
                            String strID = sessionGroupPublicIDs[i];

                            result.append("<option");
                            result.append(" value='").append(strID).append("'");
                            if(strID.equals(folderId))
                            {
                                result.append(" SELECTED");
                            }
                            result.append(">");
                            result.append(strDisplayName);
                            result.append("</option>\n");
                        }
                        if (useOptionalGroups)
                        {
                            result.append("</optgroup>\n");
                        }
                    }

                    // Add all the public folders
                    if (publicFolders != null && publicFolders.length > 0)
                    {
                        if (useOptionalGroups)
                        {
                            result.append("<optgroup label='Public Folders'>\n");
                        }
                        for (int i = 0; i < publicFolders.length; i++)
                        {
                            String strDisplayName = Utils.escapeHTML(publicFolders[i].getName());
                            String strID = publicFolders[i].getId();

                            result.append("<option");
                            result.append(" value='").append(strID).append("'");
                            if(strID.equals(folderId))
                            {
                                result.append(" SELECTED");
                            }
                            result.append(">");
                            result.append(strDisplayName);
                            result.append("</option>\n");
                        }
                        if (useOptionalGroups)
                        {
                            result.append("</optgroup>\n");
                        }
                    }
        }

        return result.toString();
    }

    // Generate <option>s for available sessions. Used by Item_Create to select a session once a fodler is selected
    public String generateSessionOptionsHTML(String folderID)
    {
        StringBuilder result = new StringBuilder();

        if (folderID == null || folderID == "")
        {
            result.append("<option value=''>-- Please select a folder first --</option>\n");
        }
        else
        {
            Utils.log("Folder ID: " + folderID);
            Session[] sessions = this.getSessions(folderID);
            if(sessions != null)
            {
                if (sessions.length == 0)
                {
                    result.append("<option value=''>-- The folder is empty --</option>");
                }
                else
                {
                    result.append("<option value=''>-- Select a Lecture --</option>");

                    for(Session session : sessions)
                    {
                        String strDisplayName = Utils.escapeHTML(session.getName());

                        result.append("<option");
                        result.append(" value='").append(session.getViewerUrl()).append("'");
                        result.append(">");
                        result.append(strDisplayName);
                        result.append("</option>\n");
                    }
                }
            }
            else
            {
                result.append("<option value=''>!! Unable to retrieve lecture list !!</option>\n");
            }
        }

        return result.toString();
    }

    // Returns true if the user is an instructor for this course
    public boolean IsInstructor()
    {
        return isInstructor;
    }
    public boolean canAddLinks() {
        return canAddLinks;
    }

    // Insert a content item in the current course with a link to the specified delivery.
    public LinkAddedResult addBlackboardContentItem(String content_id, String lectureUrl, String title, String description)
    {
        Session[] sessionArray;
        LinkAddedResult linkAddedResult = LinkAddedResult.FAILURE;
        try
        {

            //Get folder ID from URL param and add to an array to pass into updateFoldersAvailabilityStartSettings();
            Map<String, String> urlParams = this.getQueryMap(lectureUrl);
            String sessionID = urlParams.get("id");
            String[] sessionIds = {sessionID};

            // retrieve the Db persistence manager from the persistence service
            BbPersistenceManager bbPm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();

            //Generate AuthenticationInfo for calling availability window update method.
            AuthenticationInfo auth = new AuthenticationInfo(apiUserAuthCode, null, apiUserKey);

            //Get user's blackboard ID from their username
            UserDbLoader userLoader = (UserDbLoader)bbPm.getLoader(UserDbLoader.TYPE);
            User user = userLoader.loadByUserName(bbUserName);
            Id bbUserId = user.getId();

            //Load the user's membership in the current course to get their role
            CourseMembershipDbLoader membershipLoader = (CourseMembershipDbLoader)bbPm.getLoader(CourseMembershipDbLoader.TYPE);
            CourseMembership usersCourseMembership = membershipLoader.loadByCourseAndUserId(bbCourse.getId(), bbUserId);
            Role userRole = usersCourseMembership.getRole();

            //Determine if current user has creator access to session.
            boolean isCreator = (   isInstructorRole(userRole)
                                 || (   isTARole(userRole)
                                     && Utils.pluginSettings.getGrantTACreator()));

            if(isCreator)
            {
                //isInAvailabilityWindow defaults to true, so sessions will be added if calls to the availability window API cannot be made.
                boolean isInAvailabilityWindow = true;

                if(PanoptoVersions.canCallAvailabilityWindowApiMethods(serverVersion)){
                    try{
                        //If user is a creator on Panopto, check if the session is in its availability window.
                        isInAvailabilityWindow = this.isSessionInAvailabilityWindow(
                                sessionIds, auth);
                    }
                    catch(Exception e)
                    {
                        //Problem getting availability window information freom the API. Do not add the session to the course.
                        isInAvailabilityWindow = false;
                        Utils.log(e, "Error getting availability information for sessions from server.");
                        
                    }
                }
                if(isInAvailabilityWindow){
                   // If the session is in its availability window. add it to the course and return success.
                    addSessionLinkToCourse(content_id, lectureUrl, title, description,
                            bbPm);
                    linkAddedResult =  LinkAddedResult.SUCCESS;
                }
                else
                {
                    //If the session is not in its availability window, try to call the API to make the session available immediately. If the call
                    // is successful, add the session to the course and return success, otherwise it means that the session requires publisher
                    //approval and the calling user is not a publisher.
                    try
                    {
                        sessionManagement.updateSessionsAvailabilityStartSettings(auth, sessionIds, SessionStartSettingType.Immediately, null);
                        addSessionLinkToCourse(content_id, lectureUrl, title, description, bbPm);
                        linkAddedResult = LinkAddedResult.SUCCESS;
                    }
                    catch(Exception e)
                    {
                        //Needs publisher access
                        linkAddedResult = LinkAddedResult.NOTPUBLISHER;
                    }
                }
            }
            else
            {
                //If the user does not have a creator role, first call API to determine whether session is already available.
                sessionArray = sessionManagement.getSessionsById(auth, sessionIds);
                if(sessionArray.length < 1)
                {
                    //If no session is returned, it means the session is not in its availability window, and the current user cannot make it,
                    //available. Return failure indicating the user must ask a creator to make the session available.
                    linkAddedResult = LinkAddedResult.NOTCREATOR;
                }
                else
                {
                    //If the session is currently in its availability window, it can be added to the course without having to make any API call.
                    //Return success.
                    addSessionLinkToCourse(content_id, lectureUrl, title, description, bbPm);
                    linkAddedResult = LinkAddedResult.SUCCESS;
                }
            }
        }
        catch(Exception e)
        {
            //General error when trying to add a session to a course. Print details to log.
            Utils.log(e, String.format("Error adding content item (content ID: %s, lecture Url: %s, title: %s, description: %s).", content_id, lectureUrl, title, description));
            linkAddedResult = LinkAddedResult.FAILURE;
        }
        return linkAddedResult;
    }


    //Determines if a given Panopto session is currently within its availability window.
    private boolean isSessionInAvailabilityWindow(String[] sessionIds,
            AuthenticationInfo auth) throws RemoteException {

        boolean isAvailable = false;

        //Get availability window settings for session.
        SessionAvailabilitySettings sessionAvailabilitySettings = sessionManagement.getSessionsAvailabilitySettings(auth, sessionIds).getResults()[0];

        //Get datetime component of StartSettingDate and add the offset to get UTC time.
        Calendar startDate;
        Calendar endDate;
        DateTimeOffset startOffset = sessionAvailabilitySettings.getStartSettingDate();

        //If the start date time offset is not null, then extract the date from it. Otherwise, set the date to null.
        if (startOffset != null){
            startDate = startOffset.getDateTime();
            startDate.add(Calendar.MINUTE, startOffset.getOffsetMinutes());
        }
        else
        {
            startDate = null;
        }

        DateTimeOffset endOffset = sessionAvailabilitySettings.getEndSettingDate();

        //If the end date time offset is not null, then extract the date from it. Otherwise, set the date to null.
        if (endOffset != null){
            endDate = endOffset.getDateTime();
            endDate.add(Calendar.MINUTE, endOffset.getOffsetMinutes());
        }
        else
        {
            endDate = null;
        }

        SessionStartSettingType startType = sessionAvailabilitySettings.getStartSettingType();
        SessionEndSettingType endType = sessionAvailabilitySettings.getEndSettingType();

        //If session start date is either "Immediately" or is a specific date in the past,
        //we are currently past the start time of the availability window.
        if(startType.equals(SessionStartSettingType.Immediately)
           || (startType.equals(SessionStartSettingType.SpecificDate)
                && startDate.after(Calendar.getInstance(TimeZone.getTimeZone("UTC")))))
        {
            //If the session availability end date is "Forever", or a date in the future,
            //we are currently before the availability window's end time, and are therefore
            //within the session's availability window.
            if(endType.equals(SessionEndSettingType.Forever)
               || (endType.equals(SessionEndSettingType.SpecificDate)
                       && endDate.before(Calendar.getInstance(TimeZone.getTimeZone("UTC")))))
            {
                isAvailable = true;
            }

        }
        return isAvailable;
    }

    //Adds a link to a Panopto session to the content area of the current Blackboard course.
    private void addSessionLinkToCourse(String content_id, String lectureUrl,
            String title, String description, BbPersistenceManager bbPm)
            throws PersistenceException, ValidationException {
        // Create a course document and set all desired attributes
        Content content = new Content();
        content.setTitle(title);
        content.setBody(new FormattedText(description, FormattedText.Type.HTML));
        content.setUrl(lectureUrl);
        content.setRenderType(Content.RenderType.URL);
        content.setLaunchInNewWindow(true);
        content.setContentHandler("hyperlink/coursecast");

        // Set course and parent content IDs (required)
        Id parentId = bbPm.generateId(Content.DATA_TYPE, content_id);
        content.setParentId(parentId);

        Id courseId = bbCourse.getId();
        content.setCourseId(courseId);

        // retrieve the content persister and persist the content item
        ContentDbPersister persister = (ContentDbPersister) bbPm.getPersister(ContentDbPersister.TYPE);
        persister.persist(content);
    }



    //Returns map of url's query parameters and their values. Used for getting session ID for session to make available.
    public Map<String, String> getQueryMap(String url)
    {
        Map<String, String> map = null;
        String[] splitURL = url.split("\\?");
        if(splitURL[1] != null && !splitURL[1].isEmpty()){
            String[] params = splitURL[1].split("&");
            map = new HashMap<String, String>();
            for (String param : params)
            {
                String name = param.split("=")[0];
                String value = param.split("=")[1];
                map.put(name, value);
            }
        }
        return map;
    }

    // Sync's a user with Panopto so that his course memberships are up to date.
    public void syncUser()
    {
        PanoptoData.syncUser(serverName, bbUserName);
    }

    // Sync's a user with Panopto so that his course memberships are up to date.
    public static void syncUser(String serverName, String bbUserName)
    {
        String apiUserKey = Utils.decorateBlackboardUserName(bbUserName);
        String apiUserAuthCode = Utils.generateAuthCode(serverName, apiUserKey + "@" + serverName);
        AuthenticationInfo auth = new AuthenticationInfo(apiUserAuthCode, null, apiUserKey);

        try
        {
            // Load the user's profile info
            BbPersistenceManager bbPm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();
            UserDbLoader userLoader = (UserDbLoader)bbPm.getLoader(UserDbLoader.TYPE);
            User user = userLoader.loadByUserName(bbUserName);
            Id bbUserId = user.getId();

            CourseDbLoader courseLoader = (CourseDbLoader) bbPm.getLoader(CourseDbLoader.TYPE);
            CourseMembershipDbLoader courseMembershipLoader = (CourseMembershipDbLoader) bbPm.getLoader(CourseMembershipDbLoader.TYPE);
            List<Course> instructorCourses = new ArrayList<Course>();
            List<Course> studentCourses = new ArrayList<Course>();
            List<Course> taCourses = new ArrayList<Course>();

            List<CourseMembership> allCourseMemberships = courseMembershipLoader.loadByUserId(bbUserId);

            Course currentCourse;
            for(CourseMembership membership: allCourseMemberships)
            {
            	try
            	{
	                Role membershipRole = membership.getRole();
	                currentCourse = courseLoader.loadById(membership.getCourseId());
	                if(isInstructorRole(membershipRole))
	                {
	                    instructorCourses.add(currentCourse);
	                }
	                else if(isTARole(membershipRole))
	                {
	                    taCourses.add(currentCourse);
	                }
	                else
	                {
	                    studentCourses.add(currentCourse);
	                }
            	}
            	catch (Exception ex)
            	{
            		Utils.log(
        				ex,
        				String.format(
    						"Failed to load course %1$s for membership %2$s",
    						membership.getCourseId(),
    						membership.getId()));
            	}
            }

            ArrayList<String> externalGroupIds = new ArrayList<String>();
            StringBuilder courseList = new StringBuilder();
            for(Course course : studentCourses)
            {
                courseList.append(course.getTitle());
                Id courseId = course.getId();
                String courseServerName = getCourseRegistryEntry(courseId, hostnameRegistryKey);
                if (courseServerName != null && courseServerName.equalsIgnoreCase(serverName))
                {
                    String groupName = Utils.decorateBlackboardCourseID(courseId.toExternalString()) + "_viewers";
                    externalGroupIds.add(groupName);
                    courseList.append('(').append(groupName).append(')');
                }
                else if (courseServerName == null)
                {
                    courseList.append("(never provisioned)");
                }
                else
                {
                    courseList.append("(provisioned against ").append(courseServerName).append(")");
                }

                courseList.append(';');
            }
            Utils.logVerbose(String.format("Sync'ing user %s group membership to server %s. Student group membership: %s", bbUserName, serverName, courseList.toString()));

            courseList = new StringBuilder();
            for(Course course : instructorCourses)
            {
                courseList.append(course.getTitle());
                Id courseId = course.getId();
                String courseServerName = getCourseRegistryEntry(courseId, hostnameRegistryKey);
                if (courseServerName != null && courseServerName.equalsIgnoreCase(serverName))
                {
                    String groupName = Utils.decorateBlackboardCourseID(courseId.toExternalString()) + "_creators";
                    externalGroupIds.add(groupName);
                    courseList.append('(').append(groupName).append(')');
                }
                else if (courseServerName == null)
                {
                    courseList.append("(never provisioned)");
                }
                else
                {
                    courseList.append("(provisioned against ").append(courseServerName).append(")");
                }

                courseList.append(';');
            }
            Utils.logVerbose(String.format("Sync'ing user %s group membership to server %s. Instructor group membership: %s", bbUserName, serverName, courseList.toString()));

            courseList = new StringBuilder();
            for(Course course : taCourses)
            {
                courseList.append(course.getTitle());
                Id courseId = course.getId();
                String courseServerName = getCourseRegistryEntry(courseId, hostnameRegistryKey);
                if (courseServerName != null && courseServerName.equalsIgnoreCase(serverName))
                {
                    String groupName;
                    if (Utils.pluginSettings.getGrantTACreator())
                    {
                        groupName = Utils.decorateBlackboardCourseID(courseId.toExternalString()) + "_creators";
                    }
                    else
                    {
                        groupName = Utils.decorateBlackboardCourseID(courseId.toExternalString()) + "_viewers";
                    }
                    externalGroupIds.add(groupName);
                    courseList.append('(').append(groupName).append(')');
                }
                else if (courseServerName == null)
                {
                    courseList.append("(never provisioned)");
                }
                else
                {
                    courseList.append("(provisioned against ").append(courseServerName).append(")");
                }

                courseList.append(';');
            }

            Utils.logVerbose(String.format("Sync'ing user %s group membership to server %s. TA group membership: %s", bbUserName, serverName, courseList.toString()));
            getPanoptoUserManagementSOAPService(serverName).syncExternalUser(
                    auth,
                    user.getGivenName(),
                    user.getFamilyName(),
                    user.getEmailAddress(),
                    Utils.pluginSettings.getMailLectureNotifications(),
                    externalGroupIds.toArray(new String[0]));
        }
        catch(Exception e)
        {
            Utils.log(e, String.format("Error sync'ing user's group membership (server: %s, user: %s).", serverName, bbUserName));
        }
    }

    // Creates a new Panopto folder. Should only be called by the instructor of a provisioned course (They have creator rights in Panopto)
    public Folder createFolder(String folderName)
    {
        if (this.userMayCreateFolder())
        {
            ISessionManagement client = getPanoptoSessionManagementSOAPService(serverName);
            AuthenticationInfo auth = new AuthenticationInfo(apiUserAuthCode, null, apiUserKey);
            try
            {
                Folder retVal = client.addFolder(auth, folderName, null, false);
                Utils.log(String.format("Successfully created folder (server: %s, folder: %s).", serverName, folderName));
                return retVal;
            }
            catch (Exception e)
            {
                Utils.log(e, String.format("Error creating folder (server: %s, folder: %s).", serverName, folderName));
                return null;
            }
        }
        else
        {
            Utils.log("User is not allowed to create a folder");
            return null;
        }
    }

    // Updates the course so it is mapped to the given folders
    public boolean reprovisionCourse(String[] folderIds)
    {
        try
        {
            String externalCourseId = Utils.decorateBlackboardCourseID(bbCourse.getId().toExternalString());
            String fullName = bbCourse.getCourseId() + ": " + bbCourse.getTitle();

            // Provision the course
            AuthenticationInfo auth = new AuthenticationInfo(apiUserAuthCode, null, apiUserKey);
            Folder[] folders = getPanoptoSessionManagementSOAPService(serverName).setExternalCourseAccess(auth, fullName, externalCourseId, folderIds);
            updateCourseFolders(folders);

            //Add menu item if setting is enabled
            if(Utils.pluginSettings.getInsertLinkOnProvision())
            {
                addCourseMenuLink();
            }
        }
        catch(Exception e)
        {
            String folderString = Utils.encodeArrayOfStrings(folderIds);
            if (folderString == null)
            {
                folderString = "empty";
            }

            Utils.log(e, String.format("Error reprovisioning course (id: %s, server: %s, user: %s, folders: %s).", bbCourse.getId().toExternalString(), serverName, bbUserName, folderString));
            return false;
        }

        return true;
    }



    // Updates the course so it has no Panopto data
    public boolean resetCourse() throws RemoteException
    {
        boolean success = true;

        if (!isMapped())
        {
            Utils.log(String.format("Cannot reset BB course, not mapped yet. ID: %s, Title: %s\n", bbCourse.getId().toExternalString(), bbCourse.getTitle()));
            success = false;
        }
        else
        {
            // Before blowing away the data, get the folder list.
            Folder[] courseFolders = getFolders();

            Utils.log(String.format("Resetting BB course, ID: %s, Title: %s, Old Server: %s, Old Panopto IDs: %s\n, Old folders count: %s\n", bbCourse.getId().toExternalString(), bbCourse.getTitle(), serverName, Utils.encodeArrayOfStrings(sessionGroupPublicIDs), courseFolders.length));

            // In the set registry entry function, we delete existing entries and only create new ones if the value is not null.
            setCourseRegistryEntry(hostnameRegistryKey, null);
            setCourseRegistryEntry(originalContextRegistryKey, null);
            setCourseRegistryEntries(sessionGroupIDRegistryKey, null);
            setCourseRegistryEntries(sessionGroupDisplayNameRegistryKey, null);

            // If there are empty Panopto folders for this course, then delete it so we don't trail empty unused folders.
            // This also reduces provisioning errors when a folder already exists.
            if (courseFolders.length < 0)
            {
                ArrayList<String> foldersToDelete = new ArrayList<String>(courseFolders.length);
                for (int idx = 0; idx < courseFolders.length; idx++)
                {
                    Session[] sessionsInFolder = this.getSessions(courseFolders[idx].getId());
                    if ((sessionsInFolder == null) || (sessionsInFolder.length == 0))
                    {
                        foldersToDelete.add(courseFolders[idx].getId());
                    }
                }

                // Batch delete all empty folders to reduce the API calls made.
                AuthenticationInfo auth = new AuthenticationInfo(apiUserAuthCode, null, apiUserKey);
                sessionManagement.deleteFolders(auth, foldersToDelete.toArray(new String[foldersToDelete.size()]));
            }
        }
        return success;
    }

    // Re-provisions the course with the current settings. If it has never been provisioned before a new folder will be created
    public boolean reprovisionCourse()
    {
        return reprovisionCourse(sessionGroupPublicIDs);
    }

    public boolean provisionCourse(String serverName)
    {
        updateServerName(serverName);
        setCourseRegistryEntry(hostnameRegistryKey, serverName);
        setCourseRegistryEntry(originalContextRegistryKey, bbCourse.getId().toExternalString());

        try
        {
            String externalCourseId = Utils.decorateBlackboardCourseID(bbCourse.getId().toExternalString());
            String fullName = bbCourse.getCourseId() + ": " + bbCourse.getTitle();

            // Provision the course
            AuthenticationInfo auth = new AuthenticationInfo(apiUserAuthCode, null, apiUserKey);
            Folder[] folders = new Folder[] { getPanoptoSessionManagementSOAPService(serverName).provisionExternalCourse(auth, fullName, externalCourseId) };
            updateCourseFolders(folders);

            //Add menu item if setting is enabled
            if(Utils.pluginSettings.getInsertLinkOnProvision())
            {
                addCourseMenuLink();
            }
        }
        catch(Exception e)
        {
            Utils.log(e, String.format("Error provisioning course (id: %s, server: %s, user: %s).", bbCourse.getId().toExternalString(), serverName, bbUserName));
            return false;
        }

        return true;
    }

    // Called after provision or reprovision to update the local store of folder metadata
    private void updateCourseFolders(Folder[] folders)
    {
        setCourseRegistryEntry(hostnameRegistryKey, serverName);

        // First sort the folders.
        ArrayList<Folder> sortedFolders = new ArrayList<Folder>();
        sortedFolders.addAll(Arrays.asList(folders));
        Collections.sort(sortedFolders, new FolderComparator());

        // Now construct the list of ids and the list of names
        this.sessionGroupPublicIDs = new String[folders.length];
        this.sessionGroupDisplayNames = new String[folders.length];
        for (int i = 0; i < folders.length; i++)
        {
            // These are GUIDs and will never be too long
            sessionGroupPublicIDs[i] = sortedFolders.get(i).getId();

            // Display names might go past the 255 Blackboard limit so we elide
            // the string to be no more than 255 characters
            sessionGroupDisplayNames[i] = Utils.elideMiddle(sortedFolders.get(i).getName(), 100, 255);
        }

        // Save the new list of folders back into the registry
        Utils.log(String.format("Provisioned BB course, ID: %s, Title: %s, Server: %s, Panopto ID: %s\n", bbCourse.getId().toExternalString(), bbCourse.getTitle(), serverName, Utils.encodeArrayOfStrings(sessionGroupPublicIDs)));
        setCourseRegistryEntries(sessionGroupIDRegistryKey, sessionGroupPublicIDs);
        setCourseRegistryEntries(sessionGroupDisplayNameRegistryKey, sessionGroupDisplayNames);
    }

    public static boolean HasPanoptoServer(Course bbCourse)
    {
        return getCourseRegistryEntry(bbCourse.getId(), hostnameRegistryKey) != null;
    }

    public static List<Course> GetAllCourses()
    {
        BbPersistenceManager bbPm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();

        try
        {
            CourseDbLoader courseLoader = (CourseDbLoader) bbPm.getLoader(CourseDbLoader.TYPE);
            return courseLoader.loadAllCourses();
        }
        catch(Exception e)
        {
            Utils.log(e, "Error getting all courses.");
        }

        return null;
    }

    // Gets all the members of the course from Blackboard
    private static List<CourseMembership> getCourseMemberships(Course bbCourse)
    {
        BbPersistenceManager bbPm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();

        // Get the course membership (instructors, students, etc.)
        List<CourseMembership> courseMemberships = null;
        try
        {
            CourseMembershipDbLoader courseMembershipLoader = (CourseMembershipDbLoader) bbPm.getLoader(CourseMembershipDbLoader.TYPE);

            courseMemberships = courseMembershipLoader.loadByCourseId(bbCourse.getId(), null, true);
        }
        catch(Exception e)
        {
            Utils.log(e, String.format("Error getting course membership (course ID: %s).", bbCourse.getId()));
        }

        return courseMemberships;
    }

    // Gets all the members of the course from Blackboard
    private static List<CourseMembership> getCourseMembershipsByRole(Course bbCourse, CourseMembership.Role role)
    {
        BbPersistenceManager bbPm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();

        // Get the course membership (instructors, students, etc.)
        List<CourseMembership> courseMemberships = null;
        try
        {
            CourseMembershipDbLoader courseMembershipLoader = (CourseMembershipDbLoader) bbPm.getLoader(CourseMembershipDbLoader.TYPE);

            courseMemberships = courseMembershipLoader.loadByCourseIdAndRole(bbCourse.getId(), role, null, true);
        }
        catch(Exception e)
        {
            Utils.log(e, String.format("Error getting course membership (course ID: %s).", bbCourse.getId()));
        }

        return courseMemberships;
    }

    // Gets the user key of all the students of the course
    public List<String> getTAs()
    {
        ArrayList<String> lstTAs = new ArrayList<String>();

        // Get the course membership (instructors, students, etc.)
        List<CourseMembership> courseMemberships = getCourseMemberships(bbCourse);

        List<CourseMembership> TACourseMemberships = new ArrayList<CourseMembership>();
        for(CourseMembership membership: courseMemberships)
        {

            blackboard.data.course.CourseMembership.Role membershipRole = membership.getRole();
            if (isTARole(membershipRole))
            {
                TACourseMemberships.add(membership);
            }
        }
        if(!TACourseMemberships.isEmpty())
        {

            for(Object membershipObject : TACourseMemberships)
            {
                CourseMembership courseMembership = (CourseMembership)membershipObject;
                User courseUser = courseMembership.getUser();
                if(courseUser != null)
                {
                    String courseUserKey = Utils.decorateBlackboardUserName(courseUser.getUserName());
                    lstTAs.add(courseUserKey);
                }
            }
        }
        return lstTAs;
    }

    // Gets the user key of all the students of the course
    public List<String> getStudents()
    {
        ArrayList<String> lstStudents = new ArrayList<String>();

        // Get the course membership (instructors, students, etc.)
        List<CourseMembership> courseMemberships = getCourseMemberships(bbCourse);

        List<CourseMembership> studentCourseMemberships = new ArrayList<CourseMembership>();
        for(CourseMembership membership: courseMemberships)
        {

            blackboard.data.course.CourseMembership.Role membershipRole = membership.getRole();
            if (isStudentRole(membershipRole))
            {
                studentCourseMemberships.add(membership);
            }
        }
        if(!studentCourseMemberships.isEmpty())
        {

            for(Object membershipObject : studentCourseMemberships)
            {
                CourseMembership courseMembership = (CourseMembership)membershipObject;
                User courseUser = courseMembership.getUser();
                if(courseUser != null)
                {
                    String courseUserKey = Utils.decorateBlackboardUserName(courseUser.getUserName());

                    lstStudents.add(courseUserKey);
                }
            }
        }
        return lstStudents;
    }

    // Gets info about all the instructors of the course
    public List<String> getInstructors()
    {
        ArrayList<String> lstInstructors = new ArrayList<String>();

        // Get the course membership (instructors, students, etc.)
        List<CourseMembership> courseMemberships = getCourseMemberships(bbCourse);

        List<CourseMembership> instructorCourseMemberships = new ArrayList<CourseMembership>();
        for(CourseMembership membership: courseMemberships)
        {
            blackboard.data.course.CourseMembership.Role membershipRole = membership.getRole();
            if (isInstructorRole(membershipRole))
            {
                instructorCourseMemberships.add(membership);
            }

        }

        if(!instructorCourseMemberships.isEmpty())
        {
            for(Object membershipObject : instructorCourseMemberships)
            {
                CourseMembership courseMembership = (CourseMembership)membershipObject;
                User courseUser = courseMembership.getUser();
                if(courseUser != null)
                {
                    String courseUserKey = Utils.decorateBlackboardUserName(courseUser.getUserName());
                    lstInstructors.add(courseUserKey);
                }
            }
        }
        return lstInstructors;
    }

    /*Returns true if role should be treated as an Instructor. Instructors get creator access in Panopto.*/
    private static boolean isInstructorRole(
            blackboard.data.course.CourseMembership.Role membershipRole)
    {
        //Role is instructor role if it is the 'Instructor' or 'Course Builder' built in blackboard role,
        //or if it is any role marked with the 'Act As Instructor' flag.
        return membershipRole.equals(CourseMembership.Role.INSTRUCTOR)
                || membershipRole.equals(CourseMembership.Role.COURSE_BUILDER)
                || membershipRole.getDbRole().isActAsInstructor();
    }

    /*Returns true if role should be treated as a Student. Students get viewer access in Panopto.*/
    private static boolean isStudentRole(
            blackboard.data.course.CourseMembership.Role membershipRole)
    {
        //Role is student role if it is not a built in instructor role or a custom role.
        return    !isInstructorRole(membershipRole)
               && !isTARole(membershipRole);
    }

    /*Returns true if role should be treated as a TA. TA's get viewer access in Panopto, unless otherwise specified in the
       Blackboard block settings. Any custom blackboard roles are treated as TAs, unless they are marked with the 'Act As Instructor' flag.*/
    private static boolean isTARole(
            blackboard.data.course.CourseMembership.Role membershipRole)
    {
        //Role is a TA role if it is the 'Teaching Assistant' built in blackboard role
        //or if it is a custom role without the 'Act as Instructor' flag.
        CourseRole dbRole = membershipRole.getDbRole();
        return membershipRole.equals(Role.TEACHING_ASSISTANT)
                ||  (      dbRole.isRemovable()
                     &&   !dbRole.isActAsInstructor());
    }

    public boolean userMayProvision()
    {
        if(Utils.pluginSettings.getAdminProvisionOnly()){
            return Utils.userCanConfigureSystem();
        }
        else{
            // Admins may provision any course. Instructors may provision their own course if the setting is enabled
            return Utils.userCanConfigureSystem() || (IsInstructor() && Utils.pluginSettings.getInstructorsCanProvision());
        }
    }

    //Check for whether a user may add course menu links.
    //This may be done by Admins and instructors at any time
    //or by TAs if setting is checked
    public boolean userMayAddLinks() {
        //Admins can add links to any course. Instructors can add links to their own courses
        return Utils.userCanConfigureSystem()
                || this.canAddLinks();
    }

    public boolean userMayConfig() {
        // Admins may config any course. Instructors may configure their own
        // courses
        return Utils.userCanConfigureSystem()
                || this.IsInstructor();
    }

    public boolean userMayCreateFolder()
    {
        // Admins may always create folders. Instructors may if the setting is enabled
        return Utils.userCanConfigureSystem() || (this.IsInstructor() && Utils.pluginSettings.getInstructorsCanCreateFolder());
    }

    // Returns true if the specified user is an instructor of the specified course
    //If checkTACanCreateLinks is true, a check will be performed if either getGrantTAProvision
    //or getTAsCanCreateLinks returns true. This is used when checking if TAs may add course menu links
    public static boolean isUserInstructor(Id bbCourseId, String bbUserName, boolean checkTACanCreateLinks)
    {
        BbPersistenceManager bbPm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();

        try
        {
            //Get user's blackboard ID from their username
            UserDbLoader userLoader = (UserDbLoader)bbPm.getLoader(UserDbLoader.TYPE);
            User user = userLoader.loadByUserName(bbUserName);
            Id bbUserId = user.getId();

            //Load the user's membership in the current course to get their role
            CourseMembershipDbLoader membershipLoader = (CourseMembershipDbLoader)bbPm.getLoader(CourseMembershipDbLoader.TYPE);
            CourseMembership usersCourseMembership = membershipLoader.loadByCourseAndUserId(bbCourseId, bbUserId);
            Role userRole = usersCourseMembership.getRole();

            if (isInstructorRole(userRole))
            {
                return true;
            }

            //If settings are configured to treat TAs as an instructor, and the user is either a Teaching Assistant or has a custom role, return true.
            else if(    Utils.pluginSettings.getGrantTAProvision()
                    || (    checkTACanCreateLinks
                         && Utils.pluginSettings.getTAsCanCreateLinks()))
            {
                if(isTARole(userRole))
                {
                    return true;
                }
            }
        }
        catch(Exception e)
        {
            Utils.log(e, String.format("Error getting user's course membership (course ID: %s, userName: %s).", bbCourseId, bbUserName));
        }

        //User is not an instructor
        return false;
    }

    // Returns true if the specified user can add a course menu link. Nearly identical to
    //isUserInstructor but includes a check for TAsCanCreateLinks
    public static boolean canUserAddLinks(Id bbCourseId, String bbUserName) {
        return isUserInstructor(bbCourseId, bbUserName, true);
    }

    private static IAccessManagement getPanoptoAccessManagementSOAPService(String serverName)
    {
        IAccessManagement port = null;

        try
        {
            URL SOAP_URL = new URL("http://" + serverName + "/Panopto/PublicAPI/4.6/AccessManagement.svc");

            // Connect to the SessionManagement SOAP service on the specified Panopto server
            AccessManagementLocator service = new AccessManagementLocator();
            port = (IAccessManagement) service.getBasicHttpBinding_IAccessManagement(SOAP_URL);
        }
        catch(Exception e)
        {
            Utils.log(e, String.format("Error getting Access Management SOAP service (server: %s).", serverName));
        }

        return port;
    }

    private static ISessionManagement getPanoptoSessionManagementSOAPService(String serverName)
    {
        ISessionManagement port = null;

        try
        {
            URL SOAP_URL = new URL("http://" + serverName + "/Panopto/PublicAPI/4.6/SessionManagement.svc");

            // Connect to the SessionManagement SOAP service on the specified Panopto server
            SessionManagementLocator service = new SessionManagementLocator();
            port = (ISessionManagement) service.getBasicHttpBinding_ISessionManagement(SOAP_URL);
        }
        catch(Exception e)
        {
            Utils.log(e, String.format("Error getting Session Management SOAP service (server: %s).", serverName));
        }

        return port;
    }

    private static IUserManagement getPanoptoUserManagementSOAPService(String serverName)
    {
        IUserManagement port = null;

        try
        {
            URL SOAP_URL = new URL("http://" + serverName + "/Panopto/PublicAPI/4.6/UserManagement.svc");

            // Connect to the UserManagement SOAP service on the specified Panopto server
            UserManagementLocator service = new UserManagementLocator();
            port = (IUserManagement) service.getBasicHttpBinding_IUserManagement(SOAP_URL);
        }
        catch(Exception e)
        {
            Utils.log(e, String.format("Error getting User Management SOAP service (server: %s).", serverName));
        }

        return port;
    }
    
    private static IAuth getPanoptoAuthSOAPService(String serverName)
    {
        IAuth port = null;

        try
        {
            URL SOAP_URL = new URL("http://" + serverName + "/Panopto/PublicAPI/4.6/Auth.svc");

            // Connect to the UserManagement SOAP service on the specified Panopto server
            AuthLocator service = new AuthLocator();
            port = (IAuth) service.getBasicHttpBinding_IAuth(SOAP_URL);
        }
        catch(Exception e)
        {
            Utils.log(e, String.format("Error getting Auth SOAP service (server: %s).", serverName));
        }

        return port;
    }

    // Instance method just calls out to static method below
    private String getCourseRegistryEntry(String key)
    {
        return getCourseRegistryEntry(bbCourse.getId(), key);
    }

    // Instance method just calls out to static method below
    private void setCourseRegistryEntry(String key, String value)
    {
        setCourseRegistryEntry(bbCourse.getId(), key, value);
    }

    private static String getCourseRegistryEntry(Id courseId, String key)
    {
        String value = null;

        try
        {
            CourseRegistryEntryDbLoader creLoader = CourseRegistryEntryDbLoader.Default.getInstance();
            Registry registry = creLoader.loadRegistryByCourseId(courseId);

            value = registry.getValue(key);
        }
        catch (Exception e)
        {
            Utils.log(e, String.format("Error getting course registry entry (key: %s).", key));
        }

        return value;
    }

    // Blackboard DB values have a limited length so we store each string entry as its
    // own value with a number-post-fixed key. Here we have to handle legacy values
    // that might be stored with just the key name.
    // Assumption: only called for keys previously stored with setCourseRegistryEntries
    // or the old method with double-quoted, comma-separated values.
    private String[] getCourseRegistryEntries(String key)
    {
        String[] values = null;
        try
        {
            Utils.logVerbose("Getting CourseRegistry for course: " + bbCourse.getId().toExternalString());

            CourseRegistryEntryDbLoader creLoader = CourseRegistryEntryDbLoader.Default.getInstance();
            Registry registry = creLoader.loadRegistryByCourseId(bbCourse.getId());

            String value = registry.getValue(key);

            // If there's a value for the plain key, we are dealing with a legacy value
            // which should be in the "","","" format. We decode the string and return
            // the individual values
            if (value != null)
            {
                values = Utils.decodeArrayOfStrings(value);
            }
            // If no value, then we are dealing with the new case where we split
            // values up into individual keys so look each of those up until we
            // stop finding them
            else
            {
                // Use an ArrayList because we don't know ahead of time how many
                // we may have. This is Ok for small numbers (e.g., less than
                // 100).
                ArrayList<String> list = new ArrayList<String>();

                String tempValue = registry.getValue(key + list.size());

                while (tempValue != null)
                {
                    list.add(tempValue);
                    tempValue = registry.getValue(key + list.size());
                }

                // If there were no values at all then 'value' will remain null
                // as expected
                if (list.size() > 0)
                {
                    values = new String[list.size()];
                    list.toArray(values);
                }
            }
        }
        catch (Exception e)
        {
            Utils.log(e, String.format("Error getting course registry entry (key: %s).", key));
        }

        return values;
    }

    // The Blackboard course registry stores key/values pairs per-course.
    // Static setter enables us to store registry values for newly-provisioned
    // courses without the cost of instantiating and populating an object for each one.
    private static void setCourseRegistryEntry(Id courseId, String key, String value)
    {
        try
        {
            CourseRegistryEntryDbPersister crePersister = CourseRegistryEntryDbPersister.Default.getInstance();

            DeleteKeyForCourse(crePersister, key, courseId);

            if (value != null)
            {
                CourseRegistryEntry cre = new CourseRegistryEntry(key, value);
                cre.setCourseId(courseId);
                crePersister.persist(cre);
            }
        }
        catch (Exception e)
        {
            Utils.log(e, String.format("Error setting course registry entry (course ID: %s, key: %s, value: %s).", courseId.toExternalString(), key, value));
        }
    }

    // Blackboard DB values have a limited length so we store each string entry
    // as its own value with a number-post-fixed key.
    private void setCourseRegistryEntries(String key, String[] values)
    {
        try
        {
            Utils.logVerbose("Setting CourseRegistry for course: " + bbCourse.getId().toExternalString());

            CourseRegistryEntryDbPersister crePersister = CourseRegistryEntryDbPersister.Default.getInstance();
            CourseRegistryEntryDbLoader creLoader = CourseRegistryEntryDbLoader.Default.getInstance();
            Registry registry = creLoader.loadRegistryByCourseId(bbCourse.getId());

            // Delete any legacy key still hanging around
            Utils.logVerbose("Checking for legacy key " + key);
            RegistryEntry entry = registry.getEntry(key);
            if (entry != null)
            {
                Utils.logVerbose("Deleting legacy key " + key);
                DeleteKeyForCourse(crePersister, key, bbCourse.getId());
            }

            // Now delete any per-folder entries that may exist.  We don't know how many
            // so we just start at 0 and keep looking until we find a missing one.
            int j = 0;
            String keyToDelete = key + j;
            entry = registry.getEntry(keyToDelete);
            Utils.logVerbose("Checking for key " + keyToDelete);
            while (entry != null)
            {
                Utils.logVerbose("Deleting key " + keyToDelete);
                DeleteKeyForCourse(crePersister, keyToDelete, bbCourse.getId());

                j++;
                keyToDelete = key + j;
                Utils.logVerbose("Checking for key " + keyToDelete);
                entry = registry.getEntry(keyToDelete);
            }

            // If we have values, save them each in their own key
            if (values != null)
            {
                Utils.logVerbose("Adding new values - count: " + values.length);
                for (int i = 0; i < values.length; i++)
                {
                    Utils.logVerbose("Adding new key: " + key + i + " value: "+  values[i]);
                    CourseRegistryEntry cre = new CourseRegistryEntry(key + i, values[i]);
                    cre.setCourseId(bbCourse.getId());
                    crePersister.persist(cre);
                }
            }
        }
        catch (Exception e)
        {
            Utils.log(e, String.format("Error setting course registry entry (course ID: %s, key: %s, value: %s).", bbCourse.getId().toExternalString(), key, Utils.encodeArrayOfStrings(values)));
        }
    }


    // Helper method that just makes 6 lines into one in the calling method
    private static void DeleteKeyForCourse(CourseRegistryEntryDbPersister crePersister, String keyToDelete, Id courseId)
    {
        try
        {
            crePersister.deleteByKeyAndCourseId(keyToDelete, courseId);
        }
        catch (KeyNotFoundException knfe) {}
        catch (PersistenceException pe) {}
    }


    private PanoptoVersion getServerVersion() {
        //Generate AuthenticationInfo for making call to Auth to get server info.
        AuthenticationInfo auth = new AuthenticationInfo(apiUserAuthCode, null, apiUserKey);
        
        IAuth iAuth = getPanoptoAuthSOAPService(serverName);
        return PanoptoVersion.fetchOrEmpty(iAuth);
    }

    private void addCourseMenuLink() throws ValidationException, PersistenceException
    {
        Id cid;
        cid = bbCourse.getId();

        //Get list of page's current menu links
        List<CourseToc> courseTocList = CourseTocDbLoader.Default.getInstance().loadByCourseId(cid);

        //Iterate through each link and check if it's text matches the text of the item to be created
        boolean linkExists = false;
        while(courseTocList.iterator().hasNext() && linkExists == false)
        {
            CourseToc ct = courseTocList.iterator().next();

            //If the text matches, set linkExists to true so we don't add a duplicate link
            linkExists = ct.getLabel().equals(Utils.pluginSettings.getMenuLinkText());

            //BBList's iterator doesn't support remove(), so we have to remove manually by ID
            courseTocList.remove(ct);
        }

        //If link with desired text doesn't exists, create a new one
        if(!linkExists)
        {
            //Generate Schema independent URL for plugin location
            String URIRoot = PlugInUtil.getUri("ppto", "PanoptoCourseTool", "Content.jsp");
            CourseToc panLink = new CourseToc();
            panLink.setCourseId(cid);
            panLink.setTargetType(CourseToc.Target.URL);
            panLink.setLabel(Utils.pluginSettings.getMenuLinkText());
            panLink.setLaunchInNewWindow(false);
            panLink.setUrl(URIRoot + "?course_id=" + cid.toExternalString());
            CourseTocDbPersister.Default.getInstance().persist(panLink);
        }
    }

    //Enum types returned by addBlackboardContentItem, indicating whether a Panopto link has been successfully added to a course.
    public static enum LinkAddedResult{
        SUCCESS, //Link was added successfully.
        NOTCREATOR, //Link was not added because the session is not available and the user does not have creator access in order to make it available.
        NOTPUBLISHER, //Link was not added because session requires publisher approval.
        FAILURE; //Link was not added for an unspecified reason.
    }
}
