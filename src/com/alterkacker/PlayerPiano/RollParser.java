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
            "f", "fn", "f#", "g", "gb", "gn", "g#", "a", "an", "ab", "a#", "b", "bb", "bn");
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
                    if (tempoLine) {
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
            } catch (IOException e) {
            }
        }

        return notes;
    }

    private static void parseSigLine(String aline, int nLines) {
        String flds[] = aline.split(" ");
        qtrMsec = Integer.valueOf(flds[0]);
        if (flds.length > 1) {
            for (int ix = 1; ix < flds.length; ix++) {
                String fld = flds[ix];
                String noteLtr = fld.substring(0, 1);
                String noteAcc = fld.substring(1, 2);
                String tNote = noteLtr + noteAcc;
                if (validSigNotes.contains(tNote)) {
                    sigAccs.put(noteLtr, noteAcc);
                } else {
                    System.err.println("Invalid note spec '" + fld + "' in signature line (" + nLines + ")");
                }
            }
        }
    }

    private static void parseAline(String aline, int nLines) {
        String measures[] = aline.split("\\|");
        int nMeasures = measures.length;
        for (int mx = 0; mx < nMeasures; mx++) {
            String measure = measures[mx];
            msrAccs.clear();
            String specs[] = measure.trim().split(" +");
            int nSpecs = specs.length;
            for (int sx = 0; sx < nSpecs; sx++) {
                String spec = specs[sx];
                NoteSpec nspec = new NoteSpec(spec, (mx == nMeasures - 1 && sx == nSpecs - 1));
                String noteAndAcc = nspec.noteLtr + ((nspec.noteAcc != null)? nspec.noteAcc: "");
                String noteAndOctave = nspec.noteLtr + String.valueOf(nspec.noteOctave);

                if (validNotes.contains(noteAndAcc)) {
                    NoteInfo nplay = new NoteInfo(nspec, qtrMsec);
                    // note with no accidental
                    if (nspec.noteAcc == null) {
                        if (sigAccs.containsKey(nspec.noteLtr)) {
                            nplay.noteNumber += sigAccs.get(nspec.noteLtr).equals("#") ? 1 : -1;
                        } else if (msrAccs.containsKey(noteAndOctave)) {
                            nplay.noteNumber += msrAccs.get(noteAndOctave).equals("#") ? 1 : -1;
                        }
                    // note with accidental
                    } else if (!"n".equals(nspec.noteAcc) && !msrAccs.containsKey(noteAndOctave)){
                        msrAccs.put(noteAndOctave, nspec.noteAcc);
                    }
                    notes.add(nplay);
                } else {
                    System.err.println("Invalid note spec '" + spec + "' in music line (" + nLines + ")");
                }
            }
        }
    }

}
