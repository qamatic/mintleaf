package org.qamatic.mintleaf.mysql;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.qamatic.mintleaf.ConnectionContext;
import org.qamatic.mintleaf.Database;
import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.SqlResultSet;
import org.qamatic.mintleaf.core.ChangeSets;

import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by QAmatic Team on 4/1/17.
 */
@Ignore
public class MySqlDbQueriesTest extends MysqlTestCase {


    @Before
    public void setup() {
        initDb();
    }


    @Ignore
    public void mysqlDepartmentQueryTest() throws MintleafException, SQLException {
        Database employeesDb = createDbContext("testuser1", "testpassword1");
        try (ConnectionContext ctx = employeesDb.getNewConnection()) {
            ChangeSets.migrate(ctx, "res:/mysql/mysql-sample-db-employees.sql", "create employees sample database");
            ChangeSets.migrate(ctx, "res:/mysql/mysql_load_departments.dump", "load departments");
        }
        try (ConnectionContext ctx = employeesDb.getNewConnection()) {
            SqlResultSet resultSet = ctx.queryBuilder().withSql("Select count(*) from employees.departments").buildSelect();
            assertEquals(9, resultSet.first().getInt(1));
        }
    }

}
