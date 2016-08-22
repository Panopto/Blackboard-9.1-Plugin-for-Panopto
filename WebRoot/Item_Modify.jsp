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

<%@page import="blackboard.data.content.Content"%>
<%@page import="com.panopto.blackboard.Utils"%>

<%@page import="blackboard.persist.BbPersistenceManager"%>
<%@page import="blackboard.platform.persistence.PersistenceServiceFactory"%>
<%@page import="blackboard.persist.Id"%>
<%@page import="blackboard.data.course.Course"%>
<%@page import="com.panopto.blackboard.PanoptoData"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@taglib uri="/bbUI" prefix="bbUI" %>
<%@taglib uri="/bbData" prefix="bbData"%>

<bbUI:coursePage>
<bbData:context id="ctx">
<%
String iconUrl = "/images/ci/icons/bookopen_u.gif";
String page_title = "Edit ReCap Video Link";

// Passed from Blackboard to content create / modify pages
// For content create action, "content_id" refers to the parent content container
// For content modify action, "content_id" refers to the content item to modify
String course_id = request.getParameter("course_id");
String content_id = request.getParameter("content_id");

BbPersistenceManager bbPm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();
Id courseId = bbPm.generateId( Course.DATA_TYPE, course_id );

String title = request.getParameter("title");
String description = request.getParameter("description");

String courseDocsURL = Utils.getCourseDocsURL(course_id, content_id);

PanoptoData ccCourse = new PanoptoData(ctx);
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
else if(title != null)
{
	Utils.updatePanoptoContentItem(title, description, content_id);
%>
<c:catch>
	<bbUI:docTemplate title="<%=page_title%>">
		<bbUI:coursePage courseId="<%= courseId %>">
			<bbUI:receipt type="SUCCESS" iconUrl="<%=iconUrl%>" title="<%=page_title%>" recallUrl="<%=courseDocsURL%>">
				Item updated.
			</bbUI:receipt>
		</bbUI:coursePage>
	</bbUI:docTemplate>
</c:catch>
<%
	return;
}

Content content = Utils.loadPanoptoContentItem(content_id);
title = Utils.escapeHTML(content.getTitle());
description = Utils.escapeHTML(content.getBody().getText());
%>

	<bbUI:docTemplate title="<%=page_title%>">

		<bbUI:coursePage courseId="<%= courseId %>">
		<c:catch>
			<bbUI:titleBar iconUrl="<%=iconUrl%>">
				<%= page_title %>
			</bbUI:titleBar>
		</c:catch>
	
			<form>
				<input type="hidden" name="course_id" value="<%=course_id%>" />
				<input type="hidden" name="content_id" value="<%=content_id%>" />
	
				<div class="steptitle submittitle" id="steptitle1">
					<span id="stepnumber1">1</span>
					Edit title and description
				</div>
				<div class="stepcontent" id="step1">
					<ol>
						<li>
							<div class="label">
								Title
							</div>
							<div class="field">
								<input id="title" type="text" name="title" size="75" value="<%=title%>" />
							</div>
						</li>
						<li>
							<div class="label">
								Description
							</div>
							<div class="field">
								<textarea name="description" cols="100" rows="3"><%=description%></textarea><br>
								<i>Use HTML to include additional links, images, formatting, etc. in the description</i>
							</div>
						</li>
						<li>
							<script>
								window.onload = function () {
									document.getElementById("title").select();
								};
							</script>
						</li>
					</ol>
				</div>
				<div class="steptitle submittitle" id="steptitle2">
					<span id="stepnumber2">2</span>
					Save
				</div>
				<p class="taskbuttondiv">
					<input name="bottom_Cancel" class="button-2" onclick="window.location.href='<%= courseDocsURL %>'" type="button" value="Cancel"/>
					<input name="bottom_Submit" class="submit button-1" onclick="" type="submit" value="Submit"/>
				</p>
			</form>
			
		</bbUI:coursePage>

	</bbUI:docTemplate>

</bbData:context>
</bbUI:coursePage>
