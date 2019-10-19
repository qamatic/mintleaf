package org.qamatic.mintleaf.oracle;

import org.junit.*;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.Assert.assertTrue;


@Ignore
public class OracleContainerTest {

    @Rule
    public OracleContainer oracleContainer = new OracleContainer("smaject/oracle-xe")
            .withDatabaseName("myoradb")
            .withUsername("myuser")
            .withPassword("mysecret");

    @Before
    public void before() {
        oracleContainer.start();
    }

    @After
    public void after() {
        oracleContainer.stop();
    }

    @Test
    public void testConnect(){
        System.out.printf("Oracle db running, db-name: '%s', user: '%s', jdbc-url: '%s'%n ",
                oracleContainer.getDatabaseName(),
                oracleContainer.getUsername(),
                oracleContainer.getJdbcUrl());
        assertTrue(oracleContainer.isRunning());
    } 
}
