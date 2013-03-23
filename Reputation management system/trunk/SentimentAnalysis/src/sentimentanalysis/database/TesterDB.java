/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sentimentanalysis.database;

import java.util.HashMap;

/**
 *
 * @author maryger
 */
public class TesterDB
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
       Operator db = Operator.getInstance();
       HashMap<String,Double> test = new HashMap<String,Double> ();
       test=db.getRelatedWord("apple");
       for(String key:test.keySet()){
       
       System.out.println("key: "+key+" value: "+test.get(key));
       
       }
      db.fillLexicon();
    }
}
