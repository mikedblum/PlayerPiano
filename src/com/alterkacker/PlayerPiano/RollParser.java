package com.alterkacker.PlayerPiano;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mblum on 10/21/15.
 */
public class RollParser {
    private static int qtrMsec = 0;
    private static List<NoteInfo> notes = new ArrayList<>();
    private static final List<String> validNotes = Arrays.asList("cn", "c#", "db", "dn", "d#", "eb", "en", "fn", "f#", "gb", "gn", "g#",
            "an", "a#", "bb", "bn");
    private static final List<String> sigNotes = new ArrayList<>();
//        List<String> keys2 = Arrays.asList("cn", "db", "dn", "eb", "en", "fn", "gb", "gn", "ab", "an", "bb", "bn");

    public static List<NoteInfo> parseRoll(BufferedReader br) {
        String aline;
        boolean tempoLine = true;
        int nLines = 0;

        try {
            while ((aline = br.readLine()) != null) {
                nLines++;
                aline = aline.trim().replaceAll(" +", " ").toLowerCase();
                if (!(aline.startsWith("#") || aline.length() == 0)) {
                    if (tempoLine){
                        parseSigLine(aline, nLines);
                        tempoLine = false;
                    } else {
                        parseAline(aline, nLines);
                    }
                }
            }
        } catch (IOException e) {
        } finally {
            try {
                br.close();
            } catch (IOException e) {}
        }

        return notes;
    }

    private static void parseSigLine(String aline, int nLines){
        String flds[] = aline.split(" ");
        qtrMsec = Integer.valueOf(flds[0]);
        if (flds.length > 1){
            for (int ix=1; ix<flds.length; ix++){
                String fld = flds[ix];
                if (validNotes.contains(fld)){
                    sigNotes.add(fld);
                } else {
                    System.err.println("Invalid note spec '"+fld+"' in signature line ("+nLines+")");
                }
            }
        }
    }

    private static void parseAline(String aline, int nLines){
        String measures[] = aline.split("\\|");
        for (String measure: measures){
            String specs[] = measure.trim().split(" +");
            for (String spec: specs){
                if (validNotes.contains(spec)) {
                    NoteSpec nspec = new NoteSpec(spec);
                    NoteInfo nplay = new NoteInfo(nspec, qtrMsec);
                    notes.add(nplay);
                } else {
                    System.err.println("Invalid note spec '"+spec+"' in music line ("+nLines+")");
                }
            }
        }
    }
}
