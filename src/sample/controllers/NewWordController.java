package sample.controllers;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.helper_classes.FileReadWrite;
import sample.helper_classes.Word;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class NewWordController {
    int wordToEditIndex;
    boolean editWord;
    ArrayList<Word> wordList;
    Gson gson;
    FileReadWrite readWrite;
    TextField lastFocus;
    ArrayList<Button> buttonsArray;
    @FXML TextField mainLanguageTF;
    @FXML TextField secondLanguageTF;
    @FXML TextField example1TF;
    @FXML TextField example2TF;
    @FXML TextField example3TF;
    @FXML TextField pronounciationTF;
    @FXML TextArea moreInfoTA;
    @FXML AnchorPane mainPane;
    private boolean isLowerCase;
    @FXML Button btn1;
    @FXML Button btn2;
    @FXML Button btn3;
    @FXML Button addWordBtn;

    public void initialize() throws IOException {
        editWord=false;
        lastFocus = mainLanguageTF;
        initTFActions();
        buttonsArray = new ArrayList<>();
        buttonsArray.add(btn1);buttonsArray.add(btn2);buttonsArray.add(btn3);
        isLowerCase=false;
        wordList = FileReadWrite.readFromFile("words.txt");
        if(wordList==null)
            wordList = new ArrayList<>();
    }

    public void setWord(int wordIndex){
        addWordBtn.setText("Edit word");
        this.wordToEditIndex=wordIndex;
        editWord=true;
        setTextFields();
    }
    public void setTextFields(){
        Word wordToEdit = wordList.get(wordToEditIndex);
        mainLanguageTF.setText(wordToEdit.getMainLanguage());
        secondLanguageTF.setText(wordToEdit.getTranslatedLanguage());
        example1TF.setText(wordToEdit.getWordExamples().get(0));
        example2TF.setText(wordToEdit.getWordExamples().get(1));
        example3TF.setText(wordToEdit.getWordExamples().get(2));
        pronounciationTF.setText(wordToEdit.getPronounciation());
        moreInfoTA.setText(wordToEdit.getMoreInfo());
    }

    public void initTFActions(){
        for (Node node : mainPane.getChildren()) {
            if (node instanceof TextField) {
                ((TextField)node).setOnMousePressed(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                        lastFocus = (TextField) me.getSource();
                    }
                });
            }
        }
    }
    public void addWordFunction(ActionEvent event){
        try{
            ArrayList<String> exampleStrings = new ArrayList<>();
            fillExampleStrings(exampleStrings);
            Word newWord= new Word(mainLanguageTF.getText(),secondLanguageTF.getText(),pronounciationTF.getText(),exampleStrings, moreInfoTA.getText());
            System.out.println(newWord);
            if(editWord)
                wordList.set(wordToEditIndex,newWord);
            else
                wordList.add(newWord);
            FileReadWrite.saveToFile("words.txt",wordList);
            System.out.println("Word added");
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
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }
    private void fillExampleStrings(ArrayList exampleStrings){
        exampleStrings.add(example1TF.getText());
        exampleStrings.add(example2TF.getText());
        exampleStrings.add(example3TF.getText());
        String counter="";
        for (int i=0;i<exampleStrings.size();i++) {
            counter+=exampleStrings.get(i);
        }
        if(counter.length()==0){
            exampleStrings.set(0,"No examples");
        }
    }
    public void addLetter(ActionEvent event){
        Button btn = (Button)event.getSource();
        lastFocus.setText(lastFocus.getText() + btn.getText());
    }
    public void changeFocus(ActionEvent event){
        lastFocus = (TextField)event.getSource();
    }
    public void changeCaps(){
        if(!isLowerCase){
            for(int i=0;i<buttonsArray.size();i++){
                buttonsArray.get(i).setText(buttonsArray.get(i).getText().toLowerCase());
            }
        }else{
            for(int i=0;i<buttonsArray.size();i++){
                buttonsArray.get(i).setText(buttonsArray.get(i).getText().toUpperCase());
            }
        }
        isLowerCase=!isLowerCase;
    }
}
