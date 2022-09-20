package com.example.week5;

import java.util.ArrayList;
import java.util.Arrays;

public class Word {
    public ArrayList<String> badWords;
    public ArrayList<String> goodWords;
    public Word(){
        badWords = new ArrayList<>();
        goodWords = new ArrayList<>();
        goodWords.addAll(Arrays.asList("happy","enjoy","life"));
        badWords.addAll(Arrays.asList("fuck","olo"));
    }

}
