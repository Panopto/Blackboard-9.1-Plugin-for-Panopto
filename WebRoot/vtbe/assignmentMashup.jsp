<%@ page import="java.lang.reflect.Array"%>
<%@ page import="com.panopto.blackboard.PanoptoData"%>
<%@ page import="com.panopto.blackboard.Utils"%>
<%@ page import="blackboard.platform.plugin.PlugInUtil" %>
<%@ page import="com.panopto.services.SessionManagementStub.Folder"%>
<%@ page import="blackboard.platform.plugin.Version"%>
<%@ page import="blackboard.platform.plugin.PlugInManager"%>
<%@ page import="blackboard.platform.plugin.PlugInManagerFactory"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib uri="/bbData" prefix="bbData"%>

<script>
//Script to alert user and close window if course is not provisioned
function AlertAndClose(){
    alert("This course is not provisioned with Panopto, and no default Panopto server is set. Before a course can be used with Panopto, it must either be setup or a Default Panopto Server must be set in the Panopto block configuration. Please contact your administrator or instructor.");
    self.close();
}
</script>

<bbData:context id="ctx">

<%
    //Get course information from page context
    String course_key = request.getParameter("course_id");
    String course_id = ctx.getCourse().getCourseId();
    PlugInManager plugInManager = PlugInManagerFactory.getInstance();
    Version newEditorVersion = new Version("3900.0.0-rel.42+47a7c9a");
    int hasNewEditor = plugInManager.getPlatformVersion().compare(newEditorVersion);
    PanoptoData ccCourse = new PanoptoData(ctx);
    String serverName = null;
    
    // If the course is unprovisioned grab the first Panopto server available and display an embed without to that. 
    if(ccCourse.equals(null) || !ccCourse.isServerSet()){
        serverName = Utils.pluginSettings.getDefaultPanoptoServer();
    } else {
        serverName = ccCourse.getServerName();
    }

    if(serverName == null || serverName.isEmpty()){%>    
    <script> AlertAndClose();</script>   
	<%}
	    
    //Generate source URL for iframe from info. Blackboard embeds require https
    String IFrameSrc = "ltiFrameContainer.jsp?course_key=" + course_key + "&course_id=" + course_id;
    
    String activeFrameSrc = IFrameSrc + "&use_sandbox=true&show_tabs=chooseOnly";

    String toolUri = PlugInUtil.getUri("ppto", "PanoptoCourseTool", Utils.ltiFramContainerURL);
    
    if (ccCourse.IsInstructor()) {%>
        <bbNG:genericPage bodyClass="popup">
            <bbNG:form name="submitForm" action="mashup_proc.jsp" method="POST">
                <input type="hidden" id="embedHtml" name="embedHtml" value="">
            </bbNG:form>
            <bbNG:jsBlock>
                <script type="text/javascript">
                    // If an instructor entered this mashup let's assume they want to print instructions.
                    function generateInstructions() {
                        var instructions = "",
                            courseId = "<%=course_id%>",
                            courseKey = "<%=course_key%>",
                            serverName = "<%=serverName%>",
                            linkChunk = "<%=toolUri%>?view_sandbox=true&course_key=" + courseKey + "&course_id=" + courseId,
                            step1String = "Record or upload your video in Panopto. To start creating your video, open your <a href='" + linkChunk + "' target='_blank'>Panopto video library</a>.",
                            step2String = "Open the assignment in Blackboard and select <b>Write Submission</b>.",
                            step3OldEditorString = "In the text editor, expand <b>Mashups</b> and select <b>Panopto Student Video Submission</b>.",
                            step3NewEditorString = "In the text editor, select the three dots to expand the entire toolbar, and then select the icon, which looks like a circle with a plus symbol inside of it, to open the Add Content window. Then select <b>Panopto Student Video Submission</b>.",
                            step4String = "A window will open to show the videos in your personal folder. If your video is located in a different folder, select the correct folder from the drop-down at the top.",
                            step5String = "Select the video you wish to submit and click <b>Submit Video</b>.",
                            step6String = "Your video will be added to the submission. Add any extra information and <b>Submit</b>.";
                        
                        if (serverName) {
	                        instructions += 
                            "<ol>" +
                            "<li>" + step1String + "</li>" +
                            "<li>" + step2String + "</li>";
                           
                            if(parseInt(<%=hasNewEditor%>) >= 0) {
                                instructions += "<li>" + step3NewEditorString + "</li>";
                            } 
                            else {
                                instructions += "<li>" + step3OldEditorString + "</li>";
                            }
                           
                            instructions += "<li>" + step4String + "</li>" +
	                            "<li>" + step5String + "</li>" +
	                            "<li>" + step6String + "</li>" +
	                            "</ol>";
                        } else {
                        	instructions = "";
                        }
                       
                        document.getElementById("embedHtml").value = instructions;
                        document.forms["submitForm"].submit();
                    }
                    
                    generateInstructions();
                </script>
            </bbNG:jsBlock>
        </bbNG:genericPage>
    <%} else {%>
        <bbNG:genericPage bodyClass="popup" onLoad="doOnLoad()">
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
                            var message = JSON.parse(e.data),
                                linkChunk,
                                embedChunk,
                                returnString = "",
                                courseId = "<%=course_id%>",
                                courseKey = "<%=course_key%>",
                                serverName = "<%=serverName%>",
                                PLAYLIST_EMBED_ID = 1,
                                VIDEO_EMBED_ID = 0,
                                embedHeight = 360,
                                embedWidth = 540;
                            
                            //If a video is chosen, enable the "Insert" button
                            if (message.cmd === 'ready') {
                                selected = true;
                            }
                            //If no video is chosen, disable the "Insert" button
                            if (message.cmd === 'notReady') { 
                                selected = false;
                            }
                            //Called when "Insert" is clicked or the submitted assignments have been processed. Creates HTML for embedding each selected video into the editor            
                            if (message.cmd === 'deliveryList') {       
                                if(selected){
                                    
                                    //Add iframe html for each video to form
                                    for (var i = 0; i < message.ids.length; ++ i) {
                                    	var assignmentName = (message.names !== null && message.names.length > i) ? message.names[i] : "Link to Assignment Submission";
                                        linkChunk = "<%=toolUri%>?course_key=" + courseKey + "&course_id=" + courseId + "&target_submission=" + message.ids[i];
                                        
                                        console.log(linkChunk);
                                        
                                        embedChunk = "<p><a href='" + linkChunk + "' target='_blank'>" + 
                                                         assignmentName + 
                                                     "</a></p>";
                                        
                                        returnString += embedChunk;
                                    };
                                    document.getElementById("embedHtml").value = returnString;
                                    document.forms["submitForm"].submit();
                                }
                                else{
                                    alert("Please select a video to embed.");
                                }
                           } else if (selected) {
                               document.getElementById("submitIds").value = message.ids.join();
                           }
                         }, false);
                    }
                    
                    //When submit is clicked, send videos to embed script if any are selected,
                    //otherwise prompt to select
                    function clickSubmit(){
                        var embedFrame = document.getElementById("pageframe");
                            embedFrame.src = "<%=IFrameSrc%>&submit_ids=" + document.getElementById("submitIds").value;
                    }
                </script>
            </bbNG:jsBlock>
            <bbNG:form name="submitForm" action="mashup_proc.jsp" method="POST">
            <input type="hidden" id="embedHtml" name="embedHtml" value=""></input> 
            </bbNG:form>
            <input type="hidden" id="submitIds" name="submitIds" value=""></input>
            <bbNG:dataCollection>
                <bbNG:step title="Select Panopto Videos">
                    <div id="pagediv" style="display: inline-block; height: 680px; max-height: calc(100% - 250px); position: relative; width: 100%;">
                        <iframe id="pageframe" width="100%" height="100%" src="<%=activeFrameSrc%>"></iframe>
                    </div>
                </bbNG:step>
                <bbNG:stepSubmit showCancelButton="true" cancelOnClick="self.close();">
                     <bbNG:stepSubmitButton id="submitbutton" label="Submit Video" onClick="clickSubmit()"/>
                </bbNG:stepSubmit>
            </bbNG:dataCollection>
        </bbNG:genericPage>
    <%}%>
</bbData:context>

