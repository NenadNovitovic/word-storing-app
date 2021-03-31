package sample.helper_classes;

import java.util.ArrayList;

public class Word {
    String mainLanguage;
    String translatedLanguage;
    String pronounciation;
    ArrayList<String> wordExamples;
    public String moreInfo;

    public Word(String mainLanguage, String translatedLanguage, String pronounciation, ArrayList<String> wordExamples, String moreInfo) {
        this.mainLanguage = mainLanguage;
        this.translatedLanguage = translatedLanguage;
        this.pronounciation = pronounciation;
        this.wordExamples = wordExamples;
        this.moreInfo = moreInfo;
    }

    public String getMainLanguage() {
        return mainLanguage;
    }

    public void setMainLanguage(String mainLanguage) {
        this.mainLanguage = mainLanguage;
    }

    public String getTranslatedLanguage() {
        return translatedLanguage;
    }

    public void setTranslatedLanguage(String translatedLanguage) {
        this.translatedLanguage = translatedLanguage;
    }

    public String getPronounciation() {
        return pronounciation;
    }
    public ArrayList<String> getWordExamples() {
        return wordExamples;
    }

    public void setPronounciation(String pronounciation) {
        this.pronounciation = pronounciation;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    @Override
    public String toString() {
        return
                mainLanguage +
                " - " + translatedLanguage
                ;
    }
}
