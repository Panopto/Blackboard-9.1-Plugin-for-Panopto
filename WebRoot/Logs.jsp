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
