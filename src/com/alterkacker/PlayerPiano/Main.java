package com.alterkacker.PlayerPiano;

import javax.sound.midi.*;
import java.io.*;
import java.util.List;

public class Main {
    public static void main(String args[]) {
        InputStream is = Main.class.getClassLoader().getResourceAsStream("rolls/Exercise1");
        BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(is)));
        // BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
        List<NoteInfo> roll = RollParser.parseRoll(br);
        RollPlayer rollPlayer = null;
        try {
            RollPlayer.init();
//            rollPlayer = new RollPlayer();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        RollPlayer.playRoll(roll);
    }
}
