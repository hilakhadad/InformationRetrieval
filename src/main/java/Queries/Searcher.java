package Queries;

import IO.ReadFile;
import Model.*;

import java.util.HashMap;
import java.util.HashSet;

public class Searcher {
    private String postingPath;
    private boolean stem;

    public Searcher(String postingPath, boolean stem) {
        this.postingPath = postingPath;
        this.stem = stem;
    }

    public void getQueryResults(Query q) {
        double k = 2, b = 0.75;
        String query = q.getTitle();
        HashMap<String, Integer> wordsCount = putWordsInMap(query);
        HashMap<String, String> wordsPosting = getWordsPosting(query);
        HashSet<String> docCloseList = new HashSet<>();
        Ranker ranker = new Ranker(wordsCount, wordsPosting);
        for (String word : wordsCount.keySet()) {
            if (!wordsPosting.get(word).equals("")) {
                String postingLine = wordsPosting.get(word);
                String[] split = postingLine.split("\\|");
                split[0] = split[0].substring(word.length() + 1);
                for (String aSplit : split) {
                    String[] splitLine = aSplit.split(",");
                    String docName = splitLine[0];
                    if (!docCloseList.contains(docName))
                        ranker.BM25(docName, k, b);
                    docCloseList.add(docName);
                }
            }
        }
    }

    private HashMap<String, String> getWordsPosting(String query) {
        HashMap<String,String> words = new HashMap<>();
        String[] splitBySpace = query.split(" ");
        for (String word: splitBySpace) {
            String posting = "";
            String lineNumber = getPostingLineNumber(word);
            if(!lineNumber.equals(""))
                posting = ReadFile.readPostingLineAtIndex(postingPath,Character.toLowerCase(word.charAt(0)),Integer.parseInt(lineNumber),stem);
            words.put(word,posting);
        }
        return words;
    }

    private HashMap<String, Integer> putWordsInMap(String query) {
        HashMap<String,Integer> words = new HashMap<>();
        String[] splitBySpace = query.split(" ");
        for (String word: splitBySpace) {
            if(!words.containsKey(word))
                words.put(word,1);
            else
                words.replace(word,words.get(word)+1);
        }
        return words;
    }

    private String getPostingLineNumber(String word){
        String lineNumber = Model.invertedIndex.getPostingLink(word.toLowerCase());
        if(lineNumber.equals(""))
            lineNumber = Model.invertedIndex.getPostingLink(word.toUpperCase());
        return lineNumber;
    }
}
