package sample.helper_classes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReadWrite {
        static Gson gson = new Gson();
        public FileReadWrite(){
            gson=new Gson();
        }
        public static void saveToFile(String path, ArrayList<Word> content)throws IOException{
            FileWriter fw=new FileWriter(path);
            String jsonString = gson.toJson(content);
            fw.write(jsonString);
            fw.close();
        }
        public static ArrayList<Word> readFromFile(String path)throws IOException{
            String content="";
            FileReader fr=new FileReader(path);
            Scanner scan=new Scanner(fr);
            while(scan.hasNext()){
                content+=scan.nextLine();
            }
            fr.close();
            Type founderListType = new TypeToken<ArrayList<Word>>(){}.getType();
            ArrayList<Word> wordsArray = new ArrayList<>();
            wordsArray = gson.fromJson(content,founderListType);
            return wordsArray;
        }
}
