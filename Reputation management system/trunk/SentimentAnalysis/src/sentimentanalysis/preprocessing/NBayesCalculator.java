/*
 * Class which classifies tweets based on a Naibe Bayes Model
 * 
 */
package sentimentanalysis.preprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sentimentanalysis.common.LocalTweet;
import sentimentanalysis.database.Operator;
import sentimentanalysis.twitterapi.TweetOperator;
import twitter4j.TwitterException;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.core.*;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 *Class which loads a NaiveBayesClassifier from given data(16.000 tweets)
 * Available classes: 0(negative),2(neutral),4(positive)
 * The classifier will serialized in order to be able to used as classifier for new data
 * @author maryger
 */
public class NBayesCalculator
{

    /**
     * Serialize Naive Bayes classifier
     */
    private static void constructModel()
    {
        try
        {
            BufferedReader reader = new BufferedReader(
                    new FileReader("./data/train2data.arff"));

            Instances data = new Instances(reader);


            reader.close();
//            // setting class attribute
//            data.setClassIndex(data.numAttributes() - 1);


            // train J48 and output model
            NaiveBayes classifier = new NaiveBayes();
            data.setClassIndex(0);
            classifier.buildClassifier(data);
            serialize(classifier);
            // evaluate classifier and print some statistics
            Evaluation eval = new Evaluation(data);
            eval.evaluateModel(classifier, data);
            System.out.println(eval.toSummaryString("\nResults\n======\n", false));
            System.out.println("\n\nClassifier model:\n\n" + classifier);

        } catch (Exception ex)
        {
            Logger.getLogger(NBayesCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Retrieve the classifier Naive model
     * for new data
     * the classification result can be used as an 
     * additional score(weight) to the existing scores.
     */
 
    private static void serialize(Classifier object)
    {
        try
        {
            weka.core.SerializationHelper.write("nBayes.model", object);
        } catch (Exception ex)
        {
            Logger.getLogger(NBayesCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    /**
     * Method which returns an additional score based on previous tweets
     * We have trained a NaiveBayes model from previous tweets
     * with labels positive negative and neutral
     * When it gets the new tweet it will predict the most possible
     * class bases on its content.
     * @param LocalTweet instance
     * @return a score which can be combined with the existing heuristics
     */
    public static double classifyNewTweet(String tw)
    {
        double score = 0;
        try
        {
            Classifier cls = (Classifier) weka.core.SerializationHelper.read("nBayes.model");

            FastVector atts;

            // 1. set up attributes
            atts = new FastVector();
            atts.addElement(new Attribute("content", (FastVector) null));


            // Declare the class attribute along with its values
            FastVector fvClassVal = new FastVector(4);
            fvClassVal.addElement("");
            fvClassVal.addElement("neutral");
            fvClassVal.addElement("negative");
            fvClassVal.addElement("positive");
            
            
            Attribute ClassAttribute = new Attribute("Class", fvClassVal);
            atts.addElement(ClassAttribute);

            // 2. create Instances object
            Instances instdata = new Instances("testData", atts, 0);
            Instance iInst = new Instance(2);
            iInst.setValue((Attribute) atts.elementAt(0), tw);

            // iInst.setValue((Attribute) atts.elementAt(1), '?');



            instdata.add(iInst);



            StringToWordVector filter = new StringToWordVector();


            instdata.setClassIndex(instdata.numAttributes() - 1);
            filter.setInputFormat(instdata);
            Instances newdata = Filter.useFilter(instdata, filter);



          //  System.out.println(newdata.toString());
            newdata.setClassIndex(0);
            double clsLabel = cls.classifyInstance(instdata.instance(0));
            int label = (int) clsLabel;
            switch (label)
            {

                case 3:
                    score = -0.5;
                    break;
                case 2:
                    score = +0.5;
                    break;
                case 1:
                    score = +0.0;
                    break;
            }

            //      eTest.evaluateModel(cls, isTrainingSet);
        } catch (Exception ex)
        {
            Logger.getLogger(NBayesCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return score;
    }
    
    public static void evaluateModel(LinkedList<String> tw,LinkedList<String> classes){
        try
        {
            Classifier cls = (Classifier) weka.core.SerializationHelper.read("nBayes.model");
            
            
            BufferedReader reader = new BufferedReader(
                    new FileReader("./data/train2data.arff"));

            Instances traindata = new Instances(reader);


            reader.close();
            
            
            FastVector atts;

            // 1. set up attributes
            atts = new FastVector();
            atts.addElement(new Attribute("content", (FastVector) null));


            // Declare the class attribute along with its values
            FastVector fvClassVal = new FastVector(3);
             fvClassVal.addElement("");
            fvClassVal.addElement("neutral");
            fvClassVal.addElement("negative");
            fvClassVal.addElement("positive");
            
            
            Attribute ClassAttribute = new Attribute("Class", fvClassVal);
            atts.addElement(ClassAttribute);

            // 2. create Instances object
            Instances instdata = new Instances("testData", atts, 0);
            int i=0;
            for(String tweet:tw){
            Instance iInst = new Instance(2);
            iInst.setValue((Attribute) atts.elementAt(0), tweet);

           if(!classes.isEmpty())
           {
           iInst.setValue((Attribute) atts.elementAt(1), classes.get(i));
           
           }


            instdata.add(iInst);
           i++;
            }


            StringToWordVector filter = new StringToWordVector();


            instdata.setClassIndex(instdata.numAttributes() - 1);
            filter.setInputFormat(instdata);
            Instances newdata = Filter.useFilter(instdata, filter);
            ArffSaver saver = new ArffSaver();
            saver.setInstances(newdata);
            saver.setFile(new File("../data/testdata2.arff"));
            saver.writeBatch();


           // System.out.println(newdata.toString());
           traindata.setClassIndex(0);
             Evaluation eval = new Evaluation(traindata);
            eval.evaluateModel(cls, newdata);
//            String[] options = new String[6];
//            options[0] = "-T";
//            options[1] = "../data/testdata2.arff";
//            options[2] = "-l";
//            options[3] = "nBayes.model";
//            options[4] = "-p";
//            options[5] = "0";
//            
            
           // System.out.println(Evaluation.evaluateModel(cls, options));
            
           System.out.println(eval.toSummaryString("\nResults\n======\n", false));
            System.out.println("\n\nClassifier model:\n\n" + cls);
            System.out.println(instdata.numClasses());
            
            Enumeration br = newdata.enumerateInstances();
            int ind=0;
            while(br.hasMoreElements()){
            double clsLabel = cls.classifyInstance((Instance)br.nextElement());
            String cl ="";
            switch((int)clsLabel){
                case 0: cl ="";break;
                case 1: cl ="neutral"; break;
                case 2:cl= "negative"; break;
                case 3:cl = "positive"; break;
            
            
            }
            
            System.out.println("cont: "+tw.get(ind)+" pred_class: "+cl + " actual: "+classes.get(ind));
            ind++;
            }
        
        } catch (Exception ex)
        {
            Logger.getLogger(NBayesCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    
    }

    public static void main(String[] args)
    {
        
            //        Operator db = Operator.getInstance();
            //        LinkedList<String> tw = db.getTweetsForTesting();
            //        evaluateModel(tw,db);
                    // constructModel();
                     Operator db = Operator.getInstance();
               LinkedList<String> tw = db.getTweetsForTesting();
                evaluateModel(tw,null);
            //        classifyNewTweet(null);
        
    }
}
