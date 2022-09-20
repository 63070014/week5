package com.example.week5;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

//import java.awt.*;

@Route(value = "/index2")
public class MyView2 extends FormLayout {
    private TextField addWord, addTxtSentence, good_s, bad_s;
    private Button addGoodWord, addBadWord, addBtnSentence, showSentence;
    private ComboBox goodWords, badWords;
    public MyView2(){
        TextField addWord = new TextField();
        addWord.setLabel("Add Word");
        TextField addTxtSentence = new TextField();
        addTxtSentence.setLabel("Add Sentence");
        TextField good_s = new TextField();
        good_s.setLabel("Good Sentences");
        TextField bad_s = new TextField();
        bad_s.setLabel("Bad Sentences");
        Button addGoodWord = new Button("Add Good Word");
        Button addBadWord = new Button("Add Bad Word");
        Button addBtnSentence = new Button("Add Sentence");
        Button showSentence = new Button("Show Sentence");
        ComboBox goodWords = new ComboBox<>();
        goodWords.setLabel("Good Words");
        ComboBox badWords = new ComboBox();
        badWords.setLabel("Bad Words");

        VerticalLayout v1 = new VerticalLayout();
        VerticalLayout v2 = new VerticalLayout();
        HorizontalLayout h1 = new HorizontalLayout();

        v1.add(addWord, addGoodWord, addBadWord, goodWords, badWords);
        v2.add(addTxtSentence, addBtnSentence, good_s, bad_s, showSentence);
        h1.add(v1, v2);
        this.add(h1);

        addGoodWord.addClickListener(event -> {
            ArrayList out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addGood/"+ addWord.getValue())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            goodWords.setItems(out);

        });
        addBadWord.addClickListener(event ->{
           ArrayList out = WebClient.create()
                   .post()
                   .uri("http://localhost:8080/addBad/"+ addWord.getValue())
                   .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                   .retrieve()
                   .bodyToMono(ArrayList.class)
                   .block();
           badWords.setItems(out);
        });
        addBtnSentence.addClickListener(event -> {
            ArrayList out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/proof/"+ addTxtSentence.getValue())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();

        });
        showSentence.addClickListener(event -> {
           Sentence out = WebClient.create()
                   .get()
                   .uri("http://localhost:8080/getSentence")
                   .retrieve()
                   .bodyToMono(Sentence.class)
                   .block();
           good_s.setValue(out.goodSentences+"");
           bad_s.setValue(out.badSentences+"");
        });


    }


}
