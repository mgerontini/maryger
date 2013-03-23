package sentimentanalysis.pagerank;

import java.util.logging.Level;
import java.util.logging.Logger;
import sentimentanalysis.common.LocalTweet;
import sentimentanalysis.common.TweetRankInfo;
import sentimentanalysis.common.User;
import sentimentanalysis.twitterapi.TweetOperator;

/**
 * Contains the relevant information for static quality computation (pagerank)
 * of a {@code LocalTweet} object.
 * @author j
 */
public class TweetRank {
    
    LocalTweet tweet;
    TweetOperator operator;
    User author;
    TweetRankInfo rankInfo;
    
    public TweetRank(LocalTweet tweet, TweetOperator tweetOperator) {
        this.tweet = tweet;
        this.operator = tweetOperator;
        retrieveRankInfo();
    }

    private void retrieveRankInfo() {
        long authorId = tweet.getFromUserId();
        long tweetId = tweet.getId();
        this.author = operator.getUser(authorId);
        this.rankInfo = operator.getTweetRankInfo(tweetId);
    }
    
    public long getFollowerCount() {
        return author.getFollowersCount();
    }
    
    public long getRetweetCount() {
        return rankInfo.getRetweetCount();
    }

    public LocalTweet getTweet() {
        return tweet;
    }
    
    public User getAuthor() {
        return author;
    }

    public boolean isFavorited() {
        return rankInfo.isFavorited();
    }

}
