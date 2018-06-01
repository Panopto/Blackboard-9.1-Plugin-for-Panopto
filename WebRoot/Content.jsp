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
<%@page import="blackboard.platform.plugin.PlugInUtil" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@taglib uri="/bbNG" prefix="bbNG" %>
<%@taglib uri="/bbData" prefix="bbData"%>

<bbData:context id="ctx">

<%
final String iconUrl = "/images/ci/icons/bookopen_u.gif";
final String page_title = "Panopto Content";

// Passed from Blackboard.
String course_id = request.getParameter("course_id");

String courseConfigURL = PlugInUtil.getUri("ppto", "PanoptoCourseTool", Utils.courseConfigScriptURL)
                            + "?course_id=" + course_id;
                            
String courseResetURL = PlugInUtil.getUri("ppto", "PanoptoCourseTool", Utils.courseResetURL)
                            + "?course_id=" + course_id;

PanoptoData ccCourse = new PanoptoData(ctx);

Boolean courseHasBeenReset = false;
Boolean useOldLayout = false;


%>
    <bbNG:learningSystemPage>
        <bbNG:cssFile href="css/main.css" />
            <style>
            #containerdiv
            {
                overflow: hidden;
                padding:0px 0px;
                height: 800px;
            }

            #classicView
            {
                overflow: auto;
                padding:0px 0px;
                height: auto;
                width:100%
            }
            
            .configureText
            {
                margin: 5px 0px;
            }
            
            .configureButtons
            {
                margin: 5px 0px;
                display: inline-block;
            }
            
            .configureButtonPrompt, .configureButtonBox
            {
                margin-top: 5px;
                padding: 5px;   
            }
            
            .configureButtonBox
            {
                border-radius: 0px 0px 4px 4px;
                background-color: white;
                border: 1px solid #AAA;
            }
            </style>
            <div id="courseContent" style="padding:0px 0px; height: 800px; overflow: auto">
            
                <!-- Add POST parameter to links into Panopto to activate SSO auto-login -->
                <form name="SSO" method="post">
                    <input type="hidden" name="instance" value="<%=Utils.pluginSettings.getInstanceName()%>" />
                </form>
                
                <%
                if(!ccCourse.isMapped() || courseHasBeenReset)
                { 
                    if(ccCourse.userMayConfig())
                    { %>
                        <div id="configButton" class="configureButtonPrompt">
                        <div class="configureText">
                        This course is not provisioned with Panopto. Before a course can be used with Panopto it must be setup.<br />
                        </div>
                        <div class="configureButtons">
                            <bbNG:button id="Configure" label="Configure" url="<%=courseConfigURL%>" />
                        </div>
                        <%
                        // The reset setting is on, so offer course reset option.
                        if (Utils.pluginSettings.getCourseResetEnabled())
                        {
                        %>
                        <div class="configureButtons">
                            <bbNG:button id="Reset Course" label="Reset Course" url="<%=courseResetURL%>" />
                        </div>
                        <%
                        }
                         %>
                    </div><%
                    }
                    else
                    { 
                        %> Please contact your administrator or instructor.<%
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
                            String ua = request.getHeader("User-Agent");
                            boolean isIe9or10 = (
                                   ua != null 
                                && (   ua.indexOf("MSIE 10") != -1
                                    || ua.indexOf("MSIE 9") != -1));
                            if (isIe9or10 || folders.length > 1)
                            {
                                useOldLayout = true;
                            }

                            for (int i = 0; i < folders.length; i++)
                            {
                                if (folders[i] == null)
                                {
                                    %><div class="error">Error getting the folder from the Panopto server. It may have been deleted.</div><%
                                }
                                else
                                {
                                    // If we are in IE 9 or 10, or if we are linked to more than one folder,
                                    // we should use the old display method. The iframe of the folder
                                    // session list page is optimized for a course with just one folder
                                    // and will have issues in IE 9 or 10.

                                    if (!useOldLayout)
                                    {
                                            // New layout for modern browsers
                                            String folderUrl = folders[i].getEmbedUrl() +"&instance=" + Utils.pluginSettings.getInstanceName();
                                            String contentHostName = "courseContent";
                                            
                                            if (folderUrl.split("\\:")[0].equals(request.getRequestURL().toString().split("\\:")[0])) {
                                            	%><iframe src="<%=folderUrl%>" height="100%" width="100%" ></iframe><%
                                            } else {
                                            	%><div class="error">Couldn't embed the Panopto video because the Blackboard server and Panopto server use mismatched HTTP/HTTPS protocols. We recommend both servers use HTTPS.</div><%
                                            }
                                    }
                                    else
                                    {
                                        %><div id="classicView"  height="100%" width="100%"><div class="sectionHeader"><h2>Panopto Folder for: <%=ccCourse.getFolderDisplayNames()[i] %></h2></div><%
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

                                        // Finally output the recorder downloads
                                        if(ccCourse.IsInstructor())
                                        {
                                            %><div class="sectionHeader">Links</div><%
                                            RecorderDownloadUrlResponse systemInfo = ccCourse.getRecorderDownloadUrls();
                                            %><div class="courseLink">
                                                  Download Panopto Recorder
                                                  (<a href="<%=systemInfo.getWindowsRecorderDownloadUrl()%>">Windows</a>
                                                   | <a href="<%=systemInfo.getMacRecorderDownloadUrl()%>">Mac</a>)
                                                 </div><%
                                        }
                                    }
                                    %></div><%
                                }
                            }
                        }
                    }
                    catch(Exception ex)
                    {
                        %><br><br><span class='error'>Error getting Panopto course content.</span><%
                    }
                } %>
            </div></div>
<%
            if(ccCourse.isMapped() && ccCourse.userMayConfig())
            { %>
                <div id="configButtons" class="configureButtonBox">
                <div class="configureText">
                You can update the folders configured for this course in Panopto.
                <%
                // The reset setting is on, so offer course reset option.
                if (Utils.pluginSettings.getCourseResetEnabled())
                {
                %>
                    You can reset the server configuration for this course to the Panopto server.
                <%
                }
                %>
                </div>
                <div class="configureButtons">
                    <bbNG:button id="Re-Configure" label="Re-Configure" url="<%=courseConfigURL%>" />
                </div>
                <%
                // The reset setting is on, so offer course reset option.
                if (Utils.pluginSettings.getCourseResetEnabled())
                {
                %>
                <div class="configureButtons">
                    <bbNG:button id="Reset Course" label="Reset Course" url="<%=courseResetURL%>" />
                </div>
                <%
                }
            }
                     %>
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
                
                window.onload = function(){ fitToWindow(); };
                
                window.onresize = function () { setTimeout(fitToWindow(), 150); };
                
                function fitToWindow()
                {
                    var heightBuffer = 190;
                    var windowHeight = 800;
                    var navBarHeight = 650;

                    if (typeof window.innerHeight != 'undefined')
                    {
                        windowHeight = window.innerHeight;
                    }

                    // Take the window height and subtract the top tabs (90 px) and some buffer for incidental items
                    var contentHeight = windowHeight - heightBuffer;

                    // The nav bar works better with the page at this height.
                    document.getElementById("navigationPane").style.height = navBarHeight;
                    var blackboardPageHeight = Math.max(navBarHeight, contentHeight);

                    // Set height of content divs column.
                    // Containerdiv is created by the BBNG presets.
                    document.getElementById("containerdiv").style.height = contentHeight;
                    
                    document.getElementById("courseContent").style.height = contentHeight;
                    document.getElementById("contentPanel").style.height = blackboardPageHeight;
                }
              </script>
    </bbNG:learningSystemPage>
</bbData:context>
