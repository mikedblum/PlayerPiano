package com.alterkacker.PlayerPiano;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mblum on 10/21/15.
 */
public class RollParser {
    private static int qtrMsec = 0;
    private static List<NoteInfo> notes = new ArrayList<>();

    public static List<NoteInfo> parseRoll(BufferedReader br) {
        String aline;
        boolean tempoLine = true;

        try {
            while ((aline = br.readLine()) != null) {
                aline = aline.trim();
                if (!(aline.startsWith("#") || aline.length() == 0)) {
                    //System.out.println("" + n++ + aline);
                    if (tempoLine){
                        qtrMsec = Integer.valueOf(aline);
                        tempoLine = false;
                    } else {
                        parseAline(aline);
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

    private static void parseAline(String aline){
        String measures[] = aline.split("\\|");
        for (String measure: measures){
            String specs[] = measure.trim().split(" +");
            for (String spec: specs){
                NoteSpec nspec = new NoteSpec(spec);
                NoteInfo nplay = new NoteInfo(nspec, qtrMsec);
                notes.add(nplay);
            }
        }
    }
}
