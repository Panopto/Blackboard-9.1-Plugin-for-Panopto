<%@ page import="java.lang.reflect.Array"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.panopto.blackboard.PanoptoData"%>
<%@ page import="com.panopto.blackboard.Utils"%>
<%@ page import="com.panopto.services.Folder"%>
<%@ page import="blackboard.platform.blti.BasicLTILauncher"%>
<%@ page import="blackboard.data.course.Course"%>
<%@ page import="blackboard.data.blti.BasicLTIPlacement"%>
<%@ page import="blackboard.data.blti.BasicLTIPlacement.Type"%>
<%@ page import="blackboard.persist.BbPersistenceManager"%>
<%@ page import="blackboard.platform.persistence.PersistenceServiceFactory"%>
<%@ page import="blackboard.platform.blti.BasicLTILauncher.IdTypeToSend"%>
<%@ page import="blackboard.persist.course.CourseDbLoader"%>
<%@ page import="blackboard.persist.course.CourseMembershipDbLoader"%>
<%@ page import="blackboard.data.course.CourseMembership"%>
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
    // Context in this frame won't contain the course depending on usage. Course Id(unique shortname) and Course key (e.g. _31619_1) are used to get the proper course
    String course_id = request.getParameter("course_id");
    String course_key = request.getParameter("course_key");
    String use_sandbox = request.getParameter("use_sandbox");
    
    // The assignment submission EmbeddedUpload expects the video to be in a ready to submit state, so we should hide the Upload/Record tabs.
    String show_tabs = request.getParameter("show_tabs");

    String targetSubmission = request.getParameter("target_submission");
    String viewSandbox = request.getParameter("view_sandbox");
    String submitIds = request.getParameter("submit_ids");
    
    String userName = ctx.getUser().getUserName();
    PanoptoData ccCourse = new PanoptoData(course_id, userName);
    
    if(ccCourse.equals(null)){
    %>
<script> AlertAndClose();</script>
<%
    }
    String serverName = ccCourse.getServerName();
    Folder[] PanoptoFolders = ccCourse.getFolders();
    
    //Generate source URL for iframe from info. Blackboard embeds require https
    String instanceName = Utils.pluginSettings.getInstanceName();
    String LtiSrc = "https://" + serverName +"/Panopto/LTI/LTI.aspx";
    
    // If placement type is not set to ContentItemMessage then the LTI will always set the 'lti_message_type' to basic-lti-launch-request
    //  In order to have Lti.aspx open to Embeddedupload.aspx the lti_message_type needs to be 'ContentItemSelectRequest'
    BasicLTIPlacement ltiPlacement = new BasicLTIPlacement();
    ltiPlacement.setType(Type.ContentItemMessage);
    ltiPlacement.setLaunchNewWindow(false);
    
    BbPersistenceManager bbPm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();
    Course currCourse = null;
    CourseMembership usersCourseMembership = null;
    
    try {

        CourseDbLoader courseLoader = (CourseDbLoader) bbPm.getLoader(CourseDbLoader.TYPE);
        currCourse = courseLoader.loadByCourseId(course_id);

        // Load the user's membership in the current course to get their
        // role
        CourseMembershipDbLoader membershipLoader = (CourseMembershipDbLoader) bbPm
                .getLoader(CourseMembershipDbLoader.TYPE);
        usersCourseMembership = membershipLoader.loadByCourseAndUserId(currCourse.getId(), ctx.getUserId());
    } catch (Exception e) {
        Utils.log(e, String.format("Error getting course info (course ID: %s).", course_key));
    }
    
    String contextId = course_key;
    String fullName = currCourse.getCourseId() + ": " + currCourse.getTitle();
    
    HashMap<String, String> customParams = new HashMap<String, String>();
    
    // I do not think the IdTypeToSend types matter specifically, but without them the blackboard code eventually throws a null pointer exception.
    BasicLTILauncher launcher = new BasicLTILauncher( LtiSrc, instanceName, Utils.pluginSettings.getApplicationKey(ccCourse.getServerName()), contextId)
            .addResourceLinkInformation("Panopto LTI Tool", "Panopto Basic Lti Tool" )
            .addUserInformation(ctx.getUser(), usersCourseMembership, true, true, true, IdTypeToSend.PK1)
            .addCourseInformation(currCourse, IdTypeToSend.PK1)
            .addContextInformation(fullName, contextId);
    
    if (use_sandbox != null && use_sandbox.equals("true")) {
        customParams.put("use_panopto_sandbox", use_sandbox);
        
        launcher.addCustomToolParameters(customParams);
    }
    
    if (show_tabs != null && !show_tabs.isEmpty()) {
        customParams.put("panopto_show_tabs", show_tabs);
        launcher.addCustomToolParameters(customParams);
    }
    
    // targetSubmission is a submitted assignment a user is trying to view
    if (targetSubmission != null) {
        customParams.put("context_delivery", targetSubmission);
        
        // If this is not added then the embed will use the normal embedded view, we want the full featured view.
        customParams.put("use_panopto_interactive_view", "true");
        
        launcher.addCustomToolParameters(customParams);
    } 
    // If there are submitIds that means a user is trying to submit videos to an assignment.
    else if (submitIds != null) {
        customParams.put("panopto_assignment_submission", submitIds);
        
        launcher.addCustomToolParameters(customParams);
    }
    // This is the case where a user clicked the link in the instructions. Direct them to thier sandbox so they can make a video.
    else if(viewSandbox != null && viewSandbox.equals("true")){
        customParams.put("use_panopto_sandbox", viewSandbox);
        launcher.addCustomToolParameters(customParams);
    }
    // Base case is opening the EmbeddedUpload.aspx which requires a content item placement type
    else {
        // This parameter does nothing server side currently, but it will be needed when we decide to replace all EmbededUploading with LTI.
        customParams.put("panopto_single_selection", "true");
        launcher.addCustomToolParameters(customParams);
        
        launcher.setPlacement(ltiPlacement);
        
        // This is needed, if no parent is set then the blackboard code will fail with a generic hard to debug NullPointerException in 'addContentItemLaunchParameters'
        launcher.setParentId(ctx.getContentId());
    }
    
    try {
        launcher.launch( request, response, false, null );
    } catch (Exception e) {
        Utils.log(e, "There was an error launching the LTI tool");
    }
%>
</bbData:context>

