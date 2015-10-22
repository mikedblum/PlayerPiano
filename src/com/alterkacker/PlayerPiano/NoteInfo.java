package com.alterkacker.PlayerPiano;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mblum on 10/20/15.
 */
public class NoteInfo {
    static List<String> keysSharp = Arrays.asList("cn", "c#", "dn", "d#", "en", "fn", "f#", "gn", "g#", "an", "a#", "bn");
    static List<String> keysFlat = Arrays.asList("cn", "db", "dn", "eb", "en", "fn", "gb", "gn", "ab", "an", "bb", "bn");
    static List<String> keysNone = Arrays.asList("c", "", "d", "", "e", "f", "", "g", "", "a", "", "b");

    int noteNumber;
    int noteMsec;

    NoteInfo(int noteNumber, int noteMsec){
        this.noteNumber = noteNumber;
        this.noteMsec = noteMsec;
    }

    NoteInfo(NoteSpec nspec, int qtrMsec){
        String noteLtr = String.valueOf(nspec.noteLtr);
        String noteAcc = String.valueOf(nspec.noteAcc);
        String fullNote = noteLtr + noteAcc;

        int noteNumber;
        if (noteLtr.equals("r")){
            noteNumber = -1;
        } else {
            // Determine noteNumber without octave
            if (" ".equals(noteAcc)){
                noteNumber = keysNone.indexOf(noteLtr);
            } else if ("#".equals(noteAcc)) {
                noteNumber = keysSharp.indexOf(fullNote);
            } else {
                noteNumber = keysFlat.indexOf(fullNote);
            }

            // Apply octave
            noteNumber += 12 * (nspec.noteOctave + 1);
        }

        // Determine note Msec
        int fullMsec = qtrMsec * 4;
        int noteMsec = fullMsec / nspec.noteValue;

        this.noteNumber = noteNumber;
        this.noteMsec = noteMsec;
    }
}
