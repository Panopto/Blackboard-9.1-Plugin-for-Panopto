<!-- Copyright Panopto 2009 - 2011
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
<%@page import="com.panopto.services.*"%>

<%@page import="java.util.*"%>
<%@page import="java.net.URLEncoder"%>

<%@page import="blackboard.platform.*"%>
<%@page import="blackboard.data.course.*"%>
<%@page import="blackboard.data.user.*"%>
<%@page import="blackboard.persist.*"%>
<%@page import="blackboard.persist.course.*"%>
<%@page import="blackboard.persist.user.*"%>
<%@page import="com.panopto.blackboard.PanoptoData"%>

<%@taglib uri="/bbUI" prefix="bbUI" %>
<%@taglib uri="/bbData" prefix="bbData"%>

<bbData:context id="ctx">

<%
final String iconUrl = "/images/ci/icons/bookopen_u.gif";
final String page_title = "Configure Panopto Focus Course";

// Passed from Blackboard to content create / modify pages
// Persist in form for use when redirecting back to create page
String course_id = request.getParameter("course_id");
String content_id = request.getParameter("content_id");

String parentPageTitle = null;
String parentURL = null;
String selfURL = request.getRequestURL() + "?course_id=" + course_id; 
String courseDocsURL = null;
if(content_id != null)
{	
	// Fetch Blackboard-generated URL to course documents area via utility method
	courseDocsURL = Utils.getCourseDocsURL(course_id, content_id);
	parentPageTitle = "Insert Panopto Focus Video"; 
	parentURL = Utils.createScriptURL +
				"?course_id=" + course_id +
				"&content_id=" + content_id;
	selfURL += "&content_id=" + content_id;
}
else
{
	parentPageTitle = "Panopto Focus Content";
	parentURL = Utils.contentScriptURL +
				"?course_id=" + course_id;
}
String returnUrl = URLEncoder.encode(selfURL.toString(), "UTF-8");

PanoptoData ccCourse = new PanoptoData(ctx);

// First check if the caller is allowed to be here
if (!ccCourse.userMayConfig())
{
%>
	<bbUI:docTemplate title="<%=page_title%>">
		<bbUI:breadcrumbBar>
			<bbUI:breadcrumb><%=page_title%></bbUI:breadcrumb>
		</bbUI:breadcrumbBar>
		<bbUI:receipt type="FAIL" iconUrl="<%=iconUrl%>" title="<%=page_title%>" recallUrl="<%=parentURL%>">
			You do not have access to configure this course. 
		</bbUI:receipt>
	</bbUI:docTemplate>
<%
	return;
}

// Get the list of CC servers set on the config page, and display an error if there are none.
List<String> serverList = Utils.pluginSettings.getServerList();
if((serverList == null) || (serverList.size() == 0))
{
%>
	<bbUI:docTemplate title="<%=page_title%>">
		<bbUI:breadcrumbBar>
			<bbUI:breadcrumb><%=page_title%></bbUI:breadcrumb>
		</bbUI:breadcrumbBar>
		<bbUI:receipt type="FAIL" iconUrl="<%=iconUrl%>" title="<%=page_title%>" recallUrl="<%=parentURL%>">
			No Panopto servers available.  Please contact an administrator.
		</bbUI:receipt>
	</bbUI:docTemplate>
<%
	return;
}

// On main form submit, set final course association and redirect to referring page.
String[] folderIds = request.getParameterValues("selectedFolders");
if(folderIds != null)
{
	// Look through the folderIds list for anything that starts with an x. This indicates a folder which needs to be added
	for (int i = 0; i < folderIds.length; i++)
	{
		if (folderIds[i].startsWith("x"))
		{
			Folder newFolder = ccCourse.createFolder(folderIds[i].substring(1));
			
			if (newFolder != null)
			{
				folderIds[i] = newFolder.getId();
			}
			else
			{
				folderIds[i] = "";
			}
		}
	}
	
	ccCourse.reprovisionCourse(folderIds);
	
	String redirectURL = response.encodeRedirectURL(parentURL);
	response.sendRedirect(redirectURL);
	return;
}

%>
	<bbUI:docTemplate title="<%=page_title%>">

		<bbUI:docTemplateHead>
			<link rel="stylesheet" type="text/css" href="main.css" />
		</bbUI:docTemplateHead>

		<bbUI:coursePage courseId="<%= ctx.getCourseId() %>">
		
			<bbUI:breadcrumbBar>
				<bbUI:breadcrumb href="<%=parentURL%>"><%=parentPageTitle%></bbUI:breadcrumb>
				<bbUI:breadcrumb><%=page_title%></bbUI:breadcrumb>
			</bbUI:breadcrumbBar>
	
			<bbUI:titleBar iconUrl="<%=iconUrl%>">
				<%=page_title%>
			</bbUI:titleBar>
	
			<!-- If the course hasn't been mapped then let the user select a server and provision it if he has access -->
			<%
			if (!ccCourse.isMapped() && ccCourse.userMayProvision())
			{
				String serverName = request.getParameter("serverName");
				if(serverName == null && Utils.getServerList().size() == 1)
				{
					serverName = Utils.getServerList().get(0);
				}
			%> 
				<form name="serverForm" id="serverForm">
					<!-- Passed from Blackboard, see above -->
					<input type="hidden" name="course_id" value='<%=course_id%>' />
					<% if(content_id != null){ %>
						<input type="hidden" name="content_id" value='<%=content_id%>' />
					<% } %>
	
					<div class="steptitle submittitle" id="steptitle1">
						<span id="stepnumber1">1</span>
						Select Panopto Server
					</div>
					<div class="stepcontent" id="step1">
						<ol>
							<li>
								<div class="field"></div>
							</li>
							<li class="required">
								<div class="label">
								</div>
								<div class="field">
									<select name="serverName" onchange="document.getElementById('serverForm').submit()" <%=((serverList.size() == 1) ? "DISABLED='disabled'" : "")%>>
										<%= Utils.generateServerOptionsHTML(serverName) %>
									</select>
								</div>
							</li>
						</ol>
					</div>
					<div class="steptitle submittitle" id="steptitle2">
						<span id="stepnumber2">2</span>
						Provision Panopto Course
					</div>
					<div class="stepcontent" id="step2">
						<ol>
							<li>
								<div class="field"></div>
							</li>
							<li>
								<div class="label"></div>
								<div class="field">
									<%if (serverName == null)
									{
										%> <input type='button' value='Add Course to Panopto' DISABLED='disabled' /> <%
									}
									else
									{
										String provisionUrl = ccCourse.getProvisionUrl(serverName, returnUrl);
										%> 
											<input type='button' value='Add Course to Panopto' onclick='location.href="<%= provisionUrl %>"; return false;' />
											<p tabIndex="0" class="stepHelp">
												Creates a Panopto folder for the course and sets up access so that instructors are able to create content in the folder and students are able to view it.
												<br />
												Once the course has been added to Panopto you may associate additional Panopto folders to the course.
											</p> 
										<%
									}%>
								</div>
							</li>
						</ol>
					</div>
				</form>
				
			<%
				return;
			}
			else if (!ccCourse.isMapped())
			{
				%><span class="error">Course is not provisioned with Panopto. Please contact your administrator.<br></span> <%
				return;
			}
			else
			{
			 %>

	
			<!-- Use a separate form for the session group so we can validate separately and detect active postback of session group field -->		
			<form name="folderForm" id="folderForm" onsubmit="return validate(this)">
				<!-- Passed from Blackboard, see above -->
				<input type="hidden" name="course_id" value='<%=course_id%>' />
				<% if(content_id != null){ %>
					<input type="hidden" name="content_id" value='<%=content_id%>' />
				<% } %>
	
				<div class="steptitle submittitle" id="steptitle1">
					<span id="stepnumber1">1</span>
					Select Panopto Folders
				</div>
				<div class="stepcontent" id="step1">
					<ol>
						<li>
							<div class="field">
								<p tabIndex="0" class="stepHelp">
									You may update the list of Panopto folders associated with this course. 
									<br />
									Instructors of this course will be able to create content in any folder associated with it and students will be able to view the content.
								</p> 
							</div>
						</li>
						<li>
							<div class="field">
								<Table>
									<tr>
										<td>
											<span class="sectionHeader"> Available Folders: </span><br />
											<SELECT NAME="availableFolders" id="availableFolders" class="selectFoldersListBox" size=10 multiple>
												 <%= ccCourse.generateCourseConfigAvailableFoldersOptionsHTML() %>
	          								</SELECT>
										</td>
										<td>
											<input class="selectFoldersButton" type='button' value="Add  &gt;&gt;" onclick="onAddFoldersClicked()" />
											<br />
											<input class="selectFoldersButton" type='button' value="&lt;&lt;  Remove" onclick="onRemoveFoldersClicked()" />
										</td>
										<td>
											<span class="sectionHeader"> Selected Folders: </span><br />
											<SELECT NAME="selectedFolders" id="selectedFolders" class="selectFoldersListBox" size=10 multiple>
												<%= ccCourse.generateCourseConfigSelectedFoldersOptionsHTML() %>
	          								</SELECT>
	          								<br />
										</td>
									</tr> 
									<%
									if (ccCourse.userMayCreateFolder())
									{ 
									%>
									<tr>
										<td></td>
										<td></td>
										<td>
	          								<input type='button' value="Create New Folder" onclick="onCreateNewFoldersClicked()" />
										</td>
									</tr>
									<%
									}
									 %>
								</Table>
								<script>
									function onCreateNewFoldersClicked()
									{
										var name=prompt("Enter a folder name:","");
										if (name!=null && name!="")
										{
											var selectedFolders = document.getElementById("selectedFolders");
											// Insert the new folder into the table. It will be created when the user submits.
											addOptionToSortedSelect(selectedFolders, name, "x" + name);
										}
									}
									
									function addOptionToSortedSelect(select, text, value)
									{
										// Create the new element
								    	var newOption = document.createElement('option');
										newOption.text = text;
	 									newOption.value = value;
	 									
	 									// Find where to insert it
	 									var insertIndex = 0;
	 									while (insertIndex < select.length && text.toLowerCase() > select.options[insertIndex].text.toLowerCase())
	 									{
	 										insertIndex++;
	 									}
	 									
	 									// Insert it
										var currentElement = select.options[insertIndex];  
										try 
	  									{
	     										select.add(newOption, currentElement); // standards compliant; doesn't work in IE
	   									}
	   									catch(ex) 
	   									{
	     										select.add(newOption, insertIndex); // IE only
	   									}
									}
									
									// Transfers entries from available folders to selected folders
									function onAddFoldersClicked()
									{
										var availableFolders = document.getElementById("availableFolders");
										var selectedFolders = document.getElementById("selectedFolders");
										
										// Iterate from the end so we don't run into problems when removing elements
										for (i = availableFolders.length - 1; i>=0; i--) 
										{
										    if (availableFolders[i].selected)
										    {
										    	addOptionToSortedSelect(selectedFolders, availableFolders[i].text, availableFolders[i].value);
										      	availableFolders.remove(i);
										    }
									    }
									}
									
									// Transfers entries from selected folders to available folders
									function onRemoveFoldersClicked()
									{
										var availableFolders = document.getElementById("availableFolders");
										var selectedFolders = document.getElementById("selectedFolders");
										
										// Iterate from the end so we don't run into problems when removing elements
										for (i = selectedFolders.length - 1; i>=0; i--) 
										{
										    if (selectedFolders[i].selected)
										    {
										    	addOptionToSortedSelect(availableFolders, selectedFolders[i].text, selectedFolders[i].value);
										      	selectedFolders.remove(i);
										    }
									    }
									}
									
									function validate(form)
									{
										// Select all the options in selectedFolders so that it gets posted
										var selectedFolders = document.getElementById("selectedFolders");	
										for (i = 0; i < selectedFolders.length; i++)
										{
											selectedFolders[i].selected = true;
										}
										if (selectedFolders.length == 0)
										{
											alert("You must have at least one folder selected for the course.");
											return false;
										}
									}
								</script>

							</div>
						</li>
					</ol>
				</div>
				<div class="steptitle submittitle" id="steptitle2">
					<span id="stepnumber2">2</span>
					Save
				</div>
				<p class="taskbuttondiv">
					<input name="bottom_Cancel" class="button-2" onclick="window.location.href='<%= parentURL %>'" type="button" value="Cancel"/>
					<input name="bottom_Submit" class="submit button-1" type="submit" value="Submit"/>
				</p>
			</form>
		<% } %>

		</bbUI:coursePage>

	</bbUI:docTemplate>

</bbData:context>
