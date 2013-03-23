/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sentimentanalysis.common;

import java.util.Comparator;

/**
 *
 * @author dama
 */
public class LocalTweetComparator implements Comparator<LocalTweet>{

    private String query;
    
    public LocalTweetComparator(String query) {
        super();
        this.query = query;
    }
    
    public int compare(LocalTweet o1, LocalTweet o2) {
        double dif = o1.getSentimentScore(query) - o2.getSentimentScore(query);
        if (dif < 0)
            return -1;
        return 1;
    }
    
}
