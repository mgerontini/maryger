package sentimentanalysis.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Matthijs
 */
public class TabPanel extends JPanel {
    
    
    protected int width   =   0;
    protected int height  =   0;
    
    @Override 
    protected void paintComponent (Graphics g) { 
        
        
        Dimension size  = this.getSize();
        width           = size.width;
        height          = size.height;
        
        paintBackgroundBlue(g);
        paintBorderWhite(g);
        paintInnerBorder(g);
    }
    
    
    private void paintInnerBorder (Graphics g) {
        g.setColor(new Color(255, 255, 255, 125));

        int x  = width   / 18;
        int y  = height  / 18;
        int w  = width   / 32;
        int h  = height  / 30;
        
        g.fillRect(x + w, y, width - 2*x - 2*w, h);                 // Top
        g.fillRect(x, y + h, w, height - 2*y - 2*h);                // Left
        g.fillRect(x + w, height - y - h, width - 2*x - 2*w, h);    // Bottom
        g.fillRect(width - x - w, y + h, w, height - 2*y - 2*h);    // Right
        
        g.fillArc(x, y, 2 * w, 2 * h, 180, -90);                    // Top-left
        g.fillArc(width - x - 2*w, y, 2 * w, 2 * h, 0,   90);       // Top-Right
        g.fillArc(x, height - y -2*h, 2 * w, 2 * h, 180, 90);       // Bottom-left
        g.fillArc(width - x - 2*w, height - y - 2*h, 2 * w, 2 * h, 0, -90);    // Top-left
    }
    
    
    private void paintBorderWhite (Graphics g) {
        g.setColor(Color.WHITE);
        int w = width   / 30;
        int h = height  / 30;
        g.fillRect(0, 0, w, height);
        g.fillRect(0, 0, width, h);
        g.fillRect(width - w, 0, w, height);
        g.fillRect(0, height - h, width, h);
        
        // Clear a rectangle at each of the four corner:
        g.fillRect(w, h, w, h);
        g.fillRect(width - (2 * w), h, w, h);
        g.fillRect(width - (2 * w), height - (2 * h), w, h);
        g.fillRect(w, height - (2 * h), w, h);
        
        // Corner top left, top right.
        g.setColor(new Color(103,202,234));
        g.fillArc(w, h, 2 * w, 2 * h, 180, -90);
        g.fillArc((width - 3*w),h,(2 * w), (2 * h), 0, 90);
        
        // Corner bottom right, bottom left.
        g.setColor(new Color(62,189,222));
        g.fillArc((width - 3 * w), (height - 3*h), (2 * w), (2 * h), 0, -90);
        g.fillArc(w, (height - 3 * h), (2 * w), (2 * h), 180, 90);
        
        
        g.setColor(new Color(0,0,0,25));
        g.drawRoundRect(w, h, width - (2*w), height - (2*h), (2*w), (2*h));
        
    }
    
    
    private void paintBackgroundBlue (Graphics g) {
        g.setColor(new Color(62,189,222));
        g.fillRect(0, 0, width, height);
        for (int i = 0; i < 30; i++) {
            g.setColor(new Color(103,202,234, 10 + (8 * i)));            
            g.fillOval(-(width / 2), -(height / 2), width * 2, (height / 1) - i);
        }
        g.setColor(new Color(103,202,234));
        g.fillOval(-(width / 2), -(height / 2), width * 2, (height / 1) - 20);
        
    }
}
