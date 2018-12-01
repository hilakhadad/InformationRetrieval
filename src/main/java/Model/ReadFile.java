package Model;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class ReadFile {

    public static Set<String> stopWords;

    public static void initStopWords(String fileName){
        stopWords = new HashSet<>();
        String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                stopWords.add(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static LinkedList<CorpusDocument> readFiles(String pathOfDocs,int mone,int mechane) {
        File dir = new File(pathOfDocs);
        File[] directoryListing = dir.listFiles();
        LinkedList<CorpusDocument> allDocsInCorpus = new LinkedList<>();
        if (directoryListing != null && dir.isDirectory()) {
            int start = mone*directoryListing.length/mechane;
            int end = ((mone+1)*directoryListing.length/mechane)-1;
            ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
            LinkedList<Future<LinkedList<CorpusDocument>>> futureDocsInFile = new LinkedList<>();
            for (int i = start; i <= end; i++) {
                futureDocsInFile.add(pool.submit(new ReadDocuments(directoryListing[i])));
            }

            for (Future<LinkedList<CorpusDocument>> f : futureDocsInFile) {
                try {
                    allDocsInCorpus.addAll(f.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            pool.shutdown();
        } else {
            System.out.println("Not a directory");
        }

        return allDocsInCorpus;
    }

    /*public static void readFiles(String pathOfDocs) {
        File dir = new File(pathOfDocs);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null && dir.isDirectory()) {
            Manager.stopReadAndParse = directoryListing.length;
            ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
            LinkedList<Future<LinkedList<CorpusDocument>>> futureBulk = new LinkedList<>();
            for (File file : directoryListing) {
                try {
                    Manager.emptyCorpusDocSemaphore.acquire();
                    Manager.corpusDocQueue.add(pool.submit(new ReadDocuments(file)));
                    Manager.fullCorpusDocSemaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Manager.continueReadandParse++;
            }
            try {
                pool.awaitTermination(1,TimeUnit.DAYS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pool.shutdown();
        } else {
            System.out.println("Not a directory");
        }
    }*/
}
