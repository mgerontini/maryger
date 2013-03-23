/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sentimentanalysis.algorithmtestingsuite;

/**
 *
 * @author maryger
 */
public class Main implements Constants
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        TesterSuite ts =  new TesterSuite();
        ts.doTesting(USE_BAYES, "nike");
        System.out.println(TesterSuite.TP);
        System.out.println(TesterSuite.TN);
        System.out.println(TesterSuite.FP);
        System.out.println(TesterSuite.FN);
        System.out.println("precision: "+TesterSuite.getPrecision()+" recall:"+TesterSuite.getRecall());
    }
}
