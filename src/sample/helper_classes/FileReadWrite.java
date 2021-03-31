package sample.helper_classes;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileReadWrite {
        public static void saveToFile(String path, String content)throws IOException{
            FileWriter fw=new FileWriter(path);
            fw.write(content);
            fw.close();
        }
        public static String readFromFile(String path)throws IOException{
            String content="";
            FileReader fr=new FileReader(path);
            Scanner scan=new Scanner(fr);
            while(scan.hasNext()){
                content+=scan.nextLine();
            }
            fr.close();
            return content;
        }
}
