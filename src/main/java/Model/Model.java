package Model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.apache.commons.io.FileUtils;

public class Model extends Observable implements IModel {
    private InvertedIndex invertedIndex;
    private LinkedList<DocDictionaryNode> documentDictionary;
    private HashMap<String,CityInfoNode> cityDictionary;
    @Override
    /*public void onStartClick(String pathOfDocs, String stopWordsPath,String destinationPath, boolean stm){
        Manager man = new Manager();
        invertedIndex = new InvertedIndex();
        documentDictionary = new LinkedList<>();
        double[] results = man.Manage(documentDictionary, invertedIndex, pathOfDocs, pathOfStopWords, "", stm);
        setChanged();
        notifyObservers(results);
    }*/

    public void onStartClick(String pathOfDocs, String stopWordsPath,String destinationPath, boolean stm) throws IOException {
        Manager man = new Manager();
        invertedIndex = new InvertedIndex();
        documentDictionary = new LinkedList<>();
        cityDictionary = new HashMap<String,CityInfoNode>();
        ReadFile.initStopWords(stopWordsPath);
        double[] results = man.Manage(cityDictionary,documentDictionary, invertedIndex, pathOfDocs, stopWordsPath, destinationPath, stm);
        setChanged();
        notifyObservers(results);
    }


    @Override
    public void onStartOverClick(String path) {
        File dir = new File(path);
        if(dir.isDirectory()){
            try {
                FileUtils.cleanDirectory(dir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            String[] update = {"RaiseAlert","Path given is not a directory or could not be reached"};
            setChanged();
            notifyObservers(update);
        }
    }

    @Override
    public void showDictionary() {
        ObservableList<ShowDictionaryRecord> observableList = FXCollections.observableArrayList();
        observableList.add(new ShowDictionaryRecord("ALon","5"));
        observableList.add(new ShowDictionaryRecord("Hila","45"));
        observableList.add(new ShowDictionaryRecord("dsa","5453"));
        observableList.add(new ShowDictionaryRecord("dsds","53"));
        setChanged();
        notifyObservers(observableList);
    }
}
