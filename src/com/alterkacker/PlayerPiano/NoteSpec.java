package com.alterkacker.PlayerPiano;

/**
 * Created by mblum on 10/19/15.
 */
class NoteSpec {
    String noteLtr;
    String noteAcc;
    int noteOctave;
    double noteValue;
    // Following fields not included in equals & hashCode methods
    String noteSpec;

    NoteSpec(String ltr, String acc, int octave, double value) {
        this.noteLtr = ltr;
        this.noteAcc = acc;
        this.noteOctave = octave;
    }

    NoteSpec(String s){
        int xpos = 0;

        String sx = s.toLowerCase();
        int sxl = sx.length();

        String ltr = sx.substring(xpos, 1);

        // Look for accidental character
        xpos++;
        String acc = null;
        if (xpos < sxl){
            String tacc = sx.substring(xpos, xpos+1);
            if ("#bn".contains(tacc)){
                acc = tacc;
                xpos++;
            }
        }

        // Next character can be octave number, or "/" starting value spec
        int octave = 4;
        if (xpos < sxl) {
            char x = sx.charAt(xpos);
            if (x >= '0' && x <= '9'){
                octave = x - '0';
                xpos++;
            }
        }

        double value = 4;
        if (xpos < sxl) {
            char x = sx.charAt(xpos);
            if (x == '/'){
                xpos++;
                String valueSpec = sx.substring(xpos);
                boolean dotted = valueSpec.endsWith(".");
                if (dotted){
                    valueSpec = valueSpec.substring(0,valueSpec.length()-1);
                }
                value = Double.valueOf(valueSpec);
                if (dotted) {
                    value *= 2.0 / 3.0;
                }
            }
        }

        this.noteSpec = sx;
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

        if (noteOctave != noteSpec.noteOctave) return false;
        if (Double.compare(noteSpec.noteValue, noteValue) != 0) return false;
        if (!noteLtr.equals(noteSpec.noteLtr)) return false;
        return !(noteAcc != null ? !noteAcc.equals(noteSpec.noteAcc) : noteSpec.noteAcc != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = noteLtr.hashCode();
        result = 31 * result + (noteAcc != null ? noteAcc.hashCode() : 0);
        result = 31 * result + noteOctave;
        temp = Double.doubleToLongBits(noteValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
