package com.alterkacker.PlayerPiano;

import javax.sound.midi.*;
import java.util.Date;
import java.util.List;

/**
 * Created by mblum on 10/21/15.
 */
public class RollPlayer {
    private static MidiChannel mc0;

    static void init() throws MidiUnavailableException{
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();
        MidiChannel[] mcs = synth.getChannels();
        mc0 = mcs[0];
        Instrument[] instr = synth.getDefaultSoundbank().getInstruments();
        synth.loadInstrument(instr[0]);
    }

    static void playRoll(List<NoteInfo> roll){
        for (NoteInfo note: roll){
            playNote(note);
        }
    }

    private static void playNote(NoteInfo note){
        System.out.println(">"+note.noteNumber+" for "+note.noteMsec+"msec at "+(new Date()));
        if (note.noteNumber > -1) {
            mc0.noteOn(note.noteNumber, 127);
        }
        try {
            Thread.sleep(note.noteMsec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (note.noteNumber > -1){
            mc0.allNotesOff();
        }
    }

}
