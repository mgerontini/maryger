/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sentimentanalysis.endpoint;

import java.util.LinkedList;
import sentimentanalysis.twitterapi.TweetOperator;
import sentimentanalysis.common.LocalTweet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matthijs
 */
public class SoapTweetTest {
    
    private LocalTweet      tweet;
    private TweetOperator   operator;
    
    public SoapTweetTest() throws Exception {
        operator = new TweetOperator(TweetOperator.DB_AND_ONLINE);
        String queryString              = "apple";
        LinkedList<LocalTweet> result   = operator.getTweets(queryString);
        tweet                           = result.get(0);
        tweet.addSentiment(queryString, 0.0);        
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

    @Test
    public void testSomeMethod() {
        SoapTweet instance = new SoapTweet(tweet, "Microsoft");
        
        assert(instance != null);
        assertEquals(instance.rank,         0.0, 0.1);
        assertEquals(instance.sentiment,    0.0, 0.1);
        assertEquals(instance.author,       tweet.getFromUser());
        assertEquals(instance.content,      tweet.getText());
    }
}
