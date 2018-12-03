package Model;

public class InvertedIndexNode {
    private String m_word;
    private int m_termFreq;
    private int m_numOfAppearances;
    private String m_postingLink;
    private int m_postingLine;


    public InvertedIndexNode(String word, int termFreq, int numOfAppearances, String postingLink, int postingLine) {
        this.m_word = word;
        this.m_termFreq = termFreq;
        this.m_numOfAppearances = numOfAppearances;
        this.m_postingLink = postingLink;
        this.m_postingLine = postingLine;
    }

    public void increaseAppearances(){
        m_termFreq++;
    }

    public int getTermFreq() {
        return m_termFreq;
    }

    public String getWord() {
        return m_word;
    }

    public String getPostingLink() {
        return m_postingLink;
    }

    public int getPostingLine() {
        return m_postingLine;
    }

    public int getNumOfAppearances() {
        return m_numOfAppearances;
    }

    public void setPointer(String postingFile, int postingLine){
        this.m_postingLink = postingFile;
        this.m_postingLine =postingLine;
    }

    public void setNumOfAppearance(int numOfAppearance){
        this.m_numOfAppearances = numOfAppearance;
    }
}
