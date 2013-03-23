/*
 *Class which do all the required operations in database
 * storing, retrieving: sentiments,tweets,entities
 */
package sentimentanalysis.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sentimentanalysis.common.LocalTweet;
import sentimentanalysis.common.Sentiment;
import sentimentanalysis.common.TweetRankInfo;
import sentimentanalysis.common.User;
import twitter4j.Tweet;

/**
 *
 * @author maryger
 */
public class Operator implements OperatorInterface
{
    //Connection object with the database

    private         Connector   con;
    private         Connector   conLex;
    private static  Operator    instance;

    private Operator()
    {
        con     = new Connector("twitter_sentiment");
        conLex  = new Connector("twitter_lexicon");
    }
    
    
    /**
     *  Singleton Operator instance getter. 
     * 
     *  This prevents multiple connections to the same database, and makes it
     *   easier to get a DB operator reference.
     * 
     *  @return Operator instance.
     */
    public static Operator getInstance () 
    {
        if (instance == null)
            instance = new Operator();
        assert(instance != null);
        return instance;
    }
    

    /**
     * It stores a tweet(id,content,author,date,pagerank) 
     * @param con: A connection obj
     */
    public void storeTweet(Tweet tw)
    {
        long id = tw.getId();
        String content = tw.getText().replaceAll("'", "");
        String author = tw.getFromUser();
        Date date = (Date) tw.getCreatedAt();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(date);

        //TODO ADD THE PAGERANK FROM CAROLINE
        //Pagerank pgtw = new Pagerank(author);
        try
        {

            String query = "INSERT INTO tweets(id, content, author, date) VALUES(?,?,?,?)";

            //Get a preparedStatement object
            PreparedStatement stmt = (PreparedStatement) con.getCon().prepareStatement(query);

            // clear any previous parameter values
            stmt.clearParameters();
            //Insert some values into the table
            stmt.setLong(1, id);
            stmt.setString(2, content);
            stmt.setString(3, author);
            stmt.setString(4, currentTime);
            stmt.executeUpdate();


        } catch (SQLException ex)
        {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//end store tweet

    
    /**
     * Get the entityId from the database. If the entity is missing insert it
     * and return the new id
     * @return 
     */
    public long getEntityId(String entity) {
        long entityId = 0;
        try {
            String query = "SELECT id FROM entity " +
                                 "WHERE value = ?";
        
             PreparedStatement ps = (PreparedStatement) con.getCon().prepareStatement(query);

             ps.setString(1, entity);

             ResultSet rs = (ResultSet) ps.executeQuery();
             // if we've had this entity before get its id and see if we've saved 
             // tweets before. otherwise insert it so we don't have problems further on
             if (rs.next()) {
                 entityId = rs.getLong(1);
                 System.out.println("The entity id is: " + entityId);
             } else {
                 System.out.println("N-are");
                 query = "INSERT INTO entity(value, score) VALUES(?, ?)";
                 ps = con.getCon().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                 ps.setString(1, entity);
                 ps.setDouble(2, 0);
                 ps.executeUpdate();
                 rs = ps.getGeneratedKeys();
                 con.getCon().commit();
                 if (rs.next()) {
                     entityId = rs.getLong(1);
                     System.out.println("The entity id is: " + entityId);
                 } else {
                     System.out.println("Something screwey");
                 }
             }
        } catch (SQLException ex) {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entityId;
    }
    
    /**
     * Store a number of tweets. Inserting more tweets at once is less costly
     * than doing it for each in the list
     * @param list 
     */
    public void storeTweetBatch(LinkedList<LocalTweet> list, String entity) {
        String query = "INSERT IGNORE INTO  `tweets`                       "
                     + "             (id, content, author, date, pagerank) "
                     + "VALUES       (?,  ?,       ?,      ?,    ?)        ";
        try {
            int count                   = 0;
            PreparedStatement statement = con.getCon().prepareStatement(query);

            for (LocalTweet tweet : list) {
                ++count;
                statement.setLong  (1, tweet.getId());
                statement.setString(2, tweet.getText());
                statement.setString(3, tweet.getFromUser());
                statement.setString(4, tweet.getCreatedString());
                statement.setDouble(5, tweet.getPageRank());
                statement.addBatch();
                if (count % 1000 == 0) {
                    count = 0;
                    statement.executeBatch();
                }
            }
            statement.executeBatch();
            
         
            long entityId = getEntityId(entity);
            query = "INSERT INTO sentiments(tweet_id, entity_id, score) VALUES(?,?,?)";
            statement = con.getCon().prepareStatement(query);
            // finally insert the sentiment values in the db
            count = 0;
            for (LocalTweet tw : list) {
                ++count;
                long id = tw.getId();
                double sentimentScore = tw.getSentimentScore(entity);
                System.out.println(id + " " + entityId + " " + sentimentScore);
              

                statement.setLong(1, id);
                statement.setLong(2, entityId);
                statement.setDouble(3, sentimentScore);
                statement.addBatch();
                if (count % 1000 == 0) {
                    count = 0;
                    statement.executeBatch();
                }
            }
            System.out.println("count = " + count);
            // flush
            statement.executeBatch();
            
            con.getCon().commit();
        } catch(SQLException sex) {
            System.out.println("It's not saving because the following error occurs ");
            sex.printStackTrace();
        }
        
    }
    
    
        public LinkedList<String> getTweetsForTesting()
    {
        LinkedList<String> list = new LinkedList<String>();
        try
        {
            String query = "SELECT content FROM tweets  ";
            con.getCon().setAutoCommit(false);
            PreparedStatement ps = (PreparedStatement) con.getCon().prepareStatement(query);
            ResultSet rs = (ResultSet) ps.executeQuery();
            while (rs.next())
            {

                list.add(rs.getString(1));

            }

        } catch (SQLException ex)
        {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    
    /**
     * It retrieves a tweet based on a keyword from database
     * It gets the tweet which the key exists inside the content column
     * @param con
     * @param key: The keyword can be a general keyword e.g key= +greece -apple
     * @return LinkedList with retrieved tweets
     */
    public LinkedList<String> getTweets(String key)
    {
        LinkedList<String> list = new LinkedList<String>();
        try
        {
            String query = "SELECT content FROM tweets "
                    + "WHERE MATCH (content)  AGAINST ('" + key + "' IN BOOLEAN MODE);";
            con.getCon().setAutoCommit(false);
            PreparedStatement ps = (PreparedStatement) con.getCon().prepareStatement(query);
            ResultSet rs = (ResultSet) ps.executeQuery();
            while (rs.next())
            {

                list.add(rs.getString(1));

            }

        } catch (SQLException ex)
        {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    
    
    
    
    /**
     * Retrieves a list of LocalTweet objects. It joins  the tweets, entity 
     * and sentiments table and constructs the LocalTweet objects iteratively 
     */
    public LinkedList<LocalTweet> getLocalTweets(String key) {
        LinkedList<LocalTweet> list = new LinkedList<LocalTweet>();
        try
        {
            String query = 
                      "SELECT tweets.id, author, content, date, pagerank, "
                    + "       entity.value, sentiments.score, sentiments.id "
                    + "FROM tweets "
                    + "  JOIN sentiments ON (tweets.id = sentiments.tweet_id) "
                    + "  JOIN entity ON (sentiments.entity_id = entity.id) "
                    + "WHERE MATCH (content)  AGAINST ('" + key + "' IN BOOLEAN MODE) ORDER BY tweets.id;";
            System.err.println(query);
            con.getCon().setAutoCommit(false);
            PreparedStatement ps = (PreparedStatement) con.getCon().prepareStatement(query);
            ResultSet rs = (ResultSet) ps.executeQuery();
            while (rs.next())
            {
                Sentiment s = new Sentiment();
                s.setEntity(rs.getString(6));
                s.setScore(rs.getDouble(7));
                s.setId(rs.getLong(8));
                if (!list.isEmpty() && list.get(list.size() - 1).getId() == rs.getLong(1)) {
                    list.get(list.size() - 1).addSentiment(key, s);
                } else {
                    LocalTweet tweet = new LocalTweet();
                    tweet.setId(rs.getLong(1));
                    tweet.setAuthor(rs.getString(2));
                    tweet.setContent(rs.getString(3));
                    try {
                        tweet.setDate(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString(4)));
                    } catch (ParseException ex) {
                        Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tweet.setPageRank(rs.getLong(5));
                    tweet.addSentiment(key, s);
                    list.add(tweet);
                }
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;       
    }
 

    /**
     * It stores a sentiment (tweet_id,entity_id,sentiment,score)
     * //TODO CHECK LATER FUNCTIONALITY WHEN THE OTHERS PRODUCE DATA
     */
    public void storeSentiment(long tweet_id, long entity_id, double sentiment, double score)
    {
        try
        {
            String query = "INSERT INTO sentiments(tweet_id, entity_id, score) VALUES(?,?,?)";

            //Get a preparedStatement object
            PreparedStatement stmt = (PreparedStatement) con.getCon().prepareStatement(query);

            // clear any previous parameter values
            stmt.clearParameters();
            //Insert some values into the table
            stmt.setLong(1, tweet_id);
            stmt.setLong(2, entity_id);
            stmt.setDouble(3, score);
            stmt.executeUpdate();
        } catch (SQLException ex)
        {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param user 
     */
    public void storeUser(User user) {
        try {
            // Don't add a user more than once.
            if (getUser(user.getId()) != null) {
                return;
            }
            String query = "INSERT INTO users(id, followers_count,"
                    + "friends_count, statuses_count) VALUES(?,?,?,?)";
            
            PreparedStatement statement = (PreparedStatement) con.getCon().prepareStatement(query);
            
            statement.clearParameters();
            
            statement.setLong(1, user.getId());
            statement.setInt(2, user.getFollowersCount());
            statement.setInt(3, user.getFriendsCount());
            statement.setInt(4, user.getStatusesCount());
            
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE,
                    "Error while storing user info", ex);
        }
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    public User getUser(long id) {
        User user = null;
        try {
            String query = "SELECT followers_count,friends_count,statuses_count FROM users "
                    + "WHERE id = ?";

            PreparedStatement ps = (PreparedStatement) con.getCon().prepareStatement(query);

            ps.setLong(1, id);

            ResultSet rs = (ResultSet) ps.executeQuery();
            while (rs.next()) {
                user = new User(id, rs.getInt(1), rs.getInt(2), rs.getInt(3));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE,
                    "Error while retrieving user with id " + id, ex);
        }
        return user;
    }
    
    public TweetRankInfo getTweetRankInfo(long id) {
        TweetRankInfo tweetRankInfo = null;
        try {
            String query = "SELECT retweet_count,is_favorited FROM tweet_rank_info "
                    + "WHERE id = ?";

            PreparedStatement ps = (PreparedStatement) con.getCon().prepareStatement(query);

            ps.setLong(1, id);

            ResultSet rs = (ResultSet) ps.executeQuery();
            while (rs.next()) {
                tweetRankInfo = new TweetRankInfo(id, rs.getLong(1), rs.getBoolean(2));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE,
                    "Error while retrieving tweet rank info with id " + id, ex);
        }
        return tweetRankInfo;
    }
    
    public void storeTweetRankInfo(TweetRankInfo tweetRankInfo) {
        try {
            // Don't add a user more than once.
            if (getTweetRankInfo(tweetRankInfo.getId()) != null) {
                return;
            }
            String query = "INSERT INTO tweet_rank_info(id, retweet_count,"
                    + "is_favorited) VALUES(?,?,?)";
            
            PreparedStatement statement = (PreparedStatement) con.getCon().prepareStatement(query);
            
            statement.clearParameters();
            
            statement.setLong(1, tweetRankInfo.getId());
            statement.setLong(2, tweetRankInfo.getRetweetCount());
            statement.setBoolean(3, tweetRankInfo.isFavorited());
            
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE,
                    "Error while storing retweet rank info", ex);
        }
    }
    

    /**
     * It retrieves a sentiment based on a keyword(tweet id)
     * @param con
     * @param key tweet_id
     * @return LinkedList with sentiments (not sure about the format, check it later) 
     * //TODO CHECK LATER FUNCTIONALITY WHEN THE OTHERS PRODUCE DATA
     */
    public double getSentiment(long key)
    {
        double score = 0.0;

        try
        {
            String query = "SELECT score FROM sentiments "
                    + "WHERE tweet_id = ?";

            PreparedStatement ps = (PreparedStatement) con.getCon().prepareStatement(query);

            ps.setLong(1, key);

            ResultSet rs = (ResultSet) ps.executeQuery();
            while (rs.next())
            {

                score = rs.getDouble(1);

            }

        } catch (SQLException ex)
        {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return score;
    }

    /**
     * Fills the lexicon with data and sentiment scores in a separate DB
     * 
     */
    public void fillLexicon()
    {
        try
        {
            String filename1 = "C://Users//maryger//Documents//KTH//new IR project//documentation//lexicon//mpqa.lex";
            String filename2 = "C://Users//maryger//Documents//KTH//new IR project//documentation//lexicon//inquirer.lex";
            String filename3 = "C://Users//maryger//Documents//KTH//new IR project//documentation//lexicon//modifiers.txt";
            String tablename = "lexicon";
            String tablename2 = "modifiers";
            Statement stmt = (Statement) conLex.getCon().createStatement();

//
//            // file is terminated by \r\n, use this statement
//            stmt.executeUpdate("LOAD DATA INFILE '" + filename1 + "' INTO TABLE "
//                    + tablename + " FIELDS TERMINATED BY ':' LINES TERMINATED BY '\\r\\n' (word, sentiment)");
//            stmt.executeUpdate("LOAD DATA INFILE '" + filename2 + "' INTO TABLE "
//                    + tablename + " FIELDS TERMINATED BY ':' LINES TERMINATED BY '\\r\\n' (word, sentiment)");
             stmt.executeUpdate("LOAD DATA INFILE '" + filename3 + "' INTO TABLE "
                    + tablename2 + " FIELDS TERMINATED BY ':' LINES TERMINATED BY '\\r\\n' (modifier, value)");
   
        } catch (SQLException ex)
        {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    /**
     *  Retrieve the entire lexicon from the database.
     * 
     *  These are key-value pairs of words and their sentimental value.
     */
    public HashMap<String,Double> getLexicon () throws SQLException {
        HashMap<String,Double> lexicon   = new HashMap<String,Double>();
        String                 query     = "SELECT `word`, `sentiment` FROM `lexicon`";
        PreparedStatement      statement = conLex.getCon().prepareStatement(query);
        ResultSet              result    = statement.executeQuery();

        while (result.next())
            lexicon.put(result.getString(1), result.getDouble(2));
        
        return lexicon;
    }
    

    /**
     * It stores an entity(id,value,score)
     * //TODO CHECK again functionality and format
     */
    public void storeEntity(String value, double score)
    {

        try
        {
            String query = "INSERT INTO entity(value,score) VALUES(?,?)";

            //Get a preparedStatement object
            PreparedStatement stmt = (PreparedStatement) con.getCon().prepareStatement(query);

            // clear any previous parameter values
            stmt.clearParameters();
            //Insert some values into the table
            stmt.setString(1, value);
            stmt.setDouble(2, score);
            stmt.executeUpdate();
        } catch (SQLException ex)
        {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    /**
     * It returns the score of a single entity based on the key
     * @param key (the entity value)
     * @return the score of this entity (e.g. apple has value 2)
     * //TODO CHECK HOW THE key IS GONNA BE SEARCHED 
     */
    public double getEntityScore(String key)
    {

        double score = 0.0;



        return score;
    }

    /**
     * It stores an entity and all the related words
     * @param entity
     * @param related_word
     * @param score 
     */
    public void storeEntityRelatedWord(String entity, String related_word, double score)
    {

        try
        {
            String query = "INSERT INTO entity_relatedword(entity, related_word, score) VALUES(?,?,?)";

            //Get a preparedStatement object
            PreparedStatement stmt = (PreparedStatement) con.getCon().prepareStatement(query);

            // clear any previous parameter values
            stmt.clearParameters();
            //Insert some values into the table
            stmt.setString(1, entity);
            stmt.setString(2, related_word);
            stmt.setDouble(3, score);
            stmt.executeUpdate();
        } catch (SQLException ex)
        {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    /**
     * It returns a list of related words with the entity
     * @param key_entity
     * @return a List of the related words and the relational score
     */
    public HashMap<String,Double> getRelatedWord(String key_entity)
    {

       HashMap<String,Double> list = new HashMap<String,Double>();
        try
        {
            String query = "SELECT related_word,relation_score FROM entity_relatedword "
                    + "WHERE MATCH (entity)  AGAINST ('" + key_entity + "' IN BOOLEAN MODE);";
            con.getCon().setAutoCommit(false);
            PreparedStatement ps = (PreparedStatement) con.getCon().prepareStatement(query);
            ResultSet rs = (ResultSet) ps.executeQuery();
            while (rs.next())
            {

                list.put(rs.getString(1),rs.getDouble(2));

            }

        } catch (SQLException ex)
        {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;


    }
    
    public HashMap<String,Double> getLexiconEntries(String key)
    {

       HashMap<String,Double> list = new HashMap<String,Double>();
        try
        {
            String query = "SELECT word,sentiment FROM entity_relatedword "
                    + "WHERE MATCH (word)  AGAINST ('" + key + "' IN BOOLEAN MODE);";
            con.getCon().setAutoCommit(false);
            PreparedStatement ps = (PreparedStatement) con.getCon().prepareStatement(query);
            ResultSet rs = (ResultSet) ps.executeQuery();
            while (rs.next())
            {

                list.put(rs.getString(1),rs.getDouble(2));

            }

        } catch (SQLException ex)
        {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;


    }

    /**
     *Closes the connection with sentimentDB 
     */
    public void closeCon()
    {

        con.closeCon();
    }

    /**
     * Closes the connection with the lexiconDB
     */
    public void closeConLex()
    {

        conLex.closeCon();
    }

    public HashMap<String, Double> getSmileys()
    {
           HashMap<String,Double> list = new HashMap<String,Double>();
        try
        {
            String query = "SELECT smiley,value FROM smileys;";
            con.getCon().setAutoCommit(false);
            PreparedStatement ps = (PreparedStatement) conLex.getCon().prepareStatement(query);
            ResultSet rs = (ResultSet) ps.executeQuery();
            while (rs.next())
            {

                list.put(rs.getString(1),rs.getDouble(2));

            }

        } catch (SQLException ex)
        {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    
    public HashMap<String, Double> getModifiers()
    {
           HashMap<String,Double> list = new HashMap<String,Double>();
        try
        {
            String query = "SELECT modifier,value FROM modifiers;";
            con.getCon().setAutoCommit(false);
            PreparedStatement ps = (PreparedStatement) conLex.getCon().prepareStatement(query);
            ResultSet rs = (ResultSet) ps.executeQuery();
            while (rs.next())
            {

                list.put(rs.getString(1),rs.getDouble(2));

            }

        } catch (SQLException ex)
        {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    

    public HashMap<String, Double> getNegations()
    {
          HashMap<String,Double> list = new HashMap<String,Double>();
        try
        {
            String query = "SELECT negation,value FROM negations;";
            con.getCon().setAutoCommit(false);
            PreparedStatement ps = (PreparedStatement) conLex.getCon().prepareStatement(query);
            ResultSet rs = (ResultSet) ps.executeQuery();
            while (rs.next())
            {

                list.put(rs.getString(1),rs.getDouble(2));

            }

        } catch (SQLException ex)
        {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public long getIdOfLatestTweetForThisEntity(String entity) {
        long result = 0;
        try {
            String query = "SELECT MAX(tweet_id) FROM sentiments WHERE entity_id = ?";
            PreparedStatement ps = (PreparedStatement) con.getCon().prepareStatement(query);
            long entityId = getEntityId(entity);
            ps.setLong(1, entityId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getLong(1);
            }
         } catch (SQLException ex) {
           Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

 
}
