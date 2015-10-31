package com.alterkacker.PlayerPiano;

/**
 * Created by mblum on 10/30/15.
 */
public class Util {

    static String getSpaces(int n){
        StringBuffer x = new StringBuffer(n);
        for (int ix=0; ix<n; ix++){
            x.append(' ');
        }
        return x.toString();
    }

    static String returnAndSpaces(int n){
        return "\r" + getSpaces(n);
    }
}
