package com.alterkacker.PlayerPiano;

import javax.sound.midi.*;
import java.util.List;

/**
 * Created by mblum on 10/21/15.
 */
public class RollPlayer {
    private Synthesizer synth;
    private MidiChannel mc0;
    private Instrument[] instr;

    RollPlayer() throws MidiUnavailableException{
        synth = MidiSystem.getSynthesizer();
        synth.open();
        MidiChannel[] mcs = synth.getChannels();
        final MidiChannel[] mcx = synth.getChannels();
        mc0 = mcs[0];
        instr = synth.getDefaultSoundbank().getInstruments();
//        synth.loadInstrument(instr[0]);
        mcx[0].noteOn(90, 127);
    }

    void playRoll(List<NoteInfo> roll){
        for (NoteInfo note: roll){
            playNote(note);
        }
    }

    private void playNote(NoteInfo note){
//        mc0.noteOn(note.noteNumber, 600);
        mc0.noteOn(60, 127);
    }

}
