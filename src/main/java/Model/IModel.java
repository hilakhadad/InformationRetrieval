package Model;

import java.io.File;
import java.util.List;

public interface IModel {

    void startIndexing(String pathOfDocs, String destinationPath, boolean stm);

    void startOver(String path);

    void showDictionary();

    void loadDictionary(String path, boolean stem);

    void getResults(String postingPath, String stopWordsPath, File queries, boolean stem, boolean semantics, List<String> relevantCities);

    void getResults(String postingPath, String stopWordsPath, String query, boolean stem, boolean semantics, List<String> relevantCities);

    void filterCities(List<String> toFilter);

    String show5words(String docName);

    boolean writeRes(String dest);
}
