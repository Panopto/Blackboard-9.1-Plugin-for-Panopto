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
<%@page import="java.util.*"%>
<%@page import="blackboard.platform.security.SecurityUtil"%>

<%@taglib uri="/bbUI" prefix="bbUI" %>
<%@taglib uri="/bbData" prefix="bbData"%>

<%
String action = request.getParameter("action");
if("delete".equals(action))
{
	Utils.clearLogData();
	
	response.sendRedirect(Utils.logScriptURL);
}

String iconUrl = "/images/ci/icons/bookopen_u.gif";
String page_title = "Panopto Log Data";
%>
	<bbData:context id="ctx">

		<bbUI:docTemplate title="<%= page_title %>">

			<bbUI:docTemplateHead>
				<link rel="stylesheet" type="text/css" href="main.css" />
			</bbUI:docTemplateHead>
	
			<bbUI:breadcrumbBar>
				<bbUI:breadcrumb><%= page_title %></bbUI:breadcrumb>
			</bbUI:breadcrumbBar>
<%

// Check for errors loading the list
if(!SecurityUtil.userHasEntitlement("course.configure-tools.EXECUTE"))
{
%>
			<bbUI:receipt type="FAIL" iconUrl="<%=iconUrl%>" title="<%=page_title%>">
				Not authorized.
			</bbUI:receipt>
<%
}
else
{
	String logData = Utils.getLogData();
	
	if(logData != null)
	{
	%>
		<div>
			<bbUI:button type="INLINE" name="Refresh" alt="Refresh" action="LINK" targetUrl='<%= "?action=refresh&nonce=" + System.currentTimeMillis() %>' />
			<bbUI:button type="INLINE" name="Clear Log" alt="Clear Log" action="LINK" targetUrl="?action=delete" />
		</div>

		<br/>
		
		<%= logData.replace(System.getProperty("line.separator"), "<br />") %>
	<%
	}
}
%>
		</bbUI:docTemplate>

	</bbData:context>
