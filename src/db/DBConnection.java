package db;

import java.sql.Connection;

public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;


    private DBConnection(){

    }

    public void init(Connection connection){
        if(this.connection==null){
            this.connection = connection;
        }else if(this.connection != connection){
            throw new RuntimeException("The connection has been already initialized..");
        }
    }

    public static DBConnection getInstance(){
        return dbConnection==null? dbConnection = new DBConnection(): dbConnection;
    }

    public Connection getConnection(){
        if(connection==null){
            throw new RuntimeException("Init the connection first..");
        }
        return connection;
    }
}

/*TODO:
   First review the previous created questions
   finalize the upto date note
   Partially
   => Thursday interview questions
   => Friday interview questions
*  */