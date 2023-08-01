package org.soumyadev.crowdfundinnovativeworld.Utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordEncrypterTest {

    @Test
    void testHashPassword() {
        // Given
        String plainPassword = "testPassword";

        // When
        String hashedPassword = PasswordEncrypter.hashPassword(plainPassword);

        // Then
        assertNotNull(hashedPassword);
        assertNotEquals(plainPassword, hashedPassword); // Hashed password should not be the same as the plain password
    }

    @Test
    void testCheckPasswordSuccess() {
        // Given
        String plainPassword = "testPassword";
        String hashedPassword = PasswordEncrypter.hashPassword(plainPassword);

        // When
        boolean result = PasswordEncrypter.checkPassword(plainPassword, hashedPassword);

        // Then
        assertTrue(result);
    }

    @Test
    void testCheckPasswordFailure() {
        // Given
        String plainPassword = "testPassword";
        String wrongPassword = "wrongPassword";
        String hashedPassword = PasswordEncrypter.hashPassword(plainPassword);

        // When
        boolean result = PasswordEncrypter.checkPassword(wrongPassword, hashedPassword);

        // Then
        assertFalse(result);
    }
}
