package com.example.week5;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
@Service
public class SentenceConsumer {
    protected Sentence sentence;
    public SentenceConsumer(){
        sentence = new Sentence();
    }
    @RabbitListener(queues = "BadWordQueue")
    public void addBadSentence(String s){
        sentence.badSentences.add(s);
        System.out.println("In addBadSentence Method : " + sentence.badSentences);
    }
    @RabbitListener(queues = "GoodWordQueue")
    public void addGoodSentence(String s){
        sentence.goodSentences.add(s);
        System.out.println("In addGoodSentence Method : " + sentence.goodSentences);
    }
    @RabbitListener(queues = "GetQueue")
    public Sentence getSentencs(){
        return sentence;
    }

}
