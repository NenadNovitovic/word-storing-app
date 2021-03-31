package sample.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.helper_classes.FileReadWrite;
import sample.helper_classes.Word;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class TestController {

    @FXML private TextField answerTF;
    @FXML private Label wordLbl;
    @FXML private Label pointsLbl;
    Gson gson;
    FileReadWrite readWrite;
    ArrayList<Word> wordList;
    int wordIndex = 0;
    int points=0;
   // @FXML private

    public void initialize(){
        gson=new Gson();
        readWrite=new FileReadWrite();
        String wordsString;
        try {
            wordsString = readWrite.readFromFile("words.txt");
            Type founderListType = new TypeToken<ArrayList<Word>>(){}.getType();
            wordList = gson.fromJson(wordsString, founderListType);
            wordLbl.setText(wordList.get(wordIndex).getMainLanguage());
        }catch (IOException e){
            System.out.println("IO Exception");
        }
    }

    public void checkAnswer(){
        if(wordList.get(wordIndex).getTranslatedLanguage().equalsIgnoreCase(answerTF.getText())){
            pointsLbl.setText(++points + "");
        }else{
            System.out.println("Pogresan odgovor: " +wordLbl.getText() + " je " + wordList.get(wordIndex).getTranslatedLanguage() + " a ne "+ (answerTF.getText()));
        }
        wordIndex++;
        if(wordIndex>=wordList.size())
            wordIndex=0;
        wordLbl.setText(wordList.get(wordIndex).getMainLanguage());
        answerTF.clear();
    }

    public void goToMainView(ActionEvent event){
        try{
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/mainView.fxml"));
            Parent noviRoot = loader.load();

            Scene noviScene = new Scene(noviRoot);
            MainController controller = loader.getController();

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(noviScene);
            window.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
