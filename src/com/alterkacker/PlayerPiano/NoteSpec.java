package com.alterkacker.PlayerPiano;

/**
 * Created by mblum on 10/19/15.
 */
class NoteSpec {
    char noteLtr;
    char noteAcc;
    int noteOctave;
    int noteValue;

    NoteSpec(char ltr, char acc, int octave, int value) {
        this.noteLtr = ltr;
        this.noteAcc = acc;
        this.noteOctave = octave;
        this.noteValue = value;
    }

    NoteSpec(String s){
//        List<String> keys1 = Arrays.asList("cn", "c#", "dn", "d#", "en", "fn", "f#", "gn", "g#", "an", "a#", "bn");
//        List<String> keys2 = Arrays.asList("cn", "db", "dn", "eb", "en", "fn", "gb", "gn", "ab", "an", "bb", "bn");
        int xpos = 0;

        String sx = s.toLowerCase();
        int sxl = sx.length();

        // Get the note character
        //        octave = octaveChar.charAt(0) - '0';
//        noteNumber += 12*(octave+1);

        char ltr = sx.charAt(xpos);

        // Look for accidental character
        xpos++;
        char acc = 'n';
        if (xpos < sxl){
            acc = sx.charAt(xpos);
            if (acc == '#' || acc == 'b' || acc == 'n'){
                xpos++;
            } else {
                acc = 'n';
            }
        }

        // Determine noteNumber without octave
//        int noteNumber = 0;
//        if ("#n".indexOf(accChar) > 0){
//            noteNumber = keys1.indexOf(noteChar);
//        } else {
//            noteNumber = keys2.indexOf(noteChar);
//        }

        // Next character can be octave number, or "/" starting value spec
        int octave = 4;
        if (xpos < sxl) {
            char x = sx.charAt(xpos);
            if (x >= '0' && x <= '9'){
                octave = x - '0';
                xpos++;
            }
        }
//        octave = octaveChar.charAt(0) - '0';
//        noteNumber += 12*(octave+1);

        int value = 4;
        if (xpos < sxl) {
            char x = sx.charAt(xpos);
            if (x == '/'){
                xpos++;
                String valueSpec = sx.substring(xpos);
                boolean dotted = valueSpec.endsWith(".");
                if (dotted){
                    valueSpec = valueSpec.substring(0,valueSpec.length()-1);
                }
                value = Integer.valueOf(valueSpec);
                if (dotted)
                    value += value / 2;
            }
        }

        this.noteLtr = ltr;
        this.noteAcc = acc;
        this.noteOctave = octave;
        this.noteValue = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoteSpec noteSpec = (NoteSpec) o;

        if (noteLtr != noteSpec.noteLtr) return false;
        if (noteAcc != noteSpec.noteAcc) return false;
        if (noteOctave != noteSpec.noteOctave) return false;
        return noteValue == noteSpec.noteValue;

    }

    @Override
    public int hashCode() {
        int result = (int) noteLtr;
        result = 31 * result + (int) noteAcc;
        result = 31 * result + noteOctave;
        result = 31 * result + noteValue;
        return result;
    }
}
