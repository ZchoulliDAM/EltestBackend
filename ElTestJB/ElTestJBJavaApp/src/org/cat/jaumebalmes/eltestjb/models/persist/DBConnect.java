/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cat.jaumebalmes.eltestjb.models.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Zaid
 */
public class DBConnect {
    static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String PROTOCOL = "jdbc:mysql:";
    static final String HOST = "remotemysql.com:3306";
    static final String BD_NAME = "TORU3mhnTP";
    static final String USER = "TORU3mhnTP";
    static final String PASSWORD = "I1PjzNUQIx";
    
    public static void loadDriver() throws ClassNotFoundException {
        Class.forName(DRIVER);
    }
    
    
     /**
     * gets and returns a connection to database
     *
     * @return connection
     * @throws java.sql.SQLException
     */
    
    public static Connection getConnection() throws SQLException {
        final String BD_URL = String.format("%s//%s/%s", PROTOCOL, HOST, BD_NAME);
        Connection conn=null;
        conn = DriverManager.getConnection(BD_URL, USER, PASSWORD);
        return conn;
    }
}
