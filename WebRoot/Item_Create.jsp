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

<%@page import="blackboard.persist.BbPersistenceManager"%>
<%@page import="blackboard.platform.BbServiceManager"%>
<%@page import="blackboard.persist.Id"%>
<%@page import="blackboard.data.course.Course"%>
<%@page import="com.panopto.blackboard.PanoptoData"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@taglib uri="/bbUI" prefix="bbUI" %>
<%@taglib uri="/bbData" prefix="bbData"%>

<bbUI:coursePage>
<bbData:context id="ctx">

<%
final String iconUrl = "/images/ci/icons/bookopen_u.gif";
final String page_title = "Insert Panopto Video";

// Passed from Blackboard to content create / modify pages
// For content create action, "content_id" refers to the parent content container
// For content modify action, "content_id" refers to the content item to modify
String course_id = request.getParameter("course_id");
String content_id = request.getParameter("content_id");

String courseConfigURL = Utils.courseConfigScriptURL +
							"?course_id=" + course_id +
							"&content_id=" + content_id;

// Fetch Blackboard-generated URL to course documents area via utility method
String courseDocsURL = Utils.getCourseDocsURL(course_id, content_id);

// Get info about currently mapped CC course.
PanoptoData ccCourse = new PanoptoData(ctx);

// Set on submit (self-submitting form)
String title = request.getParameter("title");
String description = request.getParameter("description");
String lectureURL = request.getParameter("lectureURL");
String folderID = request.getParameter("folderID");

if (!ccCourse.userMayAddLinks())
{
%>
<c:catch>
	<bbUI:docTemplate title="<%=page_title%>">
		<bbUI:receipt type="FAIL" iconUrl="<%=iconUrl%>" title="<%=page_title%>" recallUrl="<%=courseDocsURL%>">
			You do not have access to configure this course. 
		</bbUI:receipt>
	</bbUI:docTemplate>
</c:catch>
<%
	return;
}

if(lectureURL != null)
{
	ccCourse.addBlackboardContentItem(content_id, lectureURL, title, description);
%>
<c:catch>
	<bbUI:docTemplate title="<%=page_title%>">
		<bbUI:receipt type="SUCCESS" iconUrl="<%=iconUrl%>" title="<%=page_title%>" recallUrl="<%=courseDocsURL%>">
			Item created.
		</bbUI:receipt>
	</bbUI:docTemplate>
</c:catch>
<%
	return;
}
%>

	<bbUI:docTemplate title="<%=page_title%>">

		<bbUI:docTemplateHead>
			<link rel="stylesheet" type="text/css" href="main.css" />
		</bbUI:docTemplateHead>

		<bbUI:coursePage courseId="<%= ctx.getCourseId() %>">
		<c:catch>
			<bbUI:titleBar iconUrl="<%=iconUrl%>">
				<%=page_title%>
			</bbUI:titleBar>
		</c:catch>

			<% 
			if (!ccCourse.isMapped())
			{
				%>
					Course is not yet provisioned with Panopto. <br />
					<bbUI:button type="INLINE" name="Add Course to Panopto" alt="Add Course to Panopto" action="LINK" targetUrl="<%=courseConfigURL%>" />
				<%
			}
			else
			{
			 %>	
			 
 			<form name="folderForm" id="folderForm">
				<input type="hidden" name="course_id" value='<%=course_id%>' />
				<input type="hidden" name="content_id" value='<%=content_id%>' />
	
				<div class="steptitle submittitle" id="steptitle1">
					<span id="stepnumber1">1</span>
					Select Panopto Folder
				</div>
				<div class="stepcontent" id="step1">
					<ol>
						<li class="required">
							<div class="label">
								<img alt="Required" src="/images/ci/icons/required.gif">
								Folder
							</div>
							<div class="field">
								<select name="folderID" onchange="document.getElementById('folderForm').submit()">
									<%= ccCourse.generateFolderOptionsHTML(folderID) %>
								</select>
							</div>
						</li>
					</ol>
				</div>
			</form>
			<form name="contentForm" onsubmit="return validate(this)">
	
				<input type="hidden" name="course_id" value='<%=course_id%>' />
				<input type="hidden" name="content_id" value='<%=content_id%>' />
				<div class="steptitle submittitle" id="steptitle2">
					<span id="stepnumber2">2</span>
					Select Panopto Lecture
				</div>
				<div class="stepcontent" id="step2">
					<ol>
						<li class="required">
							<div class="label">
								<img alt="Required" src="/images/ci/icons/required.gif">
								Lecture
							</div>
							<div class="field">
								<select name="lectureURL" onchange="handleSelectDelivery(this)" <%= folderID == null || folderID == "" ? "DISABLED='disabled'" : "" %>>
									<%= ccCourse.generateSessionOptionsHTML(folderID) %>
								</select>
								<script>
									function handleSelectDelivery(select)
									{
										var title = select.form.title;
										title.value = "";
										var deliveryOption = select.options[select.selectedIndex];
										if(deliveryOption.value != "") title.value = deliveryOption.text;
										title.select();
									}
								</script>
							</div>
						</li>
					</ol>
				</div>
				<div class="steptitle submittitle" id="steptitle3">
					<span id="stepnumber3">3</span>
					Edit title and description
				</div>
				<div class="stepcontent" id="step3">
					<ol>
						<li class="required">
							<div class="label">
								<img alt="Required" src="/images/ci/icons/required.gif">
								Title
							</div>
							<div class="field">
								<input type="text" name="title" size="75" />
							</div>
						</li>
						<li>
							<div class="label">
								Description
							</div>
							<div class="field">
								<textarea name="description" cols="100" rows="3"></textarea><br>
								<i>Use HTML to include additional links, images, formatting, etc. in the description</i>
							</div>
						</li>
					</ol>
				</div>
				<div class="steptitle submittitle" id="steptitle4">
					<span id="stepnumber4">4</span>
					Submit
				</div>
				<p class="taskbuttondiv">
					<input name="bottom_Cancel" class="button-2" onclick="window.location.href='<%= courseDocsURL %>'" type="button" value="Cancel"/>
					<input name="bottom_Submit" class="submit button-1" onclick="" type="submit" value="Submit"/>
				</p>
				<script>
					function validate(form)
					{
						var form = document.forms["contentForm"];
						if ( <%= folderID == null ? "true" : "false" %>)
						{
							alert("Please select a folder.");
						}
						else if(form.lectureURL.value == "")
						{
							alert("Please select a lecture.");
							return false;
						}
						else if(form.title == null || form.title.value == "")
						{
							alert("Title field cannot be blank.");
							return false;
						}
						else
						{
							return true;
						}
					}
				</script>
	
			</form>
		<% } %>
	
		</bbUI:coursePage>

	</bbUI:docTemplate>

</bbData:context>
</bbUI:coursePage>