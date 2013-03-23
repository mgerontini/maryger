package sentimentanalysis.endpoint;

import java.util.LinkedList;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import sentimentanalysis.SentimentAnalysisApp;
import sentimentanalysis.common.LocalTweet;
import sentimentanalysis.search.Search;

/**
 *  WebService implementor
 *
 *  This class implements the web search: it handles SOAP calls and returns the
 *  results to the connected SOAP client.
 *
 *  @author Matthijs
 */
@WebService() public class Implementor {

    
    /**
     *  Search method.
     * 
     *  This performs a search for the given query, and returns the tweets with
     *  associated sentiment values.
     * 
     *  @param query 
     */
    @WebMethod() public LinkedList<SoapTweet> search (@WebParam(name="query") String query) {
        SentimentAnalysisApp.getView().setQuery(query);
        Search search = new Search(query);
        search.execute();
        LinkedList<SoapTweet> result = new LinkedList<SoapTweet>();
        
        try {
            for (LocalTweet tweet : search.get()) 
                result.push(new SoapTweet(tweet, query));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    } 
}