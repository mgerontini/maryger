package sentimentanalysis.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import sentimentanalysis.common.LocalTweet;

/**
 *  A Tweet Button is a visual element used to display a tweet. That is, when
 *  a mouse-over event occurs, the tweet associated with this button must be
 *  displayed in a pre-determined location. 
 * 
 *  This is a helper class for the ResultsPanel, which uses TweetButtons to 
 *  display a top-10 positive and negative tweets.
 * 
 *  @author Matthijs
 */
public class TweetButton extends JButton implements ActionListener {
    
    private LocalTweet      tweet;
    private ResultsPanel    parent;
    private boolean         positive;
    
    
    public TweetButton (ResultsPanel parent, boolean positive) {
        super();
        this.parent     = parent;
        this.positive   = positive;
        this.setBorderPainted(false);
        this.setBackground(Color.gray);
        this.setForeground(Color.white);
        this.setOpaque(false);
        this.addActionListener(this);
    }
    
    
    @Override public void paintComponent (Graphics g) {
        if (positive)
            g.setColor(Color.BLUE);
        else
            g.setColor(Color.RED);
        
        g.fill3DRect(0, 0, 19, 19, true);
        g.setColor(Color.WHITE);
        
        if (positive)
            g.drawString("+", 7, 12);
        else
            g.drawString("-", 8, 12);
    }
    
    
    public void setTweet (LocalTweet tweet) {
        this.tweet = tweet;
    }
    
    
    
    public void actionPerformed(ActionEvent ae) {
        System.out.println("Click?");
        System.out.println(this.tweet.getText());
        
        parent.showTopTweet(this.tweet);
  }    
}