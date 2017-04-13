<!-- Copyright Panopto 2009 - 2011
 * 
 * This file is part of the Panopto plugin for Blackboard.
 * 
 * The Panopto plugin for Blackboard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Panopto plugin for Blackboard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Panopto plugin for Blackboard.  If not, see <http://www.gnu.org/licenses/>.
 */ -->

<%@page language="java" pageEncoding="ISO-8859-1" %>

<%@page import="blackboard.platform.*"%>
<%@page import="blackboard.platform.security.authentication.servlet.*"%>
<%@page import="blackboard.platform.session.*"%>
<%@page import="com.panopto.blackboard.Utils"%>
<%@page import="com.panopto.blackboard.PanoptoData"%>
<%@page import="java.net.URLEncoder"%>

<%@taglib uri="/bbUI" prefix="bbUI" %>
<%@taglib uri="/bbData" prefix="bbData"%>

<bbData:context id="ctx">
<%
	String serverName = request.getParameter("serverName");
	String callbackURL = request.getParameter("callbackURL");
	String expiration = request.getParameter("expiration");
	String requestAuthCode = request.getParameter("authCode");

	String action = request.getParameter("action");
	boolean relogin = (action != null) && action.equals("relogin") && Utils.pluginSettings.getRefreshLogins();

	// Bounce unauthenticated/guest users and forced login requests through the login page.
	BbSession bbSession = BbSessionManagerServiceFactory.getInstance().getSession(request);
	if (!bbSession.isAuthenticated() || bbSession.getUserName().equals("guest") || relogin)
	{
		// Put callbackURL last so it doesn't eat the rest of the params when Blackboard erroneously double-decodes it.
		String selfURL = request.getRequestURI() + 
							"?authCode=" + requestAuthCode +
							"&serverName=" + serverName +
							"&expiration=" + expiration +
							"&callbackURL=" + URLEncoder.encode(callbackURL, "UTF-8");

		String returnURL = URLEncoder.encode(selfURL, "UTF-8");

		String loginURL = "/webapps/login?action="; 
		
		if (Utils.pluginSettings.getRedirectToDefaultLogin()) {
			loginURL += "default_login&new_loc=" + returnURL; 
		} else {
			loginURL += "relogin&new_loc=" + returnURL;
		}
		
		response.sendRedirect(loginURL);
		return;
	}

	// Reproduce canonically-ordered incoming auth payload.
	String requestAuthPayload = "serverName=" + serverName + "&expiration=" + expiration;

	// Verify passed in parameters are properly signed.
	if(Utils.validateAuthCode(serverName, requestAuthPayload, requestAuthCode))
	{
		// Make a call to syncUser to update the current user's group membership
		PanoptoData.syncUser(serverName, bbSession.getUserName());
	
		String userKey = Utils.decorateBlackboardUserName(ctx.getUser().getUserName());
		// Generate canonically-ordered auth payload string
		String responseParams = "serverName=" + serverName + "&externalUserKey=" + userKey + "&expiration=" + expiration;
		// Sign payload with shared key and hash.
		String responseAuthCode = Utils.generateAuthCode(serverName, responseParams);
		
		// Work around double-decoding bug coming from the Blackboard login page.
		// Check for unescaped URL chars ':' '/' or '?' in ReturnUrl and re-escape if found.  
		callbackURL = Utils.checkAndEscapeTerminalUrlParam(callbackURL, "ReturnUrl");

		String separator = (callbackURL.contains("?") ? "&" : "?");
		String redirectURL = callbackURL + separator + responseParams + "&authCode=" + responseAuthCode;  
		
		// Redirect to Panopto login page.
		response.sendRedirect(redirectURL);
	}
	else
	{
		%>Invalid auth code:<br/><%
		
		if(Utils.pluginSettings == null)
		{
			%>No plugin settings.<%
		}
		else if(Utils.pluginSettings.getServerList() == null)
		{
			%>No server list.<%
		}
		else if(Utils.pluginSettings.getServerList().size() == 0)
		{
			%>No servers.<%
		}
		else
		{
			String appKey = Utils.pluginSettings.getApplicationKey(serverName);
			if(appKey == null)
			{
				%>App Key is null.<%
			}
			else
			{
				%><%= "App Key length: " + appKey.length() + "<br>" %><%
			}
		}
	}
%>
</bbData:context>
