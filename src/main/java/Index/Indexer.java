package Index;

import Parse.MiniDictionary;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Indexer implements Callable<HashMap<String, Pair<Integer,StringBuilder>>> {

    private ConcurrentLinkedDeque<MiniDictionary> m_miniDicList;

    public Indexer(ConcurrentLinkedDeque<MiniDictionary> minidic){
        m_miniDicList = minidic;
    }

    /**
     * this class creates a temporary posting in a HashMap containing all data of MiniDictionay's sent
     * @return an hash map representing all data of mini dics
     */
    @Override
    public HashMap<String, Pair<Integer,StringBuilder>> call() {
        // adding to inverted index the term and the other data
        // AND adding to the map (temporary posting)
        HashMap<String, Pair<Integer,StringBuilder>> toReturn = new HashMap<>();
        if(m_miniDicList !=null){
            for (MiniDictionary miniDic: m_miniDicList) {
                for (String word : miniDic.listOfWords()) {
                    if (toReturn.containsKey(word)) { //if the word already exists
                        Pair<Integer,StringBuilder> all = toReturn.remove(word);
                        int newShows = all.getKey()+miniDic.getFrequency(word);
                        StringBuilder newSb = all.getValue().append(miniDic.listOfData(word)).append("|");
                        Pair<Integer,StringBuilder> newAll = new Pair<>(newShows,newSb);
                        toReturn.put(word,newAll);
                    }
                    else{ //if the word doesn't exist
                        int shows = miniDic.getFrequency(word);
                        StringBuilder sb = new StringBuilder(miniDic.listOfData(word)+"|");
                        Pair<Integer,StringBuilder> all = new Pair<>(shows,sb);
                        toReturn.put(word,all);
                    }
                }
            }
        }
        return toReturn;
    }
}
