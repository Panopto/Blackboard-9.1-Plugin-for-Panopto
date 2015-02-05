<%@ page import="blackboard.platform.plugin.PlugInUtil"%>
<%
//this prepares the embed HTML and inserts it into the VTBE/WYSIWYG editor
String WYSIWYG_WEBAPP = "/webapps/wysiwyg";
String embedHtml = request.getParameter("embedHtml");

request.setAttribute( "embedHtml", embedHtml );
String embedUrl = PlugInUtil.getInsertToVtbePostUrl().replace( WYSIWYG_WEBAPP, "" );
RequestDispatcher rd = getServletContext().getContext( WYSIWYG_WEBAPP ).getRequestDispatcher( embedUrl );
rd.forward( request, response );
        
%>