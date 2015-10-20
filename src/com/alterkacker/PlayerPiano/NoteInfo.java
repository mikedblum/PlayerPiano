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

    NoteInfo (String s){
        List<String> keys1 = Arrays.asList("cn", "c#", "dn", "d#", "en", "fn", "f#", "gn", "g#", "an", "a#", "bn");
        List<String> keys2 = Arrays.asList("cn", "db", "dn", "eb", "en", "fn", "gb", "gn", "ab", "an", "bb", "bn");
        int octave = 4;
        int xpos = 0;
        int value = 4;

        String sx = s.toLowerCase();

        char s1 = sx.charAt(xpos);
        if (Character.isDigit(s1)){
            octave = s1 - '0';
            xpos = 1;
        }

        String noteChar = sx.substring(xpos, xpos+1);
        xpos++;
        char acc = 'n';
        if (xpos < sx.length()){
            acc = sx.charAt(xpos);
            switch (acc){
                case '#':
                    acc = '#';
                    xpos++;
                    break;
                case 'b':
                    acc='b';
                    xpos++;
                    break;
                case 'n':
                    acc = 'n';
                    xpos++;
                    break;
            }
        }
        noteChar += acc;
        int num = 0;
        if (acc == '#' || acc == 'n'){
            num = keys1.indexOf((String) noteChar);
        } else {
            num = keys2.indexOf((String) noteChar);
        }
        num += 12*(octave+1);

        if (xpos < s.length()){
            value = sx.charAt(xpos) - '0';
            xpos++;
        }
        if (xpos < sx.length()){
            if (sx.charAt(xpos) == '.'){
                value += value / 2;
            }
        }

        this.noteNum = num;
        this.noteAcc = acc;
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
