package com.alterkacker.PlayerPiano;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

public class Main {
    public static void main(String args[]) {
        InputStream iss = Main.class.getClassLoader().getResourceAsStream("/rolls/1");
        System.out.println(iss );
    }
}
