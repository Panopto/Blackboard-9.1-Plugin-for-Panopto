<%@ page import="blackboard.base.FormattedText"%>
<%@ page import="blackboard.base.FormattedText.Type"%>
<%@ page import="blackboard.data.content.Content"%>
<%@ page import="blackboard.data.content.ContentFile"%>
<%@ page import="blackboard.data.content.CourseDocument"%>
<%@ page import="blackboard.data.content.ContentFile.Action"%>
<%@ page import="blackboard.data.course.Course"%>
<%@ page import="blackboard.data.course.CourseUtil"%>
<%@ page import="blackboard.db.DbUtil"%>
<%@ page import="blackboard.persist.Id"%>
<%@ page import="blackboard.persist.content.ContentDbPersister"%>
<%@ page import="blackboard.persist.content.ContentDbPersister.Default"%>
<%@ page import="blackboard.platform.contentsystem.manager.MetadataManager.ContentArea"%>
<%@ page import="blackboard.platform.contentsystem.service.ContentSystemServiceExFactory"%>
<%@ page import="blackboard.platform.context.Context"%>
<%@ page import="blackboard.platform.context.ContextManagerFactory"%>
<%@ page import="blackboard.platform.course.CourseEntitlement"%>
<%@ page import="blackboard.platform.coursecontent.CourseContentManagerFactory"%>
<%@ page import="blackboard.platform.log.LogServiceFactory"%>
<%@ page import="blackboard.platform.plugin.PlugInUtil"%>
<%@ page import="blackboard.platform.servlet.InlineReceiptUtil"%>
<%@ page import="blackboard.util.FileUtil"%>
<%@ page import="blackboard.util.StringUtil"%>
<%@ page import="blackboard.util.UrlUtil"%>
<%@ page import="java.io.File"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="javax.servlet.RequestDispatcher"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="javax.servlet.http.HttpServletResponse"%>
<%@ page import="com.panopto.blackboard.Utils"%>

<%

String[] embedIds = request.getParameter("embed_ids").split(",");
String embedHtml = "";

String courseId = request.getParameter("course_id");

String contentId = request.getParameter("content_id");

String serverName = request.getParameter("server_name");

String returnUrl = request.getParameter("return_url");

String contentTitle = request.getParameter("content_title");
String contentTitleColor = "#000000"; //default black, user can edit later

// Given the list of Id's and Pid's we need to generate the iframes needed to display them.
for(int i = 0; i < embedIds.length; ++i) {
	String iframeString = "<iframe src=\"https://" + serverName + "/Panopto/Pages/Embed.aspx?instance=" + Utils.pluginSettings.getInstanceName() + embedIds[i] + "&v=1\" width=\"720\" height=\"480\" style=\"max-width: 100%; max-height: 100%;\" frameborder=\"0\" allowfullscreen></iframe><br>";
	embedHtml += iframeString;
}

CourseEntitlement.CREATE_COURSE_CONTENT.checkEntitlement();
        
Id embedUrl1 = Id.generateId(Course.DATA_TYPE, courseId);
Id rd1 = Id.generateId(CourseDocument.DATA_TYPE, contentId);
Content courseDoc = new Content();
courseDoc.setTitle(contentTitle);
courseDoc.setTitleColor(contentTitleColor);
FormattedText text = new FormattedText(embedHtml, Type.HTML);
courseDoc.setBody(text);
courseDoc.setParentId(rd1);
courseDoc.setContentHandler("resource/bb-panopto-bc-mashup");
courseDoc.setCourseId(embedUrl1);
courseDoc.setIsAvailable(true);
courseDoc.setIsTracked(false);
Context ctx = ContextManagerFactory.getInstance().getContext();
Course course = ctx.getCourse();

ContentDbPersister success = Default.getInstance();
success.persist(courseDoc);
 
response.sendRedirect(returnUrl);

%>

