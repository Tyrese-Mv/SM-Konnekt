package org.tyresemv.smkonnect.models;

import java.sql.Timestamp;

public record TokenData(String accessToken, String refreshToken, Timestamp expiry) {
}
