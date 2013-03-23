package sentimentanalysis.endpoint;

import javax.xml.ws.Endpoint;

/**
 *  The Endpoint package creates a SOAP endpoint. In other words, this is
 *  a place where our client can talk to. Incoming search requests will be
 *  handled by the implementor defined here.
 *
 *  Since we want to have this running continously, start it in a separate thread.
 *
 *  @author Matthijs
 */
public class Server extends Thread {

    
    /**
     *  Start the Server thread.
     *
     *  Note: this automatically generates a WSDL for a connecting SOAP client
     *  at http://localhost:9000/SoapContext/SoapPort?WSDL
     *
     *  Fun fact: you can check this out in your browser!
     */
    public void run ()  {
        System.out.println("Starting Server");
        Object implementor      = new Implementor();
        String address          = "http://0.0.0.0:9000/SoapContext/SoapPort";
        Endpoint.publish(address, implementor);
    }
}