<%@ page import="blackboard.base.FormattedText"%>
<%@ page import="blackboard.data.content.Content"%>
<%@ page import="blackboard.data.content.ContentFile"%>
<%@ page import="blackboard.data.content.ContentFile.Action"%>
<%@ page import="blackboard.data.course.Course"%>
<%@ page import="blackboard.db.DbUtil"%>
<%@ page import="blackboard.persist.Id"%>
<%@ page import="blackboard.persist.KeyNotFoundException"%>
<%@ page import="blackboard.persist.PersistenceException"%>
<%@ page import="blackboard.persist.content.ContentDbLoader"%>
<%@ page import="blackboard.persist.content.ContentDbPersister"%>
<%@ page import="blackboard.persist.content.ContentDbLoader.Default"%>
<%@ page import="blackboard.platform.contentsystem.manager.MetadataManager.ContentArea"%>
<%@ page import="blackboard.platform.contentsystem.service.ContentSystemServiceExFactory"%>
<%@ page import="blackboard.platform.context.Context"%>
<%@ page import="blackboard.platform.context.ContextManagerFactory"%>
<%@ page import="blackboard.platform.course.CourseEntitlement"%>
<%@ page import="blackboard.platform.coursecontent.CourseContentManagerFactory"%>
<%@ page import="blackboard.platform.log.LogServiceFactory"%>
<%@ page import="blackboard.servlet.data.FileLocation"%>
<%@ page import="blackboard.servlet.data.WysiwygText"%>
<%@ page import="blackboard.util.StringUtil"%>
<%@ page import="blackboard.util.UrlUtil"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="javax.servlet.http.HttpServletResponse"%>
<%@ page import="com.panopto.blackboard.Utils"%>

<%

// I would like to leave this chunk in as a small reminder of how to easily print all form params.
/* java.util.Enumeration e = request.getParameterNames();
while (e.hasMoreElements()){ 
    String name = (String) e.nextElement();
    Utils.log("<b>" + name + ": </b>"); 
    Utils.log(request.getParameter(name) + "<br>");
} */

String title = request.getParameter("title");

String titleColor = request.getParameter("title_color");

String targetContentId = request.getParameter("content_id");

String returnUrl = request.getParameter("return_url");

boolean isAvailable = Boolean.parseBoolean(request.getParameter("is_available"));

boolean isTracked = Boolean.parseBoolean(request.getParameter("is_tracked"));

ContentDbLoader contentLoader = Default.getInstance();
Content mashup = Utils.loadPanoptoContentItem(targetContentId);

mashup.setTitle(title);
mashup.setTitleColor(titleColor);
mashup.setIsAvailable(isAvailable);
mashup.setIsTracked(isTracked);

ContentDbPersister contentPersister = ContentDbPersister.Default.getInstance();
contentPersister.persist(mashup);

response.sendRedirect(returnUrl);

%>
   