package org.soumyadev.crowdfundinnovativeworld.Utils;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.soumyadev.crowdfundinnovativeworld.Model.CustomCredDetails;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class JwtUtilTest {
    @Mock
    private CustomCredDetails userDetails;

    @InjectMocks
    private JwtUtil jwtUtil;

    private String testToken;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        testToken = jwtUtil.generateToken(userDetails);
    }

    @Test
    public void testExtractUsername() {
        String username = jwtUtil.extractUserId(testToken);
        Assertions.assertEquals(userDetails.getUserId(), username);
    }

    @Test
    public void testExtractExpiration() {
        Date expiration = jwtUtil.extractExpiration(testToken);
        Assertions.assertTrue(expiration.after(new Date()));
    }

    @Test
    public void testGenerateToken() {
        when(userDetails.getUserId()).thenReturn("testuser");
        String token = jwtUtil.generateToken(userDetails);
        Assertions.assertNotNull(token);
    }

//    @Test
//    public void testValidateTokenValid() {
//        when(userDetails.getUsername()).thenReturn("testuser");
//        Boolean isValid = jwtUtil.validateToken(testToken, userDetails);
//        Assertions.assertTrue(isValid);
//    }

    @Test
    public void testValidateTokenInvalid() {
        when(userDetails.getUserId()).thenReturn("invaliduser");
        Boolean isValid = jwtUtil.validateToken(testToken, userDetails);
        Assertions.assertFalse(isValid);
    }
}
