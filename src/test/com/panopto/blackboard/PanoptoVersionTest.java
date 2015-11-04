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

    @Test
    public void notAVersionNumberMakesEmpty() throws Exception {
        PanoptoVersion defaultedToEmptyVersion = PanoptoVersion.from("NOT A VERSION NUMBER");
        assertEquals(0, defaultedToEmptyVersion.getNumParts());
        assertEquals("", defaultedToEmptyVersion.toString());
    }

    @Test
    public void nullVersionStringMakesEmpty() throws Exception {
        PanoptoVersion defaultedToEmptyVersion = PanoptoVersion.from(null);
        assertEquals(0, defaultedToEmptyVersion.getNumParts());
        assertEquals("", defaultedToEmptyVersion.toString());
    }

    @Test
    public void compareSingleDigit() throws Exception {
        PanoptoVersion v4 = PanoptoVersion.from("4");
        PanoptoVersion v3 = PanoptoVersion.from("3");
        assertEquals(0, v4.compareTo(v4));
        assertEquals(-1, v3.compareTo(v4));
        assertEquals(1, v4.compareTo(v3));
    }

    @Test
    public void compareDifferentDigits() throws Exception {
        PanoptoVersion v49 = PanoptoVersion.from("4.9");
        PanoptoVersion v492 = PanoptoVersion.from("4.9.2");
        PanoptoVersion v48 = PanoptoVersion.from("4.8");
        PanoptoVersion v482 = PanoptoVersion.from("4.8.2");
        PanoptoVersion v3 = PanoptoVersion.from("3");

        assertEquals(0, v49.compareTo(v49));
        assertEquals(0, v492.compareTo(v492));

        assertEquals(-1, v3.compareTo(v49));
        assertEquals(1, v49.compareTo(v3));

        assertEquals(-1, v48.compareTo(v49));
        assertEquals(1, v49.compareTo(v48));

        assertEquals(-1, v49.compareTo(v492));
        assertEquals(1, v492.compareTo(v49));

        assertEquals(-1, v482.compareTo(v49));
        assertEquals(-1, v482.compareTo(v492));
    }
}