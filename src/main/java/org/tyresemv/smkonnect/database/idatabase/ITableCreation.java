package org.tyresemv.smkonnect.database.idatabase;

public interface ITableCreation {
    void CreateUserTable();

    void CreateSessionTable();

    void CreateCredentialsTable();
    void CreateSocialMediaTokensTable();
}
