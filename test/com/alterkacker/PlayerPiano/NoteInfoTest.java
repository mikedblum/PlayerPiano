package com.alterkacker.PlayerPiano;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mblum on 10/19/15.
 */
public class NoteInfoTest {

    @Test
    public void testSimple() throws Exception {
        NoteInfo n, n2;
        n = new NoteInfo(60, 'n', 4);
        n2 = new NoteInfo("c");
        assertEquals("Should be Middle C (60)", n, n2);

        n = new NoteInfo(67, 'n', 4);
        n2 = new NoteInfo("g");
        assertEquals("Should be Middle G (67)", n, n2);

        n = new NoteInfo(69, 'n', 4);
        n2 = new NoteInfo("a");
        assertEquals("Should be Middle A (69)", n, n2);

        n = new NoteInfo(71, 'n', 4);
        n2 = new NoteInfo("b");
        assertEquals("Should be Middle B (71)", n, n2);
    }

    @Test
    public void testOctaves() throws Exception {
        NoteInfo n, n2;

        n = new NoteInfo(60, 'n', 4);
        n2 = new NoteInfo("4c");
        assertEquals("Should be Middle C (60)", n, n2);

        n = new NoteInfo(24, 'n', 4);
        n2 = new NoteInfo("1c");
        assertEquals("Should be Octave 1 C", n, n2);

        n = new NoteInfo(98, 'n', 4);
        n2 = new NoteInfo("7d");
        assertEquals("Should be Octave 7 D", n, n2);
    }

    @Test
    public void testAccidentals() throws Exception {
        NoteInfo n, n2;

        n = new NoteInfo(59, 'b', 4);
        n2 = new NoteInfo("cb");
        assertEquals("Should be Middle C flat (59)", n, n2);

        n = new NoteInfo(61, '#', 4);
        n2 = new NoteInfo("c#");
        assertEquals("Should be Middle C sharp (61)", n, n2);

        n = new NoteInfo(60, 'n', 4);
        n2 = new NoteInfo("cn");
        assertEquals("Should be Middle C (60)", n, n2);
    }

    @Test
    public void testValue() throws Exception {
        NoteInfo n, n2;

        n = new NoteInfo(60, 'n', 8);
        n2 = new NoteInfo("c8");
        assertEquals("Should be Middle C (60) eigth", n, n2);

        n = new NoteInfo(60, 'n', 12);
        n2 = new NoteInfo("c8.");
        assertEquals("Should be Middle C (60) dotted eigth", n, n2);
    }
}