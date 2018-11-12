<%@ page import="java.lang.reflect.Array"%>
<%@ page import="com.panopto.blackboard.PanoptoData"%>
<%@ page import="com.panopto.blackboard.Utils"%>
<%@ page import="blackboard.platform.plugin.PlugInUtil" %>
<%@ page import="com.panopto.services.Folder"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib uri="/bbData" prefix="bbData"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<bbData:context id="ctx">

<%
    //Get course information from page context
    PanoptoData ccCourse = new PanoptoData(ctx);

    String returnUrl = ctx.getRequestUrl();
    
    String toolUri = PlugInUtil.getUri("ppto", "PanoptoCourseTool", "");
    
    // Set a return Url to get back to the content page unless it cant be set properly, then just redirect back to the tool.
    if (returnUrl.indexOf(toolUri) != -1) {
        returnUrl = returnUrl.split(toolUri)[0] + "/webapps/blackboard/content/listContentEditable.jsp?content_id=" + ctx.getContentId().toExternalString() + "&course_id=" + ctx.getCourseId().toExternalString() + "&mode=reset";
    } else {
        returnUrl += "?course_id=" + ctx.getCourseId();
    }
    
    %><script>
    //Script to alert user and close window if course is not provisioned
    function AlertAndRedirect(){
        alert("This course is not provisioned with Panopto. Before a course can be used with Panopto it must be setup. Please contact your administrator or instructor.");
        window.location = "<%=returnUrl%>";
    }
    </script><%

    if(ccCourse.equals(null)){%>
        <script> AlertAndRedirect();</script>
    <%}
    
    String serverName = ccCourse.getServerName();
    Folder[] PanoptoFolders = ccCourse.getFolders();
    String folderId = "";
    //If course is associated with a single folder, append folder's id to iframe source url
    //This will automatically display course's video's in frame
    //  If more than one folder is provisioned to a course it will grab the folderId of the first folder with a valid Id.
    //  If no folders have a valid Id this tool will open to the first folder the user has creator access to, usually thier personal folder.
    if(PanoptoFolders != null){
        int folderCount = Array.getLength(PanoptoFolders);
        if(folderCount >= 1){
            for (int i = 0; i < folderCount; ++i) {
                if (PanoptoFolders[i].getId() != null) {
                    folderId += "&folderID=" + PanoptoFolders[i].getId();
                    break;
                }
            }
        }
        else if(folderCount == 0){
        %>
            <script> AlertAndRedirect();</script>
        <% 
        }
    }
    else{
    %>
    <script> AlertAndRedirect();</script>
    <%
    }
    //Generate source URL for iframe from info. Blackboard embeds require https
    String IFrameSrc = "https://" +serverName +"/Panopto/Pages/Sessions/EmbeddedUpload.aspx?playlistsEnabled=true&singleEmbedOnly=true&instance=" + Utils.pluginSettings.getInstanceName() + folderId;
%>
    <bbNG:learningSystemPage onLoad="doOnLoad()">

        <c:set var="addBreadcrumbStr" value="Add Panopto Video"/>
        <fmt:message var="cancelStr" key="action.cancel" bundle="${bundles.common}"/>
        <c:set var="pageTitle" value="Search for a Panopto video" />
        <c:set var="pageTitleInstructions" value="Look for a Panopto video you wish to embed as content." />
        
        <bbNG:breadcrumbBar environment="COURSE">
          <bbNG:breadcrumb title="${addBreadcrumbStr}" href="create?course_id=${ctx.courseId.externalString}&content_id=${ctx.contentId.externalString}"/>
          <bbNG:breadcrumb title="${pageTitle}" />
        </bbNG:breadcrumbBar>

        <bbNG:pageHeader instructions="${pageTitleInstructions}">
          <bbNG:pageTitleBar title="${pageTitle}" iconAlt="Panopto"/>
        </bbNG:pageHeader>

        <bbNG:jsBlock>
            <script type="text/javascript">
                //Perform on page load
                function doOnLoad(){
                    //Set to true when videos selected, false when none selected
                    var selected = false;
                    //Set event listener for page
                    var eventMethod = window.addEventListener ? "addEventListener" : "attachEvent";
                    var eventEnter = window[eventMethod];
                    var messageEvent = eventMethod === "attachEvent" ? "onmessage" : "message";
                    
                    //Event message handlers
                    eventEnter(messageEvent, function (e) {
                        var message = JSON.parse(e.data);
                        //If a video is chosen, enable the "Insert" button
                        if (message.cmd === 'ready') {
                            selected = true;
                        }
                        //If no video is chosen, disable the "Insert" button
                        if (message.cmd === 'notReady') { 
                            selected = false;
                        }
                        //Called when "Insert" is clicked. Creates HTML for embedding each selected video into the editor            
                            if (message.cmd === 'deliveryList') {       
                                if(selected){
                                    var returnString = "",
                                        PLAYLIST_EMBED_ID = 1,
                                        VIDEO_EMBED_ID = 0;
                                    
                                    // Name the content after the title of the 1st embed, user can edit this later.
                                    if (message.names && message.names.length > 0) {
                                        document.getElementById("content_title").value =  message.names[0];
                                    }
                                    
                                    //This is going to need to only pass the Id chunks, it looks link BB parses our params and strips out any 'dangerous' html
                                    for (var i = 0; i < message.ids.length; ++ i) {
                                        var idChunk = '';
                                        if ((message.playableObjectTypes != null) && (message.playableObjectTypes.size() > i) && (message.playableObjectTypes[i] === PLAYLIST_EMBED_ID)){
                                            idChunk = "&pid=" + message.ids[i];
                                        } else {
                                            idChunk = "&id=" + message.ids[i];
                                        }
                                        
                                        returnString += idChunk;
                                        if (i < message.ids.length - 1) {
                                            returnString += ","
                                        }
                                    };
                                    document.getElementById("embed_ids").value =  returnString;
                                    document.forms["submitForm"].submit();
                               }
                               else{
                                   alert("Please select a video to embed.");
                               }
                           }
                     }, false);
                }
                
                //When submit is clicked, send videos to embed script if any are selected,
                //otherwise prompt to select
                function clickSubmit(){
                    var win = document.getElementById("pageframe").contentWindow,
                    message = { cmd: "createEmbeddedFrame" };
                    //post messages with both http and https, in case panopto site has sitewide ssl enabled
                    win.postMessage(JSON.stringify(message), "https://<%=serverName%>");
                    win.postMessage(JSON.stringify(message), "http://<%=serverName%>");
                }
            </script>
        </bbNG:jsBlock>
        <bbNG:form name="submitForm" action="savePanoptoMashup.jsp" method="POST">
            <input type="hidden" id="content_title" name="content_title" value="">
            <input type="hidden" id="embed_ids" name="embed_ids" value="">
            <input type="hidden" id="course_id" name="course_id" value="${ctx.courseId.externalString}">
            <input type="hidden" id="content_id" name="content_id" value="${ctx.contentId.externalString}">
            <input type="hidden" id="server_name" name="server_name" value="<%=serverName%>">
            <input type="hidden" id="return_url" name="return_url" value="<%=returnUrl%>">
        </bbNG:form>
        <bbNG:dataCollection>
            <bbNG:step title="Select Panopto Videos">
                <div id="pagediv" style="display: inline-block; height: 680px; max-height: calc(100% - 250px); position: relative; width: 100%;">
                       <iframe id="pageframe" width="100%" height="100%" src="<%=IFrameSrc%>"></iframe>
                   </div>
            </bbNG:step>
            <bbNG:stepSubmit showCancelButton="true">
                 <bbNG:stepSubmitButton id="submitbutton" label="Insert Video" onClick="clickSubmit()"/>
            </bbNG:stepSubmit>            
        </bbNG:dataCollection>
    </bbNG:learningSystemPage> 
</bbData:context>

