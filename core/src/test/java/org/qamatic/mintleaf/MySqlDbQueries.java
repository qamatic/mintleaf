package org.qamatic.mintleaf;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.qamatic.mintleaf.core.ChangeSets;

import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by senips on 4/1/17.
 */
public class MySqlDbQueries extends MysqlTestCase{

    private static Database employeesDb = createOracleDbContext("testuser1", "testpassword1");

    @BeforeClass
    public static void cleanDb() throws MintLeafException {
        initDb();
        try (ConnectionContext ctx = employeesDb.getNewConnection()) {
            ChangeSets.migrate(ctx, "res:/mysql/mysql-sample-db-employees.sql", "create employees sample database");
            ChangeSets.migrate(ctx, "res:/mysql/mysql_load_departments.dump", "load departments");
        }
    }

    @Test
    public void mysqlDepartmentQueryTest() throws MintLeafException, SQLException {
        try (ConnectionContext ctx = employeesDb.getNewConnection()) {
            SqlResultSet resultSet = Mintleaf.selectQuery(ctx).withSql("Select count(*) from EMPLOYEES.DEPARTMENTS").buildSelect();
            assertEquals(9, resultSet.first().getInt(1));
        }
    }

}
