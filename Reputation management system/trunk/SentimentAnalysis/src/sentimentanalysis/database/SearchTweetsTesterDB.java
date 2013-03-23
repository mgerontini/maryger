/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sentimentanalysis.database;

/**
 *
 * @author maryger
 */
/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.util.List;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.7
 */
public class SearchTweetsTesterDB {
    /**
     * Usage: java twitter4j.examples.search.SearchTweets [query]
     *
     * @param args
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("java twitter4j.examples.search.SearchTweets [query]");
            
        }
        Twitter twitter = new TwitterFactory().getInstance();
        try {
            QueryResult result = twitter.search(new Query("ipad").lang("en"));
            List<Tweet> tweets = result.getTweets();
            Operator db = Operator.getInstance();
           
            for (Tweet tweet : tweets) {
                db.storeTweet(tweet);
                System.out.println("@" + tweet.getFromUser() + " - " + tweet.getText());
            }
            db.closeCon();
            db.closeConLex();
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
    }
}
