package sentimentanalysis.sentiment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import net.jeremybrooks.knicker.*;
import net.jeremybrooks.knicker.dto.Related;
import net.jeremybrooks.knicker.dto.TokenStatus;
import sentimentanalysis.SentimentAnalysisApp;
import sentimentanalysis.search.Query;
import wikiApiClient.Properties;

/**
 *  Provide thesaurus expansion. For this, we use the knicker library which
 *  interfaces with WordNik.
 * 
 *  @author Matthijs
 */
public class Thesaurus {
    
    // Please: generate your own key by signing up at WordNik, http://developer.wordnik.com/
    private static String           key         = "0799c58333d9572f5f2225ac2b80875e61f60c1ac42fdaac6";
    private static boolean          validKey    = false;
    private static HashMap          related     = new HashMap<String,HashMap>();
    
    private HashMap<String,Double>  scores      = new HashMap<String, Double>();
    private Query                   parent      = null;
    
    
    /**
     *  Thesaurus constructor. Load a reference to a parent search Query object
     * 
     *  @param query 
     */
    public Thesaurus (Query query) {
        this.parent = query;
    }
    
    
    /**
     *  Retrieve related words for the search query associated with this Thesaurus
     *  object. This may fail and return null instead.
     * 
     *  @return 
     */
    public HashMap<String,Double> getRelated () {
        try {
            if (!related.containsKey(parent.getRequest()))
                findRelated();
            return scores;
        } catch (Exception e) {
            System.err.println("Could not find related words: " + e.getMessage());
            return null;
        }
    }
    
    
    /**
     *  Find related words. 
     * 
     *  This performs a lookup request to find terms similar to the search query.
     * 
     *  @param entity
     *  @throws Exception 
     */
    private void findRelated () throws Exception {
        SentimentAnalysisApp.setStatus("Performing query expansion.");
        findWordNikRelated();
        findWikiRelated();
        related.put(parent.getRequest(), scores);
    }


    /**
     *  Find related words through the WordNik API.
     * 
     *  @param entity
     *  @param result
     *  @throws Exception 
     */
    private void findWordNikRelated () {
        SentimentAnalysisApp.setStatus("Thesaurus search through WordNik.");
        List<Related>   words;
        double          score = 0.0;        

        try {
            validateKey();
            words = WordApi.related(parent.getRequest(), 0, true, null, null, null);
        } catch (Exception e) {
            System.err.println("Could not perform WordNik thesaurus search: " + e.getMessage());
            return;
        }
        for (Related wordList : words) {
            System.out.println(wordList.getRelType() + ": " + wordList.getWords());
            score = 0.0;
            if (wordList.getRelType().contentEquals("hypernym"))
                score = 1.0;
            if (wordList.getRelType().contentEquals("hyponym"))
                score = 0.8;
            if (wordList.getRelType().contentEquals("synonym"))
                score = 0.3;

            for (String word : wordList.getWords())
                scores.put(word, score);
        }
    }
    
    
    /**
     *  Find related words through Wikipedia page links.
     * 
     *  @param entity
     *  @param result
     *  @throws Exception 
     */
    private void findWikiRelated () throws Exception {
        SentimentAnalysisApp.setStatus("Finding page links from Wikipedia.");         
        Properties              wiki    = new Properties("en.wikipedia.org");
        LinkedList<String>      links   = wiki.getLinks(parent.getRequest());
        for (String link : links) {
            if (scores.containsKey(link))
                scores.put(link, scores.get(link) + 0.9);
            else
                scores.put(link, 0.9);
        }
    }
    
    
    /**
     *  Validate the API key. Only perform this check the first time we search,
     *  as it is unlikely that our key will become invalidated for subsequent
     *  searches.
     * 
     *  @throws Exception 
     */
    private void validateKey () throws Exception {
        if (validKey)
            return;

        SentimentAnalysisApp.setStatus("Validating thesaurus API key.");
        System.setProperty("WORDNIK_API_KEY", key);
        TokenStatus status = AccountApi.apiTokenStatus();
        if (status.isValid()) {
            System.out.println("API key is valid.");
            validKey = true;
        }
    }
}