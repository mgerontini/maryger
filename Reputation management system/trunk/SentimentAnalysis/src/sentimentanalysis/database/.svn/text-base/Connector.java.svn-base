/*
 * Class which connects with the MySQL server
 * 
 */
package sentimentanalysis.database;

/**
 *
 * @author maryger
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connector{
    
    
   private Connection con; 
    
   /**
    * Constructor which does the connection with the database
    */ 
    public Connector(String nameDB)
    {
        try
        {
            //Register the JDBC driver for MySQL.
            Class.forName("com.mysql.jdbc.Driver");
            //Define URL of database server for
            // database named mysql on the localhost
            // with the default port number 3306.
            String url =
                    "jdbc:mysql://localhost:3306/"+nameDB;
            con =
                    DriverManager.getConnection(
                    url, "root", "smurf");
            //Display URL and connection information
            System.out.println("URL: " + url);
            System.out.println("Connection: " + con);
        } catch (SQLException ex)
        {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @return a Connection object with the database
     */
    public Connection getCon(){
    return con;
    }
    public void closeCon(){
        try
        {
            con.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 

}//end class Jdbc11
