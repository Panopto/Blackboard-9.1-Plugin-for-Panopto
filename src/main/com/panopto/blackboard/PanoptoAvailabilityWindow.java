package com.panopto.blackboard;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.TimeZone;

import com.panopto.services.SessionManagementStub.DateTimeOffset;
import com.panopto.services.SessionManagementStub.FolderAvailabilitySettings;
import com.panopto.services.SessionManagementStub.FolderEndSettingType;
import com.panopto.services.SessionManagementStub.FolderStartSettingType;
import com.panopto.services.SessionManagementStub.SessionAvailabilitySettings;
import com.panopto.services.SessionManagementStub.SessionEndSettingType;
import com.panopto.services.SessionManagementStub.SessionStartSettingType;

// Helper class for handling Panopto availability windows
public class PanoptoAvailabilityWindow {
    
    // Availability state of a session
    public enum AvailabilityState 
    {
        // state couldn't be determined
        Unknown,
        // session is available
        Available,
        // session is not available
        NotAvailable,
        // session requires publishing
        Unpublished
    }

    // Whether folder availability information is needed in order to determine session availability.
    //
    // Currently this is whenever the start or end setting is "WithItsFolder".  This could be further optimized, for 
    // example, by checking if specified start of end dates already rule out the session being available.   
    public static boolean isFolderRequiredForSessionAvailability(
        SessionAvailabilitySettings sessionSettings)
    {
        return (sessionSettings.getStartSettingType().equals(SessionStartSettingType.WithItsFolder)
            || sessionSettings.getEndSettingType().equals(SessionEndSettingType.WithItsFolder));
    }
    
    // Determines if a given Panopto session is currently within its availability window.
    //
    // The folder availability settings are only required when isFolderRequiredForSessionAvailability returns true.  If
    // the folder settings are needed but not provided AvailabilityState.Unknown is returned.
    public static AvailabilityState getSessionAvailability(
        SessionAvailabilitySettings sessionSettings,
        FolderAvailabilitySettings folderSettings) throws RemoteException 
    {
        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        
        // Get the start and end availability states using the session settings or both, as necessary
        AvailabilityState startAvailability;
        AvailabilityState endAvailability;
        if (PanoptoAvailabilityWindow.isFolderRequiredForSessionAvailability(sessionSettings))
        {
            startAvailability = PanoptoAvailabilityWindow.getSessionStartAvailabilityState(
                sessionSettings,
                folderSettings,
                now);
            endAvailability = PanoptoAvailabilityWindow.getSessionEndAvailabilityState(
                sessionSettings,
                folderSettings,
                now);
        }
        else
        {
            startAvailability = PanoptoAvailabilityWindow.getSessionStartAvailabilityState(
                sessionSettings,
                now);
            endAvailability = PanoptoAvailabilityWindow.getSessionEndAvailabilityState(
                sessionSettings,
                now);
        }

        // Combine the two availability results into one
        AvailabilityState result;
        if (startAvailability == AvailabilityState.Unknown
            || endAvailability == AvailabilityState.Unknown)
        {
            // If either state is unknown we can't determine the overall state
            result = AvailabilityState.Unknown;
        }
        else if (startAvailability == AvailabilityState.Unpublished
            || endAvailability == AvailabilityState.Unpublished)
        {
            // If either state is unpublished then the session is unpublished.  Currently only the start state can be
            // Unpublished, but here we handle either.
            result = AvailabilityState.Unpublished;
        }
        else if (startAvailability == AvailabilityState.Available
            && endAvailability == AvailabilityState.Available)
        {
            // Both states must be available for the session to be available
            result = AvailabilityState.Available;
        }
        else
        {
            // Otherwise not available
            result = AvailabilityState.NotAvailable;
        }
        return result;
    }
    
    // Get the start availability based only on session settings
    private static AvailabilityState getSessionStartAvailabilityState(
        SessionAvailabilitySettings sessionSettings,
        Calendar now)
    {
        SessionStartSettingType startType = sessionSettings.getStartSettingType();

        AvailabilityState result;   
        if (startType.equals(SessionStartSettingType.WithItsFolder))
        {
            // Folder settings are required
            result = AvailabilityState.Unknown;
        }
        else if (startType.equals(SessionStartSettingType.Immediately)
            || (startType.equals(SessionStartSettingType.SpecificDate)
                 && now.after(
                     PanoptoAvailabilityWindow.getCalendarFromDateTimeOffset(
                         sessionSettings.getStartSettingDate()))))
        {
            // We're after the start date, the session is available
            result = AvailabilityState.Available;
        }
        else
        {
            // Otherwise not available
            result = AvailabilityState.NotAvailable;
        }
        return result;
    }

    // Get the end availability based only on session settings
    private static AvailabilityState getSessionEndAvailabilityState(
        SessionAvailabilitySettings sessionSettings,
        Calendar now)
    {
        SessionEndSettingType endType = sessionSettings.getEndSettingType();

        AvailabilityState result;   
        if (endType.equals(SessionEndSettingType.WithItsFolder))
        {
            // Folder settings are required
            result = AvailabilityState.Unknown;
        }
        else if(endType.equals(SessionEndSettingType.Forever)
            || (endType.equals(SessionEndSettingType.SpecificDate)
                && now.before(PanoptoAvailabilityWindow.getCalendarFromDateTimeOffset(
                    sessionSettings.getEndSettingDate()))))
        {
            // We're before the end date, the session is available
            result = AvailabilityState.Available;
        }
        else
        {
            // Otherwise not available
            result = AvailabilityState.NotAvailable;
        }
        return result;
    }
    
    // Get the start availability based on session and folder settings
    private static AvailabilityState getSessionStartAvailabilityState(
        SessionAvailabilitySettings sessionSettings,
        FolderAvailabilitySettings folderSettings,
        Calendar now)
    {
        SessionStartSettingType sessionStartType = sessionSettings.getStartSettingType();
        
        AvailabilityState result;
        if (sessionStartType.equals(SessionStartSettingType.WithItsFolder)
            && folderSettings != null)
        {
            // We need and have the folder settings, determine the state
            FolderStartSettingType folderStartType = folderSettings.getStartSettingType();
            if (folderStartType.equals(FolderStartSettingType.WhenPublisherApproved))
            {
                // Session needs publishing
                result = AvailabilityState.Unpublished;
            }
            else if (folderStartType.equals(FolderStartSettingType.Immediately)
                || (folderStartType.equals(FolderStartSettingType.SpecificDate)
                    && now.after(PanoptoAvailabilityWindow.getCalendarFromDateTimeOffset(
                        folderSettings.getStartSettingDate()))))
            {
                // We're after the start date, the session is available
                result = AvailabilityState.Available;
            }
            else
            {
                // Otherwise not available
                result = AvailabilityState.NotAvailable;
            }
        }
        else
        {
            // Folder settings are not needed or not available, proceed without them
            result = PanoptoAvailabilityWindow.getSessionStartAvailabilityState(
                sessionSettings,
                now);
        }
        return result;
    }

    // Get the end availability based on session and folder settings
    private static AvailabilityState getSessionEndAvailabilityState(
        SessionAvailabilitySettings sessionSettings,
        FolderAvailabilitySettings folderSettings,
        Calendar now)
    {
        SessionEndSettingType sessionEndType = sessionSettings.getEndSettingType();
        
        AvailabilityState result;
        if (sessionEndType.equals(SessionEndSettingType.WithItsFolder)
           && folderSettings != null)
        {
            // We need and have the folder settings, determine the state
            FolderEndSettingType folderEndType = folderSettings.getEndSettingType();
            if (folderEndType.equals(FolderEndSettingType.Forever)
                || (folderEndType.equals(FolderEndSettingType.SpecificDate)
                    && now.before(PanoptoAvailabilityWindow.getCalendarFromDateTimeOffset(
                        folderSettings.getEndSettingDate()))))
            {
                // We're before the end date, the session is available
                result = AvailabilityState.Available;
            }
            else
            {
                // Otherwise not available
                result = AvailabilityState.NotAvailable;
            }
        }
        else
        {
            // Folder settings are not needed or not available, proceed without them
            result = PanoptoAvailabilityWindow.getSessionEndAvailabilityState(
                sessionSettings,
                now);
        }
        return result;
    }

    //If the offset is not null, then extract the date from it. Otherwise, set the date to null.
    private static Calendar getCalendarFromDateTimeOffset(DateTimeOffset offset)
    {
        Calendar result = null;
        if (offset != null)
        {
            result = offset.getDateTime();
            result.add(Calendar.MINUTE, offset.getOffsetMinutes());
        }
        return result;
    }
}
