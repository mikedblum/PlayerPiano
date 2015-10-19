package com.alterkacker.PlayerPiano;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            final MidiChannel[] mc = synth.getChannels();
            Instrument[] instr = synth.getDefaultSoundbank().getInstruments();
            synth.loadInstrument(instr[0]);

            JFrame frame = new JFrame("Sound1");
            JPanel pane = new JPanel();
            JButton button1 = new JButton("Click me!");
            frame.getContentPane().add(pane);
            pane.add(button1);
            frame.pack();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);

            button1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mc[0].noteOn(60, 600);
                }
            });
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }


}
