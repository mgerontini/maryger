package sentimentanalysis.pagerank;

import java.util.LinkedList;
import sentimentanalysis.common.LocalTweet;
import sentimentanalysis.search.Query;
import sentimentanalysis.twitterapi.TweetOperator;

/**
 *
 * @author Matthijs
 */
public class Pagerank {
    private LinkedList<LocalTweet> tweets;
    private TweetOperator operator;
    private Query query;
    
    public Pagerank (LinkedList<LocalTweet> tweets, Query query) {
        this.tweets = tweets;
        this.query = query;
        operator = new TweetOperator(TweetOperator.DB_AND_ONLINE);
    }
    
    /**
     * Computes and assigns a static quality measure for tweets in the interval
     * [0.0, 10.0].
     */
    public void computePageranks() {
        LinkedList<TweetRank> tweetRanks = new LinkedList<TweetRank>();

        // Populate the necessary information for pagerank score computation
        // into {@code TweetRank} objects and keep track of maxima of these
        // information pieces.
        for (LocalTweet tweet : tweets) {
            // If a tweet is neutral based on its sentiment score, it is not
            // necessary to compute its pagerank.
            if (!tweet.isNeutral(query.getRequest())) {
                TweetRank tweetRank = new TweetRank(tweet, operator);
                tweetRank.getTweet().setPageRank(computePagerank(tweetRank));
                System.out.println(
                        "tweet id:" + tweet.getId() + "\t" +
                        "pagerank:" + computePagerank(tweetRank) + "\t" +
                        "user:" + tweet.getFromUser() + "\t\t" +
                        "user id: " + tweet.getFromUserId() + "\t\t" +
                        "retweet:" + tweetRank.getRetweetCount() + "\t" +
                        "follower:" + tweetRank.getAuthor().getFollowersCount());
            }
        }
    }
    
    /**
     * Computes a static quality measure of a tweet.
     * 
     * @param tweetRank Associated {@code TweetRank} object which contains the
     *         relevant information for the computation.
     * @return A single number in the interval [0.0, 10.0], but the upper threshold is
     *         practically impossible to reach.
     */
    private double computePagerank(TweetRank tweetRank) {
        double pagerank = 0.0;
        // The following numbers are found online (all time Twitter records)
        final int MAX_RETWEET_COUNT = 100000;
        final int MAX_FOLLOWER_COUNT = 25000000;
        final int MAX_FOLLOWING_COUNT = 800000;
        pagerank += 0.01 * (tweetRank.isFavorited() ? 1 : 0);
        // +1s are for log function's sake (log(0) is problematic)
        pagerank += 0.30 * (Math.log(tweetRank.getRetweetCount() + 1) / Math.log(MAX_RETWEET_COUNT));
        pagerank += 0.64 * (Math.log(tweetRank.getAuthor().getFollowersCount() + 1) / Math.log(MAX_FOLLOWER_COUNT));
        pagerank += 0.05 * (Math.log(tweetRank.getAuthor().getFriendsCount() + 1) / Math.log(MAX_FOLLOWING_COUNT));
        return pagerank * 10;
    }
}
