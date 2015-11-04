/* Copyright Panopto 2009 - 2011
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import blackboard.base.FormattedText;
import blackboard.data.content.Content;
import blackboard.persist.BbPersistenceManager;
import blackboard.persist.Id;
import blackboard.persist.content.ContentDbLoader;
import blackboard.persist.content.ContentDbPersister;
import blackboard.platform.persistence.PersistenceServiceFactory;
import blackboard.platform.plugin.PlugInUtil;
import blackboard.platform.security.SecurityUtil;

// Utility methods applicable outside the context of a particular Blackboard course
public class Utils {

    // These identify the Building Block to Blackboard.  Set in bb-manifest.xml.
    public static final String vendorID = "ppto";
    public static final String pluginHandle = "PanoptoCourseTool";
    // By default read at least 1024 standard length lines  
    public static final long defaultLogLimit = 1024 * 128;

    // Global settings for the plugin, applicable across all courses.
    public static final Settings pluginSettings = new Settings();

    public static final String courseConfigScriptURL = "Course_Config.jsp";
    public static final String courseResetURL = "Course_Reset.jsp";
    public static final String contentScriptURL = "Content.jsp";
    public static final String createScriptURL = "Item_Create.jsp";
    public static final String modifyScriptURL = "Item_Modify.jsp";
    public static final String logScriptURL = "Logs.jsp";
    public static final String buildingBlockManagerURL = "/webapps/blackboard/admin/manage_plugins.jsp";

    public static final String logFilename = "log.txt";

    public static void log(String logMessage)
    {
        log(null, logMessage, false);
    }

    public static void log(Exception e, String logMessage)
    {
        log(e, logMessage, false);
    }

    public static void logVerbose (String logMessage)
    {
        log(null, logMessage, true);
    }

    private static void log(Exception e, String logMessage, boolean verbose)
    {
        if (!verbose || Utils.pluginSettings.getVerbose())
        {
            try
            {
                File configDir = PlugInUtil.getConfigDirectory(vendorID, pluginHandle);
                File logFile = new File(configDir, logFilename);

                FileWriter fileStream = new FileWriter(logFile, true);
                BufferedWriter bufferedStream = new BufferedWriter(fileStream);
                PrintWriter output = new PrintWriter(bufferedStream);

                output.write(new Date().toString() + ": " + logMessage);

                if(!logMessage.endsWith(System.getProperty("line.separator")))
                {
                    output.println();
                }

                if(e != null)
                {
                    e.printStackTrace(output);
                }

                output.println("===================================================================================");

                output.close();
            }
            catch(Exception ex)
            {
            }
        }
    }

    // Reads data from a log file up to a max number of bytes.  The byte limit ensures we don't attempt to read in a 
    // huge log file, unless it's explicitly asked for
    public static String getLogData(long maxBytesToRead)
    {
        StringBuilder contents = new StringBuilder();

        String lineSeparator = System.getProperty("line.separator");
        try
        {
            File configDir = PlugInUtil.getConfigDirectory(vendorID, pluginHandle);
            File logFile = new File(configDir, logFilename);
            long fileLength = logFile.length(); 

            BufferedReader input =  new BufferedReader(new FileReader(logFile));

            try
            {
                String line = null;
                if (fileLength > maxBytesToRead)
                {
                    // Skip ahead to avoid reading too much of the file
                    input.skip(fileLength - maxBytesToRead);
                    // Read the first line so we don't print a partial line
                    line = input.readLine();
                }

                while (( line = input.readLine()) != null)
                {
                    contents.append(line);
                    contents.append(lineSeparator);
                }
            }
            finally
            {
                input.close();
            }
        }
        catch(Exception e)
        {
            // If there's an exception reading the file at least return the exception
            contents.append(e.toString());
            contents.append(lineSeparator);
            
            StringWriter trace = new StringWriter();
            e.printStackTrace(new PrintWriter(trace));
            contents.append(trace.toString());
        }

        return contents.toString();
    }

    public static void clearLogData()
    {
        try
        {
            File configDir = PlugInUtil.getConfigDirectory(vendorID, pluginHandle);
            File logFile = new File(configDir, logFilename);

            logFile.delete();
        }
        catch(Exception e)
        {
        }
    }

    // Check for system tools configuration entitlement
    public static boolean userCanConfigureSystem()
    {
        return SecurityUtil.userHasEntitlement("system.panopto.EXECUTE");
    }

    // Check for course tools conifguration entitlement
    public static boolean userCanConfigureCourse()
    {
        return SecurityUtil.userHasEntitlement("course.panopto.EXECUTE");
    }

    // URL to Course Documents page (for custom content importer).
    public static String getCourseDocsURL(String course_id, String content_id)
    {
        return PlugInUtil.getEditableContentReturnURL(content_id, course_id);
    }

    public static List<String> getServerList()
    {
        return pluginSettings.getServerList();
    }

    // Generate options for drop-down list of servers with optional current selection (serverName)
    public static String generateServerOptionsHTML(String serverName)
    {
        StringBuilder result = new StringBuilder();

        boolean hasSelection = false;

        for(String strServerName : getServerList())
        {
            result.append("<option");
            result.append(" value='").append(strServerName).append("'");
            if(strServerName.equals(serverName))
            {
                result.append(" SELECTED");
                hasSelection = true;
            }
            result.append(">");
             result.append(strServerName);
             result.append("</option>\n");
        }

        if(!hasSelection)
        {
            result.insert(0, "<option value=''>-- Select a Server --</option>");
        }

        return result.toString();
    }

    // Convert a Blackboard username into a Panopto user key referencing this Blackboard instance.
    public static String decorateBlackboardUserName(String bbUserName)
    {
        return pluginSettings.getInstanceName() + "\\" + bbUserName;
    }

    // Decorate the course ID with the instance name to generate an external course ID for Panopto.
    public static String decorateBlackboardCourseID(String courseId)
    {
        return (pluginSettings.getInstanceName() + ":" + courseId);
    }

    // Sign the payload with the proof that it was generated by trusted code.
    public static String generateAuthCode(String serverName, String payload)
    {
        String signedPayload = payload + "|" + pluginSettings.getApplicationKey(serverName);

        String authCode = null;
        try
        {
            MessageDigest m = MessageDigest.getInstance("SHA-1");
            m.update(signedPayload.getBytes(), 0, signedPayload.length());

            byte[] digest = m.digest();
            BigInteger digestAsInteger = new BigInteger(1, digest);

            // Format as 0-padded hex string.  Length is 2 hex chars per byte.
            String hexPlusLengthFormatString = "%0" + (digest.length * 2) + "X";

            authCode = String.format(hexPlusLengthFormatString, digestAsInteger);
        }
        catch(Exception e)
        {
            Utils.log(e, "Error generating auth code.");
        }

        return authCode;
    }

    // Verify that the payload was generated by trusted code.
    public static boolean validateAuthCode(String serverName, String payload, String auth)
    {
        String auth_computed = generateAuthCode(serverName, payload);

        return ((auth_computed != null) && (auth_computed.equals(auth)));
    }

    // Update content item title and description in DB (link is immutable).
    public static void updatePanoptoContentItem(String title, String description, String content_id)
    {
        try
        {
            Content content = loadPanoptoContentItem(content_id);
            content.setTitle(title);
            FormattedText text = new FormattedText(description, FormattedText.Type.HTML);
            content.setBody(text);

            BbPersistenceManager bbPm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();
            ContentDbPersister persister = (ContentDbPersister) bbPm.getPersister( ContentDbPersister.TYPE );
            persister.persist( content );
        }
        catch(Exception e)
        {
            Utils.log(e, String.format("Error updating content item (title: %s, description: %s, content ID: %s).", title, description, content_id));
        }
    }

    // Load the specified content item from the DB.
    public static Content loadPanoptoContentItem(String content_id)
    {
        Content content = null;

        try
        {
            BbPersistenceManager bbPm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();
            ContentDbLoader loader = (ContentDbLoader) bbPm.getLoader( ContentDbLoader.TYPE );
            Id contentId = bbPm.generateId( Content.DATA_TYPE, content_id );
            content = loader.loadById( contentId );
        }
        catch (Exception e)
        {
            Utils.log(e, String.format("Error loading content item (content ID: %s).", content_id));
        }

        return content;
    }

    public static String checkAndEscapeTerminalUrlParam(String url, String terminalUrlParam)
    {
        String urlWithEscapedParam;

        // Greedily match up to the specified parameter.
        // Match parameter value (from '=' to end of string) containing URL chars ':' '/' or '?'.
        // Store parameter value as capture group.
        String regex = ".*[?&]" + terminalUrlParam + "=(.*[:/?].*)$";

        Matcher urlParamMatcher = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(url);

        // If we found URL chars, replace the captured parameter value with its URL-encoded value.
        if(urlParamMatcher.matches())
        {
            try
            {
                String quotedCallback = URLEncoder.encode(urlParamMatcher.group(1), "UTF-8");
                urlWithEscapedParam = url.substring(0, urlParamMatcher.start(1)) + quotedCallback;
            }
            catch(UnsupportedEncodingException ex)
            {
                // Oh well, we tried.
                urlWithEscapedParam = url;
            }
        }
        // Just return the original URL if we don't get a match.
        else
        {
            urlWithEscapedParam = url;
        }

        return urlWithEscapedParam;
    }

    // Found online, Java has no String.join
    public static <T> String join(final Iterable<T> objs, final String delimiter)
    {
        Iterator<T> iter = objs.iterator();
        if (!iter.hasNext())
        {
            return "";
        }
        StringBuilder buffer = new StringBuilder(String.valueOf(iter.next()));
        while (iter.hasNext())
        {
            buffer.append(delimiter).append(String.valueOf(iter.next()));
        }
        return buffer.toString();
    }

    // Found online, Java has no HTML escape function.
    public static String escapeHTML(String text)
    {
        StringBuilder escaped = new StringBuilder();
        StringCharacterIterator iterator = new StringCharacterIterator(text);
        char character =  iterator.current();
        while (character != CharacterIterator.DONE )
        {
            if (character == '<')
            {
                escaped.append("&lt;");
            }
            else if (character == '>')
            {
                escaped.append("&gt;");
            }
            else if (character == '&')
            {
                escaped.append("&amp;");
            }
            else if (character == '\"')
            {
                escaped.append("&quot;");
            }
            else if (character == '\'')
            {
                escaped.append("&#039;");
            }
            else if (character == '(')
            {
                escaped.append("&#040;");
            }
            else if (character == ')')
            {
                escaped.append("&#041;");
            }
            else if (character == '#')
            {
                escaped.append("&#035;");
            }
            else if (character == '%')
            {
                escaped.append("&#037;");
            }
            else if (character == ';')
            {
                escaped.append("&#059;");
            }
            else if (character == '+')
            {
                escaped.append("&#043;");
            }
            else if (character == '-')
            {
                escaped.append("&#045;");
            }
            else
            {
                escaped.append(character);
            }

            character = iterator.next();
        }

        return escaped.toString();
    }

    // Uses a simple encoding to serialize an array of strings into a single string
    public static String encodeArrayOfStrings(String[] input)
    {
        if (input == null)
        {
            return null;
        }
        StringBuilder retVal = new StringBuilder();
        for (String val : input)
        {
            retVal.append('"' + val.replaceAll("\"", "\"\"") + "\",");
        }

        return retVal.toString();
    }

    // Uses a simple encoding to de-serialize an array of strings from a single string
    public static String[] decodeArrayOfStrings(String input)
    {
        if (input == null || input.isEmpty())
        {
            return null;
        }
        // Explicitly handle the legacy case of just a single unquoted string
        else if (!input.startsWith("\"") && !input.endsWith("\","))
        {
            return new String[] { input };
        }

        ArrayList<String> retVal = new ArrayList<String>();
        StringBuilder val = new StringBuilder();
        int i = 0;

        // This outer while will loop once per entry in the string
        while (i < input.length())
        {
            // Each entry must start with a "
            if (input.charAt(i) != '"')
            {
                Utils.log(String.format("Missing opening quote at %d in encoded string %s\n", i, input));
                return null;
            }
            i++;

            // The inner while will loop over the characters in an entry
            boolean foundEndQuote = false;
            while (i < input.length() && !foundEndQuote)
            {
                // We found a quote. It might be an end quote
                if (input.charAt(i) == '"')
                {
                    if (i+1 < input.length() && input.charAt(i+1) == '"')
                    {
                        // We found two quotes in a row, so add a single quote to the output and move on
                        val.append('"');
                        i+=2;
                    }
                    else
                    {
                        // We found a quote by itself. This terminates the entry
                        i++;
                        foundEndQuote = true;
                    }
                }
                else
                {
                    // We fond some non-quote character. Copy it to the buffer and move on
                    val.append(input.charAt(i));
                    i++;
                }
            }

            // If we exited the inner loop without finding the closing quote it means we ran out of characters
            if (!foundEndQuote)
            {
                Utils.log(String.format("Missing closing quote at %d in encoded string %s\n", i, input));
                return null;
            }

            // Only add non-empty entries
            if (val.length() > 0)
            {
                retVal.add(val.toString());
            }

            val = new StringBuilder();

            // Each entry (including the last) should end with a comma.
            if (i >= input.length() || input.charAt(i) != ',')
            {
                Utils.log(String.format("Missing closing comma at %d in encoded string %s\n", i, input));
                return null;
            }
            i++;
        }

        return retVal.toArray(new String[0]);
    }

    // <summary>
    // Elide a string in the middle, if necessary. Copied from the C# version at Panopto.Shared.Helpers.StringHelpers.
    // </summary>
    // <param name="input">The string to elide.</param>
    // <param name="elideAfter">The position in the string to display the ellipsis, if truncation is required.</param>
    // <param name="totalLength">The desired length of the elided string, not including ellipsis.</param>
    // <returns>A string similar to "A long string that is elided...the middle."</returns>
    public static String elideMiddle(String input, int elideAfter, int totalLength)
    {
        String output = input;

        if (input.length() > totalLength)
        {
            // Length of text snippet after ellipsis.
            int endSnippetLength = totalLength - elideAfter;

            output = String.format("%s...%s",
                        input.substring(0, elideAfter),
                        input.substring(input.length() - endSnippetLength, endSnippetLength));
        }

        return output;
    }
}
