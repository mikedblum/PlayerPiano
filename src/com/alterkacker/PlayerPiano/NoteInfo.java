package com.alterkacker.PlayerPiano;

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
        String keys1[] = {"c0", "c#", "d0", "d#", "e0", "f0", "f#", "g0", "g#", "a0", "a#", "b0"};
        int octave = 4;
        int xpos = 0;
        int value = 4;

        char s1 = s.charAt(xpos);
        if (Character.isDigit(s1)){
            octave = s1 - '4';
            xpos = 1;
        }

        int num = (s.charAt(xpos) - 'c') % 12;
        xpos++;
        char acc = '0';
        if (xpos < s.length()){
            acc = s.charAt(xpos);
            switch (acc){
                case '#':
                    num++;
                    acc = '+';
                    break;
                case 'b':case 'B':
                    num--;
                    acc='-';
                    break;
                case 'N':case 'n':
                    acc = '0';
                    break;
            }
            xpos++;
        }
        num += 12*(octave + 1);

        if (xpos < s.length()){
            value = Integer.valueOf(s.charAt(xpos));
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
