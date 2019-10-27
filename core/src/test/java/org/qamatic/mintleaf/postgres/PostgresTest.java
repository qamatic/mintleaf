package org.qamatic.mintleaf.postgres;

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
import static junit.framework.TestCase.assertNotNull;

public class PostgresTest extends PostgresTestCase {


    @Before
    public void setup() {
        initDb();
    }

    @Ignore
    public void checkCategroiesCount() throws MintleafException, SQLException {
        Database employeesDb = createDbContext("northwind_user", "thewindisblowing", "northwind");
        try (ConnectionContext ctx = employeesDb.getNewConnection()) {

                 ChangeSets.migrate(ctx, "res:/postgres/postgres-northwind-db.sql", "create tables, load categories");

        }
        try (ConnectionContext ctx = employeesDb.getNewConnection()) {
            SqlResultSet resultSet = ctx.queryBuilder().withSql("Select count(*) from categories").buildSelect();
            assertEquals(7, resultSet.first().getInt(1));
        }
    }

}
