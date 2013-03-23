package sentimentanalysis.common;

/**
 *
 * @author dama
 */
public class Sentiment {
    private long id;
    private String entity;
    private double score;
    
    public long getId() {
        return id;
    }
    
    public String getEntity() {
        return entity;
    }
    
    public double getScore() {
        return score;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public void setEntity(String entity) {
        this.entity = entity;
    }
    
    public void setScore(double score) {
        this.score = score;
    }
    
    
}
