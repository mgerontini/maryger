package sentimentanalysis.sentiment;

import java.util.HashMap;
import sentimentanalysis.database.Operator;

/**
 *
 * @author Matthijs
 */
public class Lexicon {
 
    private static HashMap<String, Double> lexicon;
    
    
    public Lexicon () throws Exception {
        if (lexicon == null)
            lexicon = Operator.getInstance().getLexicon();
    }
    
    
    public Double get (String word) {
        if (lexicon.containsKey(word))
            return lexicon.get(word);
        return 0.0;
    }
}
