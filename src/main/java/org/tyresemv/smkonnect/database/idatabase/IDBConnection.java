package org.tyresemv.smkonnect.database.idatabase;

import java.sql.Connection;

public interface IDBConnection {

    String ConnectionString = "jdbc:sqlite:src/main/resources/database/smkonnect.db";
    Connection getDbContext();

    void CreateUserTable();

    void CreateSessionTable();

    void CreateCredentialsTable();
}
