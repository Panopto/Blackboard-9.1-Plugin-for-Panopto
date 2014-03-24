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
<%@page import="com.panopto.blackboard.PanoptoData"%>
<%@page import="com.panopto.services.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@taglib uri="/bbUI" prefix="bbUI" %>
<%@taglib uri="/bbData" prefix="bbData"%>

<bbUI:coursePage>
<bbData:context id="ctx">

<%
final String iconUrl = "/images/ci/icons/bookopen_u.gif";
final String page_title = "Panopto Focus Content";

// Passed from Blackboard.
String course_id = request.getParameter("course_id");

String courseConfigURL = Utils.courseConfigScriptURL
							+ "?course_id=" + course_id;
							
String courseResetURL = Utils.courseResetURL
							+ "?course_id=" + course_id;

PanoptoData ccCourse = new PanoptoData(ctx);

%>
	<bbUI:docTemplate title="<%=page_title%>">

		<bbUI:docTemplateHead>
			<link rel="stylesheet" type="text/css" href="main.css" />
		</bbUI:docTemplateHead>

		<bbUI:coursePage courseId="<%= ctx.getCourseId() %>">
		<c:catch>	
			<bbUI:titleBar iconUrl="<%=iconUrl%>">
				<%=page_title%>
			</bbUI:titleBar>
		</c:catch>
			<div id="courseContent">
			
				<!-- Add POST parameter to links into Panopto to activate SSO auto-login -->
				<form name="SSO" method="post">
					<input type="hidden" name="instance" value="<%=Utils.pluginSettings.getInstanceName()%>" />
				</form>
				
				<%
				if(ccCourse.userMayConfig())
				{ %>
					<div id="configButton">
						<bbUI:button type="INLINE" name="Configure" alt="Configure" action="LINK" targetUrl="<%=courseConfigURL%>" />
						<%
						if (!ccCourse.isOriginalContext() || (Utils.pluginSettings.getCourseResetEnabled()))
						{
						%>
						
						<bbUI:button type="INLINE" name="Reset Copied Course" alt="Reset Copied Course" action="LINK" targetUrl="<%=courseResetURL%>" />
						<%
						}
						 %>
					</div>
					<%
				}
				
				if(!ccCourse.isMapped())
				{ 
					%>This course is not provisioned with Panopto. Before a course can be used with Panopto it must be provisioned.<br /><%
					if(ccCourse.userMayProvision())
					{ 
						%> Please press the configure button to provision.<%
					}
					else
					{ 
						%> Please contact your administrator.<%
					}
				}
				else
				{
					try
					{
		        		// Get course info from SOAP service.
		        		Folder[] folders = ccCourse.getFolders();

						// SOAP call failed
						if(folders == null)
						{
							%><span class='error'>Error retrieving Panopto course.</span><%
						}
						else
						{
							for (int i = 0; i < folders.length; i++)
							{
								%><div class="sectionHeader"><%=ccCourse.getFolderDisplayNames()[i] %></div><%
								if (folders[i] == null)
								{
									%><div class="error">Error getting the folder from the Panopto server. It may have been deleted.</div><%
								}
								else
								{
									// Write out all the sessions (both live and completed)
									Session[] sessions = ccCourse.getSessions(folders[i].getId());
									if (sessions == null)
									{
										%><div class="error">Error getting the recordings from the Panopto server.</div><%								
									}
									else if (sessions.length == 0)
									{
										%><div class="completedRecording">No recordings.</div><%
									}
									else
									{
										for (Session s : sessions)
										{
											if (s.getState() == SessionState.Broadcasting)
											{
										        %><div class="liveLecture">
													<%=s.getName()%>
													<span class="liveLectureActions">
												 		[<a href="javascript:launchNotes('<%=s.getNotesURL()%>')"
												 			>take notes</a
												 	  	 >]
										 	  	[<a href="<%= s.getViewerUrl()%>" onclick="return startSSO(this)"
													 		>watch live</a
												 	  	 >]
												 	  	 	</span>
												 </div><%
											}
											else if (s.getState() == SessionState.Recording)
											{
											%><div class="liveLecture">
													<%=s.getName()%>
													<span class="liveLectureActions">
												 		[<a href="javascript:launchNotes('<%=s.getNotesURL()%>')"
												 			>take notes</a
												 	  	 >]
										 	  	 	</span>
												 </div><%
											}
											else
											{
												%><div class="completedRecording">
					      							<a href="<%=s.getViewerUrl()%>" onclick="return startSSO(this)">
					   								<%=s.getName()%>
					      							</a>
					   							  </div><%
											}
										}
									}
									
									// Write out the podcast links for this folder
									String audioPodcastURL = folders[i].getAudioPodcastITunesUrl();
									if((audioPodcastURL != null) && !audioPodcastURL.equals(""))
									{
										%><div class="courseLink">
				    						<img src="images/feed_icon.gif" />
				      						<a href="<%=audioPodcastURL%>">Audio Podcast</a>
				     						<span class="rssLink">(<a
				   								href="<%=folders[i].getAudioRssUrl()%>" target="_blank">RSS</a
				    						>)</span>
				                     	 </div><%
									}
									
									String videoPodcastURL = folders[i].getVideoPodcastITunesUrl();
									if((videoPodcastURL != null) && !videoPodcastURL.equals(""))
									{
										%><div class="courseLink">
				    						<img src="images/feed_icon.gif" />
				      						<a href="<%=videoPodcastURL%>">Video Podcast</a>
				     						<span class="rssLink">(<a
				   								href="<%=folders[i].getVideoRssUrl()%>" target="_blank">RSS</a
				    						>)</span>
				                     	 </div><%
									}
									
									// If applicable, write out the config links for this folder
									if(ccCourse.IsInstructor())
									{
										%><div class="courseLink">
				   							<a href="<%=folders[i].getSettingsUrl()%>" onclick="return startSSO(this)"
				   								>Panopto Folder Settings</a>
					                   	  </div><%
				                   	} 
			                   	}
							}
							
							// Finally output the recorder downloads
							if(ccCourse.IsInstructor())
							{
								%><div class="sectionHeader">Links</div><%
								RecorderDownloadUrlResponse systemInfo = ccCourse.getRecorderDownloadUrls();
								%><div class="courseLink">
								  	Download Panopto Focus Recorder
								  	(<a href="<%=systemInfo.getWindowsRecorderDownloadUrl()%>">Windows</a>
			   						| <a href="<%=systemInfo.getMacRecorderDownloadUrl()%>">Mac</a>)
			                   	  </div><%
		                	}
	                	}
                	}
                	catch(Exception ex)
                	{
                		%><br><br><span class='error'>Error getting Panopto course content.</span><%
                	}
				} %>
			</div>
				  	
		  	<script type="text/javascript">
                // Function to pop up Panopto live note taker.
                function launchNotes(url)
        		{
					// Open empty notes window, then POST SSO form to it.
					var notesWindow = window.open("", "PanoptoNotes", "width=500,height=800,resizable=1,scrollbars=0,status=0,location=0");
					document.SSO.action = url;
					document.SSO.target = "PanoptoNotes";
					document.SSO.submit();

					// Ensure the new window is brought to the front of the z-order.
					notesWindow.focus();
				}
				
				function startSSO(linkElem)
				{
					document.SSO.action = linkElem.href;
					document.SSO.target = "_blank";
					document.SSO.submit();
					
					// Cancel default link navigation.
		  			return false;
		  		}
		  	</script>
				  	
		</bbUI:coursePage>

	</bbUI:docTemplate>

</bbData:context>
</bbUI:coursePage>