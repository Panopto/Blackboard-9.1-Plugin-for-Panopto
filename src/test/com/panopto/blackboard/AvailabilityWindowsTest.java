package com.panopto.blackboard;

import java.rmi.RemoteException;
import java.util.Calendar;

import org.junit.Test;
import org.junit.Assert;

import com.panopto.blackboard.PanoptoAvailabilityWindow.AvailabilityState;
import com.panopto.services.DateTimeOffset;
import com.panopto.services.FolderAvailabilitySettings;
import com.panopto.services.FolderEndSettingType;
import com.panopto.services.FolderStartSettingType;
import com.panopto.services.SessionAvailabilitySettings;
import com.panopto.services.SessionEndSettingType;
import com.panopto.services.SessionStartSettingType;

// Tests for Availability window logic
public class AvailabilityWindowsTest {
    private static final int offsetSeconds = 3600;

    // Test various combinations of folder and session settings for available sessions.  There are 4 different
    // possibilities for both start date and end date, so a total of 16 combinations are tested.  The possibilities 
    // are:
    //  - specific date on session
    //  - unbounded on session
    //  - specific date on folder
    //  - unbounded on folder
    @Test
    public void availableCombinations() throws Exception {
        Calendar now = Calendar.getInstance();
        
        SessionAvailabilitySettings sessionSettings = new SessionAvailabilitySettings();
        FolderAvailabilitySettings folderSettings = new FolderAvailabilitySettings();
     
        // Test all combinations with specific end date on session
        AvailabilityWindowsTest.setEndInFuture(
            sessionSettings,
            now);
        AvailabilityWindowsTest.testSessionFolderStartInPastCombinations(
            sessionSettings,
            folderSettings,
            now,
            PanoptoAvailabilityWindow.AvailabilityState.Available);
        
        // Test all combinations with unbounded end date on session
        AvailabilityWindowsTest.setEndNever(
            sessionSettings);
        AvailabilityWindowsTest.testSessionFolderStartInPastCombinations(
            sessionSettings,
            folderSettings,
            now,
            PanoptoAvailabilityWindow.AvailabilityState.Available);

        // Test all combinations with specific end date on folder
        AvailabilityWindowsTest.setEndInFuture(
            sessionSettings,
            folderSettings,
            now);
        AvailabilityWindowsTest.testSessionFolderStartInPastCombinations(
            sessionSettings,
            folderSettings,
            now,
            PanoptoAvailabilityWindow.AvailabilityState.Available);
        
        // Test all combinations with unbounded end date on folder
        AvailabilityWindowsTest.setEndNever(
            sessionSettings,
            folderSettings);
        AvailabilityWindowsTest.testSessionFolderStartInPastCombinations(
            sessionSettings,
            folderSettings,
            now,
            PanoptoAvailabilityWindow.AvailabilityState.Available);
    }

    // Test various combinations where the session isn't available yet using session and folder settings.  Each of the 
    // possibilities in availableCombinations needs to be tested for end date while testing an explicit start date on
    // both session and folder.
    @Test
    public void beforeCombinations() throws Exception {
        Calendar now = Calendar.getInstance();
        
        SessionAvailabilitySettings sessionSettings = new SessionAvailabilitySettings();
        FolderAvailabilitySettings folderSettings = new FolderAvailabilitySettings();

        // Test all combinations with specific start date on session
        AvailabilityWindowsTest.setStartInFuture(
            sessionSettings,
            now);
        AvailabilityWindowsTest.testSessionFolderEndInFutureCombinations(
            sessionSettings,
            folderSettings,
            now,
            PanoptoAvailabilityWindow.AvailabilityState.NotAvailable);

        // Test all combinations with specific start date on folder
        AvailabilityWindowsTest.setStartInFuture(
            sessionSettings,
            folderSettings,
            now);
        AvailabilityWindowsTest.testSessionFolderEndInFutureCombinations(
            sessionSettings,
            folderSettings,
            now,
            PanoptoAvailabilityWindow.AvailabilityState.NotAvailable);
    }

    // Test various combinations where the session isn't available anymore using session and folder settings.  Each of
    // the possibilities in availableCombinations needs to be tested for start date while testing an explicit end date
    // on both session and folder.
    @Test
    public void afterCombinations() throws Exception {
        Calendar now = Calendar.getInstance();

        SessionAvailabilitySettings sessionSettings = new SessionAvailabilitySettings();
        FolderAvailabilitySettings folderSettings = new FolderAvailabilitySettings();

        // Test all combinations with specific end date on session
        AvailabilityWindowsTest.setEndInPast(
            sessionSettings,
            now);
        AvailabilityWindowsTest.testSessionFolderStartInPastCombinations(
            sessionSettings,
            folderSettings,
            now,
            PanoptoAvailabilityWindow.AvailabilityState.NotAvailable);

        // Test all combinations with specific end date on folder
        AvailabilityWindowsTest.setEndInPast(
            sessionSettings,
            folderSettings,
            now);
        AvailabilityWindowsTest.testSessionFolderStartInPastCombinations(
            sessionSettings,
            folderSettings,
            now,
            PanoptoAvailabilityWindow.AvailabilityState.NotAvailable);
    }

    // Test combinations of settings for unpublished sessions
    @Test
    public void unpublishedCombinations() throws Exception {
        Calendar now = Calendar.getInstance();

        SessionAvailabilitySettings sessionSettings = new SessionAvailabilitySettings();
        FolderAvailabilitySettings folderSettings = new FolderAvailabilitySettings();

        // Test all combinations with an unpublished start date - only start state can be unpublished
        sessionSettings.setStartSettingType(SessionStartSettingType.WithItsFolder);
        folderSettings.setStartSettingType(FolderStartSettingType.WhenPublisherApproved);
        AvailabilityWindowsTest.testSessionFolderEndInFutureCombinations(
            sessionSettings,
            folderSettings,
            now,
            PanoptoAvailabilityWindow.AvailabilityState.Unpublished);
    }
    
    // Test combinations of settings where the state can't be determined without the folder settings
    @Test
    public void unknownCombinations() throws Exception {
        Calendar now = Calendar.getInstance();

        SessionAvailabilitySettings sessionSettings = new SessionAvailabilitySettings();

        // Folder settings needed for start date
        sessionSettings.setStartSettingType(SessionStartSettingType.WithItsFolder);
        AvailabilityWindowsTest.setEndNever(sessionSettings);
        Assert.assertEquals(
            AvailabilityState.Unknown,
            PanoptoAvailabilityWindow.getSessionAvailability(
                sessionSettings, 
                null));

        // Folder settings needed for end date
        AvailabilityWindowsTest.setStartImmediately(sessionSettings);
        sessionSettings.setEndSettingType(SessionEndSettingType.WithItsFolder);
        Assert.assertEquals(
            AvailabilityState.Unknown,
            PanoptoAvailabilityWindow.getSessionAvailability(
                sessionSettings, 
                null));
    }
    
    // Helper methods for setting up test scenarios
    
    // Add the given number of seconds to a Calendar, and return a corresponding DateTimeOffset
    private static DateTimeOffset addSecondsOffset(
        Calendar calendar,
        int seconds)
    {
        Calendar adjustedCalendar = Calendar.getInstance();
        adjustedCalendar.setTimeInMillis(
            calendar.getTimeInMillis() + (long)(seconds * 1000));
        
        DateTimeOffset result = new DateTimeOffset();
        result.setDateTime(adjustedCalendar);
        return result;
    }
    
    
    // Set a start date in the past on session settings 
    private static void setStartInPast(
        SessionAvailabilitySettings sessionSettings,
        Calendar now)
    {        
        sessionSettings.setStartSettingType(SessionStartSettingType.SpecificDate);
        sessionSettings.setStartSettingDate(
            AvailabilityWindowsTest.addSecondsOffset(
                now,
                -2 * AvailabilityWindowsTest.offsetSeconds));
    }

    // Set a start date in the past on folder settings
    private static void setStartInPast(
        SessionAvailabilitySettings sessionSettings,
        FolderAvailabilitySettings folderSettings,
        Calendar now)
    {
        sessionSettings.setStartSettingType(SessionStartSettingType.WithItsFolder);
        
        folderSettings.setStartSettingType(FolderStartSettingType.SpecificDate);
        folderSettings.setStartSettingDate(
            AvailabilityWindowsTest.addSecondsOffset(
                now,
                -2 * AvailabilityWindowsTest.offsetSeconds));
    }

    // Set an immediate start on session settings
    private static void setStartImmediately(
        SessionAvailabilitySettings sessionSettings)
    {
        sessionSettings.setStartSettingType(SessionStartSettingType.Immediately);
    }

    // Set an immediate start on folder settings
    private static void setStartImmediately(
        SessionAvailabilitySettings sessionSettings,
        FolderAvailabilitySettings folderSettings)
    {
        sessionSettings.setStartSettingType(SessionStartSettingType.WithItsFolder);
        
        folderSettings.setStartSettingType(FolderStartSettingType.Immediately);
    }

    // Set a start date in the future on session settings
    private static void setStartInFuture(
        SessionAvailabilitySettings sessionSettings,
        Calendar now)
    {
        sessionSettings.setStartSettingType(SessionStartSettingType.SpecificDate);
        sessionSettings.setStartSettingDate(
            AvailabilityWindowsTest.addSecondsOffset(
                now,
                AvailabilityWindowsTest.offsetSeconds));
    }
    
    // Set a start date in the future on folder settings
    private static void setStartInFuture(
        SessionAvailabilitySettings sessionSettings,
        FolderAvailabilitySettings folderSettings,
        Calendar now)
    {
        sessionSettings.setStartSettingType(SessionStartSettingType.WithItsFolder);

        folderSettings.setStartSettingType(FolderStartSettingType.SpecificDate);
        folderSettings.setStartSettingDate(
            AvailabilityWindowsTest.addSecondsOffset(
                now,
                AvailabilityWindowsTest.offsetSeconds));
    }
    

    // Set an end date in the past on session settings
    private static void setEndInPast(
        SessionAvailabilitySettings sessionSettings,
        Calendar now)
    {   
        sessionSettings.setEndSettingType(SessionEndSettingType.SpecificDate);
        sessionSettings.setEndSettingDate(
            AvailabilityWindowsTest.addSecondsOffset(
                now,
                -AvailabilityWindowsTest.offsetSeconds));
    }

    // Set an end date in the past on folder settings
    private static void setEndInPast(
        SessionAvailabilitySettings sessionSettings,
        FolderAvailabilitySettings folderSettings,
        Calendar now)
    {   
        sessionSettings.setEndSettingType(SessionEndSettingType.WithItsFolder);
        
        folderSettings.setEndSettingType(FolderEndSettingType.SpecificDate);
        folderSettings.setEndSettingDate(
            AvailabilityWindowsTest.addSecondsOffset(
                now,
                -AvailabilityWindowsTest.offsetSeconds));
    }

    // Set an end date in the future on session settings
    private static void setEndInFuture(
        SessionAvailabilitySettings sessionSettings,
        Calendar now)
    {   
        sessionSettings.setEndSettingType(SessionEndSettingType.SpecificDate);
        sessionSettings.setEndSettingDate(
            AvailabilityWindowsTest.addSecondsOffset(
                now,
                2 * AvailabilityWindowsTest.offsetSeconds));
    }

    // Set an end date in the future on folder settings
    private static void setEndInFuture(
        SessionAvailabilitySettings sessionSettings,
        FolderAvailabilitySettings folderSettings,
        Calendar now)
    {   
        sessionSettings.setEndSettingType(SessionEndSettingType.WithItsFolder);
        
        folderSettings.setEndSettingType(FolderEndSettingType.SpecificDate);
        folderSettings.setEndSettingDate(
            AvailabilityWindowsTest.addSecondsOffset(
                now,
                2 * AvailabilityWindowsTest.offsetSeconds));
    }

    // Set no end date on session settings
    private static void setEndNever(
        SessionAvailabilitySettings sessionSettings)
    {   
        sessionSettings.setEndSettingType(SessionEndSettingType.Forever);
    }

    // Set no end date on folder settings
    private static void setEndNever(
        SessionAvailabilitySettings sessionSettings,
        FolderAvailabilitySettings folderSettings)
    {   
        sessionSettings.setEndSettingType(SessionEndSettingType.WithItsFolder);

        folderSettings.setEndSettingType(FolderEndSettingType.Forever);
    }
    
    // Test all different combinations of start date in the past - these are the 4 states described in 
    // availableCombinations
    private static void testSessionFolderStartInPastCombinations(
        SessionAvailabilitySettings sessionSettings,
        FolderAvailabilitySettings folderSettings,
        Calendar now,
        PanoptoAvailabilityWindow.AvailabilityState expectedResult) throws RemoteException
    {
        // Specific date on session
        AvailabilityWindowsTest.setStartInPast(
            sessionSettings,
            now);
        Assert.assertEquals(
            expectedResult,
            PanoptoAvailabilityWindow.getSessionAvailability(
                sessionSettings, 
                folderSettings));

        // Unbounded date on session
        AvailabilityWindowsTest.setStartImmediately(
            sessionSettings);
        Assert.assertEquals(
            expectedResult,
            PanoptoAvailabilityWindow.getSessionAvailability(
                sessionSettings, 
                folderSettings));

        // Specific date on folder
        AvailabilityWindowsTest.setStartInPast(
            sessionSettings,
            folderSettings,
            now);
        Assert.assertEquals(
            expectedResult,
            PanoptoAvailabilityWindow.getSessionAvailability(
                sessionSettings, 
                folderSettings));

        // Unbounded date on folder
        AvailabilityWindowsTest.setStartImmediately(
            sessionSettings,
            folderSettings);
        Assert.assertEquals(
            expectedResult,
            PanoptoAvailabilityWindow.getSessionAvailability(
                sessionSettings, 
                folderSettings));
    }

    // Test all different combinations of end date in the future - these are the 4 states described in 
    // availableCombinations
    private static void testSessionFolderEndInFutureCombinations(
        SessionAvailabilitySettings sessionSettings,
        FolderAvailabilitySettings folderSettings,
        Calendar now,
        PanoptoAvailabilityWindow.AvailabilityState expectedResult) throws RemoteException
    {
        // Specific date on session
        AvailabilityWindowsTest.setEndInFuture(
            sessionSettings,
            now);
        Assert.assertEquals(
            expectedResult,
            PanoptoAvailabilityWindow.getSessionAvailability(
                sessionSettings, 
                folderSettings));

        // Unbounded date on session
        AvailabilityWindowsTest.setEndNever(
            sessionSettings);
        Assert.assertEquals(
            expectedResult,
            PanoptoAvailabilityWindow.getSessionAvailability(
                sessionSettings, 
                folderSettings));

        // Specific date on folder
        AvailabilityWindowsTest.setEndInFuture(
            sessionSettings,
            folderSettings,
            now);
        Assert.assertEquals(
            expectedResult,
            PanoptoAvailabilityWindow.getSessionAvailability(
                sessionSettings, 
                folderSettings));

        // Unbounded date on folder
        AvailabilityWindowsTest.setEndNever(
            sessionSettings,
            folderSettings);
        Assert.assertEquals(
            expectedResult,
            PanoptoAvailabilityWindow.getSessionAvailability(
                sessionSettings, 
                folderSettings));
    }
}
