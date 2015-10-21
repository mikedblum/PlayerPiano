package com.alterkacker.PlayerPiano;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mblum on 10/20/15.
 */
public class NoteInfo {
    static List<String> keys1 = Arrays.asList("cn", "c#", "dn", "d#", "en", "fn", "f#", "gn", "g#", "an", "a#", "bn");
    static List<String> keys2 = Arrays.asList("cn", "db", "dn", "eb", "en", "fn", "gb", "gn", "ab", "an", "bb", "bn");

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
        // Determine noteNumber without octave
        int noteNumber;
        if ("#n".indexOf(noteAcc) > 0){
            noteNumber = keys1.indexOf(fullNote);
        } else {
            noteNumber = keys2.indexOf(fullNote);
        }

        // Apply octave
        noteNumber += 12*(nspec.noteOctave+1);

        // Determine note Msec
        int fullMsec = qtrMsec * 4;
        int noteMsec = fullMsec / nspec.noteValue;

        this.noteNumber = noteNumber;
        this.noteMsec = noteMsec;
    }
}
