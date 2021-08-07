package com.patikaclone.Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DBConnector {
    private Connection c=null;


    public  Connection  connectDB(){

        try{
           this.c= DriverManager.getConnection(Config.DB_URL,
                    Config.DB_USERNAME, Config.DB_PASSWORD);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return this.c;

    }


    public static Connection getInstance(){
        DBConnector db=new DBConnector();
        return db.connectDB();
    }

}
