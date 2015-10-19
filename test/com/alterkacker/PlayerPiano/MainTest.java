package com.alterkacker.PlayerPiano;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mblum on 10/19/15.
 */
public class MainTest {

    @Test
    public void testNoteInfoSimple() throws Exception {
        NoteInfo n = new NoteInfo(60, '0', 4);
        NoteInfo n2 = new NoteInfo("c");
        assertEquals("Should be Middle C (60)", n, n2);

        n = new NoteInfo(67, '0', 4);
        n2 = new NoteInfo("g");
        assertEquals("Should be Middle G (67)", n, n2);

        n = new NoteInfo(69, '0', 4);
        n2 = new NoteInfo("a");
        assertEquals("Should be Middle A (69)", n, n2);

        n = new NoteInfo(71, '0', 4);
        n2 = new NoteInfo("b");
        assertEquals("Should be Middle B (71)", n, n2);
    }
}