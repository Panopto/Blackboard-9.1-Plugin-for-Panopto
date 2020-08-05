/* Copyright Panopto 2009 - 2016
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
 */

package com.panopto.blackboard;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import blackboard.platform.plugin.PlugInUtil;

public class Settings {

    // Enable users to connect to multiple Panopto servers from one Blackboard site.
    private Map<String, String> serverTable = new HashMap<String, String>();

    // Enable multiple instances to talk to the same CC server.
    // Set a default to avoid confusing users.
    private String instanceName = "blackboard";

    // Whether to allow any instructor to provision their course into Panopto,
    // or to allow only admins to provision.
    private Boolean instructorsCanProvision = true;

    // Whether to automatically email instructors when lectures are available, by default.
    private Boolean mailLectureNotifications = true;

    // Whether to send authenticated users back through the login page when action="relogin" is set.
    // This is a workaround for servers which do not allow unauthenticated deep-linking to the SSO page.
    // "false" will avoid doubled Recorder logins, but will force a Recorder quit to clear BB logins.
    // Default to true, the standard behavior.
    private Boolean refreshLogins = true;
    
    // This setting determines whether the code that syncs a user to panopto filters out unavailable memberships or unavailable courses. Default off.
    private Boolean syncAvailabilityStatus = false;
    
    // If this flag is set to true instead of using the reloggin param we will use the default_login param on sso.jsp
    private Boolean redirectToDefaultLogin = false;

    // Whether to grant TA's creator access
    private Boolean grantTACreator = false;

    // Whether TAs can create course links
    private Boolean TAsCanCreateLinks = false;

    // Whether to grant TA's provsioning access
    private Boolean grantTAProvision = false;

    // Should the reset button show for all courses
    private Boolean courseResetEnabled = false;

    // Whether to log verbose
    private Boolean verbose = false;

    // Insert a link to panopto tool on course page when a course is provisioned
    private Boolean insertLinkOnProvision = false;

    // Whether or not the Panopto Link tool uses Mapped + Public or Mapped + Creator folders. 
    private Boolean videoLinkToolIncludeCreatorFolders = false;
    
    // Text for link created on provision. Default is "Panopto Video"
    private String menuLinkText = "Panopto Video";

    private String roleMappingString = "";
    
    private String maxListedFolders = "10000";

    /**
     * Boolean setting that turns on copying Panopto permissions during Blackboard course copy
     */
    private Boolean courseCopyEnabled = true;

    // Parse current settings from XML file in config directory. Creates a new settings object from this file.
    public Settings() {
        try {
            // Get path to plugin config directory.
            File configDir = PlugInUtil.getConfigDirectory(Utils.vendorID, Utils.pluginHandle);
            File settingsFile = new File(configDir, "settings.xml");

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            if (settingsFile.exists()) {
                // Parse XML
                Document settingsDocument = db.parse(settingsFile);

                // Walk document tree and set corresponding setting values.
                readSettings(settingsDocument);
            }
        } catch (Exception e) {
            // Utils has static reference to instance of Settings, so can't use Utils.log() here
            e.printStackTrace();
        }
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
        save();
    }

    public void addServer(String serverName, String applicationKey) {
        if (serverTable != null) {
            serverTable.put(serverName, applicationKey);
            save();
        }
    }

    public void removeServer(String serverName) {
        if (serverTable != null) {
            serverTable.remove(serverName);
            save();
        }
    }

    // Return a list of available servers, or null if we failed to parse the server list.
    public List<String> getServerList() {
        List<String> serverList = null;

        if (serverTable != null) {
            // Return a copy of the server list so that it cannot be modified by client code.
            serverList = new ArrayList<String>(serverTable.keySet());
        }

        return serverList;
    }

    public String getApplicationKey(String serverName) {
        String applicationKey = null;

        if (serverTable != null) {
            applicationKey = serverTable.get(serverName);
        }

        return applicationKey;
    }

    public boolean getInstructorsCanProvision() {
        return instructorsCanProvision;
    }

    public void setInstructorsCanProvision(boolean canProvision) {
        instructorsCanProvision = canProvision;
        save();
    }

    public boolean getCourseResetEnabled() {
        return courseResetEnabled;
    }

    public void setCourseResetEnabled(boolean canResetAll) {
        courseResetEnabled = canResetAll;
        save();
    }

    public boolean getVerbose() {
        return verbose;
    }

    public void setVerbose(boolean v) {
        verbose = v;
        save();
    }

    public boolean getMailLectureNotifications() {
        return mailLectureNotifications;
    }

    public void setMailLectureNotifications(boolean mailLectureNotifications) {
        this.mailLectureNotifications = mailLectureNotifications;
        save();
    }

    public boolean getRefreshLogins() {
        return refreshLogins;
    }

    public void setRefreshLogins(boolean refresh) {
        refreshLogins = refresh;
        save();
    }

    public boolean getSyncAvailabilityStatus() {
        return syncAvailabilityStatus;
    }

    public void setSyncAvailabilityStatus(boolean syncAvailability) {
        syncAvailabilityStatus = syncAvailability;
        save();
    }

    public boolean getRedirectToDefaultLogin() {
        return redirectToDefaultLogin;
    }

    public void setRedirectToDefaultLogin(boolean useDefaultLogin) {
        redirectToDefaultLogin = useDefaultLogin;
        save();
    }

    public boolean getGrantTACreator() {
        return grantTACreator;
    }

    public void setGrantTACreator(boolean val) {
        grantTACreator = val;
        save();
    }

    public boolean getTAsCanCreateLinks() {
        return TAsCanCreateLinks;
    }

    public void setTAsCanCreateLinks(boolean val) {
        TAsCanCreateLinks = val;
        save();
    }

    public boolean getGrantTAProvision() {
        return grantTAProvision;
    }

    public void setGrantTAProvision(boolean val) {
        grantTAProvision = val;
        save();
    }

    public Boolean getInsertLinkOnProvision() {
        return insertLinkOnProvision;
    }

    public void setInsertLinkOnProvision(Boolean insertLinkOnProvision) {
        this.insertLinkOnProvision = insertLinkOnProvision;
        save();
    }

    public Boolean getVideoLinkToolIncludeCreatorFolders() {
        return videoLinkToolIncludeCreatorFolders;
    }

    public void setVideoLinkToolIncludeCreatorFolders(Boolean videoLinkToolIncludeCreatorFolders) {
        this.videoLinkToolIncludeCreatorFolders = videoLinkToolIncludeCreatorFolders;
        save();
    }
    
    public String getMenuLinkText() {
        return menuLinkText;
    }

    public void setMenuLinkText(String menuLinkText) {
        this.menuLinkText = menuLinkText;
        save();
    }

    public String getRoleMappingString() {
        return roleMappingString;
    }

    public void setRoleMappingString(String roleMappingString) {
        this.roleMappingString = roleMappingString;
        save();
    }
    
    public String getMaxListedFolders() {
        return maxListedFolders;
    }

    public void setMaxListedFolders(String maxListedFolders) {
        this.maxListedFolders = maxListedFolders;
        save();
    }
    /**
     * Get accessor for the Panopto copy permissions boolean
     * 
     * @return Boolean true if copying Panopto permissions is enabled
     */
    public Boolean getCourseCopyEnabled() {
        return courseCopyEnabled;
    }

    /**
     * Set accessor for the Panopto copy permissions boolean
     * 
     * @param courseCopyEnabled
     *            New value for if copying Panopto permissions enabled
     */
    public void setCourseCopyEnabled(Boolean courseCopyEnabled) {
        this.courseCopyEnabled = courseCopyEnabled;
        save();
    }

    // Serialize current settings to XML settings file in config directory.
    private void save() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document settingsDocument = db.newDocument();

            Element docElem = settingsDocument.createElement("config");
            settingsDocument.appendChild(docElem);

            Element instanceNameElem = settingsDocument.createElement("instanceName");
            instanceNameElem.setAttribute("name", instanceName);
            docElem.appendChild(instanceNameElem);

            Element serverListElem = settingsDocument.createElement("serverList");
            docElem.appendChild(serverListElem);

            for (Map.Entry<String, String> serverEntry : serverTable.entrySet()) {
                Element serverElem = settingsDocument.createElement("server");
                serverElem.setAttribute("hostname", serverEntry.getKey());
                serverElem.setAttribute("applicationKey", serverEntry.getValue());
                serverListElem.appendChild(serverElem);
            }

            // Write <instructorsCanProvision canProvision="true/false" />
            Element instructorsCanProvisionElem = settingsDocument.createElement("instructorsCanProvision");
            instructorsCanProvisionElem.setAttribute("canProvision", instructorsCanProvision.toString());
            docElem.appendChild(instructorsCanProvisionElem);

            // Write <mailLectureNotifications default="true/false" />
            Element mailLectureNotificationsElem = settingsDocument.createElement("mailLectureNotifications");
            mailLectureNotificationsElem.setAttribute("default", mailLectureNotifications.toString());
            docElem.appendChild(mailLectureNotificationsElem);

            Element refreshLoginsElem = settingsDocument.createElement("refreshLogins");
            refreshLoginsElem.setAttribute("refresh", refreshLogins.toString());
            docElem.appendChild(refreshLoginsElem);

            Element syncAvailabilityStatusElem = settingsDocument.createElement("syncAvailabilityStatus");
            syncAvailabilityStatusElem.setAttribute("syncAvailability", syncAvailabilityStatus.toString());
            docElem.appendChild(syncAvailabilityStatusElem);

            Element redirectToDefaultLoginElem = settingsDocument.createElement("redirectToDefaultLogin");
            redirectToDefaultLoginElem.setAttribute("useDefaultLogin", redirectToDefaultLogin.toString());
            docElem.appendChild(redirectToDefaultLoginElem);

            Element grantTACreatorElem = settingsDocument.createElement("grantTACreator");
            grantTACreatorElem.setAttribute("grantTACreator", grantTACreator.toString());
            docElem.appendChild(grantTACreatorElem);

            Element TAsCanCreateLinksElem = settingsDocument.createElement("TAsCanCreateLinks");
            TAsCanCreateLinksElem.setAttribute("TAsCanCreateLinks", TAsCanCreateLinks.toString());
            docElem.appendChild(TAsCanCreateLinksElem);

            Element grantTAProvisionElem = settingsDocument.createElement("grantTAProvision");
            grantTAProvisionElem.setAttribute("grantTAProvision", grantTAProvision.toString());
            docElem.appendChild(grantTAProvisionElem);

            Element courseResetEnabledElem = settingsDocument.createElement("courseResetEnabled");
            courseResetEnabledElem.setAttribute("courseResetEnabled", courseResetEnabled.toString());
            docElem.appendChild(courseResetEnabledElem);

            Element verboseElem = settingsDocument.createElement("verbose");
            verboseElem.setAttribute("verbose", verbose.toString());
            docElem.appendChild(verboseElem);

            Element insertLinkOnProvisionElem = settingsDocument.createElement("insertLinkOnProvision");
            insertLinkOnProvisionElem.setAttribute("insertLinkOnProvision", insertLinkOnProvision.toString());
            docElem.appendChild(insertLinkOnProvisionElem);

            Element videoLinkToolIncludeCreatorFoldersElem = settingsDocument.createElement("videoLinkToolIncludeCreatorFolders");
            videoLinkToolIncludeCreatorFoldersElem.setAttribute("videoLinkToolIncludeCreatorFolders", videoLinkToolIncludeCreatorFolders.toString());
            docElem.appendChild(videoLinkToolIncludeCreatorFoldersElem);
            
            Element menuLinkTextElem = settingsDocument.createElement("menuLinkText");
            menuLinkTextElem.setAttribute("menutext", menuLinkText);
            docElem.appendChild(menuLinkTextElem);

            Element roleMappingStringElem = settingsDocument.createElement("roleMappingString");
            roleMappingStringElem.setAttribute("roleMappingString", roleMappingString);
            docElem.appendChild(roleMappingStringElem);

            Element courseCopyEnabledElem = settingsDocument.createElement("courseCopyEnabled");
            courseCopyEnabledElem.setAttribute("courseCopyEnabled", courseCopyEnabled.toString());
            docElem.appendChild(courseCopyEnabledElem);

            Element maxListedFoldersElem = settingsDocument.createElement("maxListedFolders");
            maxListedFoldersElem.setAttribute("maxListedFolders", maxListedFolders);
            docElem.appendChild(maxListedFoldersElem);

            File configDir = PlugInUtil.getConfigDirectory(Utils.vendorID, Utils.pluginHandle);
            File settingsFile = new File(configDir, "settings.xml");
            
            DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            DOMImplementationLS impl = (DOMImplementationLS)registry.getDOMImplementation("LS");
            LSSerializer writer = impl.createLSSerializer();
            writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
            
            LSOutput output = impl.createLSOutput();
            FileOutputStream outStream = new FileOutputStream(settingsFile);
            output.setByteStream(outStream);

            // Serialize to XML.
            writer.write(settingsDocument, output);
        } catch (Exception e) {
            Utils.log(e, "Error saving settings.");
        }
    }

    // Walk document tree and set corresponding setting values.
    private void readSettings(Document settingsDocument) {
        Map<String, String> servers = new HashMap<String, String>();

        Element docElem = settingsDocument.getDocumentElement();

        NodeList instanceNameNodes = docElem.getElementsByTagName("instanceName");
        if (instanceNameNodes.getLength() != 0) {
            Element instanceNameElem = (Element) instanceNameNodes.item(0);
            this.instanceName = instanceNameElem.getAttribute("name");
        }

        NodeList serverListNodes = docElem.getElementsByTagName("serverList");
        if (serverListNodes.getLength() != 0) {
            Element serverListElem = (Element) serverListNodes.item(0);

            NodeList nl = serverListElem.getElementsByTagName("server");
            for (int i = 0; i < nl.getLength(); i++) {
                Element serverElement = (Element) nl.item(i);

                String hostname = serverElement.getAttribute("hostname");
                String applicationKey = serverElement.getAttribute("applicationKey");
                if (!hostname.equals("") && !applicationKey.equals("")) {
                    servers.put(hostname, applicationKey);
                }
            }
        }
        this.serverTable = servers;

        // Parse <instructorsCanProvision canProvision="true/false" />
        NodeList instructorsCanProvisionNodes = docElem.getElementsByTagName("instructorsCanProvision");
        if (instructorsCanProvisionNodes.getLength() != 0) {
            Element instructorsCanProvisionElem = (Element) instructorsCanProvisionNodes.item(0);
            this.instructorsCanProvision = Boolean.valueOf(instructorsCanProvisionElem.getAttribute("canProvision"));
        }

        // Parse <mailLectureNotifications default="true/false" />
        NodeList mailLectureNotificationsNodes = docElem.getElementsByTagName("mailLectureNotifications");
        if (mailLectureNotificationsNodes.getLength() != 0) {
            Element mailLectureNotificationsElem = (Element) mailLectureNotificationsNodes.item(0);
            this.mailLectureNotifications = Boolean.valueOf(mailLectureNotificationsElem.getAttribute("default"));
        }

        NodeList refreshLoginsNodes = docElem.getElementsByTagName("refreshLogins");
        if (refreshLoginsNodes.getLength() != 0) {
            Element refreshLoginsElem = (Element) refreshLoginsNodes.item(0);
            this.refreshLogins = Boolean.valueOf(refreshLoginsElem.getAttribute("refresh"));
        }

        NodeList syncAvailabilityStatusNodes = docElem.getElementsByTagName("syncAvailabilityStatus");
        if (syncAvailabilityStatusNodes.getLength() != 0) {
            Element syncAvailabilityStatusElem = (Element) syncAvailabilityStatusNodes.item(0);
            this.syncAvailabilityStatus = Boolean.valueOf(syncAvailabilityStatusElem.getAttribute("syncAvailability"));
        }


        NodeList redirectToDefaultLoginNodes = docElem.getElementsByTagName("redirectToDefaultLogin");
        if (redirectToDefaultLoginNodes.getLength() != 0) {
            Element redirectToDefaultLoginElem = (Element) redirectToDefaultLoginNodes.item(0);
            this.redirectToDefaultLogin = Boolean.valueOf(redirectToDefaultLoginElem.getAttribute("useDefaultLogin"));
        }

        NodeList grantTACreatorNodes = docElem.getElementsByTagName("grantTACreator");
        if (grantTACreatorNodes.getLength() != 0) {
            Element grantTACreatorElem = (Element) grantTACreatorNodes.item(0);
            this.grantTACreator = Boolean.valueOf(grantTACreatorElem.getAttribute("grantTACreator"));
        }

        NodeList TAsCanCreateLinksNodes = docElem.getElementsByTagName("TAsCanCreateLinks");
        if (TAsCanCreateLinksNodes.getLength() != 0) {
            Element TAsCanCreateLinksElem = (Element) TAsCanCreateLinksNodes.item(0);
            this.TAsCanCreateLinks = Boolean.valueOf(TAsCanCreateLinksElem.getAttribute("TAsCanCreateLinks"));
        }

        NodeList grantTAProvisionNodes = docElem.getElementsByTagName("grantTAProvision");
        if (grantTAProvisionNodes.getLength() != 0) {
            Element grantTAProvisionElem = (Element) grantTAProvisionNodes.item(0);
            this.grantTAProvision = Boolean.valueOf(grantTAProvisionElem.getAttribute("grantTAProvision"));
        }

        NodeList canResetAllNodes = docElem.getElementsByTagName("courseResetEnabled");
        if (canResetAllNodes.getLength() != 0) {
            Element canResetAllElem = (Element) canResetAllNodes.item(0);
            this.courseResetEnabled = Boolean.valueOf(canResetAllElem.getAttribute("courseResetEnabled"));
        }

        NodeList verboseNodes = docElem.getElementsByTagName("verbose");
        if (verboseNodes.getLength() != 0) {
            Element verboseElem = (Element) verboseNodes.item(0);
            this.verbose = Boolean.valueOf(verboseElem.getAttribute("verbose"));
        }

        NodeList insertLinkOnProvisionNodes = docElem.getElementsByTagName("insertLinkOnProvision");
        if (insertLinkOnProvisionNodes.getLength() != 0) {
            Element insertLinkOnProvisionElem = (Element) insertLinkOnProvisionNodes.item(0);
            this.insertLinkOnProvision = Boolean
                    .valueOf(insertLinkOnProvisionElem.getAttribute("insertLinkOnProvision"));
        }

        NodeList videoLinkToolIncludeCreatorFoldersNodes = docElem.getElementsByTagName("videoLinkToolIncludeCreatorFolders");
        if (videoLinkToolIncludeCreatorFoldersNodes.getLength() != 0) {
            Element videoLinkToolIncludeCreatorFoldersElem = (Element) videoLinkToolIncludeCreatorFoldersNodes.item(0);
            this.videoLinkToolIncludeCreatorFolders = Boolean
                    .valueOf(videoLinkToolIncludeCreatorFoldersElem.getAttribute("videoLinkToolIncludeCreatorFolders"));
        }
        
        NodeList menuLinkTextNodes = docElem.getElementsByTagName("menuLinkText");
        if (menuLinkTextNodes.getLength() != 0) {
            Element menuLinkTextElem = (Element) menuLinkTextNodes.item(0);
            this.menuLinkText = menuLinkTextElem.getAttribute("menutext");
        }

        NodeList roleMappingStringNodes = docElem.getElementsByTagName("roleMappingString");
        if (roleMappingStringNodes.getLength() != 0) {
            Element roleMappingStringElem = (Element) roleMappingStringNodes.item(0);
            this.roleMappingString = roleMappingStringElem.getAttribute("roleMappingString");
        }

        NodeList courseCopyEnabledNodes = docElem.getElementsByTagName("courseCopyEnabled");
        if (courseCopyEnabledNodes.getLength() != 0) {
            Element courseCopyEnabledElem = (Element) courseCopyEnabledNodes.item(0);
            this.courseCopyEnabled = Boolean.valueOf(courseCopyEnabledElem.getAttribute("courseCopyEnabled"));
        }

        NodeList maxListedFoldersNodes = docElem.getElementsByTagName("maxListedFolders");
        if (maxListedFoldersNodes.getLength() != 0) {
            Element maxListedFoldersElem = (Element) maxListedFoldersNodes.item(0);
            this.maxListedFolders = maxListedFoldersElem.getAttribute("maxListedFolders");
        }
    }

}
