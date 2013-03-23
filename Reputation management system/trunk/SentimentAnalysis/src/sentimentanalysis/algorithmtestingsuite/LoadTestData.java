/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sentimentanalysis.algorithmtestingsuite;

import com.csvreader.CsvReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maryger
 */
public class LoadTestData
{

   LinkedList<String> contents;
    LinkedList<String> classes;

    public LoadTestData()
    {
        try
        {
            contents = new LinkedList<String>();
            classes = new LinkedList<String>();
            
            CsvReader data = new CsvReader("../classified data/sampleoftestdata.csv");


            while (data.readRecord())
            {

                String class_id = data.get(0);

                String tweet_content = data.get(5);
                contents.add(tweet_content);
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
            Logger.getLogger(LoadTestData.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public LinkedList<String> getClasses()
    {
        return classes;
    }

    public LinkedList<String> getContents()
    {
        return contents;
    }
    
}
