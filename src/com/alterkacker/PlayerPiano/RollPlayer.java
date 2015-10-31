package com.alterkacker.PlayerPiano;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

    static void playRoll(List<List<NoteInfo>> roll){
        List<Integer> notePosition = new ArrayList<>();

        for (List<NoteInfo> oneLine: roll) {
            notePosition.clear();
            String outLine = "";
            int opos = 0;

            for (NoteInfo note: oneLine){
                outLine += note.noteSpec + " ";
                notePosition.add(opos);
                opos += note.noteSpec.length() + 1;
            }
            System.out.println(outLine);

            Iterator<Integer> posIter = notePosition.listIterator();
            for (NoteInfo note : oneLine) {
                int nSpaces = posIter.next();
                System.out.print(Util.returnAndSpaces(nSpaces) + "^");
                System.out.flush();
                playNote(note);
            }
            System.out.println(Util.returnAndSpaces(opos));
        }

    }

    private static void playNote(NoteInfo note){
//        System.out.println(">"+note.noteNumber+" for "+note.noteMsec+"msec at "+(new Date()));
//        System.out.print(note.noteSpec + "(" + note.noteNumber + ") ");
//        System.out.print(note.noteSpec + " ");
//        if (note.noteNewLine)
//            System.out.println();
//        System.out.flush();
        if (note.noteNumber > -2) {
            if (note.noteNumber > -1) {
                mc0.noteOn(note.noteNumber, 127);
            }
            try {
                Thread.sleep((long) note.noteMsec);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mc0.allNotesOff();
        }
    }

}
