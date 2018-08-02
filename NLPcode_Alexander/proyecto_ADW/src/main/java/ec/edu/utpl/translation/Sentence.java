package ec.edu.utpl.translation;

public class Sentence {
    
    private int id;
    private String word;

    public Sentence() {
    }

    public Sentence(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
    
}
