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

<%
final String iconUrl = "/images/ci/icons/bookopen_u.gif";
final String page_title = "Reset Panopto Course";

// Passed from Blackboard to content create / modify pages
// Persist in form for use when redirecting back to create page
String course_id = request.getParameter("course_id");

String parentPageTitle = null;
String parentURL = null;
String selfURL = request.getRequestURL() + "?course_id=" + course_id; 
String courseDocsURL = null;

parentPageTitle = "Panopto Content";
parentURL = Utils.contentScriptURL +
            "?course_id=" + course_id;

String returnUrl = URLEncoder.encode(selfURL.toString(), "UTF-8");
String courseConfigURL = Utils.courseConfigScriptURL
                            + "?course_id=" + course_id;

%>

    <bbData:context id="ctx">
    <bbUI:coursePage courseId="<%= ctx.getCourseId() %>">
<%

// First check if the caller is allowed to be here
PanoptoData ccCourse = new PanoptoData(ctx);
if (!ccCourse.userMayConfig())
{
%>
    <bbUI:docTemplate title="<%=page_title%>">
        <bbUI:receipt type="FAIL" iconUrl="<%=iconUrl%>" title="<%=page_title%>" recallUrl="<%=parentURL%>">
            Only an admin can reset a course. 
        </bbUI:receipt>
    </bbUI:docTemplate>
<%
} else {
    // Reset the course to have no Panopto configuration.
    ccCourse.resetCourse(); %>
    <bbUI:docTemplate title="<%=page_title%>">
        <bbUI:receipt type="PASS" iconUrl="<%=iconUrl%>" title="<%=page_title%>" recallUrl="<%=parentURL%>">
            This course has been reset.
        </bbUI:receipt>
        <div id="configButton">
            <bbUI:button type="INLINE" name="Configure" alt="Configure" action="LINK" targetUrl="<%=courseConfigURL%>" />
        </div>
    </bbUI:docTemplate> 
    <%
} %>
    </bbUI:coursePage>
    </bbData:context>
