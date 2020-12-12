<%@ page import="java.lang.reflect.Array"%>
<%@ page import="com.panopto.blackboard.PanoptoData"%>
<%@ page import="com.panopto.blackboard.Utils"%>
<%@ page import="com.panopto.services.SessionManagementStub.Folder"%>
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
    String course_id = request.getParameter("course_id");
    PanoptoData ccCourse = new PanoptoData(ctx);
    String serverName = null;
    Folder[] PanoptoFolders = null;
    String folderId = "";
    
    // If the course is unprovisioned check for a default Panopto server and use that. If the server is still null after that just display an empty frame with error.  
    if(ccCourse.equals(null) || !ccCourse.isServerSet()){
        serverName = Utils.pluginSettings.getDefaultPanoptoServer();
    } else {
        serverName = ccCourse.getServerName();
        PanoptoFolders = ccCourse.getFolders();
    }
    
    if(serverName == null || serverName.isEmpty()){%>    
        <script> AlertAndClose();</script>   
    <%}
    
    // If course is associated with a single folder, append folder's id to iframe source url
    //  This will automatically display course's video's in frame
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
    }
    //Generate source URL for iframe from info. Blackboard embeds require https
    String IFrameSrc = "https://" +serverName +"/Panopto/Pages/Sessions/EmbeddedUpload.aspx?playlistsEnabled=true&instance=" + Utils.pluginSettings.getInstanceName() + folderId;
%>
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
                                    
                                    //Add iframe html for each video to form
                                    for (var i = 0; i < message.ids.length; ++ i) {
                                        var idChunk = ''
                                        if ((message.playableObjectTypes != null) && (message.playableObjectTypes.size() > i) && (message.playableObjectTypes[i] === PLAYLIST_EMBED_ID)){
                                            idChunk = "&pid=" + message.ids[i];
                                        } else {
                                            idChunk = "&id=" + message.ids[i];
                                        }
                                        var iframeString = "<iframe src=\"https://<%=serverName%>/Panopto/Pages/Embed.aspx?instance=<%=Utils.pluginSettings.getInstanceName()%>" + idChunk + "&v=1\" width=\"720\" height=\"480\" style=\"max-width: 100%; max-height: 100%;\" frameborder=\"0\" allowfullscreen></iframe><br>";
                                        returnString += iframeString;
                                    };
                                    document.getElementById("embedHtml").value = returnString;
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
        <bbNG:form name="submitForm" action="mashup_proc.jsp" method="POST">
        <input type="hidden" id="embedHtml" name="embedHtml" value="">
        </bbNG:form>
            <bbNG:dataCollection>
                <bbNG:step title="Select Panopto Videos">
                    <div id="pagediv" style="display: inline-block; height: 680px; max-height: calc(100% - 250px); position: relative; width: 100%;">
                        <iframe id="pageframe" width="100%" height="100%" src="<%=IFrameSrc%>"></iframe>
                    </div>
                </bbNG:step>
                <bbNG:stepSubmit showCancelButton="true" cancelOnClick="self.close();">
                     <bbNG:stepSubmitButton id="submitbutton" label="Insert Videos" onClick="clickSubmit()"/>
                </bbNG:stepSubmit>
            </bbNG:dataCollection>
    </bbNG:genericPage>
</bbData:context>

