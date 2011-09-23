<%@ include file="doctype.jspf"%>
<!-- Copyright (c) Blackboard, Inc. 2003-2004.  All Rights Reserved. -->
<%@ page info="Course Search" %>
<%@ page isThreadSafe="true" errorPage="../error.jsp" %>
<%@ page import="
                blackboard.platform.intl.*,
                blackboard.db.*,
                blackboard.util.*,
                blackboard.base.*,
                blackboard.persist.*,
                blackboard.platform.*,
                blackboard.platform.persistence.*
                 "%>

           
<%@ taglib uri="/bbUI" prefix="bbUI"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:message var="pageTitle" key="search.dosearch.pagetitle" bundle="${bundles.cs_search}"/>
<fmt:message var="pageSearchFor" key="search.terms.searchfor" bundle="${bundles.cs_search}"/>
<fmt:message var="pageSearchString" key="search.terms.searchstring" bundle="${bundles.cs_search}"/>
<fmt:message var="pageSearchField" key="search.terms.searchfield" bundle="${bundles.cs_search}"/>
  <fmt:message var="termContains" key="search.terms.contains" bundle="${bundles.cs_search}"/>
  <fmt:message var="termExact" key="search.terms.exactmatch" bundle="${bundles.cs_search}"/>
  <fmt:message var="termSearch" key="search.terms.search" bundle="${bundles.cs_search}"/>
  <fmt:message var="viewcatalogSearchCriteriaWarning"
	key="viewcatalog.search.criteria.warning" bundle="${bundles.catalog}" />

<bbNG:genericPage ctxId="ctx" bodyClass="popup" title="${pageTitle}">

<% BundleManager bundleManager = BundleManagerFactory.getInstance();%>
<% BbResourceBundle resourceBundle  = bundleManager.getBundle("cs_search"); %>
<% BbResourceBundle commonBundle  = bundleManager.getBundle("cs_common"); %>
  
<%
    String termCourse = commonBundle.getString("bbterms.course");
    String termOrgs = commonBundle.getString("bbterms.org");
    String termDescription = commonBundle.getString("bbterms.description");
%>

<%  
    String termId = commonBundle.getString("bbterms.courseid");
    String strTypeName = termCourse;
    String strTypeFullName = commonBundle.getString("bbterms.coursename");
    String strType =  request.getParameter("type");
    if (strType == null) {
        strTypeName = termCourse;
    } else if (strType.equalsIgnoreCase("org")) {
        strTypeName = termOrgs;
        strTypeFullName = commonBundle.getString("bbterms.orgname");
        termId = commonBundle.getString("bbterms.orgid");
    }
    
    String strOption =  request.getParameter("options");
    if (strOption == null) {
        strOption = "false";
    }

    String strSearchString = request.getParameter("search_string");
    if (strSearchString == null) {
        strSearchString = "";
    }

    boolean boolOption = new Boolean(strOption).booleanValue();

%>


<bbNG:cssBlock>
<STYLE type="text/css">
.inputTypeButton
{
	COLOR:#003366;
	FONT-FAMILY:Arial, Helvetica, Sans Serif;
	FONT-SIZE: 10 pt;
	BORDER-WIDTH: 1 px;
	BORDER-STYLE: solid;
	BORDER-TOP-COLOR: #CCCCCC;
	BORDER-BOTTOM-COLOR: #333333;
	BORDER-LEFT-COLOR: #CCCCCC;
	BORDER-RIGHT-COLOR: #333333;
	BACKGROUND-COLOR: #EEEEEE;
}
form[name=searchForm]
{
  overflow:auto;
}
</STYLE>
</bbNG:cssBlock>
<bbNG:jsBlock>
<SCRIPT LANGUAGE="JavaScript">
    function searchNow() 
    {
      if (document.searchForm.search_string.value=="")
       {
         alert('${bbNG:EncodeLabel(viewcatalogSearchCriteriaWarning)}');
       }
       else
       {
         document.forms.searchForm.submit();
       }
    }

    function changeOptions(f, options) {
            
        f.target = "_self";
        f.action = "left_frame.jsp";
        f.options.value = options;
        f.submit();
        
    }
</SCRIPT>
</bbNG:jsBlock>

<bbNG:form target="Search_Results" action="right_frame.jsp" name="searchForm">
<input type="hidden" name="type" value="<%=request.getParameter("type")%>">
<INPUT TYPE='hidden' NAME="options" VALUE="<%=boolOption%>">

<!-- BEGIN SEARCH WIDGET TABLE -->
<TABLE width="100%" cellpadding="2" cellspacing="6" border="0">
<!-- START SEARCH TITLE ROW -->
  <TR class="bAccentMedium">
	<TD width="100%" nowrap ><SPAN class="label">&nbsp;${pageTitle}</SPAN></TD>
  </TR>
<!-- END SEARCH TITLE ROW -->

 <TR>
	<TD>
<!-- START SIMPLE SEARCH -->
	  <TABLE width="100%" cellpadding="0" cellspacing="0" border="0">
		<TR>         
		  <TD nowrap><SPAN class="label">&nbsp;${pageSearchFor}&nbsp;</SPAN></TD>
		  <TD width="100%"  nowrap>
        <bbNG:textElement name="search_string" value="<%=strSearchString%>" size="20" title="${pageSearchFor}"/>
      </TD>
		</TR>
	  </TABLE>
<!-- END SIMPLE SEARCH -->
	  <HR width="100%" />
<!-- START OPTIONS AREA -->
	  <TABLE width="100%" cellpadding="0" cellspacing="0" border="0">
    	<!-- START ADVANCED OPTIONS -->
		<TR>
		  <TD valign="top" nowrap><SPAN class="label">&nbsp;${pageSearchString}&nbsp;</SPAN></TD>
		  <TD width="100%" valign="top" nowrap>
        <bbNG:radioElement name="match" value="contains" isSelected="true" id="match_contains" optionLabel="${termContains}" isVertical="true" />
        <bbNG:radioElement name="match" value="exact" id="match_exact" optionLabel="${termExact}" isVertical="true" />
      </TD>
		</TR>
		<TR>
		  <TD valign="top" nowrap><SPAN class="label">&nbsp;${pageSearchField}&nbsp;</SPAN></TD>
		  <TD width="100%" valign="top" nowrap>
        <bbNG:radioElement name="search_field" value="course_name" isSelected="true" id="search_field_course_name" optionLabel="<%=strTypeFullName%>" isVertical="true" />
        <bbNG:radioElement name="search_field" value="course_id" id="search_field_course_id" optionLabel="<%=termId%>" isVertical="true" />
        <bbNG:radioElement name="search_field" value="description" id="search_field_description" optionLabel="<%=termDescription%>" isVertical="true" />
      </TD>
		</TR>
	<!-- END ADVANCED OPTIONS -->
	  </TABLE>
<!-- END OPTIONS AREA -->
	  <HR width="100%" />
	  <TABLE width="100%" cellpadding="0" cellspacing="0" border="0">
		<TR>
		  <TD align="${bbUI:align('inverse')}"><INPUT type="button" value="<%=TextFormat.escape(String.valueOf(pageContext.getAttribute("termSearch")))%>" class="inputTypeButton" onclick='searchNow()'></TD>
		</TR>
	  </TABLE>
	</TD>
  </TR>
</TABLE>
</bbNG:form>
</bbNG:genericPage>



