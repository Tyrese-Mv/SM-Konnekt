package org.tyresemv.smkonnect.models;

public record User(String userID,
                   String Name,
                   String Surname,
                   String email_address,
                   String cellphone_number,
                   boolean confirmed_email) {
}
