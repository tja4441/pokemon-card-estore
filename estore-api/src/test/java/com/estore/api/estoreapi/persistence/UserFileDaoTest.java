package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test the User File DAO class
 * 
 * @author Timothy Avila
 */
@Tag("Persistence-tier")
public class UserFileDaoTest {
    UserFileDao userFileDao;
    User[] testUsers;
    ObjectMapper mockObjectMapper;

    @BeforeEach
        public void setupUserFileDao() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        this.mockObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        testUsers = new User[5];
        testUsers[0] = new User(2,"Tim",new ShoppingCart());
        testUsers[1] = new User(3, "Zach",new ShoppingCart());
        testUsers[2] = new User(4,"Daniel",new ShoppingCart());
        testUsers[3] = new User(5, "Gabe",new ShoppingCart());
        testUsers[4] = new User(6, "Jensen", new ShoppingCart());
    }
}

