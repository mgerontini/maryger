package sentimentanalysis.common;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import twitter4j.*;

/**
 *
 * @author dama
 */
public class LocalTweet implements Tweet {
    private long id;
    private String content;
    private String author;
    private long author_id;
    private Date date;
    private double pagerank;
    private HashMap<String, Sentiment> sentiments;
    private boolean fromDB;
    private HashMap<String, Double> score;
    
    public LocalTweet() {
        sentiments = new HashMap<String, Sentiment>();
        score = new HashMap<String, Double>();
        fromDB = true;
    }
    
    /**
     * @param queryString - this corresponds to a database "entity"
     * @param s 
     */
    public void addSentiment(String queryString, Sentiment s) {
        sentiments.put(queryString, s);
    }

    
    public void addSentiment(String queryString, double score) {
        Sentiment sentiment = new Sentiment();
        sentiment.setEntity(queryString);
        sentiment.setScore(score);
        this.addSentiment(queryString, sentiment);
    }
    
    
    public void copyFrom(Tweet tweet) {
        setId(tweet.getId());
        setContent(tweet.getText());
        setAuthor(tweet.getFromUser());
        setDate(tweet.getCreatedAt());
        setAuthorId(tweet.getFromUserId());
        fromDB = false;
    }
    
    public String getText() {
        return content;
    }

    public long getToUserId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getToUser() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getFromUser() {
        return author;
    }

    public long getId() {
        return id;
    }

    public long getFromUserId() {
        return author_id;
    }

    public String getIsoLanguageCode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getSource() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getProfileImageUrl() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    public String getCreatedString (String format) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);    
        return sdf.format(date);        
    }
    
    public String getCreatedString () {
        return getCreatedString("yyyy-MM-dd HH:mm:ss");
    }
    
    public Date getCreatedAt() {
        return date;
    }

    public GeoLocation getGeoLocation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getLocation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Place getPlace() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Annotations getAnnotations() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int compareTo(Tweet o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public UserMentionEntity[] getUserMentionEntities() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public URLEntity[] getURLEntities() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public HashtagEntity[] getHashtagEntities() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public MediaEntity[] getMediaEntities() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * @return The static quality/authoritativeness metric associated with the
     *      tweet. Note that it's irrelevant and not computed if the tweet is
     *      neutral, and should not be taken into account in that case.
     */
    public double getPageRank() {
        return pagerank;
    }
    
    /**
     * 
     * @param queryString - corresponds to a database "entity"
     * @return 
     */
    public Sentiment getSentiment(String queryString) {
        return sentiments.get(queryString);
    }
    
    public double getSentimentScore(String queryString) {
        if (sentiments.containsKey(queryString))
            return sentiments.get(queryString).getScore();
        return 0.0;
    }
    
    public boolean isPositive(String queryString) {
        return getSentimentScore(queryString) > 0;
    }
    
    public boolean isNegative(String queryString) {
        return getSentimentScore(queryString) < 0;
    }
    
    public boolean isNeutral(String queryString) {
        return !isPositive(queryString) && !isNegative(queryString);
    }
    
    public boolean isFromDB() {
        return fromDB;
    }
    
 
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public void setAuthorId(long author_id) {
        this.author_id = author_id;
    }
    
    public void setContent(String content) {
        try {
            this.content = new String(content.getBytes("utf8"));
        } catch (Exception e) {}
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public void setPageRank(double pagerank) {
        this.pagerank = pagerank;
    }
    
    public void setSentiments(HashMap<String, Sentiment> sentiments) {
        this.sentiments = sentiments;
    }
    
    /**
     * Compute overall score, combining pagerank and sentiment score
     */
    public void computeScore() {
        double combination = 0;
        for (String s:sentiments.keySet()) {
            if (pagerank == 0)
                combination = sentiments.get(s).getScore();
            else
                combination = sentiments.get(s).getScore() * pagerank;
            score.put(s,combination);
        }
    }
}
