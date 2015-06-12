
<!-- Copyright Panopto 2009 - 2013
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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@taglib uri="/bbUI" prefix="bbUI" %>
<%@taglib uri="/bbData" prefix="bbData"%>

<bbUI:coursePage>
<bbData:context id="ctx">

<%
String iconUrl = "/images/ci/icons/bookopen_u.gif";
String page_title = "Configure Panopto Focus Connector";

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
                <link rel="stylesheet" type="text/css" href="main.css" />
            </bbUI:docTemplateHead>
        </c:catch>
<%
    // Scalar settings form submitted, store in settings file.
String instanceName = request.getParameter("instanceName");
Boolean instructorsCanProvision = (request.getParameter("instructorsCanProvision") != null);
Boolean mailLectureNotifications = (request.getParameter("mailLectureNotifications") != null);
Boolean refreshLogins = (request.getParameter("refreshLogins") != null);
Boolean grantTACreator = (request.getParameter("grantTACreator") != null);
Boolean grantTAProvision = (request.getParameter("grantTAProvision") != null);
Boolean instructorsCanCreateFolder = (request.getParameter("instructorsCanCreateFolder") != null);
Boolean courseResetEnabled = (request.getParameter("courseResetEnabled") != null);
Boolean verbose = (request.getParameter("verbose") != null);
Boolean adminProvisionOnly = (request.getParameter("adminProvisionOnly") != null);
Boolean insertLinkOnProvision = (request.getParameter("insertLinkOnProvision") != null);
String menuLinkText = request.getParameter("menuLinkText");
if(instanceName != null)
{
    if(!instanceName.trim().equals(""))
    {
        Utils.pluginSettings.setInstanceName(instanceName.trim());
    }
    
    Utils.pluginSettings.setInstructorsCanProvision(instructorsCanProvision);
    Utils.pluginSettings.setMailLectureNotifications(mailLectureNotifications);
    Utils.pluginSettings.setRefreshLogins(refreshLogins);
    Utils.pluginSettings.setGrantTACreator(grantTACreator);
    Utils.pluginSettings.setGrantTAProvision(grantTAProvision);
    Utils.pluginSettings.setInstructorsCanCreateFolder(instructorsCanCreateFolder);
    Utils.pluginSettings.setCourseResetEnabled(courseResetEnabled);
    Utils.pluginSettings.setVerbose(verbose);
    Utils.pluginSettings.setAdminProvisionOnly(adminProvisionOnly);
    Utils.pluginSettings.setInsertLinkOnProvision(insertLinkOnProvision);
    Utils.pluginSettings.setMenuLinkText(menuLinkText);
}
instanceName = Utils.pluginSettings.getInstanceName();
instructorsCanProvision = Utils.pluginSettings.getInstructorsCanProvision();
mailLectureNotifications = Utils.pluginSettings.getMailLectureNotifications();
refreshLogins = Utils.pluginSettings.getRefreshLogins();
grantTACreator = Utils.pluginSettings.getGrantTACreator();
grantTAProvision = Utils.pluginSettings.getGrantTAProvision();
instructorsCanCreateFolder = Utils.pluginSettings.getInstructorsCanCreateFolder();
courseResetEnabled = Utils.pluginSettings.getCourseResetEnabled();
verbose = Utils.pluginSettings.getVerbose();
adminProvisionOnly = Utils.pluginSettings.getAdminProvisionOnly();
insertLinkOnProvision = Utils.pluginSettings.getInsertLinkOnProvision();
menuLinkText = Utils.pluginSettings.getMenuLinkText();

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
    add_hostname = add_hostname.trim();
    add_hostname_key = add_hostname_key.trim();
    
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
    // If there's only one server, prepopulate server field in provision form.
    String provisionServerName = null;
    if(serverList.size() == 1)
    {
        provisionServerName = serverList.get(0);
    }
%>
            <bbUI:titleBar iconUrl="<%=iconUrl%>">
                <%=page_title%>
            </bbUI:titleBar>

            <form name="instanceForm">
                 <div class="form">
                    <div class="steptitle submittitle" id="steptitle1">
                        <span id="stepnumber1">1</span>
                        Settings
                    </div>
                    <div class="stepcontent" id="step1">
                        <ol>
                            <li>
                                <div class="field"></div>
                            </li>
                            <li>
                                <div class="label">Instance Name</div>
                                <div class="field"><input name="instanceName" type="text" size="30" value="<%= instanceName %>" style="float:left" /></div>
                            </li>
                            <li>
                                <div class="field">
                                    <p tabIndex="0" class="stepHelp">
                                        The instance name identifies this Blackboard system to Panopto.<br />
                                        If this is the only Blackboard system that will connect to the Panopto servers below, it is not necessary to change the default ("blackboard"). 
                                    </p>
                                </div>
                            </li>
                            <li>
                                <div class="field"></div>
                            </li>
                            <li>
                                <div class="label">User Opt In</div>
                                <div class="field"><input name="instructorsCanProvision" type="checkbox" <%= instructorsCanProvision ? "checked" : "" %> style="float:left" /></div>
                            </li>
                            <li>
                                <div class="field">
                                    <p tabIndex="0" class="stepHelp">
                                        This setting determines whether instructors are entitled to add courses to Panopto.<br/>
                                        If checked, instructors will be able to create content for their course with the Panopto Recorder.<br/>
                                        If unchecked, only administrators (users entitled to configure tools at the system level) will be able to add courses to Panopto.
                                    </p>
                                </div>
                            </li>
                            <li>
                                <div class="field"></div>
                            </li>
                            <li>
                                <div class="label">Email Instructors</div>
                                <div class="field">
                                    <input name="mailLectureNotifications" type="checkbox" <%= mailLectureNotifications ? "checked" : "" %> style="float:left" />
                                </div>
                            </li>
                            <li>
                                <div class="field">
                                    <p tabIndex="0" class="stepHelp">
                                        This setting determines whether an email is automatically sent to instructors when lectures are processed and ready to view.<br/>
                                        This default may be overridden for individual users via the account settings page in Panopto, after adding a course to Panopto or syncing users.
                                    </p>
                                </div>
                            </li>
                            <li>
                                <div class="field"></div>
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
                                        However, if your Blackboard server does not permit unauthenticated users to make requests to the SSO page,
                                        this setting may cause unauthenticated users to be prompted to login twice when logging into the Panopto Recorder.<br/>
                                        In this case, disable this setting, and instruct your users to exit the Panopto Recorder to complete the logout process.
                                        <br/>
                                        <br/>
                                    </p>
                                </div>
                            </li>
                            <li>
                                <div class="field"></div>
                            </li>
                            <li>
                                <div class="label">Grant TA Creator Access</div>
                                <div class="field">
                                    <input name="grantTACreator" type="checkbox" <%= grantTACreator ? "checked" : "" %> style="float:left" />
                                </div>
                            </li>
                            <li>
                                <div class="field">
                                    <p tabIndex="0" class="stepHelp">
                                        This setting determines whether teaching assistants are granted creator access in Panopto.<br/>
                                        If checked TAs will be able to create, edit, and delete recordings. 
                                        In addition to this global setting this can be overridden on a course by course basis.
                                        <br/>
                                        <br/>
                                    </p>
                                </div>
                            </li>
                            <li>
                                <div class="label">Grant TA Provisioning Access</div>
                                <div class="field">
                                    <input name="grantTAProvision" type="checkbox" <%= grantTAProvision ? "checked" : "" %> style="float:left" />
                                </div>
                            </li>
                            <li>
                                <div class="field">
                                    <p tabIndex="0" class="stepHelp">
                                        This setting determines whether teaching assistants are granted provisioning access in the Panopto block.<br/>
                                        If checked TAs will be able to provision courses and create links. 
                                        <br/>
                                        <br/>
                                    </p>
                                </div>
                            </li>
                            <li>
                                <div class="label">Provisioning by administrators only</div>
                                <div class="field">
                                    <input name="adminProvisionOnly" type="checkbox" <%= adminProvisionOnly ? "checked" : "" %> style="float:left" />
                                </div>
                            </li>
                            <li>
                                <div class="field">
                                    <p tabIndex="0" class="stepHelp">
                                       When checked, provisioning of a course to Panopto may only be done by a user with administrative status.<br/>
                                        <br/>
                                        <br/>
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
                                <div class="label">Panopto Link Text</div>
                                <div class="field">
                                    <input name="menuLinkText" type="text" value="<%=menuLinkText%>" style="float:left" />
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
                                <div class="label">Instructors may create folders</div>
                                <div class="field">
                                    <input name="instructorsCanCreateFolder" type="checkbox" <%= instructorsCanCreateFolder ? "checked" : "" %> style="float:left" />
                                </div>
                            </li>
                            <li>
                                <div class="field">
                                    <p tabIndex="0" class="stepHelp">
                                        This setting determines whether instructors are allowed to create new folders in Panopto.<br/>
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
                                <div class="field">
                                    <input name="ok" class="submit" type="submit" border="0" hspace="5" value="Ok"/>
                                </div>
                            </li>
                        </ol>
                    </div>
                </div>
            </form>

            <form name="settingsForm">
                 <div class="form">
                    <div class="steptitle submittitle" id="steptitle2">
                        <span id="stepnumber2">2</span>
                        Manage Panopto Server List
                    </div>
                    <div class="stepcontent" id="step2">
                        <ol>
                            <li>
                                <div class="field">
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
                                                    <div class="settingNote">e.g. panopto.myinstitution.edu</div>
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
            <form name="batchProvisionForm" action="Course_Provision.jsp">
                 <div class="form">
                    <div class="steptitle submittitle" id="steptitle3">
                        <span id="stepnumber3">3</span>
                        Provision Panopto courses
                    </div>
                    <div class="stepcontent" id="step3">
                        <ol>
                            <li>
                                <div class="field">
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
                                        <%= Utils.generateServerOptionsHTML(provisionServerName) %>
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
                                    <span tabIndex="0" class="stepHelp">Course IDs may be entered as a comma seperated list.</span>
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
                    
                    <div class="steptitle submittitle" id="steptitle4">
                        <span id="stepnumber4">4</span>
                        Done
                    </div>
                    <div class="stepcontent" id="step4">
                        <ol>
                            <li>
                                <div class="jsSubmitButtonDiv">
                                    <bbUI:button type="FORM_ACTION" name="ok" alt="Ok" action="LINK" targetUrl="<%=Utils.buildingBlockManagerURL%>" />
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
