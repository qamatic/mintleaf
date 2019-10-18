package org.qamatic.mintleaf.postgres;

import org.junit.*;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.Assert.assertTrue;


public class PostgresTest {

    @Rule
    public PostgreSQLContainer postgres = new PostgreSQLContainer()
            .withDatabaseName("mydb")
            .withUsername("user")
            .withPassword("secret");

    @Before
    public void before() {
        postgres.start();
    }

    @After
    public void after() {
        postgres.stop();
    }

    @Test
    public void testConnect(){
        System.out.printf("postgres db running, db-name: '%s', user: '%s', jdbc-url: '%s'%n ",
                postgres.getDatabaseName(),
                postgres.getUsername(),
                postgres.getJdbcUrl());
        assertTrue(postgres.isRunning());
    }
}
