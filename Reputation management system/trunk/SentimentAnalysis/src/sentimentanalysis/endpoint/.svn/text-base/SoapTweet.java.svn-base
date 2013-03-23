package sentimentanalysis.endpoint;

import sentimentanalysis.common.LocalTweet;

/**
 *  SoapTweet 
 * 
 *  A Tweet object designed for use in the SOAP Service. To this end, only the
 *  minimum of required data fields are used, and those are made public so they
 *  are accessible through the SOAP service.
 * 
 *  @author Matthijs
 */
public class SoapTweet {
    
    public String author;
    public double sentiment;
    public String date;
    public double rank;
    public String content;
    
    public SoapTweet () {}
    public SoapTweet (LocalTweet parent, String query) {
        author      = parent.getFromUser();
        sentiment   = parent.getSentimentScore(query);
        rank        = parent.getPageRank();
        date        = parent.getCreatedAt().toGMTString();
        content     = parent.getText();
    }
}
