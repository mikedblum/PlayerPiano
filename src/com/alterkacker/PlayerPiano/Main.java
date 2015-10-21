package com.alterkacker.PlayerPiano;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Main {
    public static void main(String args[]) {
        String x = "A B  C   D|E| F |G  |  H";
        String xx = " A BB  CCCC   D  ";
        String[] pcs = xx.trim().split(" +");
        System.out.println(pcs);
        InputStream is = Main.class.getClassLoader().getResourceAsStream("rolls/1");
        BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(is)));
        // BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
        String aline;
        int n = 1;
        try{
            while ((aline = br.readLine()) != null) {
                System.out.println("" + n++ + aline);
            }
        } catch (IOException e) {}
        finally {
            try {
                br.close();
            } catch (IOException e) {}
        }
    }
}
