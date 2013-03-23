package sentimentanalysis.view;

import com.googlecode.charts4j.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import sentimentanalysis.SentimentAnalysisApp;
import sentimentanalysis.common.LocalTweet;
import sentimentanalysis.twitterapi.TweetOperator;

/**
 *  Results Panel
 * 
 *  This is in essence a JPanel, used to display results. Thus, it requires
 *  results to visualize much of anything, although the basic background will
 *  always be displayed, courtesy of the TabPanel it extends.
 * 
 *  External classes should not have to worry about interacting with this class:
 *  all that is handled directly by the SWING framework. The only public method
 *  is therefore the method that sets the data which these results are based on.
 * 
 *  @author Matthijs
 */
public class ResultsPanel extends ChartPanel {
    
    private JTextArea              topTweetContent;
    private JLabel                  topTweetAuthor;
    private LinkedList<TweetButton> tweetButtons;
    
    
    /**
     *  Paint this component.
     * 
     *  Every time the visual aspect of this component changes, this method is
     *  called. Thus, when the user resizes the window, the window changes, or 
     *  it is simple minimized, etc, the paintComponent method is called. This
     *  allows us to always show the graphs with a correct aspect ratio. Thus,
     *  if we have data associated with this view, repaint the visualizations
     *  of those graphs.
     * 
     *  @param g 
     */
    @Override protected void paintComponent (Graphics g) { 
        super.paintComponent(g);
        
        Dimension size  = this.getSize();
        width           = size.width;
        height          = size.height;
        graphics        = g;
        
        if (results == null)
            return;
        
        JLabel title    = SentimentAnalysisApp.getView().resultTitle;
        title.setText("Trends for \"" + query.getRequest() + "\"");
        title.setLocation(0, (int) (0.085 * height));
        
        showTweetBreakdown();
        showSentimentOverTime();
        showTopTweets();
    }
    
    
    /**
     *  Show a tweet sentiment breakdown.
     *  
     *  This generates a pie chart displaying the number of positive, neutral
     *  and negative tweets in our result set. No further details are given.
     */
    private void showTweetBreakdown () {
        int positive    = 0,
            negative    = 0,
            neutral     = 0;
        
        // Count the number of tweets in each category: 
        for (LocalTweet tweet : results) {
            if (tweet.getSentimentScore(query.getRequest()) > 0)
                positive++;
            if (tweet.getSentimentScore(query.getRequest()) < 0)
                negative++;
            if (tweet.getSentimentScore(query.getRequest()) == 0)
                neutral++;
        }
        
        // Determine the fraction of positive / negative / neutral tweets:
        int total       = Math.max(1, positive + negative + neutral);
        positive        = (int) (100 * positive / total);
        negative        = (int) (100 * negative / total);
        neutral         = (int) (100 * neutral  / total);        
        
        // Create pie-chart slices for each fraction:
        Slice s1        = Slice.newSlice(positive, Color.BLUE,   "Positive", "Positive (" + positive + "%)");
        Slice s2        = Slice.newSlice(negative, Color.RED,    "Negative", "Negative (" + negative + "%)");
        Slice s3        = Slice.newSlice(neutral,  Color.ORANGE, "Neutral",  "Neutral  (" + neutral  + "%)");
        
        // Generate the chart:
        PieChart chart  = GCharts.newPieChart(s1, s2, s3); 
        chart.setTitle("Sentiment breakdown by tweets:", Color.BLACK, 16); 
        chart.setThreeD(true); 
        displayChart(chart, 0);
    }
    
    
    /**
     *  Show the cumulative sentiment progress over time.
     * 
     *  For a time-ordered list of tweets, this will display the progression of
     *  sentiment over time; starting from zero, each positive tweet increases
     *  the sentiment value, whereas each negative tweet decreases it. Given a
     *  long enough period (ie, enough tweets to parse), this can visualize 
     *  changes in how people think about the search query.
     */
    private void showSentimentOverTime () {
        LinkedList<String> dates  = new LinkedList();
        
        // Extract some tweet dates:
        for (int i = 0; i < results.size(); i+= results.size() / 4)
            dates.add(results.get(i).getCreatedString("yy-MM-dd"));
        
        Plot plot = Plots.newPlot(generateData(results, query.getRequest()));
        plot.setColor(Color.BLACK);
        
        // Create a chart, and add the plot:
        LineChart chart = GCharts.newLineChart(plot);
        chart.addHorizontalRangeMarker(0, 50, Color.newColor(Color.RED, 10));
        chart.addHorizontalRangeMarker(50, 100, Color.newColor(Color.BLUE, 10));
        chart.setTitle("Sentiment progress over time:", Color.BLACK, 16);
        chart.addYAxisLabels(AxisLabelsFactory.newAxisLabels("Negative", "Neutral", "Positive"));
        chart.addXAxisLabels(AxisLabelsFactory.newAxisLabels(dates));
        displayChart(chart, 1);
    }
    
    
    /**
     *  Show the most influential tweets.
     * 
     *  These are the tweets with the greatest positive or negative net
     *  sentiment score (taking into account page rank, if possible).
     */
    private void showTopTweets () {
        int x = (int) (0.15 * width);
        int y = (int) (0.72 * height);
        int w = (int) (0.7  * width);
        int h = (int) (0.18 * height);
        graphics.setColor(java.awt.Color.WHITE);
        graphics.fillRoundRect(x, y, w, h, 20, 20);
        
        initializeTweetLabels();
        LinkedList<LocalTweet> mostPositive = TweetOperator.getTopPositive(query.getRequest(), 8 , results);
        LinkedList<LocalTweet> mostNegative = TweetOperator.getTopNegative(query.getRequest(), 8 , results);        
        
        graphics.setColor(java.awt.Color.GRAY);
        for (int i = 0; i < Math.min(8, mostPositive.size()); i++) {
            TweetButton current  = tweetButtons.get(i);
            int labelX      = (int) ((0.3 + 0.05 * i) * width);
            int labelY      = (int) (0.68 * height);
            current.setBounds(labelX, labelY, 25, 25);
            current.setTweet(mostPositive.get(i));
        }
        for (int i = 0; i < Math.min(8, mostNegative.size()); i++) {
            TweetButton current  = tweetButtons.get(i + 10);
            int labelX      = (int) ((0.3 + 0.05 * i) * width);
            int labelY      = (int) (0.64 * height);
            current.setBounds(labelX, labelY, 25, 25);
            current.setTweet(mostNegative.get(i));            
            
        }        
    }
    
    
    protected void showTopTweet (LocalTweet tweet) {
        topTweetContent.setText(tweet.getText());
        topTweetAuthor.setText(tweet.getFromUser());
    }
    
    
    
    /**
     *  Initialize the JLabel components that display the top tweets content
     *  and author.
     * 
     *  Regardless of their state, this will also position the labels correctly
     *  relative to the rest of the panel.
     */
    private void initializeTweetLabels () {
        if (topTweetContent == null) {
            tweetButtons     = new LinkedList<TweetButton>();
            for (int i = 0; i < 20; i++) {
                tweetButtons.add(new TweetButton(this, i < 10));
                this.add(tweetButtons.get(i));
            }
            
            topTweetContent = new JTextArea();
            topTweetAuthor  = new JLabel();
            topTweetContent.setForeground(java.awt.Color.black);
            topTweetAuthor.setForeground(java.awt.Color.blue);            
            topTweetContent.setFont(new Font("Verdana", Font.PLAIN, 16));
            topTweetAuthor.setFont(new Font("Verdana", Font.ITALIC, 14));
            this.add(topTweetContent);
            this.add(topTweetAuthor);
            
            topTweetContent.setLineWrap(true);
            topTweetContent.setAlignmentX(CENTER_ALIGNMENT);
        }
        
        topTweetContent.setBounds((int) (0.2 * width), (int) (0.73 * height), 
                                  (int) (0.6 * width), (int) (0.14 * height));
        topTweetAuthor.setBounds((int) (0.6 * width), (int) (0.85 * height), 
                                 (int) (0.2 * width), (int) (0.05  * height));
    }
}