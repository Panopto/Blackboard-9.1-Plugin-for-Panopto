<%@ page import="java.lang.reflect.Array"%>
<%@ page import="com.panopto.blackboard.PanoptoData"%>
<%@ page import="com.panopto.blackboard.Utils"%>
<%@ page import="com.panopto.services.Folder"%>
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
	String course_id = request.getParameter("course_id");
	PanoptoData ccCourse = new PanoptoData(ctx);	
	if(ccCourse.equals(null)){
	%>
      <script> AlertAndClose();</script>   
    <%
    }
	String serverName = ccCourse.getServerName();
	Folder[] PanoptoFolders = ccCourse.getFolders();
	String folderId = "";
	//If course is associated with a single folder, append folder's id to iframe source url
	//This will automatically display course's video's in frame
	//If multiple folders are associated, a picker will appear before displaying sessions
	if(PanoptoFolders != null){
		if( Array.getLength(PanoptoFolders)== 1 && PanoptoFolders[0].getExternalId() != null){
		    folderId += ("?externalId=" + PanoptoFolders[0].getExternalId());
		}
		else if(Array.getLength(PanoptoFolders)== 0){
		%>
		      <script> AlertAndClose();</script>
		<% 
		}
	}
	else{
	%>
      <script> AlertAndClose();</script>    
    <%
    }
	//Generate source URL for iframe from info. Blackboard embeds require https
	String IFrameSrc = "https://" +serverName +"/Panopto/Pages/Sessions/EmbeddedUpload.aspx" + folderId;
%>
	<bbNG:genericPage bodyClass = "popup" onLoad="doOnLoad()">
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
					                    var returnString = "";
					                    //Add iframe html for each video to form
					                    message.ids.each(function (value) {
					                        var iframeString = "<iframe src=\"https://<%=serverName%>/Panopto/Pages/Embed.aspx?id=" + value + "&v=1\" width=\"720\" height=\"480\" frameborder=\"0\"></iframe><br>";
					                        returnString += iframeString;
					                    });
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
				    <div id='pagediv'>
				        <iframe id="pageframe" width="990" height="680" src="<%=IFrameSrc %>"></iframe>
					</div>			   
				</bbNG:step>
				<bbNG:stepSubmit showCancelButton="true" cancelOnClick="self.close();">
				     <bbNG:stepSubmitButton id="submitbutton" label="Insert Videos" onClick="clickSubmit()"/>
				</bbNG:stepSubmit>			
			</bbNG:dataCollection>
	</bbNG:genericPage>
</bbData:context>

