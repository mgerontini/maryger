/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sentimentanalysis.algorithmtestingsuite;

import com.csvreader.CsvReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import sentimentanalysis.database.Operator;
import sentimentanalysis.preprocessing.NBayesCalculator;
import sentimentanalysis.preprocessing.TextNormalizer;
import sentimentanalysis.sentiment.Lexicon;
import sentimentanalysis.sentiment.Tokenizer;

/**
 *
 * @author maryger
 */
public class TesterSuite implements Constants
{

    double[] gaussian;
    private Lexicon lexicon;
    static double TP;
    static double FP;
    static double TN;
    static double FN;

    public TesterSuite()
    {
        try
        {

            gaussian = new double[6];
            lexicon = new Lexicon();
           this.gaussian = CalculateDistance();
        } catch (Exception ex)
        {
            Logger.getLogger(TesterSuite.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public static void testData(int mode)
    {
        LinkedList<String> content = new LinkedList<String>();
        LinkedList<String> classes = new LinkedList<String>();
        try
        {
            CsvReader data = new CsvReader("../classified data/sampleoftestdata.csv");


            while (data.readRecord())
            {

                String class_id = data.get(0);

                String tweet_content = data.get(5);

                switch (mode)
                {
                    case NORMALIZE_TEXT:
                        tweet_content = TextNormalizer.getTweetWithoutUrlsAnnotations(tweet_content);
                        content.add(tweet_content);
                        break;
                    case REMOVE_DUPLICATES:
                        TextNormalizer.removeDuplicates(tweet_content);
                        content.add(tweet_content);
                        break;
                    case LOWER_CASE:
                        TextNormalizer.toLowerCase(tweet_content);
                        content.add(tweet_content);
                        break;
                    default:
                        content.add(tweet_content);
                        break;



                }
                switch (Integer.parseInt(class_id))
                {

                    case 0:
                        class_id = "negative";
                        break;
                    case 2:
                        class_id = "neutral";
                        break;
                    case 4:
                        class_id = "positive";
                        break;


                }
                classes.add(class_id);
            }
        } catch (IOException ex)
        {
            Logger.getLogger(TesterSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
        NBayesCalculator.evaluateModel(content, classes);
    }

    /**
     * 
     * @return PR = TP / TP+FP
     */
    public static double getPrecision()
    {
        return TP / (TP + FP);
    }

    /**
     * 
     * @return PR = TP / TP+FN
     */
    public static double getRecall()
    {
        return TP / (TP + FN);
    }

    //TODO SMILEY DETECTION AND COMBINATION OF SCORE WITH THE CLASSIFIER
    public void classifyBasedonSmiley(String tw, HashMap<String, Double> smils)
    {
        double score = TextNormalizer.detectSmiley(tw, smils);
        double clas = 1.0;
        if (score > 0)
        {

            clas = 3.0;

        } else
        {
            clas = 2.0;
        }
    }

    public void doTesting(int mode,String query) throws IOException
    {
        double sentiment = 0.0;
        LoadTestData data = new LoadTestData();
        LinkedList<String> tweets = data.getContents();
        LinkedList<String> classes = data.getClasses();
     //   Tokenizer token= new Tokenizer();
        int i=0;
        for (String tw:tweets )
        {
         //  tw =  TextNormalizer.removeDuplicates(tw);
          tw =  TextNormalizer.getTweetWithoutUrlsAnnotations(tw);
            switch (mode)
            {

                case USE_DISTANCE:

                    double score = getSentiment(query, tw);

                    TP = (isTP(score, -1, classes.get(i))) ? TP + 1 : TP;
                    FP = (isFP(score, -1, classes.get(i))) ? FP + 1 : FP;
                    TN = (isTN(score, -1, classes.get(i))) ? TN + 1 : TN;
                    FN = (isFN(score, -1, classes.get(i))) ? FN + 1 : FN;



                    break;
                case USE_BAYES: 
                  score =  NBayesCalculator.classifyNewTweet(tw);

                    TP = (isTP(score, -1, classes.get(i))) ? TP + 1 : TP;
                    FP = (isFP(score, -1, classes.get(i))) ? FP + 1 : FP;
                    TN = (isTN(score, -1, classes.get(i))) ? TN + 1 : TN;
                    FN = (isFN(score, -1, classes.get(i))) ? FN + 1 : FN;

                    break;
                case USE_NEGATIONS:


                    break;
                case USE_MODIFIERS:

                    break;
                case USE_SMILEYS:
         score = TextNormalizer.detectSmiley(tw, Operator.getInstance().getSmileys());

                    TP = (isTP(score, -1, classes.get(i))) ? TP + 1 : TP;
                    FP = (isFP(score, -1, classes.get(i))) ? FP + 1 : FP;
                    TN = (isTN(score, -1, classes.get(i))) ? TN + 1 : TN;
                    FN = (isFN(score, -1, classes.get(i))) ? FN + 1 : FN;

                    break;




            }
i++;
        }//for


    }

    private static boolean isNegation(String token)
    {
        HashMap<String, Double> negations = Operator.getInstance().getNegations();
        return negations.containsKey(token);
    }

    private static double getModifierValue(String token, Double negation)
    {
        HashMap<String, Double> modifiers = Operator.getInstance().getModifiers();
        double m;
        if (modifiers.containsKey(token))
        {
            m = modifiers.get(token);
            if ((negation == -1.0) && (m == 2))
            {
                m = 0.8;
            }
        } else
        {
            m = 1.0;
        }
        return m;
    }

    private double getSentiment(String query, String tweet) throws IOException
    {
        Tokenizer tokenizer = new Tokenizer();
        LinkedList<String> tokens = tokenizer.getTokens(tweet);
        Double score = 0.0;
        Double negation = 1.0;
        Double modifier = 1.0;
        int range = 0;
        Double gauss = 1.0;
        int i = 0;
        for (String token : tokens)
        {
            gauss = getDistance(query, token, tokens, i, this.gaussian);
            // score += /*(negation) * (modifier)  (gauss)****/  lexicon.get(token);
          //  score += /*(negation) * (modifier) ***/ (gauss)*  lexicon.get(token);
            score += /*(negation) ****/ (modifier)*  /*(gauss)*/  lexicon.get(token);
         //   score += (negation)* /** (modifier) ***/ /*(gauss)*/  lexicon.get(token);

            // Deal with negations
            if (isNegation(token))
            {
                negation = -1.0;
                range = 2;
            } else if (range > 1)
            {
                range--;
            } else
            {
                negation = 1.0;
            }

            // Deal with modifiers (like 'very')
            modifier = getModifierValue(token, negation);

            i++;
        }

        //System.out.println("Score:\t" + score);

//        if (score > 1 || score < -1)
//            System.out.println(tweet.getText());

        return score;
    }

    public double[] CalculateDistance()
    {
        double sigma = 1.0;
        double gaussian_max = Math.exp(-(1 / (sigma * sigma * 2))) / (sigma * Math.sqrt(2 * Math.PI));
        for (int i = 0; i <= 5; i++)
        {
            gaussian[i] = (1 / gaussian_max) * Math.exp(-(((i) / sigma) * ((i) / sigma)) / 2) / (sigma * Math.sqrt(2 * Math.PI));
        }
        return gaussian;
    }

    private double getDistance(String entity, String token, LinkedList<String> tokens, int i, double gaussian[])
    {

        int x = Math.abs(tokens.indexOf(entity));
        double height = Math.abs(lexicon.get(token));
        if (Math.abs(x - i) <= 5)
        {
            return height * gaussian[Math.abs(x - i)];
        } else
        {
            return 0.0;
        }
    }

    public boolean isTP(double score, double wekaclass, String trueclass)
    {
        double clas = 0;
        if (score > 0)
        {
            clas = 3.0;
        } else if(score<0)
        {
            clas = 2.0;

        }

        if ((clas == 3.0 && trueclass.equals("positive")) || (wekaclass == 3.0 && trueclass.equals("positive")) || (clas == 3.0 && trueclass.equals("positive")))
        {
            return true;
        }
        //  if(clas ){}
        return false;
    }

    public boolean isTN(double score, double wekaclass, String trueclass)
    {
        double clas = 0;
        if (score > 0)
        {
            clas = 3.0;
        } else if(score<0)
        {
            clas = 2.0;

        }

        if ((clas == 2.0 && trueclass.equals("negative")) || (wekaclass == 2.0 && trueclass.equals("negative")) || (clas == 2.0 && trueclass.equals("negative")))
        {
            return true;
        }
        //  if(clas ){}
        return false;
    }

    public boolean isFP(double score, double wekaclass, String trueclass)
    {
        double clas = 0;
        if (score > 0)
        {
            clas = 3.0;
        } else if(score<0)
        {
            clas = 2.0;

        }

        if ((clas == 3.0 && trueclass.equals("negative")) || (wekaclass == 3.0 && trueclass.equals("negative")) || (clas == 3.0 && trueclass.equals("negative")))
        {
            return true;
        }
        //  if(clas ){}
        return false;
    }

    public boolean isFN(double score, double wekaclass, String trueclass)
    {
        double clas = 0;
        if (score > 0)
        {
            clas = 3.0;
        } else if(score<0)
        {
            clas = 2.0;

        }

        if ((clas == 2.0 && trueclass.equals("positive")) || (wekaclass == 2.0 && trueclass.equals("positive")) || (clas == 2.0 && trueclass.equals("positive")))
        {
            return true;
        }
        //  if(clas ){}
        return false;
    }

    public static void main(String args[])
    {
        //   testData(NORMALIZE_TEXT);
        //       testData(REMOVE_DUPLICATES);
        //     testData(LOWER_CASE);
    }
}
