package org.soumyadev.crowdfundinnovativeworld.Utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.soumyadev.crowdfundinnovativeworld.Model.CustomCredDetails;
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private CustomCredDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        userDetails = new CustomCredDetails("testuser", "Funder", "ROLE_USER","");
    }

    @Test
    void testExtractUserId() {
        String token = generateTestToken();
        String userId = jwtUtil.extractUserId(token);
        assertEquals(userDetails.getUserId(), userId);
    }

    @Test
    void testExtractExpiration() {
        String token = generateTestToken();
        Date expirationDate = jwtUtil.extractExpiration(token);
        assertNotNull(expirationDate);
    }

    @Test
    void testExtractClaim() {
        String token = generateTestToken();
        String username = jwtUtil.extractClaim(token, claims -> claims.get("username", String.class));
        assertEquals(userDetails.getUserName(), username);

        String role = jwtUtil.extractClaim(token, claims -> claims.get("role", String.class));
        assertEquals(userDetails.getRole(), role);
    }

    @Test
    void testGenerateToken() {
        String token = jwtUtil.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    void testValidateTokenWithValidUserDetails() {
        String token = generateTestToken();
        assertTrue(jwtUtil.validateToken(token, userDetails));
    }

    @Test
    void testValidateTokenWithInvalidUserDetails() {
        String token = generateTestToken();
        CustomCredDetails invalidUserDetails = new CustomCredDetails("anotheruser", "Funder", "ROLE_ADMIN","");
        assertFalse(jwtUtil.validateToken(token, invalidUserDetails));
    }

    @Test
    void testValidateTokenWithUserId() {
        String token = generateTestToken();
        assertTrue(jwtUtil.validateToken(token, userDetails.getUserId()));
    }

    @Test
    void testValidateTokenWithInvalidUserId() {
        String token = generateTestToken();
        assertFalse(jwtUtil.validateToken(token, "invalidUserId"));
    }

    private String generateTestToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUserName());
        claims.put("role", userDetails.getRole());
        return jwtUtil.createToken(claims, userDetails.getUserId());
    }
}
