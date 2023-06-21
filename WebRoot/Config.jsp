
<!-- Copyright Panopto 2009 - 2016
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
 */ -->

<%@page language="java" pageEncoding="ISO-8859-1" %>

<%@page import="com.panopto.blackboard.Utils"%>
<%@page import="java.util.*"%>
<%@page import="java.net.URL"%>
<%@page import="java.net.MalformedURLException"%>
<%@page import="blackboard.platform.plugin.PlugInUtil" %>
<%@page import="blackboard.data.course.CourseMembership" %>
<%@page import="blackboard.data.course.CourseMembership.Role" %>



<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@taglib uri="/bbUI" prefix="bbUI" %>
<%@taglib uri="/bbData" prefix="bbData"%>

<bbUI:coursePage>
<bbData:context id="ctx">

<%
String iconUrl = "/images/ci/icons/bookopen_u.gif";
String page_title = "Configure Panopto Connector";

// First check if the caller is allowed to be here
if (!Utils.userCanConfigureSystem()) 
{
%>
    <bbUI:docTemplate title="<%=page_title%>">
    <c:catch>
        <bbUI:receipt type="FAIL" iconUrl="<%=iconUrl%>" title="<%=page_title%>" recallUrl="<%=Utils.buildingBlockManagerURL%>">
            You do not have access to configure the Panopto building block. 
        </bbUI:receipt>
    </c:catch>
    </bbUI:docTemplate>
<%
    return;
}

%>
        <bbUI:docTemplate title="<%= page_title %>">
        <c:catch>
            <bbUI:docTemplateHead>
                <link rel="stylesheet" type="text/css" href="css/main.css" />
            </bbUI:docTemplateHead>
        </c:catch>
<%
    // Scalar settings form submitted, store in settings file.
String instanceName = request.getParameter("instanceName");
String defaultPanoptoServer = request.getParameter("defaultPanoptoServer");
Boolean instructorsCanProvision = (request.getParameter("instructorsCanProvision") != null);
Boolean mailLectureNotifications = (request.getParameter("mailLectureNotifications") != null);
Boolean refreshLogins = (request.getParameter("refreshLogins") != null);
Boolean syncAvailabilityStatus = (request.getParameter("syncAvailabilityStatus") != null);
Boolean redirectToDefaultLogin = (request.getParameter("redirectToDefaultLogin") != null);
Boolean grantTACreator = (request.getParameter("grantTACreator") != null);
Boolean grantTAProvision = (request.getParameter("grantTAProvision") != null);
Boolean TAsCanCreateLinks = (request.getParameter("TAsCanCreateLinks") != null);
Boolean courseResetEnabled = (request.getParameter("courseResetEnabled") != null);
Boolean verbose = (request.getParameter("verbose") != null);
Boolean insertLinkOnProvision = (request.getParameter("insertLinkOnProvision") != null);
Boolean insertLTILinkOnProvision = (request.getParameter("insertLTILinkOnProvision") != null);
Boolean insertFolderLinkOnProvision = (request.getParameter("insertFolderLinkOnProvision") != null);
Boolean videoLinkToolIncludeCreatorFolders = (request.getParameter("videoLinkToolIncludeCreatorFolders") != null);
String menuLinkText = request.getParameter("menuLinkText");
String roleMappingString = request.getParameter("roleMappingString");
Boolean courseCopyEnabled = (request.getParameter("courseCopyEnabled") != null);
String maxListedFolders = request.getParameter("maxListedFolders");
String maxConnectionWait = request.getParameter("maxConnectionWait");
String maxSocketWait = request.getParameter("maxSocketWait");

//Bounce page address to copy into Panopto
String SSOAddress = "https://" + ctx.getHostName()  + PlugInUtil.getUri("ppto", "PanoptoCourseTool", "SSO.jsp");
String LogsAddress = "https://" + ctx.getHostName()  + PlugInUtil.getUri("ppto", "PanoptoCourseTool", "Logs.jsp");

//Array of all course membership roles to list.
Role[] courseRoles = CourseMembership.Role.getAllCourseRoles();
List<Role> customRoles = new ArrayList<Role>();

for(Role courseRole: courseRoles)
{
	if(courseRole.getDbRole().isRemovable())
	{
		customRoles.add(courseRole);
	}
}


if(instanceName != null)
{
    if(!instanceName.trim().equals(""))
    {
        Utils.pluginSettings.setInstanceName(instanceName.trim());
    }
	
    Utils.pluginSettings.setDefaultPanoptoServer(defaultPanoptoServer);
	Utils.pluginSettings.setInstructorsCanProvision(instructorsCanProvision);
	Utils.pluginSettings.setMailLectureNotifications(mailLectureNotifications);
	Utils.pluginSettings.setRefreshLogins(refreshLogins);
	Utils.pluginSettings.setSyncAvailabilityStatus(syncAvailabilityStatus);
	Utils.pluginSettings.setRedirectToDefaultLogin(redirectToDefaultLogin);
	Utils.pluginSettings.setGrantTACreator(grantTACreator);
	Utils.pluginSettings.setGrantTAProvision(grantTAProvision);
	Utils.pluginSettings.setTAsCanCreateLinks(TAsCanCreateLinks);
	Utils.pluginSettings.setCourseResetEnabled(courseResetEnabled);
	Utils.pluginSettings.setVerbose(verbose);
	Utils.pluginSettings.setInsertLinkOnProvision(insertLinkOnProvision);
    Utils.pluginSettings.setInsertLTILinkOnProvision(insertLTILinkOnProvision);
    Utils.pluginSettings.setInsertFolderLinkOnProvision(insertFolderLinkOnProvision);
	Utils.pluginSettings.setVideoLinkToolIncludeCreatorFolders(videoLinkToolIncludeCreatorFolders);
	Utils.pluginSettings.setCourseCopyEnabled(courseCopyEnabled);
	
	maxListedFolders = (maxListedFolders == null) || maxListedFolders.isEmpty() ? Utils.pluginSettings.getMaxListedFolders() : maxListedFolders;
	Utils.pluginSettings.setMaxListedFolders(Integer.toString(Integer.max(100, Integer.parseInt(maxListedFolders))));

	maxConnectionWait = (maxConnectionWait == null) || maxConnectionWait.isEmpty() ? Utils.pluginSettings.getMaxConnectionWait() : maxConnectionWait;
	Utils.pluginSettings.setMaxConnectionWait(Integer.toString(Integer.max(1, Integer.parseInt(maxConnectionWait))));
	
	maxSocketWait = (maxSocketWait == null) || maxSocketWait.isEmpty() ? Utils.pluginSettings.getMaxSocketWait() : maxSocketWait;
	Utils.pluginSettings.setMaxSocketWait(Integer.toString(Integer.max(1, Integer.parseInt(maxSocketWait))));
	
	if(roleMappingString != null)
	{
	    Utils.pluginSettings.setRoleMappingString(roleMappingString.trim());
	}
	else
	{
	    Utils.pluginSettings.setRoleMappingString("");
	}
}

 
//If menu link text is not null, save it.
if(menuLinkText != null)
{
    //If the field is left blank, changes to the text will not be saved
    if(!menuLinkText.trim().equals(""))
    {
        Utils.pluginSettings.setMenuLinkText(menuLinkText.trim());
    }
}


instanceName = Utils.pluginSettings.getInstanceName();
defaultPanoptoServer = Utils.pluginSettings.getDefaultPanoptoServer();
instructorsCanProvision = Utils.pluginSettings.getInstructorsCanProvision();
mailLectureNotifications = Utils.pluginSettings.getMailLectureNotifications();
refreshLogins = Utils.pluginSettings.getRefreshLogins();
syncAvailabilityStatus = Utils.pluginSettings.getSyncAvailabilityStatus();
redirectToDefaultLogin = Utils.pluginSettings.getRedirectToDefaultLogin();
grantTACreator = Utils.pluginSettings.getGrantTACreator();
grantTAProvision = Utils.pluginSettings.getGrantTAProvision();
TAsCanCreateLinks = Utils.pluginSettings.getTAsCanCreateLinks();
courseResetEnabled = Utils.pluginSettings.getCourseResetEnabled();
verbose = Utils.pluginSettings.getVerbose();
insertLinkOnProvision = Utils.pluginSettings.getInsertLinkOnProvision();
insertLTILinkOnProvision = Utils.pluginSettings.getInsertLTILinkOnProvision();
insertFolderLinkOnProvision = Utils.pluginSettings.getInsertFolderLinkOnProvision();
videoLinkToolIncludeCreatorFolders = Utils.pluginSettings.getVideoLinkToolIncludeCreatorFolders();
menuLinkText = Utils.pluginSettings.getMenuLinkText();
roleMappingString = Utils.pluginSettings.getRoleMappingString();
courseCopyEnabled = Utils.pluginSettings.getCourseCopyEnabled();
maxListedFolders = Utils.pluginSettings.getMaxListedFolders();
maxConnectionWait = Utils.pluginSettings.getMaxConnectionWait();
maxSocketWait = Utils.pluginSettings.getMaxSocketWait();

// Server list form submitted, add/remove servers if valid operation
String add_hostname = request.getParameter("add_hostname");
String add_hostname_key = request.getParameter("add_hostname_key");
String remove_hostname = request.getParameter("remove_hostname");
if((remove_hostname != null) && !remove_hostname.equals(""))
{
    Utils.pluginSettings.removeServer(remove_hostname);
}
if((add_hostname != null) && (add_hostname_key != null))
{
    String originalHostname = add_hostname.toLowerCase().trim();
    add_hostname = originalHostname;
    add_hostname_key = add_hostname_key.trim();
    
    try {
        URL url = new URL(add_hostname);
        add_hostname = url.getAuthority() + url.getPath();
    } catch (MalformedURLException e) {
        Utils.log("New Panopto server URL, " + originalHostname + ", is invalid. Please input a proper FQDN, e.g. myinstitution.region.panopto.com");
    }
    
    if(!add_hostname.equals("") && !add_hostname_key.equals(""))
    {
        Utils.pluginSettings.addServer(add_hostname, add_hostname_key);
    }
}

// Load current server list.
List<String> serverList = Utils.pluginSettings.getServerList(); 

// Check for errors loading the list
if(serverList == null)
{
%>
            <bbUI:receipt type="FAIL" iconUrl="<%=iconUrl%>" title="<%=page_title%>" recallUrl="Config.jsp">
                Unable to load server list.
            </bbUI:receipt>
<%
}
else
{
    String defaultProvisionServerName = defaultPanoptoServer;
%>
    <bbUI:titleBar iconUrl="<%=iconUrl%>">
        <%=page_title%>
    </bbUI:titleBar>
    <form name="settingsForm">
         <div class="form">
            <div class="steptitle submittitle" id="steptitle1">
                <span id="stepnumber1">1</span>
                Manage Panopto Server List
            </div>
            <div class="stepcontent" id="step1">
            	
                <ol>
                    <li>
                        <div class="field">
                        	<h2>Manage Panopto Server List</h2>
                            <p tabIndex="0" class="stepHelp">
                                Users will be able to select from this list of Panopto servers when associating Panopto courses with their Blackboard courses.
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="field">
                            <input type="hidden" name="remove_hostname" />
                              <script type="text/javascript">
                                  var settingsForm = document.forms["settingsForm"];
                                  
                                  function remove(serverName)
                                  {
                                      settingsForm.remove_hostname.value = serverName;
                                      settingsForm.submit();
                                  }
                              </script>
                              <bbUI:list collection="<%=serverList%>" className="String" objectId="serverName">
                                <bbUI:listElement label="Server Name">
                                    <%=serverName%>
                                </bbUI:listElement>
                                <bbUI:listElement label="Application Key">
                                    <%=Utils.pluginSettings.getApplicationKey(serverName)%>
                                </bbUI:listElement>
                                <bbUI:listElement label="Action">
                                    <a href="javascript:remove('<%=serverName%>')" class="inlineAction">remove</a>
                                </bbUI:listElement>
                            </bbUI:list>
                            <% if(serverList.size() == 0) { %>
                                <div id="noServersMessage">
                                    No servers.  Please enter a server below.
                                </div>
                            <% } %>
                            <div id="addServerDiv">
                                <table class="settingTable">
                                    <tr>
                                        <th>Hostname</th>
                                        <th>Application Key</th>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input name="add_hostname" size="30" value="" />
                                            <div class="settingNote">e.g. myinstitution.region.panopto.com</div>
                                        </td>
                                        <td>
                                            <input name="add_hostname_key" size="40" value="" />
                                            <div class="settingNote">e.g. 12345678-1234-1234-1234-123456789012</div>
                                        </td>
                                        <td>
                                            <bbUI:button type="FORM_ACTION" name="add" alt="Add" action="SUBMIT_FORM" />
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </li>
                </ol>
            </div>
          </div>
    </form>

    <!-- Post to breakout provisioning page. -->
    <form name="batchProvisionForm" action="Course_Provision.jsp" method="post">
         <div class="form">
            <div class="steptitle submittitle" id="steptitle2">
                <span id="stepnumber2">2</span>
                Provision Panopto courses
            </div>
            <div class="stepcontent" id="step2">
                <ol>
                    <li>
                        <div class="field">
            				<h2>Provision Panopto courses</h2>
                            <p tabIndex="0" class="stepHelp">
                                Selected courses will be created on the specified Panopto server.<br />
                                Instructor and student lists will be populated in Panopto.
                            </p>
                        </div>
                    </li>
                    <%
                    if(serverList.size() == 0)
                    { %>
                    <li>
                        <div id="noServersMessage">
                            No servers.  Please enter a server above.
                        </div>
                    </li>
                    <%
                    }
                    else
                    { %>
                    <li>
                        <!-- URL of current page so provision breakout page knows where to return to. -->
                        <input type="hidden" name="returnUrl" value="<%= request.getRequestURL() %>" />

                        <!-- Function to launch built-in Blackboard course selection widget in a popup window. -->
                          <script type="text/javascript">
                            function launchCoursePicker()
                            {
                                // 1280x800 popup window (clipped to available screen size).
                                var width = Math.min(1280, screen.width);
                                var height = Math.min(800, screen.height);
                                
                                // Position against the right edge of the screen, with top margin of 20px.
                                var top = 20;                        
                                var left = Math.max(screen.width - 1280, 0);
                        
                                // Open the popup window
                                var courseSelectWindow = window.open('course/frameset.jsp','course_browse','width='+width+',height='+height+',resizable=yes,scrollbars=yes,status=yes,top='+top+',left='+left);
                        
                                if (courseSelectWindow != null)
                                {
                                    // Ensure popup window is on top.
                                    courseSelectWindow.focus();
                                    
                                    // Give the popup a reference to this page.
                                    if (courseSelectWindow.opener == null) courseSelectWindow.opener = self;
                                    // Identify the form control to populate with the selected courseIds
                                    courseSelectWindow.opener.inputToSet = document.forms.batchProvisionForm.bbCourses;
                                    
                                    window.top.name = 'bbWin';
                                }
                             } 
                          </script>
                    </li>
                    <li class="required">
                        <div class="label">
                            <img alt="Required" src="/images/ci/icons/required.gif"/>
                            Server 
                        </div>
                        <div class="field">
                            <select name="provisionServerName" <%=((serverList.size() == 1) ? "DISABLED='disabled'" : "")%>>
                                <%= Utils.generateServerOptionsHTML(defaultProvisionServerName) %>
                            </select>
                        </div>
                    </li>
                    <li class="required">
                        <div class="label">
                            <img alt="Required" src="/images/ci/icons/required.gif"/>
                            Course IDs 
                        </div>
                        <div class="field">
                            <!-- Comma-delimited list of Blackboard courseIds to provision, populated by Blackboard course picker widget. -->
                            <input type="text" name="bbCourses"> <input type="button" value="Course Picker" onclick="launchCoursePicker()" name="browse">
                            <br />
                            <span tabIndex="0" class="stepHelp">Course IDs may be entered as a comma separated list.</span>
                            <br />
                            <br />
                            Add courses from a CSV:  <input type="file" name="bbCourseCSV"  id="csvFile" accept=".csv"/>
                            <br/>
                            <span tabIndex="0" class="stepHelp">Course IDs may also be entered via a CSV file.</span>
                            <br />
                            
                            <!-- String representation of processed CSV -->
                            <input type="hidden" name="CSVString" id ="CSVString" value="" />
                            
                            <!-- Function for processing input CSV into string for submission -->
                            <script type="text/javascript">
                                var fileString = "";
                                document.getElementById('csvFile').onchange = function(event){
                                    if(window.File && window.FileReader && window.FileList && window.Blob)
                                    {
                                        var file = document.getElementById('csvFile').files[0];
                                        if(file){
                                            var reader = new FileReader();
                                            reader.onload = function(e) 
                                            { 
                                                var contents = e.target.result;
                                                fileString = contents.replace(/(\r\n|\n|\r)/gm,", ");
                                                document.getElementById('CSVString').value = fileString;
                                             }
                                             reader.readAsText(file); 
                                           
                                         }
                                         else
                                         {
                                         alert("File not found");
                                         }
                                    } 
                                    else 
                                    {
                                        alert("The File APIs are not fully supported by your browser.");
                                    }
                                    
                                };
                            </script>

                            <input name="add" class="secondary" type="submit" border="0" hspace="5" value="Add"/>
                        </div>
                    </li>
                    <li>
                        <div class="label">
                        </div>
                        <div class="field">
                            <br />
                            -- OR --
                        </div>
                    </li>
                    <li>
                        <div class="label">
                        </div>
                        <div class="field">
                            <br />
                            <input name="reprovisionAll" class="secondary" type="submit" border="0" hspace="5" value="Reprovision All Courses"/>
                            <p tabIndex="0" class="stepHelp">Ensures that all previously provisioned courses are correct on the Panopto server. Should be run after upgrading the building block</p>
                        </div>
                    </li>
                    <%
                    }
                    %>
                </ol>
            </div>
          </div>
    </form>
    
    <!-- Post to breakout bulk reset page. -->
    <form name="bulkResetForm" action="Bulk_Reset.jsp" method="post">
         <div class="form">
            <div class="steptitle submittitle" id="steptitle2">
                <span id="stepnumber2">2</span>
                Reset All Provisioned courses
            </div>
            <div class="stepcontent" id="step2">
                <ol>
                    <li><!-- URL of current page so provision breakout page knows where to return to. -->
                        <input type="hidden" name="returnUrl" value="<%= request.getRequestURL() %>" />
                        <div class="label">
                        </div>
                        <div class="field">
                            <br />
                            <input name="resetAll" class="secondary" type="submit" border="0" hspace="5" value="Reset All Courses"/>
                            <p tabIndex="0" class="stepHelp">Resets all courses previously provisioned to Panopto. This will unprovision all Panopto courses and delete their mapping from the Blackboard database. This operation cannot be undone.</p>
                        </div>
                    </li>
                </ol>
            </div>
          </div>
    </form>
    
    <!-- Post to breakout bulk LTI nav link page. -->
    <form name="bulkLTIForm" action="Bulk_LTI_Link.jsp" method="post">
         <div class="form">
            <div class="steptitle submittitle" id="steptitle2">
                <span id="stepnumber2">2</span>
                Add LTI navigation link to all provisioned courses
            </div>
            <div class="stepcontent" id="step2">
                <ol>
                    <li><!-- URL of current page so provision breakout page knows where to return to. -->
                        <input type="hidden" name="returnUrl" value="<%= request.getRequestURL() %>" />
                        <div class="label">
                        </div>
                        <div class="field">
                            <br />
                            <input name="ltiLinkAll" class="secondary" type="submit" border="0" hspace="5" value="Add LTI Link to all courses"/>
                            <p tabIndex="0" class="stepHelp">Adds an LTI navigation link to the table of contents menu for all provisioned courses. <br />
                            Note: This feature requires an LTI Tool Provider and corresponding Course Tool placement for the provisioned Panopto server to work properly.</p>
                        </div>
                    </li>
                </ol>
            </div>
          </div>
    </form>
    
    <!-- Post to breakout bulk folder list nav link page. -->
    <form name="bulkListLinkForm" action="Bulk_List_Link.jsp" method="post">
         <div class="form">
            <div class="steptitle submittitle" id="steptitle2">
                <span id="stepnumber2">2</span>
                Add non-LTI Folder list navigation links to all provisioned courses
            </div>
            <div class="stepcontent" id="step2">
                <ol>
                    <li><!-- URL of current page so provision breakout page knows where to return to. -->
                        <input type="hidden" name="returnUrl" value="<%= request.getRequestURL() %>" />
                        <div class="label">
                        </div>
                        <div class="field">
                            <br />
                            <input name="folderListLinkAll" class="secondary" type="submit" border="0" hspace="5" value="Add Folder List Link to all courses"/>
                            <p tabIndex="0" class="stepHelp">Adds non-LTI navigation links for each provisioned folder to the table of contents menu for all provisioned courses. <br />
                        </div>
                    </li>
                </ol>
            </div>
          </div>
    </form>
    
    <form name="instanceForm">
         <div class="form">
            <div class="steptitle submittitle" id="steptitle3">
                <span id="stepnumber3">3</span>
                Settings
            </div>
            <div class="stepcontent" id="step3">
                <ol>
                    <li>
                        <div class="field">
            				<h2>Panopto General Settings</h2>
            			</div>
                    </li>
                    <li>
                        <div class="label">Instance Name</div>
                        <div class="field"><input name="instanceName" type="text" size="30" value="<%= instanceName %>" style="float:left" /></div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                                The instance name identifies this Blackboard system to Panopto.<br />
                                If this is the only Blackboard system that will connect to the Panopto servers above, it is not necessary to change the default ("blackboard"). 
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="field">
                        <div class="label">Default Panopto Server</div>
                            <select name="defaultPanoptoServer" <%=((serverList.size() == 1) ? "DISABLED='disabled'" : "")%>>
                                <%= Utils.generateServerOptionsHTML(defaultPanoptoServer) %>
                            </select>
                        </div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                                Sets the default Panopto server to use if this Blackboard server is connected to multiple Panopto servers.<br /> 
                                Note: The default Panopto server is used for embedding Panopto content using the Mashup tool in unprovisioned courses, and will also be pre-selected in the server select boxes for provisioning.<br />
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="label">Bounce Page URL</div>
                        <div class="field"><b><%= SSOAddress %></b></div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                                Use this address for the bounce page URL in for your Blackboard instance in the Identity Provider section of your Panopto server.
                            </p>
                        </div>
                    </li>
                    
                    <li>
                        <div class="label">Panopto link on course menu when provisioned</div>
                        <div class="field">
                            <input name="insertLinkOnProvision" type="checkbox" <%= insertLinkOnProvision ? "checked" : "" %> style="float:left" />
                        </div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                               When checked, a link to a course's Panopto content will be automatically inserted into the course's navigation menu when it is provisioned.<br/>
                                <br/>
                                <br/>
                            </p>
                        </div>
                    </li>
                    
                    <li>
                        <div class="label">Panopto LTI link on course menu when provisioned</div>
                        <div class="field">
                            <input name="insertLTILinkOnProvision" type="checkbox" <%= insertLTILinkOnProvision ? "checked" : "" %> style="float:left" />
                        </div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                               When checked, a LTI link to a course's Panopto content will be automatically inserted into the course's navigation menu when it is provisioned. <br />
                            Note: This feature requires an LTI Tool Provider and corresponding Course Tool placement for the provisioned Panopto server to work properly. <br />
                                <br/>
                                <br/>
                            </p>
                        </div>
                    </li>
                    
                    <li>
                        <div class="label">Panopto non-LTI folder list links on course menu when provisioned</div>
                        <div class="field">
                            <input name="insertFolderLinkOnProvision" type="checkbox" <%= insertFolderLinkOnProvision ? "checked" : "" %> style="float:left" />
                        </div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                               When checked, non-LTI links to a course's Panopto content for each provisioned folder will be automatically inserted into the course's navigation menu when it is provisioned. <br />
                                <br/>
                                <br/>
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="label">Panopto Video Link Tool will include creator folders</div>
                        <div class="field">
                            <input name="videoLinkToolIncludeCreatorFolders" type="checkbox"
                                <%=videoLinkToolIncludeCreatorFolders ? "checked" : ""%>
                                style="float: left" />
                        </div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                                When checked, the Panopto Video Link Tool will include folders the user has creator access to in it's list.<br />
                                Check this if you want to embed videos from sub-folders that are 
                                inheriting access from mapped folders.<br /> <br />
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="label">Panopto Link Text</div>
                        <div class="field">
                            <input name="menuLinkText" type="text" size="30" value="<%= menuLinkText %>" style="float:left" />
                        </div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                              Text of link to Panopto content generated on provision, if setting is selected. Default text is "Panopto Video"<br/>
                                <br/>
                                <br/>
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="label">Instructors may provision courses</div>
                        <div class="field"><input name="instructorsCanProvision" type="checkbox" <%= instructorsCanProvision ? "checked" : "" %> style="float:left" /></div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                                This setting determines whether instructors can provision their Blackboard courses to create Panopto folders.<br/>
                                If checked, instructors will be able to configure courses on their own.<br/>
                                If unchecked, instructors will come across an error when attempting to provision a course. Administrators (users entitled to configure tools at the system level) will need to assist them.
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="label">Grant TA Provisioning Access</div>
                        <div class="field">
                            <input id="grantTAProvision" name="grantTAProvision" type="checkbox" <%= grantTAProvision ? "checked" : "" %> style="float:left" />
                        </div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                                This setting determines whether teaching assistants are granted provisioning access in the Panopto block.<br/>
                                If checked TAs will be able to provision courses and create links.<br />
                                If unchecked, they will receive an error "Please contact your administrator or instructor."
                                <br/>
                                <br/>
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="label">Grant TA Creator Access</div>
                        <div class="field">
                            <input id="grantTACreator" name="grantTACreator" type="checkbox" <%= grantTACreator ? "checked" : "" %> style="float:left" />
                        </div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                                This setting determines whether teaching assistants are granted creator access in Panopto.<br/>
                                If checked TAs will be able to create, edit, and delete recordings.
                                <br/>
                                <br/>
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="label">Allow TAs to embed with the Panopto Video Link Tool</div>
                        <div class="field">
                            <input name="TAsCanCreateLinks" type="checkbox" <%= TAsCanCreateLinks ? "checked" : "" %> style="float:left" />
                        </div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                                This setting determines whether teaching assistants have the ability to create Panopto Video Links on a course's content page.<br/>
                                 If checked, TAs will be able to create video links regardless of whether they have creator access or course provisioning access.              
                                <br/>
                                <br/>
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="label">Assign custom role mappings</div>
                        <div class="field">
                            <input name="roleMappingString" type="text" size="30" value="<%= roleMappingString %>" style="float:left" />
                        </div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                            Define mappings between custom blackboard roles and the blackboard roles they are recognized by the Panopto block.<br/>
                            The syntax for mappings is [custom role ID]:[mapped role type], with each mapping separated by a ';'. <br/>
                            The available role types to map to are 'instructor', 'ta', and 'none' with any unmapped custom roles being recognized<br/>
                            as students by the Panopto block. Role specified as 'none' gets no access to the Panopto folder associated with the course.<br/><br/>
                            
                            An example mapping string would be: 'RoleID1:instructor;RoleID2:ta;RoleID3:none' (Note: case does not matter)<br/><br/>
                            
                            The custom roles available to be mapped are the following:<br/></p>
	                        <div id = "rolesList">
	                            <table>
	                                <tr>
	                                    <th class="customRoleCell">Role Type</th>
	                                    <th class="customRoleCell">Role ID</th>
	                                </tr>
	                                  <% for(Role customRole:customRoles)
	                                    { %>
	                                        <tr>
	                                            <td class="customRoleCell">
	                                            <%=customRole.getDbRole().getCourseName()%>
	                                            </td>
	                                            <td class = "customRoleCell">
	                                            <%=customRole.getIdentifier()%>
	                                            </td>
	                                        </tr>
	                                <% } %>
	                            </table>
	                         </div>   
	                        <br/>
	                        <br/>
	                        <br/>
                        </div>
                    </li>                    
                    <li>
                        <div class="label">Blackboard course copy also copies Panopto permissions</div>
                        <div class="field">
                            <input name="courseCopyEnabled" type="checkbox" <%= courseCopyEnabled ? "checked" : "" %> style="float:left" />
                        </div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                                This setting determines whether users enrolled in the new course can see Panopto content created for the old course when Blackboard courses are copied.<br/>
                                If checked the Panopto Viewer and Creator groups from the new course folder are added to the old course's folder as 'viewer' groups.
                                <br/>
                                <br/>
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="label">Synchronize course availability status</div>
                        <div class="field">
                            <input name="syncAvailabilityStatus" type="checkbox" <%= syncAvailabilityStatus ? "checked" : "" %> style="float:left" />
                        </div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                                This setting enables to reflect the course availability and user enrollment status to access control of Panopto permissions.
                                <br />
                                Warning: If this setting is enabled, then the users will lose Panopto permission for unavailable courses or users.
                                <br/>
                                <br/>
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="label">Email Users</div>
                        <div class="field">
                            <input name="mailLectureNotifications" type="checkbox" <%= mailLectureNotifications ? "checked" : "" %> style="float:left" />
                        </div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                                This setting determines whether an email is automatically sent to users when lectures are processed and ready to view.<br/>
                                This default may be overridden for individual users via the account settings page in Panopto, after adding a course to Panopto or syncing users.
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="label">Refresh Logins</div>
                        <div class="field">
                            <input name="refreshLogins" type="checkbox" <%= refreshLogins ? "checked" : "" %> style="float:left" />
                        </div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                                This setting determines whether to refresh credentials for users logging in from the Panopto Recorder.<br/>
                                Normally, this should be left checked, so that when one Blackboard user logs out and another logs in,
                                the Recorder will switch to the new user.<br/>
                                <br/>
                                However, if Panopto site cannot redirect to Blackboard SSO page automatically with some reason, 
                                this behavior may cause unauthenticated users to be prompted to login twice when logging into the Panopto Recorder.<br/>
                                In this case, disable this setting and instruct your users to exit the Panopto Recorder to complete the logout process.
                                <br/>
                                <br/>
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="label">Redirect to Blackboard Default Login</div>
                        <div class="field">
                            <input name="redirectToDefaultLogin" type="checkbox" <%= redirectToDefaultLogin ? "checked" : "" %> style="float:left" />
                        </div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                                This setting determines if the SSO forces a redirection to the default login page instead of the relogin URL that was provided. <br />
                                Enable this feature only if Panopto support advises it. 
                                <br/>
                                <br/>
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="label">Allow all courses to be reset</div>
                        <div class="field">
                            <input name="courseResetEnabled" type="checkbox" <%= courseResetEnabled ? "checked" : "" %> style="float:left" />
                        </div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                                This setting is best used temporarily if you need to reset non-copied folders. Will allow creators to de-provision a course.<br/>
                                <br/>
                                <br/>
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="label">Verbose logging</div>
                        <div class="field">
                            <input name="verbose" type="checkbox" <%= verbose ? "checked" : "" %> style="float:left" />
                        </div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                                This setting controls if the Panopto building block writes verbose logs.<br/>
                                <br/>
                                <br/>
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="label">Max folders per request</div>
                        <div class="field">
                            <input name="maxListedFolders" type="number" min="100" value="<%= maxListedFolders %>" style="float:left" />
                        </div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                                This setting controls the max number of folders all pages that deal with folders will load.<br/>
                                It is not recommended to change this setting, increasing this setting may cause timeouts. <br/>
                                By default this is set to 10000, minimum is 100<br/>
                                <br/>
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="label">Max connection timeout per Panopto request (in seconds)</div>
                        <div class="field">
                            <input name="maxConnectionWait" type="number" min="1" value="<%= maxConnectionWait %>" style="float:left" />
                        </div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                                This setting controls how long a single Panopto request will wait to get a connection with Panopto before timing out.<br/>
                                By Default this is set to 15 seconds, minimum is 1 second.<br/>
                                <br/>
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="label">Max socket timeout per Panopto request (in seconds)</div>
                        <div class="field">
                            <input name="maxSocketWait" type="number" min="1" value="<%= maxSocketWait %>" style="float:left" />
                        </div>
                    </li>
                    <li>
                        <div class="field">
                            <p tabIndex="0" class="stepHelp">
                                This setting controls how long a single Panopto request will wait to process a task with Panopto before timing out.<br/>
                                By Default this is set to 60 seconds, minimum is 1 second.<br/>
                                <br/>
                            </p>
                        </div>
                    </li>
                    <li>
			            <div class="steptitle submittitle" id="steptitle4">
			                <span id="stepnumber4">4</span>
			                Done
			            </div>
			            <div class="stepcontent" id="step4">
			                <ol>
			                    <li>
			                        <div class="jsSubmitButtonDiv">
			                        	<input name="save-and-return" class="submit button-1" type="submit" value="Save general settings" onclick="document.location='<%=Utils.buildingBlockManagerURL%>';"/>
                                        <input class="button-1" type="button" name="bottom_Return" role="button" value="Return to Block Manager" onclick="window.location.href='<%=Utils.buildingBlockManagerURL%>';" />
                                        <input class="button-1" type="button" name="bottom_Logs" role="button" value="View Panopto block logs" onclick="window.location.href='<%=LogsAddress%>';" />
			                        </div>
			                    </li>
			                </ol>
			            </div>
                    </li>
                </ol>
            </div>
        </div>
    </form>
    
<%
}
%>
    </bbUI:docTemplate>

</bbData:context>
</bbUI:coursePage>
