package com.alterkacker.PlayerPiano;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mblum on 10/19/15.
 */
public class NoteSpecTest {

    @Test
    public void testSimple() throws Exception {
        NoteSpec n, n2;
        n = new NoteSpec("c", null, 4, 4);
        n2 = new NoteSpec("c");
        assertEquals("Should be Middle C (60)", n, n2);

        n = new NoteSpec("g", null, 4, 4);
        n2 = new NoteSpec("g");
        assertEquals("Should be Middle G (67)", n, n2);

        n = new NoteSpec("a", null, 4, 4);
        n2 = new NoteSpec("a");
        assertEquals("Should be Middle A (69)", n, n2);

        n = new NoteSpec("b", null, 4, 4);
        n2 = new NoteSpec("b");
        assertEquals("Should be Middle B (71)", n, n2);
    }

    @Test
    public void testOctaves() throws Exception {
        NoteSpec n, n2;

        n = new NoteSpec("c", null, 4, 4);
        n2 = new NoteSpec("c4");
        assertEquals("Should be Middle C (60)", n, n2);

        n = new NoteSpec("c", null, 1, 4);
        n2 = new NoteSpec("c1");
        assertEquals("Should be Octave 1 C", n, n2);

        n = new NoteSpec("d", null, 7, 4);
        n2 = new NoteSpec("d7");
        assertEquals("Should be Octave 7 D", n, n2);
    }

    @Test
    public void testAccidentals() throws Exception {
        NoteSpec n, n2;

        n = new NoteSpec("c", "b", 4, 4);
        n2 = new NoteSpec("cb");
        assertEquals("Should be Middle C flat (59)", n, n2);

        n = new NoteSpec("c", "#", 4, 4);
        n2 = new NoteSpec("c#");
        assertEquals("Should be Middle C sharp (61)", n, n2);

        n = new NoteSpec("c", "n", 4, 4);
        n2 = new NoteSpec("cn");
        assertEquals("Should be Middle C (60)", n, n2);
    }

    @Test
    public void testValue() throws Exception {
        NoteSpec n, n2;

        n = new NoteSpec("c", null, 4, 8);
        n2 = new NoteSpec("c/8");
        assertEquals("Should be Middle C (60) eigth", n, n2);

        n = new NoteSpec("c", null, 4, 8.0*(2.0/3.0));
        n2 = new NoteSpec("c/8.");
        assertEquals("Should be Middle C (60) dotted eigth", n, n2);
    }

    @Test
    public void testAll() throws Exception{
        NoteSpec n, n2;

        n = new NoteSpec("f", "#", 2, (double) 4.0*((double) 2.0/3.0));
        n2 = new NoteSpec("f#2/4.");
        assertEquals("Should be F#4 dotted fourth", n, n2);
    }

}