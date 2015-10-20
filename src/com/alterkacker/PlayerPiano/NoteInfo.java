package com.alterkacker.PlayerPiano;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mblum on 10/19/15.
 */
class NoteInfo {
    int noteNum;
    char noteAcc;
    int noteValue;

    NoteInfo(int num, char acc, int value) {
        this.noteNum = num;
        this.noteAcc = acc;
        this.noteValue = value;
    }

    NoteInfo(String s){
        List<String> keys1 = Arrays.asList("cn", "c#", "dn", "d#", "en", "fn", "f#", "gn", "g#", "an", "a#", "bn");
        List<String> keys2 = Arrays.asList("cn", "db", "dn", "eb", "en", "fn", "gb", "gn", "ab", "an", "bb", "bn");
        int octave = 4;
        int xpos = 0;
        int value = 4;

        String sx = s.toLowerCase();
        int sxl = sx.length();

//        char s1 = sx.charAt(xpos);
//        if (Character.isDigit(s1)){
//            octave = s1 - '0';
//            xpos = 1;
//        }

        // Get the note character
        String noteChar = sx.substring(xpos, xpos+1);

        // Look for accidental character
        xpos++;
        String accChar = "n";
        if (xpos < sxl){
            accChar = sx.substring(xpos, xpos+1);
            if ("#bn".indexOf(accChar) > 0){
                xpos++;
            } else {
                accChar = "n";
            }
        }

        // Determine noteNumber without octave
        noteChar += accChar;
        int noteNumber = 0;
        if ("#n".indexOf(accChar) > 0){
            noteNumber = keys1.indexOf(noteChar);
        } else {
            noteNumber = keys2.indexOf(noteChar);
        }

        // Next character can be octave number, or "/" starting value spec
        String octaveChar = "4";
        if (xpos < sxl) {
            String x = sx.substring(xpos, xpos + 1);
            if (Character.isDigit(x.charAt(0))){
                octaveChar = x;
                xpos++;
            }
        }
        octave = octaveChar.charAt(0) - '0';
        noteNumber += 12*(octave+1);

        if (xpos < sxl) {
            String x = sx.substring(xpos, xpos + 1);
            if ("/".equals(x)){
                xpos++;
                String valueSpec = x.substring(xpos);
                boolean dotted = valueSpec.endsWith(".");
                if (dotted){
                    valueSpec = valueSpec.substring(0,valueSpec.length()-1);
                }
                value = 32 / Integer.valueOf(valueSpec);
                if (dotted)
                    value += value / 2;
            }
        }

        this.noteNum = noteNumber;
        this.noteAcc = accChar.charAt(0);
        this.noteValue = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoteInfo noteInfo = (NoteInfo) o;

        if (noteNum != noteInfo.noteNum) return false;
        if (noteAcc != noteInfo.noteAcc) return false;
        return noteValue == noteInfo.noteValue;

    }

    @Override
    public int hashCode() {
        int result = noteNum;
        result = 31 * result + (int) noteAcc;
        result = 31 * result + noteValue;
        return result;
    }
}
