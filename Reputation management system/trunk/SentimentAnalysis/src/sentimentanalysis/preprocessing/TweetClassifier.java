/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sentimentanalysis.preprocessing;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.*;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 *
 * @author maryger
 */
public class TweetClassifier
{

    /** The training data gathered so far. */
    private Instances m_Data = null;
    /** The filter used to generate the word counts. */
    private StringToWordVector m_Filter = new StringToWordVector();
    /** The actual classifier. */
    private Classifier m_Classifier = new NaiveBayes();
    private boolean m_UpToDate;

    /* Create empty training dataset*/
    public TweetClassifier()
    {
        String nameOfDataset = "MessageClassificationProblem";
// Create vector of attributes.
        FastVector attributes = new FastVector(2);
// Add attribute for holding messages.
        attributes.addElement(new Attribute("content", (FastVector) null));
// Add class attribute.
        FastVector classValues = new FastVector(4);
        classValues.addElement("");
        classValues.addElement("neutral");
        classValues.addElement("negative");
        classValues.addElement("positive");

        attributes.addElement(new Attribute("Class", classValues));
        // Create dataset with initial capacity of 100, and set index
        m_Data = new Instances(nameOfDataset, attributes, 100);
        m_Data.setClassIndex(m_Data.numAttributes() - 1);
    }

    /**
     * Updates model using the given training message.
     *
     * @param message the message content
     * @param classValue the class label
     */
    public void updateData(String message, String classValue)
    {
// Make message into instance.
        Instance instance = makeInstance(message, m_Data);
// Set class value for instance.
        instance.setClassValue(classValue);
// Add instance to training data.
        m_Data.add(instance);
        m_UpToDate = false;

    }

    /**
     * Method that converts a text message into an instance.
     *
     * @param text the message content to convert
     * @param data the header information
     * @return the generated Instance
     */
    private Instance makeInstance(String text, Instances data)
    {
// Create instance of length two.
        Instance instance = new Instance(2);
// Set value for message attribute
        Attribute messageAtt = data.attribute("content");
        instance.setValue(messageAtt, messageAtt.addStringValue(text));
// Give instance access to attribute information from the dataset.
        instance.setDataset(data);
        return instance;
    }

    /**
     * Classifies a given message.
     *
     * @param message the message content
     * @throws Exception if classification fails
     */
    public void classifyMessage(String message) throws Exception
    {
// Check whether classifier has been built.
        if (m_Data.numInstances() == 0)
        {
            throw new Exception("No classifier available.");
        }
// Check whether classifier and filter are up to date.
        if (!m_UpToDate)
        {
// Initialize filter and tell it about the input format.
            m_Filter.setInputFormat(m_Data);
// Generate word counts from the training data.
            Instances filteredData = Filter.useFilter(m_Data, m_Filter);
// Rebuild classifier.
            m_Classifier.buildClassifier(filteredData);
            m_UpToDate = true;
        }

    }
}
