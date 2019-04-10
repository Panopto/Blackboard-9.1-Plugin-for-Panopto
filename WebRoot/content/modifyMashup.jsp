<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib uri="/bbFunctions" prefix="bbFN"%>
<%@ taglib uri="/bbData" prefix="bbData"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="blackboard.data.content.Content"%>
<%@ page import="com.panopto.blackboard.Utils"%>
<%@ page import="blackboard.platform.plugin.PlugInUtil" %>

<fmt:message var="strLContentAvailable"   key="content.common.contentavailable" bundle="${bundles.content}"/>
<fmt:message var="strLTrackViews"         key="content.common.trackviews" bundle="${bundles.content}"/>
<fmt:message var="strLChooseDates"        key="content.common.choosedates" bundle="${bundles.content}"/>
<fmt:message var="strLYes"                key="common.boolean.yes" bundle="${bundles.common}"/>
<fmt:message var="strLNo"                 key="common.boolean.no" bundle="${bundles.common}"/>
<fmt:message var="breadcrumb" key="results.breadcrumb" />
<c:set var="strTitle" value="Edit Content" />
<c:set var="strTitleHelp" value="Edit the settings for the Panopto mashup content." />
<c:set var="strInformation" value="Information" />
<c:set var="strOptions" value="Options" />
<c:set var="strCreateVideoTitle" value="Name" />
<c:set var="strCreateVideoTitleColor" value="Color of name" />

<bbNG:learningSystemPage ctxId="ctx">
  <bbNG:jsBundleMessage key="wysiwyg.insert_picture.csalign" bundle="wysiwyg"/>
  
  
  <bbNG:breadcrumbBar environment="COURSE">
    <bbNG:breadcrumb title="${strTitle}" />
  </bbNG:breadcrumbBar>
  
  <bbNG:pageHeader instructions="${strTitleHelp}">
      <bbNG:pageTitleBar title="${strTitle}"/>      
  </bbNG:pageHeader>
  
  <%
    String contentId = ctx.getContentId().toExternalString();
    Content content = Utils.loadPanoptoContentItem(contentId);

    String title = content.getTitle();
    String titleColor = content.getTitleColor();
    boolean isAvailable = content.getIsAvailable();
    boolean isTracked = content.getIsTracked();
    
    String returnUrl = ctx.getRequestUrl();
    
    String toolUri = PlugInUtil.getUri("ppto", "PanoptoCourseTool", "");
    
    // Set a return Url to get back to the content page
    returnUrl = PlugInUtil.getContentReturnURL(ctx.getContentId(), ctx.getCourseId());
  %>
  
  <bbNG:form action="processModifyPanoptoMashup.jsp" method="post" name="modForm">
    <input type="hidden" id="content_id" name="content_id" value="${ctx.contentId.externalString}">
    <input type="hidden" id="return_url" name="return_url" value="<%=returnUrl%>">
    <bbNG:dataCollection>
    
        <bbNG:step title="${strInformation}" >
        <bbNG:dataElement isRequired="true" label="${strCreateVideoTitle}" labelFor="title" >
            <bbNG:textElement name="title"
                              value="<%=title%>"
                              title="${strCreateVideoTitle}"
                              format="H"
                              isRequired="true"
                              size="40"
                              maxLength="333" />
        </bbNG:dataElement>         
        <bbNG:dataElement label="${strCreateVideoTitleColor}" labelFor="titleColor" >
          <bbNG:colorPicker name="title_color" initialColor="<%=titleColor%>"/>
        </bbNG:dataElement>
        </bbNG:step>
        
        <bbNG:step title="${strOptions}">       
          <bbNG:dataElement label="${strLContentAvailable}" renderLegendAndFieldset="true">
            <bbNG:radioElement name="is_available" id="isAvailable_true" value="true" isSelected="<%=isAvailable%>" optionLabel="${strLYes}"/>
            <bbNG:radioElement name="is_available" id="isAvailable_false" value="false" isSelected="<%=!isAvailable%>" optionLabel="${strLNo}"/>
          </bbNG:dataElement> 
          
          <bbNG:dataElement label="${strLTrackViews}" renderLegendAndFieldset="true">
            <bbNG:radioElement name="is_tracked" id="isTrack_true" value="true" isSelected="<%=isTracked%>" optionLabel="${strLYes}"/>
            <bbNG:radioElement name="is_tracked" id="isTrack_false" value="false" isSelected="<%=!isTracked%>" optionLabel="${strLNo}"/>      
          </bbNG:dataElement>     
        </bbNG:step>
        
        <bbNG:stepSubmit cancelUrl="${cancelUrl}" />
              
    </bbNG:dataCollection>
  </bbNG:form>
  
</bbNG:learningSystemPage>