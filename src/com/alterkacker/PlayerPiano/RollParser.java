package com.alterkacker.PlayerPiano;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by mblum on 10/21/15.
 */
public class RollParser {
    private static int qtrMsec = 0;
    private static List<NoteInfo> notes = new ArrayList<>();
    private static final List<String> validNotes = Arrays.asList("cn", "c#", "db", "dn", "d#", "eb", "en", "fn", "f#", "gb", "gn", "g#",
            "an", "a#", "bb", "bn");
    private static final List<String> validSigNotes = Arrays.asList("c#", "db", "d#", "eb", "f#", "gb", "g#", "a#", "bb", "bn");
    private static final Map<String, String> sigNotes = new HashMap<>();

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
                if (validSigNotes.contains(fld)){
                    String noteLtr = fld.substring(0, 1);
                    String noteAcc = fld.substring(1, 1);
                    sigNotes.put(noteLtr, noteAcc);
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
                NoteSpec nspec = new NoteSpec(spec);
                String fullNote = String.valueOf(nspec.noteLtr)+String.valueOf(nspec.noteAcc);
                if (validNotes.contains(fullNote)) {
                    NoteInfo nplay = new NoteInfo(nspec, qtrMsec);
                    if (sigNotes.containsKey(fullNote)){
                        nplay.noteNumber += sigNotes.get(fullNote).equals("#")? 1: -1;
                    }
                    notes.add(nplay);
                } else {
                    System.err.println("Invalid note spec '"+spec+"' in music line ("+nLines+")");
                }
            }
        }
    }
}
