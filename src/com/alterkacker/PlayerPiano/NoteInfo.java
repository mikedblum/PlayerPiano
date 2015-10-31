package com.alterkacker.PlayerPiano;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mblum on 10/20/15.
 */
public class NoteInfo {
    int noteNumber;
    double noteMsec;
    // Following fields not included in equals & hashCode methods
    String noteSpec;

    static List<String> keysSharp = Arrays.asList("cn", "c#", "dn", "d#", "en", "fn", "f#", "gn", "g#", "an", "a#", "bn");
    static List<String> keysFlat = Arrays.asList("cn", "db", "dn", "eb", "en", "fn", "gb", "gn", "ab", "an", "bb", "bn");
    static List<String> keysNone = Arrays.asList("c", "", "d", "", "e", "f", "", "g", "", "a", "", "b");

    NoteInfo(int noteNumber, double noteMsec){
        this.noteNumber = noteNumber;
        this.noteMsec = noteMsec;
    }


    NoteInfo(int noteNumber, double noteMsec, String spec){
        this.noteNumber = noteNumber;
        this.noteMsec = noteMsec;
        this.noteSpec = spec;
    }

    NoteInfo(NoteSpec nspec, int qtrMsec){
        this.noteSpec = nspec.noteSpec;

        String fullNote = nspec.noteLtr;
        if (nspec.noteAcc != null)
            fullNote += nspec.noteAcc;

        int noteNumber;
        if (nspec.noteLtr.equals("r")){
            noteNumber = -1;
        } else {
            // Determine noteNumber without octave
            if (nspec.noteAcc == null){
                noteNumber = keysNone.indexOf(nspec.noteLtr);
            } else if ("#n".contains(nspec.noteAcc)) {
                noteNumber = keysSharp.indexOf(fullNote);
            } else {
                noteNumber = keysFlat.indexOf(fullNote);
            }

            // Apply octave
            noteNumber += 12 * (nspec.noteOctave + 1);
        }

        // Determine note Msec
        double fullMsec = qtrMsec * 4;
        double noteMsec = fullMsec / nspec.noteValue;

        this.noteNumber = noteNumber;
        this.noteMsec = noteMsec;
    }
}
