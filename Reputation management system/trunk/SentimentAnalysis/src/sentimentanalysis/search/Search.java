package sentimentanalysis.search;

import java.util.LinkedList;
import javax.swing.SwingWorker;
import sentimentanalysis.SentimentAnalysisApp;
import sentimentanalysis.common.LocalTweet;
import sentimentanalysis.pagerank.Pagerank;
import sentimentanalysis.sentiment.Analysis;
import sentimentanalysis.twitterapi.TweetOperator;


/**
 *  Search handler class.
 *
 *  This will call the Twitter and Database interface classes in turn to
 *  load information relevant for the provided search query.
 *
 *  Mihai, Caroline: you should probably add your entry code here. Please keep
 *  it simple: a single call to a class within the .sentiment / .pagerank
 *  package, no more than a few lines of code per method here.
 *
 *  @author Matthijs
 */
public class Search extends SwingWorker<LinkedList<LocalTweet>, Void> {

    private String                      request;
    private Query                       query;
    private LinkedList<LocalTweet>      tweets;
    private TweetOperator               operator;
    
    
    public Search (String request) {
        this.request = request;
        operator = new TweetOperator(TweetOperator.DB_AND_ONLINE);
    }

    
    @Override
    public LinkedList<LocalTweet> doInBackground () {
        try {
            query = new Query(this.request);
            loadTweets();
            analyzeTweets();
        } catch( Exception e) {
            e.printStackTrace();
        }
        return getTweets();
    }
    
    
    /**
     * Added the saving to database here. My logic was the following: People 
     * probably take a look at the results for a while so we might as well use 
     * that moment for our saving. Feel free to move it if it interferes with
     * something.
     */
    @Override
    public void done () {
        SentimentAnalysisApp.getView().showResults(tweets, query);
        operator.saveNewTweetsToDB(tweets, query.toString());
    }
    
    public LinkedList<LocalTweet> getTweets () {
        return tweets;
    }
    
    public Query getQuery () {
        return this.query;
    }
    

    /**
     *  Load tweets.
     * 
     *  Attempt to load relevant tweets from both the database as well as the
     *  twitter API. Those are returned as a single LinkedList of Tweet objects.
     * 
     *  @throws Exception 
     */
    private void loadTweets () throws Exception {
        SentimentAnalysisApp.setStatus("Loading tweets.");
        tweets                  = operator.getTweets(query.toString());
    }


    /**
     *  Analyze the tweets just found: assign a relevance score, a sentiment
     *  score, and a page rank score.
     */
    private void analyzeTweets () throws Exception {
        SentimentAnalysisApp.setStatus("Analyzing tweets."); 
        new Analysis(this);
        //Pagerank pagerank = new Pagerank(tweets, query);
        //pagerank.computePageranks();
        
        for (LocalTweet tweet : tweets) {
            tweet.computeScore();
        } 
                  
    }
}