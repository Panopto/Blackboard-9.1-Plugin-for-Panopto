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

<%@page import="java.util.Arrays"%>
<%@page language="java" pageEncoding="ISO-8859-1" %>

<%@page import="com.panopto.blackboard.Utils"%>
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
String page_title = "Configure Panopto Connector";

String returnUrl = request.getParameter("returnUrl");


String provisionServerName = null;

List<String> serverList = Utils.pluginSettings.getServerList(); 
if(serverList.size() == 1)
{
    provisionServerName = serverList.get(0);
}
else
{
    provisionServerName = request.getParameter("provisionServerName");
}
String bbCourses = request.getParameter("bbCourses");
String csvCourses = request.getParameter("CSVString");
boolean reprovisionAll = request.getParameter("reprovisionAll") != null;

%>
<bbUI:coursePage>
    <bbData:context id="ctx">
        <bbUI:docTemplate title="<%=page_title%>">
        <c:catch>
            <bbUI:docTemplateHead>
                <link rel="stylesheet" type="text/css" href="main.css" />
            </bbUI:docTemplateHead>

            <bbUI:titleBar iconUrl="<%=iconUrl%>">
                <%=page_title%>
            </bbUI:titleBar>
        </c:catch>
<%

if((   provisionServerName != null) 
    && !provisionServerName.trim().isEmpty() 
    && (   (bbCourses != null && !bbCourses.trim().isEmpty())
        || (csvCourses != null && !csvCourses.trim().isEmpty())))
{
%>
            <div id='batchProvisionResultsHeader'>Provisioning Results:</div>
            <div id='batchProvisionResults'>
    <%
    provisionServerName = provisionServerName.trim();
    
    //Get individual lists of course IDs from both text input and csv
    String[] inputCourseIDs = null;
    if (bbCourses != null)
    {
        inputCourseIDs = bbCourses.split(",");
    }
    else
    {
        inputCourseIDs = new String[0];
    }
    
    String[] csvCourseIDs = null;
    if (csvCourses != null)
    { 
        csvCourseIDs = csvCourses.split(",");
    }
    else
    {
        csvCourseIDs = new String[0];
    }
    
    
    //Concatenate CourseId lists
    int aLen = inputCourseIDs.length;
    int bLen = csvCourseIDs.length;
    String[] arrCourseIDs = new String[aLen + bLen];
    System.arraycopy(inputCourseIDs, 0, arrCourseIDs, 0, aLen);
    System.arraycopy(csvCourseIDs, 0, arrCourseIDs, aLen, bLen);
    
    for(String courseID : arrCourseIDs)
    {
        courseID = courseID.trim();

        if(!courseID.equals(""))
        {
            PanoptoData ccCourse = new PanoptoData(courseID, ctx.getUser().getUserName());
            
            if(ccCourse.getBBCourse() != null)
            {
                if (!ccCourse.userMayProvision())
                {
                    %><div class='error'>Error. You do not have access to provision <%=ccCourse.getBBCourse().getTitle()%></div><%
                }
                else
                {
                %>
                        <div class='courseProvisionResult'>

                            <div class='attribute'>Course Name</div>
                            <div class='value'><%=ccCourse.getBBCourse().getTitle()%></div>
                                              
                            <div class='attribute'>Instructors</div>
                            <div class='value'>
                    <%
                    List<String> instructors = ccCourse.getInstructors();
                    if (instructors == null)
                    {
                    %>
                                <div class='errorMessage'>Error getting instructors.</div>
                    <%
                    }
                    else if(instructors.size() > 0)
                    {
                                %><%=Utils.join(instructors, "<br />")%><%
                    }
                    else
                    {
                    %>
                                <div class='errorMessage'>No instructors.</div>
                    <%
                    }
                    %>
                            </div>

                            <div class='attribute'>Teaching Assistants</div>
                            <div class='value'>
                    <%
                    List<String> tas = ccCourse.getTAs();
                    if (tas == null)
                    {
                    %>
                                <div class='errorMessage'>Error getting teaching assistants.</div>
                    <%
                    }
                    else if(tas.size() > 0)
                    {
                                %><%=Utils.join(tas, "<br />")%><%
                    }
                    else
                    {
                    %>
                                <div class='errorMessage'>No teaching assistants.</div>
                    <%
                    }
                    %>
                            </div>
                                            
                            <div class='attribute'>Students</div>
                            <div class='value'>
                    <%
                        List<String> students = ccCourse.getStudents();
                                            if (students == null)
                                            {
                    %>
                                <div class='errorMessage'>Error getting students.</div>
                    <%
                    }
                    else if(students.size() > 0)
                    {
                                %><%=Utils.join(students, ", ")%><%
                    }
                    else
                    {
                    %>
                                <div class='errorMessage'>No students.</div>
                    <%
                    }
                    %>
                            </div>
                        
                    <%
                    if (ccCourse.getFolderDisplayNames() != null && ccCourse.getFolderDisplayNames().length > 0)
                    {
                    %>
                        <div class='attribute'>Folders</div>
                        <div class='value'>
                        <%=Utils.join(Arrays.asList(ccCourse.getFolderDisplayNames()), ", ")%>
                        </div>
                    <%
                    }
                    
                    %>
        
                            <div class='attribute'>Result</div>
                            <div class='value'>
                    <%
                    if (ccCourse.isMapped() && provisionServerName.toLowerCase().equals(ccCourse.getServerName().toLowerCase()))
                    {
                        if (ccCourse.reprovisionCourse())
                        {
                            %><div class='successMessage'>Successfully reprovisioned course <%= ccCourse.getBBCourse().getTitle() %></div><%
                        }
                        else
                        {
                            %><div class='errorMessage'>Error reprovisioning course <%= ccCourse.getBBCourse().getTitle() %></div><%
                        }
                    }
                    else
                    {
                        if (ccCourse.provisionCourse(provisionServerName))
                        {
                            %><div class='successMessage'>Successfully provisioned course <%= ccCourse.getBBCourse().getTitle() %></div><%
                        }
                        else
                        {
                            %><div class='errorMessage'>Error provisioning course <%= ccCourse.getBBCourse().getTitle() %></div><%
                        }
                    }
                    %>
                            </div>
                
                        </div>
                <%
                }
            }
            else
            {
            %>
                    <div class='courseProvisionResult'>
                        <div class='errorMessage'>Error getting provisioning data for '<%= courseID %>'</div>
                    </div>
            <%
            }
        }
    }
    %>
            </div>
<%
}
else if (reprovisionAll)
{
    if (!Utils.userCanConfigureSystem())
    {
        %><div class='error'>Error. You do not have access to re-provision all courses.</div><%
    }
    else
    {
        %>
                <div id='batchProvisionResultsHeader'>Re-Provisioning Results:</div>
                <div id='batchProvisionResults'>
        <%
        
        java.util.List<Course> allCourses = PanoptoData.GetAllCourses();
        if (allCourses.size() == 0)
        {
            %><div class='errorMessage'>Unable to retrieve Blackboard courses</div><%
        }
        
        for (Course course : allCourses)
        {
            if (PanoptoData.HasPanoptoServer(course))
            {
                PanoptoData ccCourse = new PanoptoData(course, ctx.getUser().getUserName());
                
                // PanoptoData.HasPanoptoServer(course) just checks for a sentinal value in the blackboard registry.
                // We also check ccCourse.isMapped() which will only return true if all the needed fields are there.
                if (ccCourse.isMapped())
                {
                    if (ccCourse.reprovisionCourse())
                    {
                        %><div class='successMessage'>Successfully provisioned course <%= ccCourse.getBBCourse().getCourseId() %>: <%= ccCourse.getBBCourse().getTitle() %></div><%
                    }
                    else
                    {
                        %><div class='errorMessage'>Error provisioning course <%= ccCourse.getBBCourse().getCourseId() %>: <%= ccCourse.getBBCourse().getTitle() %></div><%
                    }
                }
            }
        }
    
        %>
                </div>
        <%  
    }
}
else
{
%>
    <div class='error'>Error no server selected.</div>
<% }
 %>
        <div>
            <bbUI:button type="INLINE" name="OK" alt="OK" action="LINK" targetUrl="<%=returnUrl%>" />
        </div>
        </bbUI:docTemplate>
    </bbData:context>
</bbUI:coursePage>
