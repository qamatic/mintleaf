package org.qamatic.mintleaf.mysql;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.qamatic.mintleaf.MysqlTestCase;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.Assert.assertTrue;


public class MySqlContainerTest   {

    @Rule
    public MySQLContainer mySQLContainer = new MySQLContainer()
            .withDatabaseName("mysqldb")
            .withUsername("myuser")
            .withPassword("mysecret");




    @Before
    public void before() {
        mySQLContainer.start();
    }

    @After
    public void after() {
        mySQLContainer.stop();
    }

    @Test
    public void testConnect(){
        System.out.printf("MySQL db running, db-name: '%s', user: '%s', jdbc-url: '%s'%n ",
                mySQLContainer.getDatabaseName(),
                mySQLContainer.getUsername(),
                mySQLContainer.getJdbcUrl());
        assertTrue(mySQLContainer.isRunning());
    }
}
