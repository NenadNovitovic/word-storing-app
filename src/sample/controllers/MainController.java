package sample.controllers;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.helper_classes.FileReadWrite;
import sample.helper_classes.Word;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainController {

    @FXML private ListView wordsListView;
    @FXML private TextField searchWordTF;
    @FXML private TextField selectedWordTF;
    @FXML private TextArea examplesTA;
    @FXML private TextArea moreInfoTA;
    @FXML private TextField pronounciationTF;
    @FXML private Label searchLangLbl;
    @FXML private Button newWordBtn;
    @FXML private Button editWordBtn;
    private boolean isSwedish;
    Gson gson;
    FileReadWrite readWrite;
    ArrayList<Word> wordList;

    public void initialize(){
        isSwedish=true;
        editWordBtn.setDisable(true);
        try {
            wordList = FileReadWrite.readFromFile("words.txt");
            if(wordList!=null)
            wordsListView.getItems().addAll(wordList);
        }catch (IOException e){
            System.out.println("IO Exception");
        }
    }
    public void switchSearchLanguage(){
        isSwedish=!isSwedish;
        if(isSwedish){
            searchLangLbl.setText("Swedish");
        }else{
            searchLangLbl.setText("English");
        }
    }
    public void newWordClicked(){
        int index = wordsListView.getSelectionModel().getSelectedIndex();
        if(index<0){
            System.out.println(index);
            index=0;
        }
        Word selectedWord = wordList.get(index);
        selectedWordTF.setText(selectedWord.getMainLanguage());
        examplesTA.clear();
        for(int i=0;i<selectedWord.getWordExamples().size();i++){
            examplesTA.setText(examplesTA.getText()+selectedWord.getWordExamples().get(i)+"\n");
        }
        moreInfoTA.setText(selectedWord.getMoreInfo());
        pronounciationTF.setText(selectedWord.getPronounciation());
        editWordBtn.setDisable(false);
    }

    public void goToAddNewWordView(ActionEvent event){
        try{
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/newWordView.fxml"));
            Parent noviRoot = loader.load();

            Scene noviScene = new Scene(noviRoot);
            NewWordController controller = loader.getController();
            if(wordsListView.getSelectionModel().getSelectedItem()!=null && event.getSource()!=newWordBtn)
                controller.setWord(wordsListView.getSelectionModel().getSelectedIndex());

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(noviScene);
            window.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void goToTestView(ActionEvent event){
        try{
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/testView.fxml"));
            Parent noviRoot = loader.load();

            Scene noviScene = new Scene(noviRoot);
            TestController controller = loader.getController();

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(noviScene);
            window.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void searchWord(){
        String searchString=searchWordTF.getText().toUpperCase();
        ArrayList<Word> newWordList = new ArrayList<>();
        if(isSwedish){
            for(int i=0;i<wordList.size();i++){
                if(wordList.get(i).getMainLanguage().toUpperCase().contains(searchString))
                    newWordList.add(wordList.get(i));
            }
        }else{
            for(int i=0;i<wordList.size();i++){
                if(wordList.get(i).getTranslatedLanguage().toUpperCase().contains(searchString))
                    newWordList.add(wordList.get(i));
            }
        }
        wordsListView.getItems().clear();
        wordsListView.getItems().addAll(newWordList);
    }
    public void sortByEnglish(){
        for(int i=0;i<wordList.size()-1;i++){
            for(int j=i;j<wordList.size();j++){
                if((wordList.get(i).getTranslatedLanguage().compareToIgnoreCase(wordList.get(j).getTranslatedLanguage())>=0)){
                    Word temp=wordList.get(i);
                    wordList.set(i,wordList.get(j));
                    wordList.set(j,temp);
                }
            }
        }
        wordsListView.getItems().clear();
        wordsListView.getItems().addAll(wordList);
    }
    public void sortBySwedish(){
        for(int i=0;i<wordList.size()-1;i++){
            for(int j=i;j<wordList.size();j++){
                if((wordList.get(i).getMainLanguage().compareToIgnoreCase(wordList.get(j).getMainLanguage())>=0)){
                    Word temp=wordList.get(i);
                    wordList.set(i,wordList.get(j));
                    wordList.set(j,temp);
                }
            }
        }
        wordsListView.getItems().clear();
        wordsListView.getItems().addAll(wordList);
    }
}
