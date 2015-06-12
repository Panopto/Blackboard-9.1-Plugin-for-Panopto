<%@ include file="doctype.jspf"%>

<!-- Copyright (c) Blackboard, Inc. 2003-2004.  All Rights Reserved. -->
<%@ page info="Course Search" %>
<%@ page isThreadSafe="true" errorPage="../error.jsp" %>
<%@ page import="blackboard.base.BaseComparator,
                 blackboard.base.GenericFieldComparator,
                 blackboard.data.course.Course,
                 blackboard.platform.*,
                 blackboard.platform.intl.*,
                 blackboard.util.*,
                 blackboard.util.StringUtil,
                 blackboard.webapps.searchwidgets.*,
                 java.util.*" %>

<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib uri="/bbUI" prefix="bbUI"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
  String match = request.getParameter("match");
  String bodyClass = "";
  if ( match == null )
  {
    bodyClass = "popup";
  }
%>

<bbNG:genericPage ctxId="ctx" bodyClass="<%=bodyClass %>">

<fmt:message var="pageSearchFor" key="search.terms.searchfor" bundle="${bundles.cs_search}"/>
<fmt:message var="pageNoCoursesFound" key="coursesearch.messages.nocoursesfound" bundle="${bundles.cs_search}"/>
<fmt:message var="pageNoOrgsFound" key="coursesearch.messages.noorgsfound" bundle="${bundles.cs_search}"/>
<fmt:message var="pageEnterCriteria" key="coursesearch.messages.entercriteriaonleft" bundle="${bundles.cs_search}"/>
<fmt:message var="institutionLabel" key="search.messages.institution" bundle="${bundles.cs_search}"/>
<fmt:message var="pageCancel" key="action.cancel" bundle="${bundles.cs_common}"/>
<fmt:message var="pageSubmit" key="action.submit" bundle="${bundles.cs_common}"/>
<fmt:message var="pageSelect" key="action.select" bundle="${bundles.cs_common}"/>
<fmt:message var="termDescription" key="bbterms.description" bundle="${bundles.cs_common}"/>

<% BundleManager bundleManager = BundleManagerFactory.getInstance();
   BbResourceBundle resourceBundle  = bundleManager.getBundle("cs_search");
   BbResourceBundle commonBundle  = bundleManager.getBundle("cs_common");

   String termCourseName = commonBundle.getString("bbterms.coursename");
   String termCourseId = commonBundle.getString("bbterms.courseid");
   String termOrgName = commonBundle.getString("bbterms.orgname");
   String termOrgId = commonBundle.getString("bbterms.orgid");

    String strMaxRows = ""+ CourseSearch.MAX_ROWS;

    List<Course> courses = new ArrayList<Course>();
    String searchString = request.getParameter("search_string");
    if ( searchString == null )
    {
      searchString = "";
    }

    boolean isCourseSearch = !"org".equalsIgnoreCase(request.getParameter("type"));
    if ( match != null && StringUtil.notEmpty(searchString) )
    {
      boolean exactMatch = "exact".equals(match );
      int searchBy = CourseSearch.SEARCH_BY_COURSE_NAME;
      if ( "course_name".equals(request.getParameter("search_field")) )
      {
        searchBy = CourseSearch.SEARCH_BY_COURSE_NAME;
      }
      if ( "description".equals(request.getParameter("search_field")) )
      {
        searchBy = CourseSearch.SEARCH_BY_COURSE_DESC;
      }
      if ( "course_id".equals(request.getParameter("search_field")) )
      {
        searchBy = CourseSearch.SEARCH_BY_COURSE_ID;
      }
      courses = CourseSearch.searchForCourses(searchString, exactMatch, searchBy, isCourseSearch);
    }

    GenericFieldComparator<Course> compCourseId = new GenericFieldComparator<Course>( BaseComparator.ASCENDING, "getCourseId", Course.class );
    GenericFieldComparator<Course> compTitle = new GenericFieldComparator<Course>( BaseComparator.ASCENDING, "getTitle", Course.class );
    GenericFieldComparator<Course> compDescription = new GenericFieldComparator<Course>( BaseComparator.ASCENDING, "getDescription", Course.class );

    // Switch to print out the correct headers for course versus orgs
    String strCourseIdHeader = termCourseId;
    String strCourseNameHeader = termCourseName;

    if ( !isCourseSearch )
    {
      strCourseIdHeader = termOrgId;
      strCourseNameHeader = termOrgName;
    }
    String pageCancel = commonBundle.getString("action.cancel");
    String pageSubmit = commonBundle.getString("action.submit");
    String pageSelect = commonBundle.getString("action.select");
    pageContext.setAttribute("strMaxRows", strMaxRows );
%>

  <fmt:message var="strMaxResults" key="search.maxresults" bundle="${bundles.cs_common}">
    <fmt:param value="${strMaxRows}"/>
  </fmt:message>

<bbNG:jsBlock>
<script>
  function pickCourses()
  {
    var form = document.forms["right_frame"];
    val = top.opener.inputToSet.value.trim();
    // Check to see that they've found at least one record
    if (form.elements.length == 0)
    {
      alert("${bbNG:EncodeLabel(pageEnterCriteria)}");
      return;
    }

    for ( i = 0; i < form.elements.length; i++ )
    {
      if ( form.elements[i].name = "courseid" && form.elements[i].checked )
      {
        if ( val.length > 0 )
        {
          val = val + ",";
        }
        val = val + form.elements[i].value;
      }
    }

    top.opener.inputToSet.value = val;
    parent.window.close();
  }
</script>
</bbNG:jsBlock>
<bbNG:form name="right_frame">
<%
  if ( courses.size() > 0 )
  {
    if ( courses.size() == CourseSearch.MAX_ROWS )
    {
      out.println("<strong>" + String.valueOf(pageContext.getAttribute("strMaxResults")) + "</strong><p>");
    }
%>
<bbNG:inventoryList collection="<%=courses %>"  objectVar="course" className="Course" emptyMsg="<strong>${pageNoCoursesFound}</strong> <br/> ${pageEnterCriteria}">
  <bbNG:listCheckboxElement name="theCheckbox" value="<%=course.getCourseId()%>"/>
  <bbNG:listElement  label='<%=strCourseIdHeader%>' name="courseid" comparator="<%=compCourseId%>"  isRowHeader="true">
    <%=course.getCourseId()%>
  </bbNG:listElement>
  <bbNG:listElement  label='<%=strCourseNameHeader%>' name="coursename" comparator="<%=compTitle%>">
    <%=course.getTitle()%>
  </bbNG:listElement>
  <bbNG:listElement  label='${termDescription}' name="description" comparator="<%=compDescription%>">
    <%=course.getDescription()%>
  </bbNG:listElement>

  <bbNG:listOptions allowEditPaging="true"/>
</bbNG:inventoryList>
<%
  }
  else
  {
    if ( isCourseSearch )
    {
%>
    <%if ( searchString != null && !searchString.equals("") ) {%>
      <strong>${pageNoCoursesFound}</strong>
    <%}%>
    <p>
    ${pageEnterCriteria}
<%
    }
    else
    {
%>
    <%if ( searchString != null && !searchString.equals("") ) {%>
      <strong>${pageNoOrgsFound}</strong>
    <%}%>
    <p>
    ${pageEnterCriteria}
<%
    }
  }
  if ( courses.size() > 0 )
  {
    %>
    <bbNG:genericSubmit>
      <bbNG:genericSubmitButton label="${pageCancel}" onClick="parent.window.close(); return false;" primary="false"/>
      <bbNG:genericSubmitButton label="${pageSubmit}" onClick="pickCourses(); return false;" primary="true"/>
    </bbNG:genericSubmit>
    <%
  } else {
    %>
    <bbNG:genericSubmit>
      <bbNG:genericSubmitButton label="${pageCancel}" onClick="parent.window.close(); return false;" primary="false"/>
    </bbNG:genericSubmit>
<%
  }
%>
</bbNG:form>
</bbNG:genericPage>
