package com.alterkacker.PlayerPiano;

import cogitolearning.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by mblum on 10/21/15.
 */
public class RollParser {
    private static int qtrMsec = 0;
    private static List<List<NoteInfo>> noteLines = new ArrayList<>();
    private static final List<String> validNotes = Arrays.asList("r", "c", "cn", "c#", "d", "db", "dn", "d#", "e", "eb", "en",
            "f", "fn", "f#", "g", "gb", "gn", "g#", "a", "an", "ab", "a#", "b", "bb", "bn");
    private static final List<String> validSigNotes = Arrays.asList("c#", "db", "d#", "eb", "f#", "gb", "g#", "ab", "a#", "bb", "bn");
    private static final Map<String, String> sigAccs = new HashMap<>();
    private static final Map<String, String> msrAccs = new HashMap<>();
    private static Tokenizer tokenizer = new Tokenizer();

    static {
        tokenizer.add("[a-g](#|b|n)?\\d?(/\\d{1,2}\\.?)?", 1);
        tokenizer.add("r(/\\d{1,2}\\.?)?", 2);
        tokenizer.add("\\|", 3);
        tokenizer.add("\\[", 4);
        tokenizer.add("]", 5);
    }


    public static List<List<NoteInfo>> parseRoll(BufferedReader br) {
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
                        noteLines.add(parseAline(aline, nLines));
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

        return noteLines;
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

    private static List<NoteInfo> parseAline(String aline, int nLines) {
        NoteInfo nplay;
        List<NoteInfo> lineNotes = new ArrayList<>();
        tokenizer.tokenize(aline);
        msrAccs.clear();

        for (Tokenizer.Token tok : tokenizer.getTokens()){
            if (tok.token > 2){
                nplay = new NoteInfo(-2, 0, "|");
                lineNotes.add(nplay);
                msrAccs.clear();
            } else {
                NoteSpec nspec = new NoteSpec(tok.sequence);
                String noteAndAcc = nspec.noteLtr + ((nspec.noteAcc != null)? nspec.noteAcc: "");
                String noteAndOctave = nspec.noteLtr + String.valueOf(nspec.noteOctave);

                if (validNotes.contains(noteAndAcc)) {
                    nplay = new NoteInfo(nspec, qtrMsec);
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
                    lineNotes.add(nplay);
                } else {
                    System.err.println("Invalid note spec '" + tok.sequence + "' in music line (" + nLines + ")");
                }

            }
        }
        return lineNotes;
    }

}
