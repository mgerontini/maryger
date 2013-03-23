/*
 * SentimentAnalysisApp.java
 */
package sentimentanalysis;

import sentimentanalysis.view.SentimentAnalysisView;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import sentimentanalysis.endpoint.Server;


/**
 * The main class of the application.
 */
public class SentimentAnalysisApp extends SingleFrameApplication {

    private         SentimentAnalysisView   view;
    private         Server                  endpoint;
    private static  SentimentAnalysisApp    instance;
    
    
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        assert(instance == null);
        view        = new SentimentAnalysisView(this);
        endpoint    = new Server();
        show(view);
        endpoint.start();
        instance    = this;
    }
    

    /**
     * A convenient static getter for the application instance.
     * @return the instance of SentimentAnalysisApp
     */
    public static SentimentAnalysisApp getApplication() {
        return instance;
    }

    
    public static void setStatus (String message) {
        if (getView() != null)
            getView().setStatus(message);
    }

    
    public static SentimentAnalysisView getView () {
        if (instance != null) return instance.view;
        return null;
    }
    
    
    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(SentimentAnalysisApp.class, args);
    }
}