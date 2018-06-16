<%@ page import="java.lang.reflect.Array"%>
<%@ page import="com.panopto.blackboard.PanoptoData"%>
<%@ page import="com.panopto.blackboard.Utils"%>
<%@ page import="com.panopto.services.Folder"%>
<%@ page import="blackboard.platform.plugin.ContentHandler"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib uri="/bbData" prefix="bbData"%>

<script>
//Script to alert user and close window if course is not provisioned
function AlertAndClose(){
    alert("This course is not provisioned with Panopto. Before a course can be used with Panopto it must be setup. Please contact your administrator or instructor.");
    self.close();
}
</script>

<bbData:context id="ctx">

<%
    //Get course information from page context
    String course_key = request.getParameter("course_id");
    String course_id = ctx.getCourse().getCourseId();
    PanoptoData ccCourse = new PanoptoData(ctx);
    ContentHandler contentHandler;
    
    if(ccCourse.equals(null)){%>
        <script> AlertAndClose();</script>
    <%
    }

    String serverName = ccCourse.getServerName();
    
    //Generate source URL for iframe from info. Blackboard embeds require https
    String IFrameSrc = "ltiFrameContainer.jsp?course_key=" + course_key + "&course_id=" + course_id;
    
    String activeFrameSrc = IFrameSrc + "&use_sandbox=true&show_tabs=chooseOnly";
    
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
                            linkChunk = "/webapps/ppto-PanoptoCourseTool-BBLEARN/vtbe/ltiFrameContainer.jsp?view_sandbox=true&course_key=" + courseKey + "&course_id=" + courseId,
                            step1String = "Prepare your video in Panopto video library. If it's not ready, click <a href='" + linkChunk + "' target='_blank'>here</a> to navigate to your personal folder in Panopto.",
                            step2String = "Videos are submitted as part of assignments via clicking on \"Write Submission\".",
                            step3String = "In the text editor expand \"Mashups\" and select \"Panopto Student Video Submission\".",
                            step4String = "If your submission is not in the default personal folder select the folder where your submission is stored.",
                            step5String = "Once at the folder your submission is located select the submission and click \"Insert\".",
                            step6String = "Once your submission has been added to the text editor add any extra information and submit.";
                        
                        instructions += 
                        "<div>" +
                            "<div><b>Step 1:</b> " + step1String + "</div>" +
                            "<div><b>Step 2:</b> " + step2String + "</div>" +
                            "<div><b>Step 3:</b> " + step3String + "</div>" +
                            "<div><b>Step 4:</b> " + step4String + "</div>" +
                            "<div><b>Step 5:</b> " + step5String + "</div>" +
                            "<div><b>Step 6:</b> " + step6String + "</div>" +
                        "</div>";
                        
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
                                        linkChunk = "/webapps/ppto-PanoptoCourseTool-BBLEARN/vtbe/ltiFrameContainer.jsp?course_key=" + courseKey + "&course_id=" + courseId + "&target_submission=" + message.ids[i];
                                        
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

