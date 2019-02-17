package org.qamatic.mintleaf;

import org.junit.BeforeClass;
import org.junit.Test;
import org.qamatic.mintleaf.core.ChangeSets;

import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by QAmatic Team on 4/1/17.
 */
public class MySqlDbQueriesTest extends MysqlTestCase {

    private static Database employeesDb = createMySqlDbContext("testuser1", "testpassword1");

    @BeforeClass
    public static void cleanDb() throws MintleafException {
        initDb();
        try (ConnectionContext ctx = employeesDb.getNewConnection()) {
            ChangeSets.migrate(ctx, "res:/mysql/mysql-sample-db-employees.sql", "create employees sample database");
            ChangeSets.migrate(ctx, "res:/mysql/mysql_load_departments.dump", "load departments");
        }
    }

//    @Test
//    public void mysqlDepartmentQueryTest() throws MintleafException, SQLException {
//        try (ConnectionContext ctx = employeesDb.getNewConnection()) {
//            SqlResultSet resultSet = ctx.queryBuilder().withSql("Select count(*) from employees.departments").buildSelect();
//            assertEquals(9, resultSet.first().getInt(1));
//        }
//    }

}
