package com.panopto.blackboard;

import org.junit.Test;

import static org.junit.Assert.*;

public class PanoptoVersionsTest {

    @Test
    public void canCallWindowsApi() throws Exception {
        assertTrue(PanoptoVersions.canCallAvailabilityWindowApiMethods(PanoptoVersion.from("4.9")));
        assertTrue(PanoptoVersions.canCallAvailabilityWindowApiMethods(PanoptoVersion.from("4.9.0")));
        assertTrue(PanoptoVersions.canCallAvailabilityWindowApiMethods(PanoptoVersion.from("4.9.0.0")));
        assertTrue(PanoptoVersions.canCallAvailabilityWindowApiMethods(PanoptoVersion.from("4.9.2")));

        assertFalse(PanoptoVersions.canCallAvailabilityWindowApiMethods(PanoptoVersion.from("4.8")));
    }

    @Test
    public void emptyCannotCallWindowsApi() throws Exception {
        assertFalse(PanoptoVersions.canCallAvailabilityWindowApiMethods(PanoptoVersion.EMPTY));
    }
}