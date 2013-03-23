/*
 * Class which produces ngrams
 * These ngrams can be used to detect negations.
 * 
 */
package sentimentanalysis.preprocessing;


import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author maryger
 */
public class NGramExtractor  {

  
    private static List<String> ngrams(int n, String str) {
        List<String> ngrams = new ArrayList<String>();
        String[] words = str.split(" ");
        for (int i = 0; i < words.length - n + 1; i++)
            ngrams.add(concat(words, i, i+n));
        return ngrams;
    }

    private static String concat(String[] words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words[i]);
        return sb.toString();
    }
    /**
     * Function which returns a list with n-grams
     * if min = 1 and max = 3 will return 
     * nGrams with 1 token, ngrams with 2 tokens and ngrams with 3 tokens
     * if min =2 and max = 3 will return
     * nGrams with 2 tokens and 3 tokens
     * @param minNgram: minumum number of token for a Ngram
     * @param MaxNgram: maximum number of token for a Ngram
     * @param s : the tokenized String
     * @return arraylist with results
     */
    public static ArrayList<String> getNGrams(String s,int minNgram, int maxNgram){
        ArrayList<String> ngrams = new ArrayList<String>();   
        for (int n = minNgram; n <= maxNgram; n++) {
            for (String ngram : ngrams(n, s))
            {  ngrams.add(ngram);}
           
        }
    
    return ngrams;
    }
    

   

//    public static void main(String[] args) {
//       ArrayList<String> test = getNGrams("hello world, I am so happy today!",2,2);
//            for (String ngram : test )
//                System.out.println(ngram);
//            
//        
//    }
}
