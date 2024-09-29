package org.tyresemv.smkonnect.database;

import org.tyresemv.smkonnect.database.idatabase.IDBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect implements IDBConnection {

    private Connection dbConnection;

    private static DbConnect dbInstance;

    private DbConnect() throws SQLException {
        this.dbConnection = DriverManager.getConnection(IDBConnection.ConnectionString);
    }

    public static DbConnect getInstance() throws SQLException {
        if (dbInstance == null){
            dbInstance = new DbConnect();
        }
        return dbInstance;
    }

    @Override
    public Connection getDbConnection() {
        return dbInstance.dbConnection;
    }
}
