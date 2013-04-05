<%@ include file="doctype_frameset.jspf" %> 
<!-- Copyright (c) Blackboard, Inc. 2003-2004.  All Rights Reserved. -->
<%@ page info="Course Search" %>
<%@ page isThreadSafe="true" errorPage="../error.jsp" %>
<%@ page import="
                blackboard.platform.intl.*,
                blackboard.db.*,
                blackboard.base.*,
                blackboard.persist.*,
                blackboard.platform.*,
                blackboard.platform.persistence.*
                 "%>

<%@ taglib uri="/bbData" prefix="bbData"%>
<%@ taglib uri="/bbUI" prefix="bbUI"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:message var="searchFormStr" key="search.form.frame.title" bundle="${bundles.cs_search}"/>
<fmt:message var="searchResultsStr" key="search.results.frame.title" bundle="${bundles.cs_search}"/>

<bbData:context id="ctx">

<% BundleManager bundleManager = BundleManagerFactory.getInstance();%>
<% BbResourceBundle resourceBundle  = bundleManager.getBundle("cs_search"); %>
<% BbResourceBundle commonBundle  = bundleManager.getBundle("cs_common"); %>

<%
    String pageSearchFor = resourceBundle.getString("search.terms.searchfor");
    String termCourse = commonBundle.getString("bbterms.course");
    String termOrgs = commonBundle.getString("bbterms.org");
%>

<%
  String type = "course";
  String printType = termCourse;
  if ("org".equalsIgnoreCase(request.getParameter("type")) )
  {
	type = "org";
    printType = termOrgs;
  }
%>
<html dir="${bbUI:htmlDirValue()}">
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE><%=pageSearchFor%> <%=printType%></TITLE>
</HEAD>
<%
  String searchFormSrc = "left_frame.jsp?type=" + type;
  String searchResultSrc = "right_frame.jsp?type=" + type;
%>
<bbUI:frameset rows="100%" cols="290px,*" frameborder="yes">

  <bbUI:frame name="Search_Form" title="${searchFormStr}" src="<%=searchFormSrc%>" marginheight="0" marginwidth="0"/>
  <bbUI:frame name="Search_Results" title="${searchResultsStr}" src="<%=searchResultSrc%>" marginheight="0" marginwidth="0"/>
  <noframes>
		<h1><fmt:message key="noframe.text" bundle="${bundles.content}"/></h1>	  
  </noframes>
</bbUI:frameset>
</HTML>
</bbData:context>
