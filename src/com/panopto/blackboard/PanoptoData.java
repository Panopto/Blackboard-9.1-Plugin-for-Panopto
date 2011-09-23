package com.panopto.blackboard;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import blackboard.base.BbList;
import blackboard.base.FormattedText;
import blackboard.data.content.Content;
import blackboard.data.course.Course;
import blackboard.data.course.CourseMembership;
import blackboard.data.course.CourseMembership.Role;
import blackboard.data.registry.CourseRegistryEntry;
import blackboard.data.registry.Registry;
import blackboard.data.user.User;
import blackboard.persist.BbPersistenceManager;
import blackboard.persist.Id;
import blackboard.persist.KeyNotFoundException;
import blackboard.persist.PersistenceException;
import blackboard.persist.content.ContentDbPersister;
import blackboard.persist.course.CourseCourseDbLoader;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.persist.registry.CourseRegistryEntryDbLoader;
import blackboard.persist.registry.CourseRegistryEntryDbPersister;
import blackboard.persist.user.UserDbLoader;
import blackboard.platform.context.Context;
import blackboard.platform.log.Log;
import blackboard.platform.persistence.PersistenceServiceFactory;
import com.panopto.services.*;

// Wrap interaction with DB and Panopto SOAP services for a particular Blackboard course
public class PanoptoData
{
	// Keys for Blackboard course registry settings
	private static final String hostnameRegistryKey = "CourseCast_Hostname";
	private static final String sessionGroupIDRegistryKey = "CourseCast_SessionGroupID";
	private static final String sessionGroupDisplayNameRegistryKey = "CourseCast_SessionGroupDisplayName";

	// Blackboard course we are associating with
	private Course bbCourse;
	// Blackboard username of currently logged in user
	private String bbUserName;
	
	private boolean isInstructor = false;
	
	// Panopto server to talk to
	private String serverName;

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
		this.isInstructor = PanoptoData.isUserInstructor(this.bbCourse.getId(), this.bbUserName);
		
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
		
		sessionGroupPublicIDs = Utils.decodeArrayOfStrings(getCourseRegistryEntry(sessionGroupIDRegistryKey));
		sessionGroupDisplayNames = Utils.decodeArrayOfStrings(getCourseRegistryEntry(sessionGroupDisplayNameRegistryKey));
		
		// Check that the list of Ids and names are valid. They must both be the same length
		if ((sessionGroupPublicIDs == null && sessionGroupDisplayNames != null)
				|| (sessionGroupPublicIDs != null && sessionGroupDisplayNames == null)
				|| (sessionGroupPublicIDs != null && sessionGroupPublicIDs.length != sessionGroupDisplayNames.length))
		{
			Utils.log(String.format("Invalid course registry settings. Reseting both to null. sessionGroupPublicIDs = %s          sessionGroupDisplayNames = %s", 
					getCourseRegistryEntry(sessionGroupIDRegistryKey), 
					getCourseRegistryEntry(sessionGroupDisplayNameRegistryKey)));
			
			this.sessionGroupPublicIDs = null;
			this.sessionGroupDisplayNames = null;
			setCourseRegistryEntry(hostnameRegistryKey, null);
			setCourseRegistryEntry(sessionGroupIDRegistryKey, null);
			setCourseRegistryEntry(sessionGroupDisplayNameRegistryKey, null);
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
		}
		else
		{
			apiUserKey = null;
			apiUserAuthCode = null;
			sessionManagement = null;
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
	
	///////////////////////////////////////////////////////////////////////////////////////////
	///// The following "get..." functions are just wrappers for the relevant SOAP calls, /////
	///// using stored course mapping and credentials.                                    /////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	// Gets all the sessions in a folder from the Panopto server. Returns null on error.
	public Session[] getSessions(String folderId)
	{
		try
		{
			AuthenticationInfo auth = new AuthenticationInfo(apiUserAuthCode, null, apiUserKey);
			ListSessionsRequest request = new ListSessionsRequest();
			request.setFolderId(folderId);
			request.setPagination(new Pagination(Integer.MAX_VALUE, 0));
			request.setSortBy(SessionSortField.Date);
			request.setSortIncreasing(true);
			request.setStates(new SessionState[] { SessionState.Broadcasting, SessionState.Complete, SessionState.Recording });
			ListSessionsResponse response = sessionManagement.getSessionsList(auth, request, null);
			Session[] retVal = response.getResults();
			if (retVal == null)
			{
				retVal = new Session[0];
			}
			return retVal; 
		}
		catch(Exception e)
		{
			Utils.log(e, String.format("Error getting sessions (folder ID: %s, api user: %s).", folderId, apiUserKey));
			return null;
		}
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
							Utils.log(e3, String.format("Error getting folder(folder ID: %s, api user: %s).", sessionGroupPublicIDs[i], apiUserKey));
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
		// TODO: this is three soap calls. Should I try to improve it? Right now it is only called by course_config and these are the only calls that page makes.
		try
		{
			// First sync the user so his memberships will be up to date
			syncUser(serverName, bbUserName);
			
			// Next get the user's access details
			AuthenticationInfo auth = new AuthenticationInfo(apiUserAuthCode, null, apiUserKey);
			HashSet<String> foldersWithCreatorAccess = new HashSet<String>();
			UserAccessDetails access = getPanoptoAccessManagementSOAPService(serverName).getSelfUserAccessDetails(auth);
			foldersWithCreatorAccess.addAll(Arrays.asList(access.getFoldersWithCreatorAccess()));
			for (GroupAccessDetails group : access.getGroupMembershipAccess())
			{
				foldersWithCreatorAccess.addAll(Arrays.asList(group.getFoldersWithCreatorAccess()));
			}
			
			// Finally get folder details
			return sessionManagement.getFoldersById(auth, foldersWithCreatorAccess.toArray(new String[0]));
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
			ListFoldersRequest request = new ListFoldersRequest();
			request.setPublicOnly(true);
			request.setPagination(new Pagination(Integer.MAX_VALUE, 0));
			ListFoldersResponse response = getPanoptoSessionManagementSOAPService(serverName).getFoldersList(auth, request, null);
			
			return response.getResults();
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
		StringBuffer result = new StringBuffer();
		if (sessionGroupDisplayNames != null)
		{
			for (int i = 0; i < sessionGroupDisplayNames.length; i++)
			{
				result.append("<option");
				result.append(" value='" + sessionGroupPublicIDs[i] + "'");
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
		StringBuffer result = new StringBuffer();

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
				result.append(" value='" + folder.getId() + "'");
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
		StringBuffer result = new StringBuffer();

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
			boolean useOptionaGroups = sessionGroupDisplayNames != null && sessionGroupDisplayNames.length > 0 
										&& publicFolders != null && publicFolders.length > 0;
			
			// Add all the mapped folders
			if (sessionGroupDisplayNames != null && sessionGroupDisplayNames.length > 0)
			{
				if (useOptionaGroups)
				{
					result.append("<optgroup label='Mapped Folders'>\n");
				}
		    	for (int i = 0; i < sessionGroupDisplayNames.length; i++)
				{
					String strDisplayName = Utils.escapeHTML(sessionGroupDisplayNames[i]);
					String strID = sessionGroupPublicIDs[i];
					
					result.append("<option");
					result.append(" value='" + strID + "'");
					if(strID.equals(folderId))
					{
						result.append(" SELECTED");
					}
					result.append(">");
			 		result.append(strDisplayName);
			 		result.append("</option>\n");
				}
		    	if (useOptionaGroups)
		    	{
		    		result.append("</optgroup>\n");
		    	}
			}
			
			// Add all the public folders
			if (publicFolders != null && publicFolders.length > 0)
			{
				if (useOptionaGroups)
				{
					result.append("<optgroup label='Public Folders'>\n");
				}
		    	for (int i = 0; i < publicFolders.length; i++)
				{
					String strDisplayName = Utils.escapeHTML(publicFolders[i].getName());
					String strID = publicFolders[i].getId();
					
					result.append("<option");
					result.append(" value='" + strID + "'");
					if(strID.equals(folderId))
					{
						result.append(" SELECTED");
					}
					result.append(">");
			 		result.append(strDisplayName);
			 		result.append("</option>\n");
				}
		    	if (useOptionaGroups)
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
		StringBuffer result = new StringBuffer();
		
		if (folderID == null || folderID == "")
		{
			result.append("<option value=''>-- Please select a folder first --</option>\n");
		}
		else
		{
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
						result.append(" value='" + session.getViewerUrl() + "'");
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
	
	// Insert a content item in the current course with a link to the specified delivery.
	public Content addBlackboardContentItem(String content_id, String lectureUrl, String title, String description)
	{
		Content content = null;
		try
		{
			// retrieve the Db persistence manager from the persistence service
			BbPersistenceManager bbPm = PersistenceServiceFactory.getInstance().getDbPersistenceManager(); 
			
			// create a course document and set all desired attributes
			content = new Content();
			content.setTitle(title);
			FormattedText text = new FormattedText(description, FormattedText.Type.HTML);
			content.setBody(text);
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
		catch(Exception e)
		{
			Utils.log(e, String.format("Error adding content item (content ID: %s, lecture Url: %s, title: %s, description: %s).", content_id, lectureUrl, title, description));
		}
		
		return content;
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
			
			// We will lump together instructors and course_builders. They both get creator access 
			CourseDbLoader courseLoader = (CourseDbLoader) bbPm.getLoader(CourseDbLoader.TYPE);
			List<Course> instructorCourses = courseLoader.loadByUserIdAndCourseMembershipRole(bbUserId, Role.INSTRUCTOR);
			instructorCourses.addAll(courseLoader.loadByUserIdAndCourseMembershipRole(bbUserId, Role.COURSE_BUILDER));

			// Students, guests, and graders all get viewer access
			List<Course> studentCourses = courseLoader.loadByUserIdAndCourseMembershipRole(bbUserId, Role.STUDENT);
			studentCourses.addAll(courseLoader.loadByUserIdAndCourseMembershipRole(bbUserId, Role.GUEST));
			studentCourses.addAll(courseLoader.loadByUserIdAndCourseMembershipRole(bbUserId, Role.GRADER));

			// TAs get access controlled by the setting in the config page
			List<Course> taCourses = courseLoader.loadByUserIdAndCourseMembershipRole(bbUserId, Role.TEACHING_ASSISTANT);

			ArrayList<String> externalGroupIds = new ArrayList<String>();
			for(Course course : studentCourses)
			{
				Id courseId = course.getId();
				String courseServerName = getCourseRegistryEntry(courseId, hostnameRegistryKey);
				if (courseServerName != null && courseServerName.equalsIgnoreCase(serverName))
				{
					externalGroupIds.add(Utils.decorateBlackboardCourseID(courseId.toExternalString()) + "_viewers");
				}
			}
			for(Course course : instructorCourses)
			{
				Id courseId = course.getId();
				String courseServerName = getCourseRegistryEntry(courseId, hostnameRegistryKey);
				if (courseServerName != null && courseServerName.equalsIgnoreCase(serverName))
				{
					externalGroupIds.add(Utils.decorateBlackboardCourseID(courseId.toExternalString()) + "_creators");
				}
			}
			for(Course course : taCourses)
			{
				Id courseId = course.getId();
				String courseServerName = getCourseRegistryEntry(courseId, hostnameRegistryKey);
				if (courseServerName != null && courseServerName.equalsIgnoreCase(serverName))
				{
					if (Utils.pluginSettings.getGrantTACreator())
					{
						externalGroupIds.add(Utils.decorateBlackboardCourseID(courseId.toExternalString()) + "_creators");
					}
					else
					{
						externalGroupIds.add(Utils.decorateBlackboardCourseID(courseId.toExternalString()) + "_viewers");
					}
				}
			}
		
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

	// Re-provisions the course with the current settings. If it has never been provisioned before a new folder will be created
	public boolean reprovisionCourse()
	{
		return reprovisionCourse(sessionGroupPublicIDs);
	}
	
	public boolean provisionCourse(String serverName)
	{
		updateServerName(serverName);
		setCourseRegistryEntry(hostnameRegistryKey, serverName);

		try
		{
			String externalCourseId = Utils.decorateBlackboardCourseID(bbCourse.getId().toExternalString());
			String fullName = bbCourse.getCourseId() + ": " + bbCourse.getTitle();
			
			// Provision the course
			AuthenticationInfo auth = new AuthenticationInfo(apiUserAuthCode, null, apiUserKey);
			Folder[] folders = new Folder[] { getPanoptoSessionManagementSOAPService(serverName).provisionExternalCourse(auth, fullName, externalCourseId) };
			updateCourseFolders(folders);
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
		// First sort the folders.
		ArrayList<Folder> sortedFolders = new ArrayList<Folder>();
		sortedFolders.addAll(Arrays.asList(folders));
		Collections.sort(sortedFolders, new FolderComparator());

		// Now construct the list of ids and the list of names
		this.sessionGroupPublicIDs = new String[folders.length];
		this.sessionGroupDisplayNames = new String[folders.length];
		for (int i = 0; i < folders.length; i++)
		{
			sessionGroupPublicIDs[i] = sortedFolders.get(i).getId();
			sessionGroupDisplayNames[i] = sortedFolders.get(i).getName();
		}
		
		// Save the new list of folders back into the registry
		String encodedFolderIdList = Utils.encodeArrayOfStrings(sessionGroupPublicIDs);
		Utils.log(String.format("Provisioned BB course, ID: %s, Panopto ID: %s\n", bbCourse.getId().toString(), encodedFolderIdList));
		setCourseRegistryEntry(bbCourse.getId(), sessionGroupIDRegistryKey, encodedFolderIdList);
		setCourseRegistryEntry(bbCourse.getId(), sessionGroupDisplayNameRegistryKey, Utils.encodeArrayOfStrings(sessionGroupDisplayNames));
	}
		
	public static boolean HasPanoptoServer(Course bbCourse)
	{
		return getCourseRegistryEntry(bbCourse.getId(), hostnameRegistryKey) != null;
	}
	
	public static BbList<Course> GetAllCourses()
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
	
	// Gets the user key of all the students of the course
	public List<String> getStudentUserKeys()
	{
		// Get the course membership (instructors, students, etc.)
		List<CourseMembership> courseMemberships = getCourseMemberships(bbCourse);
		
		if(courseMemberships != null)
		{
			ArrayList<String> lstStudents = new ArrayList<String>();
			
			for(Object membershipObject : courseMemberships.toArray())
			{
				CourseMembership courseMembership = (CourseMembership)membershipObject;
				CourseMembership.Role role = courseMembership.getRole();

				if(role == CourseMembership.Role.STUDENT)
				{
					User courseUser = courseMembership.getUser();
					String courseUserKey = Utils.decorateBlackboardUserName(courseUser.getUserName());
					lstStudents.add(courseUserKey);
				}
			}
			
			return lstStudents;
		}
		
		return null;
	}
	
	public boolean userMayProvision()
	{
		// Admins may provision any course. Instructors may provision their own course if the setting is enabled
		return Utils.userCanConfigureSystem() || (IsInstructor() && Utils.pluginSettings.getInstructorsCanProvision());
	}
	
	public boolean userMayConfig()
	{
		// Admins may config any course. Instructors may configure their own courses
		return Utils.userCanConfigureSystem() || this.IsInstructor();
	}
	
	public boolean userMayCreateFolder()
	{
		// Admins may always create folders. Instructors may if the setting is enabled
		return Utils.userCanConfigureSystem() || (this.IsInstructor() && Utils.pluginSettings.getInstructorsCanCreateFolder());
	}
	
	// Returns true if the specified user is an instructor of the specified course
	public static boolean isUserInstructor(Id bbCourseId, String bbUserName)
	{
		BbPersistenceManager bbPm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();
		
		try
		{
			UserDbLoader userLoader = (UserDbLoader)bbPm.getLoader(UserDbLoader.TYPE);
			User user = userLoader.loadByUserName(bbUserName);
			Id bbUserId = user.getId();
			CourseDbLoader courseLoader = (CourseDbLoader) bbPm.getLoader(CourseDbLoader.TYPE);
			List<Course> courses = courseLoader.loadByUserIdAndCourseMembershipRole(bbUserId, Role.INSTRUCTOR);

			for (Course course : courses)
			{
				if (course.getId().equals(bbCourseId))
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			Utils.log(e, String.format("Error getting user's course membership (course ID: %s, userName: %s).", bbCourseId, bbUserName));
		}
		
		return false;
	}
	
	// Gets info about all the instructors of the course
	public com.panopto.services.User[] getInstructors()
	{
		// Get the course membership (instructors, students, etc.)
		List<CourseMembership> courseMemberships = getCourseMemberships(bbCourse);
		
		if(courseMemberships != null)
		{
			ArrayList<com.panopto.services.User> lstInstructors = new ArrayList<com.panopto.services.User>();
			
			boolean mailLectureNotifications = Utils.pluginSettings.getMailLectureNotifications();
			
			for(Object membershipObject : courseMemberships.toArray())
			{
				CourseMembership courseMembership = (CourseMembership)membershipObject;
				CourseMembership.Role role = courseMembership.getRole();
				
				if(role == CourseMembership.Role.INSTRUCTOR)
				{
					User courseUser = courseMembership.getUser();
					String courseUserKey = Utils.decorateBlackboardUserName(courseUser.getUserName());
					com.panopto.services.User userInfo = new com.panopto.services.User();
					userInfo.setUserKey(courseUserKey);
					userInfo.setFirstName(courseUser.getGivenName());
					userInfo.setLastName(courseUser.getFamilyName());
					userInfo.setEmail(courseUser.getEmailAddress());
					userInfo.setEmailSessionNotifications(mailLectureNotifications);
					
					lstInstructors.add(userInfo);
				}
			}
			
			return lstInstructors.toArray(new com.panopto.services.User[0]);
		}
		
		return null;
	}
	
	private static IAccessManagement getPanoptoAccessManagementSOAPService(String serverName)
	{
		IAccessManagement port = null;
		
		try
		{
			URL SOAP_URL = new URL("http://" + serverName + "/Panopto/PublicAPI/4.0/AccessManagement.svc");

			// Connect to the SessionManagement SOAP service on the specified Panopto server
			AccessManagement service = new AccessManagementLocator();
			port = service.getBasicHttpBinding_IAccessManagement(SOAP_URL);
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
			URL SOAP_URL = new URL("http://" + serverName + "/Panopto/PublicAPI/4.0/SessionManagement.svc");

			// Connect to the SessionManagement SOAP service on the specified Panopto server
			SessionManagement service = new SessionManagementLocator();
			port = service.getBasicHttpBinding_ISessionManagement(SOAP_URL);
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
			URL SOAP_URL = new URL("http://" + serverName + "/Panopto/PublicAPI/4.0/UserManagement.svc");

			// Connect to the UserManagement SOAP service on the specified Panopto server
			UserManagement service = new UserManagementLocator();
			port = service.getBasicHttpBinding_IUserManagement(SOAP_URL);
		}
		catch(Exception e)
		{
			Utils.log(e, String.format("Error getting User Management SOAP service (server: %s).", serverName));
		}
		
		return port;
	}
	
	// The Blackboard course registry stores key/values pairs per-course.
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
			Registry registry  = creLoader.loadRegistryByCourseId(courseId);

			value = registry.getValue(key);
		}
		catch(Exception e)
		{
			Utils.log(e, String.format("Error getting course registry entry (key: %s).", key));
		}
		
		return value;
	}
	
	// The Blackboard course registry stores key/values pairs per-course.
	// Static setter enables us to store registry values for newly-provisioned courses
	// without the cost of instantiating and populating an object for each one.
	private static void setCourseRegistryEntry(Id courseId, String key, String value)
	{
		try
		{
			CourseRegistryEntryDbPersister crePersister = CourseRegistryEntryDbPersister.Default.getInstance();
			
			try
			{
				crePersister.deleteByKeyAndCourseId(key, courseId);
			}
			catch(KeyNotFoundException knfe) {}
			catch(PersistenceException pe) {}
	
			CourseRegistryEntry cre = new CourseRegistryEntry(key, value);
	        cre.setCourseId(courseId);
	      	crePersister.persist(cre);
		}
		catch(Exception e)
		{
			Utils.log(e, String.format("Error setting course registry entry (course ID: %s, key: %s, value: %s).", courseId.toExternalString(), key, value));
		}
	}

}
