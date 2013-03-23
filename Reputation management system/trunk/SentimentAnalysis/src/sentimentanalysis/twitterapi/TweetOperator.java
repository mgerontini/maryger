/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sentimentanalysis.twitterapi;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sentimentanalysis.common.User;
import sentimentanalysis.common.LocalTweet;
import sentimentanalysis.common.LocalTweetComparator;
import sentimentanalysis.common.TweetRankInfo;
import sentimentanalysis.database.Operator;

import twitter4j.*;
import twitter4j.auth.AccessToken;


/**
 *
 * @author dama
 */
public class TweetOperator {
    private Operator db;
    private int mode;
    public static final int DB_AND_ONLINE = 3;
    public static final int DB_ONLY = 1;
    public static final int ONLINE_ONLY = 2;

    private static final String consumerKey = "joi6fkZJBohp1FtU6Fqjg";
    private static final String consumerSecret = "LRsA9Wt1xMpCM0q13xp5KdlfwagoPUtrlBYvbTMeM0";
    private static final String tokenKey = "560277922-XKTrGOH0iyNNxaA1SxjqP0ayEKOd8wdC9c5zxPhX";
    private static final String tokenSecret = "MekMr8QEGihVSqXgibivqIvf69t3ALtdAjQC4vvDA";

    //cihat
//    private static final String consumerKey = "AfOuwL3SVpAHbCUNpIYftQ";
//    private static final String consumerSecret = "djrxQfjFExv1aQcNkA0SPTOcmWm2OZco3quiE2rVlYs";
//    private static final String tokenKey = "114618961-r3zolbFPCOzz9HQH1BOviDm6YxSEh93MyoOLqWgI";
//    private static final String tokenSecret = "7ClxMzNCKspLD5NS71wdBsRk6P2liK8CGyGZv9F7Co";

    Twitter twitter;
 
    
    public TweetOperator(int mode) {
        try {
            db = Operator.getInstance();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        this.mode = mode;
        twitter =  new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        AccessToken token = new AccessToken(tokenKey, tokenSecret);
        twitter.setOAuthAccessToken(token);
    }
    
    @Override
    protected void finalize() throws Throwable{
        super.finalize();
    }
    
    protected LinkedList<LocalTweet> getTweetsWithAPI(String queryString) throws TwitterException {
        Query query = new Query(queryString);
        long sinceId = db.getIdOfLatestTweetForThisEntity(queryString);
        System.out.println("Get ids since " + sinceId);
        query.setRpp(100);

        query.setLang("en");
        query.setSinceId(sinceId);
        QueryResult result = twitter.search(query);
        LinkedList<LocalTweet> results = new LinkedList<LocalTweet>();
        for (Tweet tweet : result.getTweets()) {
            LocalTweet lt = new LocalTweet();
            lt.copyFrom(tweet);
            results.add(lt);
        }
        return results;
    }
    
    protected LinkedList<LocalTweet> getTweetsFromDB(String queryString) {
        return db.getLocalTweets(queryString);
    }
    
    public LinkedList<LocalTweet> getTweets(String queryString) throws TwitterException {
        LinkedList<LocalTweet> results;
        if (mode == DB_AND_ONLINE) {
            results = getTweetsFromDB(queryString);
            results.addAll(getTweetsWithAPI(queryString));
        } else if (mode == DB_ONLY) {
            results = getTweetsFromDB(queryString);
        } else if (mode == ONLINE_ONLY) {
            results = getTweetsWithAPI(queryString);
        } else {
            results = new LinkedList<LocalTweet>();
        }
        return results;
    }
    
    /**
     * Retrieve user info related to pageranking.
     * @param id Twitter ID number of the user
     * @return 
     */
    public User getUser(long id) {
        User user = null;
        if (mode == DB_AND_ONLINE) {
            user = db.getUser(id);
            if (user == null) {
                user = getUserWithAPI(id);
            } else {
                System.out.println("User info already found in database:" + id);
            }
        } else if (mode == DB_ONLY) {
            user = db.getUser(id);
        } else {
            user = getUserWithAPI(id);
        }
        // Store the user info in DB.
        // NOTE: this is not done in batch manner here, it may be slower this way
        // yet it's easier to get done for now.
        if (user != null) {
            db.storeUser(user);
        }
        return user;
    }
    
    public TweetRankInfo getTweetRankInfo(long id) {
        TweetRankInfo tweetRankInfo = null;
        if (mode == DB_AND_ONLINE) {
            tweetRankInfo = db.getTweetRankInfo(id);
            if (tweetRankInfo == null) {
                tweetRankInfo = getTweetRankInfoWithAPI(id);
            } else {
                System.out.println("Tweet rank info already found in database:" + id);
            }
        } else if (mode == DB_ONLY) {
            tweetRankInfo = db.getTweetRankInfo(id);
        } else {
            tweetRankInfo = getTweetRankInfoWithAPI(id);
        }
        // Store the user info in DB.
        // NOTE: this is not done in batch manner here, it may be slower this way
        // yet it's easier to get done for now.
        if (tweetRankInfo != null) {
            db.storeTweetRankInfo(tweetRankInfo);
        }
        return tweetRankInfo;
    }

    private TweetRankInfo getTweetRankInfoWithAPI(long id) {
        boolean favorited = isFavorited(id);
        long retweetCount = getRetweetCount(id);
        TweetRankInfo tweetRankInfo = new TweetRankInfo(id, retweetCount, favorited);
        System.out.println(tweetRankInfo);
        return tweetRankInfo;
    }
    
    /**
     * Retrieve a Twitter user (with relevant info about pagerank) via Twitter
     * API and create a common.User object.
     * @param id Twitter ID number of the user
     * @return 
     */
    private User getUserWithAPI(long id) {
        int followersCount = getFollowersCount(id);
        int friendsCount = getFriendsCount(id);
        // number of tweets is not used now in pageranking, so don't waste a
        // query for it now.
        int statusesCount = 0;
        //int statusesCount = getTweetCount(id);
        return new User(id, followersCount, friendsCount, statusesCount);
    }
    
    public int getFollowersCount(long userID) {
        try {
            return twitter.showUser(userID).getFollowersCount(); 
        } catch (TwitterException ex) {
            Logger.getLogger(TweetOperator.class.getName()).log(Level.SEVERE, "Problem when getting followers count", ex);
            return 0;
        }
    }
    
    public int getFollowersCount(String user) {
        try {
            return twitter.showUser(user).getFollowersCount(); 
        } catch (TwitterException ex) {
            Logger.getLogger(TweetOperator.class.getName()).log(Level.SEVERE, "Problem when getting followers count", ex);
            return 0;
        }
    }
    
    public int getFriendsCount(long userID) {
        try {
            return twitter.showUser(userID).getFriendsCount(); 
        } catch (TwitterException ex) {
            Logger.getLogger(TweetOperator.class.getName()).log(Level.SEVERE, "Problem when getting friends(followed by me) count", ex);
            return 0;
        }
    }
    
    public int getFriendsCount(String user) {
        try {
            return twitter.showUser(user).getFriendsCount(); 
        } catch (TwitterException ex) {
            Logger.getLogger(TweetOperator.class.getName()).log(Level.SEVERE, "Problem when getting friends(followed by me) count", ex);
            return 0;
        }
    }
    
    public LinkedList<Long> getFollowerIds(String user) {
        try {
            long cursor = -1;
            LinkedList<Long> res = new LinkedList<Long>();
            IDs ids;
            do {
                ids = twitter.getFollowersIDs(user, cursor);
                for (long id: ids.getIDs()) {
                    res.add(id);
                }
            } while ((cursor = ids.getNextCursor()) != 0);
            return res;
        } catch (TwitterException ex) {
            Logger.getLogger(TweetOperator.class.getName()).log(Level.SEVERE, null, ex);
            return new LinkedList<Long>();
        }
    }
    
      
    public LinkedList<Long> getFollowerIds(long userId) {
        try {
            long cursor = -1;
            LinkedList<Long> res = new LinkedList<Long>();
            IDs ids;
            do {
                ids = twitter.getFollowersIDs(userId, cursor);
                for (long id: ids.getIDs()) {
                    res.add(id);
                }
            } while ((cursor = ids.getNextCursor()) != 0);
            return res;
        } catch (TwitterException ex) {
            Logger.getLogger(TweetOperator.class.getName()).log(Level.SEVERE, null, ex);
            return new LinkedList<Long>();
        }
    }
    
    public int getFollowersCount(LocalTweet tweet) {
        return getFollowersCount(tweet.getFromUser());
    }
    
    /**
    * 
    * @param tweet
    * @return a list of user ids that follow this user
    */
    public LinkedList<Long> getFollowerIds(LocalTweet tweet) {
        return getFollowerIds(tweet.getFromUser());
    }
    
    public LinkedList<Long> getRetweetedByIds(long id) {
        int page = 1;
        IDs ids;
        LinkedList<Long> res = new LinkedList<Long>();
        try {
            do {
                ids = twitter.getRetweetedByIDs(id, new Paging(page, 100));
                for (long this_id : ids.getIDs()) {
                    res.add(this_id);
                }
                ++page;
            } while (ids.getIDs().length != 0);
        } catch (TwitterException ex) {
           Logger.getLogger(TweetOperator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    /**
     * 
     * @param tweet
     * @return a list of user ids who've retweeted this
     */
    public LinkedList<Long> getRetweetedByIds(LocalTweet tweet) {
        return getRetweetedByIds(tweet.getId());
    }
    
    public long getRetweetCount(LocalTweet tweet) {
        try {
            return twitter.showStatus(tweet.getId()).getRetweetCount();
        } catch (TwitterException ex) {
            Logger.getLogger(TweetOperator.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public long getRetweetCount(long id) {
         try {
             return twitter.showStatus(id).getRetweetCount();
        } catch (TwitterException ex) {
            Logger.getLogger(TweetOperator.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public int getTweetCount(long userId) {
        try {
            return twitter.showUser(userId).getStatusesCount();
        } catch (TwitterException tex) {
        }
        return 0;
    }
    
    public int getTweetCount(LocalTweet tweet) {
        return getTweetCount(tweet.getFromUserId());
    }
    
    
    public static LinkedList<LocalTweet> getTopPositive(String queryString, int N, LinkedList<LocalTweet> tweets) {
        LinkedList<LocalTweet> toSort = new LinkedList<LocalTweet>();
        LinkedList<LocalTweet> result = new LinkedList<LocalTweet>();
        for (LocalTweet tweet : tweets) {
            if (tweet.getSentimentScore(queryString) > 0.1) {
                toSort.add(tweet);
            }
        }
        
        Collections.sort(toSort, new LocalTweetComparator(queryString));
        int sz = toSort.size();
        N = Math.min(N, sz);
        for (int i = 0; i < N; ++i) {
            result.add(toSort.get(sz - i - 1));
        }    
        return result;
    }
    
    public static LinkedList<LocalTweet> getTopNegative(String queryString, int N, LinkedList<LocalTweet> tweets) {
        LinkedList<LocalTweet> toSort = new LinkedList<LocalTweet>();
        LinkedList<LocalTweet> result = new LinkedList<LocalTweet>();
        for (LocalTweet tweet : tweets) {
            if (tweet.getSentimentScore(queryString) < -0.1) {
                toSort.add(tweet);
            }
        }
        Collections.sort(toSort, new LocalTweetComparator(queryString));
        int sz = toSort.size();
        N = Math.min(N, sz);
        for (int i = 0; i < N; ++i) {
            result.add(toSort.get(i));
        }    
        return result;
    }
    
    public boolean isFavorited(long id) {
        try {
            return twitter.showStatus(id).isFavorited();
        } catch (TwitterException tex) {
            
        }
        return false;
    }
    
    public boolean isFavorited(LocalTweet tweet) {
        return isFavorited(tweet.getId());
    }
    
    public void saveToDB(LocalTweet tweet) {
        db.storeTweet(tweet);
    }
    
    public void saveBatch(LinkedList<LocalTweet> list, String entity) {
        System.out.println("Storing tweets to database.");
        db.storeTweetBatch(list, entity);
    }
    
    public void saveNewTweetsToDB(LinkedList<LocalTweet> tweets, String query) {
        int sz = tweets.size() - 1;
        LinkedList<LocalTweet> list = new LinkedList<LocalTweet>();
        for (int i = sz; i >= 0 && !tweets.get(i).isFromDB(); --i) {
            list.add(tweets.get(i));
        }
        
        saveBatch(list, query);
    }
}
 
