package sentimentanalysis.view;

import com.googlecode.charts4j.AbstractGraphChart;
import com.googlecode.charts4j.Data;
import java.awt.Graphics;
import java.net.URL;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import sentimentanalysis.common.LocalTweet;
import sentimentanalysis.search.Query;

/**
 *  Chart Panel
 * 
 *  Base class for JPanels that display charts.
 * 
 *  @author Matthijs
 */
abstract public class ChartPanel extends TabPanel {

    protected LinkedList<LocalTweet>  results;
    protected Query                   query;
    protected Graphics                graphics;

    
    /**
     *  Set the results to visualize. 
     * 
     *  This expects both a LinkedList of LocalTweet objects (our results), as
     *  well as the search query that generated them.
     * 
     *  @param results
     *  @param query 
     */
    public void setResults (LinkedList<LocalTweet> results, Query query) {
        this.results    = results;
        this.query      = query;
    }
    
    
    /**
     *  Display a chart at the given (vertical) position.
     * 
     *  Provided with a chart object and an index, this will display the chart
     *  in this JPanel directly, with some nice background behind it for good
     *  measure. The index determines the vertical location of the chart: index
     *  zero means the chart is on top, every subsequent index puts the chart
     *  one position further down.
     *  
     *  @param chart
     *  @param index 
     */
    protected void displayChart (AbstractGraphChart chart, int index) {
        chart.setSize((int) (0.6 * width), (int) (0.21 * height));
        String url = chart.toURLString();
        
        int x = (int) (0.15 * width);
        int y = (int) ((0.15 + (0.25 * index)) * height);
        int w = (int) (0.7  * width);
        int h = (int) (0.23 * height);
        graphics.setColor(java.awt.Color.WHITE);
        graphics.fillRoundRect(x, y, w, h, 20, 20);

        try {
            ImageIcon image = new ImageIcon(ImageIO.read(new URL(url)));
            image.paintIcon(this, graphics, (int) (x + 0.05 * width), (int) (y + 0.01 * height));
        } catch (Exception e) {
            System.err.println("Could not display URL string.");
            e.printStackTrace();
        }
    }
    
    
    /**
     *  Generate a data object from a set of tweets. Data objects are fed into
     *  the Google Charts API to generate the charts.
     * 
     *  Define a interval for which data points are generated. Without this, we
     *  risk generating too many data points, which will be rejected by the 
     *  charts API.
     */
    protected Data generateData (LinkedList<LocalTweet> tweets, String query) {
        LinkedList<Double> values   = new LinkedList<Double>();
        double             max      = 0.0,
                           score    = 0.0;
        int                interval = Math.max(1, (int) tweets.size() / 100);
        
        // Extract tweet data:
        for (int i = 0; i < tweets.size(); i++) {
            score += tweets.get(i).getSentimentScore(query);
            
            // If this score exceeds the max score recorded so far:
            if (Math.abs(score) > max)
                max = Math.abs(score);
            
            // Only add values at interval number of tweets:
            if (i % interval == 0)
                values.add(score);
        }

        // Normalize values.
        for (int i = 0; i < values.size(); i++) 
            values.set(i, 51 + values.get(i) * Data.MAX_VALUE / (max * 2.1));
        
        // Create and return a new Data object containing these values:
        return Data.newData(values);
    }
}