<%@ page import="java.lang.reflect.Array"%>
<%@ page import="com.panopto.blackboard.PanoptoData"%>
<%@ page import="com.panopto.services.SessionManagementStub.Folder"%>
<%@ page import="com.panopto.blackboard.Utils"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib uri="/bbData" prefix="bbData"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script>
//Script to alert user and close window if course is not provisioned
function AlertAndClose(){
    alert("This course is not provisioned with Panopto, and no default Panopto server is set. Before a course can be used with Panopto, it must either be setup or a Default Panopto Server must be set in the Panopto block configuration. Please contact your administrator or instructor.");
    self.close();
}
</script>

<bbData:context id="ctx">

<%

    String targetSubmission = request.getParameter("target_submission");
    String course_key = request.getParameter("course_key");
    String course_id = request.getParameter("course_id");
    
    String gradebookUrl = "/webapps/gradebook/do/instructor/viewNeedsGrading?course_id=" + course_key;
    String courseUrl = "/webapps/blackboard/execute/launcher?type=Course&id=" + course_key;

    //Generate source URL for iframe from info. Blackboard embeds require https
    String IFrameSrc = "ltiFrameContainer.jsp?course_key=" + course_key + "&course_id=" + course_id + "&target_submission=" + targetSubmission;
%>
    <bbNG:learningSystemPage>

        <c:set var="gradebookBreadcrumb" value="Course Gradebook"/>
        <c:set var="courseBreadcrumb" value="Course"/>
        <fmt:message var="cancelStr" key="action.cancel" bundle="${bundles.common}"/>
        <c:set var="pageTitle" value="View Panopto Assignment Submission" />
        <c:set var="pageTitleInstructions" value="View Panopto Assignment Submissions" />
        
        <bbNG:breadcrumbBar environment="COURSE">
          <bbNG:breadcrumb title="${courseBreadcrumb}" href="<%=courseUrl%>"/>
          <bbNG:breadcrumb title="${gradebookBreadcrumb}" href="<%=gradebookUrl%>"/>
          <bbNG:breadcrumb title="${pageTitle}" />
        </bbNG:breadcrumbBar>

        <bbNG:pageHeader instructions="${pageTitleInstructions}">
          <bbNG:pageTitleBar title="${pageTitle}" iconAlt="Panopto"/>
        </bbNG:pageHeader>
        
        <div id="pagediv" style="display: inline-block; height: 680px; max-height: calc(100% - 250px); position: relative; width: 100%;">
            <iframe id="pageframe" width="100%" height="100%" src="<%=IFrameSrc%>"></iframe>
        </div>
        
    </bbNG:learningSystemPage> 
</bbData:context>

