package sample.controllers;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.FileReadWrite;
import sample.Word;
import sample.controllers.MainController;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class NewWordController {
    Gson gson;
    FileReadWrite readWrite;
    @FXML TextField mainLanguageTF;
    @FXML TextField secondLanguageTF;
    @FXML TextField example1TF;
    @FXML TextField example2TF;
    @FXML TextField example3TF;
    @FXML TextField pronounciationTF;
    @FXML TextArea moreInfoTA;

    public void initialize(){
        gson=new Gson();
        readWrite=new FileReadWrite();
    }
    public void addWordFunction(ActionEvent event){
        try{
            ArrayList<String> exampleStrings = new ArrayList<>();
            fillExampleStrings(exampleStrings);
            Word newWord= new Word(mainLanguageTF.getText(),secondLanguageTF.getText(),pronounciationTF.getText(),exampleStrings, moreInfoTA.getText());
            String wordsString = readWrite.readFromFile("words.txt");
            Type founderListType = new TypeToken<ArrayList<Word>>(){}.getType();
            ArrayList<Word> wordList = gson.fromJson(wordsString, founderListType);
            if(wordList==null)
                wordList = new ArrayList<>();
            System.out.println(wordList);
            wordList.add(newWord);
            String gsonString=gson.toJson(wordList);
            readWrite.saveToFile("words.txt",gsonString);
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
}
