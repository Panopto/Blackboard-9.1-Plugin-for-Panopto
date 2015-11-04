package com.panopto.blackboard;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PanoptoVersionTest {

    @Test
    public void singleDigit() throws Exception {
        PanoptoVersion version = PanoptoVersion.from("4");
        assertEquals(1, version.getNumParts());
        assertEquals(4, version.getPart(0));
        assertEquals("4", version.toString());
    }

    @Test
    public void coupleDigitsWithZeroPrefix() throws Exception {
        PanoptoVersion version = PanoptoVersion.from("4.34.5.06");
        assertEquals(4, version.getNumParts());
        assertEquals(4, version.getPart(0));
        assertEquals(34, version.getPart(1));
        assertEquals(5, version.getPart(2));
        assertEquals(6, version.getPart(3));
        assertEquals("4.34.5.6", version.toString());
    }

    @Test(expected = NumberFormatException.class)
    public void notAVersionNumber() throws Exception {
        PanoptoVersion.from("NOT A VERSION NUMBER");
    }

    @Test
    public void compareSingleDigit() throws Exception {
        PanoptoVersion version4 = PanoptoVersion.from("4");
        PanoptoVersion version3 = PanoptoVersion.from("3");
        assertEquals(0, version4.compareTo(version4));
        assertEquals(-1, version3.compareTo(version4));
        assertEquals(1, version4.compareTo(version3));
    }

    @Test
    public void compareDifferentDigits() throws Exception {
        PanoptoVersion version49 = PanoptoVersion.from("4.9");
        PanoptoVersion version492 = PanoptoVersion.from("4.9.2");
        PanoptoVersion version48 = PanoptoVersion.from("4.8");
        PanoptoVersion version3 = PanoptoVersion.from("3");

        assertEquals(0, version49.compareTo(version49));
        assertEquals(0, version492.compareTo(version492));

        assertEquals(-1, version3.compareTo(version49));
        assertEquals(1, version49.compareTo(version3));

        assertEquals(-1, version48.compareTo(version49));
        assertEquals(1, version49.compareTo(version48));

        assertEquals(-1, version49.compareTo(version492));
        assertEquals(1, version492.compareTo(version49));
    }
}