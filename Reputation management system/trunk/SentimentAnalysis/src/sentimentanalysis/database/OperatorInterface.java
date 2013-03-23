/*
 * Interface which stores all the functionalities that
 * Operator class is going to implement
 */
package sentimentanalysis.database;

import java.util.HashMap;
import java.util.LinkedList;
import sentimentanalysis.common.LocalTweet;
import twitter4j.Tweet;
/**
 *
 * @author maryger
 */
public interface OperatorInterface
{
    
    public void storeTweet(Tweet tw);
    public void storeTweetBatch(LinkedList<LocalTweet> list, String entity);
    public long getIdOfLatestTweetForThisEntity(String entity);
    public LinkedList<String> getTweets(String key);
    public HashMap<String,Double> getSmileys();
     public HashMap<String, Double> getModifiers();
    public HashMap<String,Double> getNegations();
    public LinkedList<LocalTweet> getLocalTweets(String key);
    public void storeSentiment(long tweet_id, long entity_id, double sentiment ,double score );
    public double getSentiment(long key);
    public void fillLexicon();
    public void storeEntity(String value,double score);
    public void storeEntityRelatedWord(String entity,String related_word,double score);
    public HashMap<String,Double> getRelatedWord(String key_entity);
    public double getEntityScore(String key);
    
}
