<!-- Copyright Panopto 2009 - 2016
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

<%@page import="java.util.Arrays"%>
<%@page language="java" pageEncoding="ISO-8859-1" %>

<%@page import="com.panopto.blackboard.Utils"%>
<%@page import="com.panopto.blackboard.PanoptoCourseSearch"%>
<%@page import="blackboard.data.course.Course"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.panopto.blackboard.PanoptoData"%>
<%@page import="com.panopto.services.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@taglib uri="/bbUI" prefix="bbUI" %>
<%@taglib uri="/bbData" prefix="bbData"%>

<%
String iconUrl = "/images/ci/icons/bookopen_u.gif";
String page_title = "Bulk Folder List Navigation Links on Provisioned Courses";

String returnUrl = request.getParameter("returnUrl");
%>
<bbUI:coursePage>
    <bbData:context id="ctx">
        <bbUI:docTemplate title="<%=page_title%>">
        <c:catch>
            <bbUI:docTemplateHead>
                <link rel="stylesheet" type="text/css" href="css/main.css" />
            </bbUI:docTemplateHead>

            <bbUI:titleBar iconUrl="<%=iconUrl%>">
                <%=page_title%>
            </bbUI:titleBar>
        </c:catch>
<%

if (!Utils.userCanConfigureSystem())
{
    %><div class='error'>Error. You do not have access to create an list links for courses.</div><%
}
else
{
    %>
            <div id='bulkListLinkResultsHeader'>Folder List Link Results:</div>
            <div id='bulkListLinkResults'>
    <%
    
    java.util.List<Course> allCourses = PanoptoCourseSearch.GetAllCourses();
    if (allCourses.size() == 0)
    {
        %><div class='errorMessage'>Unable to retrieve list of provisioned Blackboard courses</div><%
    }
    
    for (Course course : allCourses)
    {
        if (PanoptoData.HasPanoptoServer(course))
        {
            PanoptoData ccCourse = new PanoptoData(course, ctx.getUser().getUserName());
            
            if (ccCourse.addCourseMenuFolderListLinks()) {
                %><div class='successMessage'>Added folder list links to course <%= ccCourse.getBBCourse().getCourseId() %>: <%= ccCourse.getBBCourse().getTitle() %></div><%
            } else {
                %><div class='errorMessage'>Unable to add folder list links for course <%= ccCourse.getBBCourse().getCourseId() %>: <%= ccCourse.getBBCourse().getTitle() %>. Please see Panopto block logs for more details.</div><%
            }
        }
    }

    %>
            </div>
    <%  
}
 %>
        <div>
            <bbUI:button type="INLINE" name="OK" alt="OK" action="LINK" targetUrl="<%=returnUrl%>" />
        </div>
        </bbUI:docTemplate>
    </bbData:context>
</bbUI:coursePage>
