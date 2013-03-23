/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sentimentanalysis.twitterapi;

import sentimentanalysis.twitterapi.TweetOperator;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import twitter4j.Tweet;
import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sentimentanalysis.common.LocalTweet;

/**
 *
 * @author Matthijs
 */
public class TweetOperatorTest {
    
    
    private TweetOperator instance;
    
    public TweetOperatorTest() {
         instance = new TweetOperator(TweetOperator.DB_AND_ONLINE);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of getTweetsWithAPI method, of class TweetOperator.
     */
    @Test
    public void testGetTweetsWithAPI() throws Exception {
        System.out.println("getTweetsWithAPI");
        String queryString = "apple";
        
        LinkedList<LocalTweet> result = instance.getTweetsWithAPI(queryString);
       
        assert(result != null);        
        for (LocalTweet tweet : result) {
            System.out.println(tweet.getFromUser() + "-" + tweet.getText());
        }
    }

    /**
     * Test of getTweetsFromDB method, of class TweetOperator.
     */
    @Test
    public void testGetTweetsFromDB() {
        System.out.println("getTweetsFromDB");
        String queryString = "apple";        
        LinkedList<LocalTweet> result = instance.getTweetsFromDB(queryString);
        
        assert(result != null);
        for (LocalTweet tweet : result) {
            System.out.println(tweet.getFromUser() + "-" + tweet.getText());
        }
    }

    /**
     * Test of getTweets method, of class TweetOperator.
     */
    @Test
    public void testGetTweets() throws Exception {
        System.out.println("getTweets");
        String queryString = "apple";
        LinkedList<LocalTweet> result = instance.getTweets(queryString);

        assert(result != null);
        for (Tweet tweet : result) {
            System.out.println(tweet.getFromUser() + "-" + tweet.getText());
        }        
    }
    
    @Test
    public void testGetFollowerCount() throws Exception {
        LinkedList<LocalTweet> result = instance.getTweets("apple");

        assert(result != null);
        System.out.println(instance.getFollowersCount(result.get(0)));
        int count = 1;
        for (long id : instance.getFollowerIds(result.get(0))) {
             System.out.println(count + " " + id); 
             ++count;
        }
    }
    
    @Test
    public void testGetRetweetIds() throws Exception {
        LinkedList<LocalTweet> result = instance.getTweets("apple");
        System.out.println("Retweets");
        assert(result != null);
        int count = 1;
        // Below line should output 35 (or more if the tweet will be further retweeted)
        System.out.println(instance.getRetweetedByIds(Long.parseLong("174512929205534720")).size());
        System.out.println(instance.getRetweetCount(result.get(0)));
        System.out.println(instance.getRetweetedByIds(result.get(0)).size());
        for (long id : instance.getRetweetedByIds(result.get(0))) {
             System.out.println(count + " " + id); 
             ++count;
        }
    }
    
    @Test
    public void AddToDB() throws Exception {
        
    }
    
    @Test
    public void testSorting() throws Exception {
        System.out.println("Sorting");
        LinkedList<LocalTweet> list = new LinkedList<LocalTweet>();
        for (int i = 1; i < 100; ++i) {
            LocalTweet lt = new LocalTweet();
            lt.addSentiment("apple", i - 50);
            list.add(lt);
        }
        LinkedList<LocalTweet> low = instance.getTopNegative("apple", 10, list);
        System.out.println(low.size());
        for (int i = 0; i < Math.min(10, low.size()); ++i) {
            System.out.println(low.get(i).getSentimentScore("apple"));
        }
        
        LinkedList<LocalTweet> high = instance.getTopPositive("apple", 10, list);
        for (int i = 0; i < Math.min(10, high.size()); ++i) {
            System.out.println(high.get(i).getSentimentScore("apple"));
        }
        
    }
}
