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
<%@page import="blackboard.platform.security.SecurityUtil"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
<bbUI:coursePage>
	<bbData:context id="ctx">

		<bbUI:docTemplate title="<%= page_title %>">
		<c:catch>
			<bbUI:docTemplateHead>
				<link rel="stylesheet" type="text/css" href="main.css" />
			</bbUI:docTemplateHead>
		</c:catch>
<%

// Check for errors loading the list
if(!SecurityUtil.userHasEntitlement("course.panopto.EXECUTE"))
{
%>
			<bbUI:receipt type="FAIL" iconUrl="<%=iconUrl%>" title="<%=page_title%>">
				Not authorized.
			</bbUI:receipt>
<%
}
else
{
	// Parse out the limit parameter to know how much of the log to show
	String limitString = request.getParameter("limit");
	long limit = Utils.defaultLogLimit;
	if (limitString != null 
		&& limitString.length() > 0)
	{
		try
		{
			limit = Long.parseLong(limitString);
		}
		catch (NumberFormatException ex)
		{
		}
	}
	
	String logData = Utils.getLogData(limit);
	
	if(logData != null)
	{
	%>
		<div>
			<bbUI:button type="INLINE" name="Refresh" alt="Refresh" action="LINK" targetUrl='<%= "?action=refresh&nonce=" + System.currentTimeMillis() + "&limit=" + limit %>' />
			<bbUI:button type="INLINE" name="Clear Log" alt="Clear Log" action="LINK" targetUrl="?action=delete" />
		<%
		// Show a button to display all log content, or the most recent based on the current limit
		if (limit != Long.MAX_VALUE)
		{
		%>
			<bbUI:button type="INLINE" name="View All" alt="View All" action="LINK" targetUrl='<%= "?limit=" + Long.MAX_VALUE %>' />
		<%
		}
		else
		{
		%>
			<bbUI:button type="INLINE" name="View Recent" alt="View Recent" action="LINK" targetUrl='<%= "?limit=" + Utils.defaultLogLimit %>' />
		<%
		}
		%>
		</div>

		<br/>
		
		<%= logData.replace(System.getProperty("line.separator"), "<br />") %>
	<%
	}
}
%>
		</bbUI:docTemplate>

	</bbData:context>
</bbUI:coursePage>