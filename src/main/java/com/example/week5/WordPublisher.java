package com.example.week5;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
@RestController
public class WordPublisher {
    protected Word words;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    public WordPublisher(){
        words = new Word();
    }

    @RequestMapping(value = "/addBad/{word}", method = RequestMethod.POST)
    public ArrayList<String> addBadWord(@PathVariable("word") String s){
        words.badWords.add(s);
        return words.badWords;
    }

    @RequestMapping(value = "/delBad/{word}", method = RequestMethod.GET)
    public ArrayList<String> deleteBadWord(@PathVariable("word") String s){
        words.badWords.remove(words.badWords.indexOf(s));
        return words.badWords;
    }
    @RequestMapping(value = "/addGood/{word}", method = RequestMethod.POST)
    public ArrayList<String> addGoodWord(@PathVariable("word") String s){
        words.goodWords.add(s);
        return words.goodWords;
    }
    @RequestMapping(value = "/delGood/{word}", method = RequestMethod.GET)
    public ArrayList<String> deleteGoodWord(@PathVariable("word") String s){
        words.goodWords.remove(words.goodWords.indexOf(s));
        return words.goodWords;
    }
    @RequestMapping(value = "/proof/{sentence}", method = RequestMethod.POST)
    public void proofSentence(@PathVariable("sentence") String s){
        boolean c1 = false;
        boolean c2 = false;
        for (String i : words.goodWords) {
            if(s.contains(i)){
                c1 = true;
            }
        }
        for (String i : words.badWords) {
            if(s.contains(i)){
                c2 = true;
        }
        if (c1 && c2) {
            rabbitTemplate.convertAndSend("Fanout", "", s);
        }else if(c1){
            rabbitTemplate.convertAndSend("Direct", "good", s);
        }else if(c2) {
            rabbitTemplate.convertAndSend("Direct", "bad", s);
        }
    }
}
    @RequestMapping(value = "/getSentence", method = RequestMethod.GET)
    public Sentence getSentence(){
        Object objs = rabbitTemplate.convertSendAndReceive("Direct", "", "");
        return (Sentence)objs ;
    }
}
