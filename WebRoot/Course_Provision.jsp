<%@page import="java.util.Arrays"%>
<%@page language="java" pageEncoding="ISO-8859-1" %>

<%@page import="com.panopto.blackboard.Utils"%>
<%@page import="blackboard.data.course.Course"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.panopto.blackboard.PanoptoData"%>
<%@page import="com.panopto.services.*"%>
<%@page import="blackboard.base.BbList"%>

<%@taglib uri="/bbUI" prefix="bbUI" %>
<%@taglib uri="/bbData" prefix="bbData"%>

<%
String iconUrl = "/images/ci/icons/bookopen_u.gif";
String page_title = "Configure Panopto Focus Connector";

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

boolean reprovisionAll = request.getParameter("reprovisionAll") != null;

%>

	<bbData:context id="ctx">

		<bbUI:docTemplate title="<%=page_title%>">

			<bbUI:docTemplateHead>
				<link rel="stylesheet" type="text/css" href="main.css" />
			</bbUI:docTemplateHead>
	
			<bbUI:breadcrumbBar>
				<bbUI:breadcrumb><%=page_title%></bbUI:breadcrumb>
			</bbUI:breadcrumbBar>

			<bbUI:titleBar iconUrl="<%=iconUrl%>">
				<%=page_title%>
			</bbUI:titleBar>
<%
if((provisionServerName != null) && !provisionServerName.trim().isEmpty() && (bbCourses != null && !bbCourses.trim().isEmpty()))
{
%>
			<div id='batchProvisionResultsHeader'>Provisioning Results:</div>
			<div id='batchProvisionResults'>
	<%
	provisionServerName = provisionServerName.trim();
	String[] arrCourseIDs = bbCourses.split(",");
	
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
					com.panopto.services.User[] instructors = ccCourse.getInstructors();
					if (instructors == null)
					{
					%>
								<div class='errorMessage'>Error getting instructors.</div>
					<%
					}
					else if(instructors.length > 0)
					{
						List<String> lstInstructorInfo = new ArrayList<String>();
						for(com.panopto.services.User instructor : instructors)
						{
							lstInstructorInfo.add(instructor.getUserKey() + " (" + instructor.getFirstName() + " " + instructor.getLastName() + " &lt;" + instructor.getEmail() + "&gt;)");
						}
								%><%=Utils.join(lstInstructorInfo, "<br />")%><%
					}
					else
					{
					%>
								<div class='errorMessage'>No instructors.</div>
					<%
					}
					%>
							</div>
				
							<div class='attribute'>Students</div>
							<div class='value'>
					<%
					List<String> students = ccCourse.getStudentUserKeys();
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
		
		BbList<Course> allCourses = PanoptoData.GetAllCourses();
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
<% } %>
		<div>
			<bbUI:button type="INLINE" name="OK" alt="OK" action="LINK" targetUrl="<%=returnUrl%>" />
		</div>
		</bbUI:docTemplate>

	</bbData:context>
