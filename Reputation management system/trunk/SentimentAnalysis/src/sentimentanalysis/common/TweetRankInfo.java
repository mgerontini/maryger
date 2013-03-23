/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sentimentanalysis.common;

/**
 * This is a class to keep a single tweet's ranking related information.
 * This is somewhat a hacky solution, but as Mary suggested, it is not good to
 * change tweets table at this point. So I basically created a new table with
 * relevant information (retweet count, isfavorited) and this class represent
 * entries of the table.
 * @author j
 */
public class TweetRankInfo {
    long retweetCount;
    boolean favorited;
    long id;
    
    public long getId() {
        return id;
    }
    
    public boolean isFavorited() {
        return favorited;
    }

    @Override
    public String toString() {
        return "TweetRankInfo{" + "retweetCount=" + retweetCount + ", favorited=" + favorited + ", id=" + id + '}';
    }

    public long getRetweetCount() {
        return retweetCount;
    }

    public TweetRankInfo(long id, long retweetCount, boolean favorited) {
        this.retweetCount = retweetCount;
        this.favorited = favorited;
        this.id = id;
    }
    
}
