package org.qamatic.mintleaf.core;

import org.junit.Test;
import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.ReadListener;
import org.qamatic.mintleaf.Row;
import org.qamatic.mintleaf.core.rows.InMemoryRow;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;

import static org.junit.Assert.assertTrue;

public class BaseReaderTest {


//   @Test
//   public void testGenerics(){
//       Function<Double, Double> square = Hey::square;
//       Function<Double, Double> square1 =  square.andThen(Hey::square).andThen(Hey::square);
//      System.out.println( square1.apply(2d));
//
//       Set<String> set = new HashSet<>();
//       set.addAll(Arrays.asList("leo","bale","hanks"));
//       BiPredicate<Double, Double> pred = (Hey::contains);
//       BiPredicate<Double, Double> pred1 = pred.and(pred).or(pred).negate();
//       boolean exists = pred.test(2D, 2D);
//
//       List<Apple> apples = Arrays.asList(new Apple("green", 120.0), new Apple("red", 110.0),
//               new Apple("brown", 150.0), new Apple("green", 160.0), new Apple("red", 122.0));
//
//      List<Apple> newApples =  apples.stream().filter(new Predicate<Apple>() {
//           @Override
//           public boolean test(Apple apple) {
//               return true;
//           }
//       }).collect(Collectors.toList());
//       newApples.forEach(System.out::println);
//       Table wrapper = new SqlQueryTable();
//       apples.forEach((apple -> System.out.println(apple.getColor())));
//
//   }

    @Test
    public void testSqlLite() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:target/h2testdb1013");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            Instant start = Instant.now();
            statement.executeUpdate("drop table if exists person");
            //  statement.executeUpdate("create table person (ID NUMBER (18)  NOT NULL, NAME VARCHAR2 (60 CHAR)  NOT NULL)");
            statement.executeUpdate("create table person (id integer, name string)");
            statement.executeUpdate("begin transaction");
            for (int i = 0; i < 1000; i++) {
                statement.executeUpdate(String.format("insert into person values(%s, 'leo-%s')", i, i));
            }
            statement.executeUpdate("end transaction");
            ResultSet rs = statement.executeQuery("select * from person order by name desc");
//            while(rs.next())
//            {
//                // read the result set
//                System.out.print("name = " + rs.getString("name"));
//                System.out.println("id = " + rs.getInt("id"));
//            }
            Instant finish = Instant.now();

            long timeElapsed = Duration.between(start, finish).toMillis();  //in millis
            System.out.println(timeElapsed);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
    }

    @Test
    public void testCustomRowGeneric() throws MintleafException {
        CustomReader<InMemoryRow> reader = new CustomReader<>();
        reader.setRowClassType(InMemoryRow.class);
        reader.getPreProcessors().add(new ReadListener() {
            @Override
            public void eachRow(int rowNum, Row row) throws MintleafException {
                assertTrue(row instanceof InMemoryRow);
            }
        });
        reader.read();
    }

    @Test
    public void testCustomRowGenericChangingInRowType() throws MintleafException {
        CustomReader<CustomInMemoryRow> reader = new CustomReader<>();
        reader.setRowClassType(CustomInMemoryRow.class);
        reader.getPreProcessors().add(new ReadListener() {
            @Override
            public void eachRow(int rowNum, Row row) throws MintleafException {
                assertTrue(row instanceof CustomInMemoryRow);
            }
        });
        CustomInMemoryRow o = reader.eachRow(1, new CustomInMemoryRow());
        reader.read();
    }

    private class CustomReader<T extends Row> extends BaseReader<T> {


        @Override
        public void read() throws MintleafException {

            for (int i = 0; i < 3; i++) {


                try {
                    T r = getRowClassType().newInstance();
                    if (!readRow(0, r)) {
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }


    }

}
