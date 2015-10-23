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
    private static final List<String> validNotes = Arrays.asList("r", "c", "cn", "c#", "d", "db", "dn", "d#", "e", "eb", "en",
            "f", "fn", "f#", "g", "gb", "gn", "g#", "a", "an", "a#", "b", "bb", "bn");
    private static final List<String> validSigNotes = Arrays.asList("c#", "db", "d#", "eb", "f#", "gb", "g#", "ab", "a#", "bb", "bn");
    private static final Map<String, String> sigAccs = new HashMap<>();
    private static final Map<String, String> msrAccs = new HashMap<>();

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
                if (fld.length() == 2){
                    fld = fld.substring(0,1)+"4"+fld.substring(1);
                }
                String noteAcc = fld.substring(0, 1) + fld.substring(2);
                if (validSigNotes.contains(fld)){
                    String noteLtr = fld.substring(0, 1);
                    String noteAcc = fld.substring(1, 2);
                    sigAccs.put(noteLtr, noteAcc);
                } else {
                    System.err.println("Invalid note spec '"+fld+"' in signature line ("+nLines+")");
                }
            }
        }
    }

    private static void parseAline(String aline, int nLines){
        boolean atLineStart = true;
        String measures[] = aline.split("\\|");
        for (String measure: measures){
            msrAccs.clear();
            msrAccs.putAll(sigAccs);
            String specs[] = measure.trim().split(" +");
            for (String spec: specs){
                NoteSpec nspec = new NoteSpec(spec, atLineStart);
                String fullNote = nspec.noteLtr;
                if (nspec.noteAcc != null) {
                    fullNote += nspec.noteAcc;
                    if (!"n".equals(nspec.noteAcc) && !msrAccs.containsKey(nspec.noteLtr))
                        msrAccs.put(nspec.noteLtr, nspec.noteAcc);
                }
                if (validNotes.contains(fullNote)) {
                    NoteInfo nplay = new NoteInfo(nspec, qtrMsec);
                    if (msrAccs.containsKey(fullNote)){
                        nplay.noteNumber += msrAccs.get(fullNote).equals("#")? 1: -1;
                    }
                    notes.add(nplay);
                } else {
                    System.err.println("Invalid note spec '"+spec+"' in music line ("+nLines+")");
                }
                atLineStart = false;
            }
        }
    }

}
