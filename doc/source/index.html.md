---
title: Mintleaf Documentation
 
toc_footers:  
  - <a href='http://getmintleaf.org'>getmintleaf.org</a>
  - <a href='https://github.com/qamatic/mintleaf'>Source @ github</a>
 

includes:
  
search: true
---

# Get started

Welcome to the Mintleaf! Mintleaf is a light weight framework tool helps you to advance your database developement on continuous integration / continuous delivery model as easy as possible.

### Features
- Simplified Database Migration (either from command line or programatic approach)
- Compare Data between database tables/csv/spreadsheets/list of objects/any custom data sources
- Data export/import: database table to CSV, CSV to database table and, between database tables  
- Unit testing: write automated tests and run them on migrated database schemas, objects, data integrity checks during CI/CD.
- Seamless Test life cycle management such as creation of test data,  test data setup, test data teardown, schema and any database objects using changesets
- Extensibility framework offers you the power of customizations - custom data wrappers, custom import/export, custom comparer implementation, and report generation and so on..
- Low memory overhead, high performance on large datasets for data exports, imports, and data compare scenarios
- Nothing more but to use Plain old SQL that you know of
  

##Installation



# Understanding Mintleaf
 
## Migration at a glance
Database migraton refers to the management of incremental, reversible changes to relational database schemas. A schema migration is performed on a database whenever it is necessary to update or revert that database's schema to some newer or older version.  Look at the below diagram which shows you the schema changes over a period of time during a Agile Software Developement Life Cycle.   Every schema changes during a developement sprint will be applied to QA Databases and Prod Database at the end of sprint release.  
  
  
![Database Migration](/images/basicflow.png)   

When developing software applications backed by a database, developers typically develop the application source code in tandem with an evolving database schema. The code typically has rigid expectations of what columns, tables and constraints are present in the database schema whenever it needs to interact with one, so only the version of database schema against which the code was developed is considered fully compatible with that version of source code.

#### Version Control Systems v.s Database Migration Versions?
Teams of software developers usually use version control systems to manage and collaborate on changes made to versions of source code. Different developers can develop on divergent, relatively older or newer branches of the same source code to make changes and additions during development.  So version control system by itself does not maintain database versions but data migration tools does it for you.  So using Mintleaf you can maintain your database versions either upgrade to a newer version or degrade it a older version. 

## Why MintLeaf?

Schema migration to a production database is always a risky task. Production databases are usually huge, old, and full of surprise things like the following 
    
- Assumptions on certian data conditions 
- Implied dependencies which no body knows
- Unclean / stale data which are hard to resolve 
- Direct schema patches for bug fixes 
- Direct performance fixes
and so on....

 
>So for these reasons, a solid migration process needs high level of discipline through build, test and deploy strategy is a way to go as like shown in below diagram where **Mintleaf** serves as a tool and as a framework to help an organisation move forward to next level of Agile Database Developement.

![Mintleaf](/images/overall.png)   

 
So again, welcome to the Mintleaf!  Mintleaf is a light weight framework tool helps you to advance your database developement on continuous integration / continuous delivery model as easy as possible.

 


Let's look at the core concepts behind Mintleaf design so that you will be able to understand and use it accordingly before you move onto technical section.  Here is the simple steps to get you started quick.


## Change-sets
 

 Changesets are basically contains one ore more changes that are to be applied during a database migration.  Mintleaf changesets are stored in plain old sql files but changeset information is described using sql comment lines as shown below 
 
```sql
 -- <ChangeSet id="{a-change-set-id}" delimiter=";" userdata="" />
```
where,
 
Parameter | Description
--------- | -----------
id | The ID of the changeset and it should be unique
delimiter | Sql DDL/DML statement delimiter
userdata | user defined columns that one can store additional meta data of the changeset. 


 
**_For example, a file 'abcdb-changeset.sql' contains the following two changesets: create schema and create tables_**

 
```sql
-- <ChangeSet id="create schema" delimiter=";" userdata="" />
DROP SCHEMA IF EXISTS ABCDB;
CREATE SCHEMA IF NOT EXISTS ABCDB;
 
-- <ChangeSet id="create tables" delimiter=";" userdata="" /> 
CREATE TABLE IF NOT EXISTS ABCDB.USERS
(
  USERID      INT NOT NULL,
  USERNAME    VARCHAR2(50),
  RATE        NUMBER(12, 2),
  CREATE_TIME DATE DEFAULT sysdate,
  CONSTRAINT PK_USERID PRIMARY KEY (USERID)
);
 
``` 
 
 
<!--Tips: -->
<!--- Create a folder-->
<!--- Under that new folder, just create plain old sql file with an extension .sql-->
<!--- Edit that sql file to create a new changeset by starting a single line comment inside that sql file-->
<!--- Add your changes (DDL/DML scripts) after changeset comment line declaration-->
<!--- And repeat as much as changeset that you like and you can create as many as sql files as you like to spread them up-->



 
<aside class="notice"> 
It's very easy to add changeset definition on your exising sql files if you would like.  So nothing much to do apart from this comment line declaration. 
</aside>


## Configuration file

Version profile is a configuration file which contains list of changesets to be applied for a particular version of a database migration.  Typically you will have all versions from the beginning to the latest.   So the Mintleaf looks for a default version profile at the folder where it is being execution location.

 **dbconfig.xml**

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mintleaf version="1.0">
    <description>Database connections and Schema version configuration file</description>
    <databases>
        <id>abcdb</id>
        <type>H2</type>
        <url>jdbc:h2:file:./target/H2Db;mv_store=false;</url>
        <connectionProperties>poolSize=100</connectionProperties>
    </databases>
    <schemaVersions>
        <version>
            <id>1.0</id>
            <changeSets>
                create schema,
                load seed data
            </changeSets>
            <scriptLocation>./path/*.sql</scriptLocation>
        </version>
    </schemaVersions>
</mintleaf>
         
```


## Components of Mintleaf and tool kits 

![Mintleaf](/images/mintleaf.png)  

 
# Creating test data

Before run your test you should load your test data to target test database so that your tests can be run for to prove to be working with developer code changes. So generally, your test scenearios may be involved with one or more database tables that needs to be populated with data in order to verify developer code changes either stored procedure side or application side. Mintleaf simplifies the test data creation process as easy as possible.  Here are the possible ways that one can think of creating test data with Mintleaf, 

- Create your own data set in a csv file format and load it to your target test database
- Or copy data from existing database to a csv file and later use it for loading in to your target test database 
- Or directly copy data from one database to another database/your target test database
 
So over all you can use csv file and database-to-database copy of data for your tests.  
 
 
<aside class="warning"> Mintleaf copy data process is intended for to use for test data creation purpose only so it is not meant for production use   
</aside>

### _Usage:_

**mintleaf -config=&lt;[configuration file](#configuration-file)&gt; -targetdb=&lt;db id&gt; -file=&lt;csv file to save&gt; -task=&lt;dbtocsv|csvtodb|dbtodb&gt; -targetsql=&lt;a select query&gt;**


## Database to CSV

For example, if we want to dump data from a table called HRDB.USERS in abcd-db to a CSV file then you run like the following 
 
```java
 
        //connect to a database, below is an example for H2 database but you can change to any database
        DatabaseContext h2db = new Mintleaf.DatabaseBuilder().
                        withDriverSource(JdbcDataSource.class).
                        withUrl("jdbc:h2:file:./target/H2DbTests;mv_store=false;").
                        build();

        //export to csv
        DataAction dataAction = new Mintleaf.DbToCsvBuilder().
                withSourceDb(h2db).
                withSourceSql("select * from HRDB.USERS").
                withTargetCsvFile("users.csv").
                build();
                
        dataAction.execute();
        
```  
 
  
## CSV to Database

Suppose you have a data in a CSV file called abcd.csv and want to load it in to a table called HRDB.USERS then you run like the following command.  Note that CSV file must have a header row as first which contains column names. Please note that whatever the column name you define in CSV file will be used exactly in your targetsql argument as shown below.  Here target sql is a template and the CSV column names are prefixed and suffixed with dollar sign as variables.   One great thing about using a template approach here is that it enables use of builtin functions of the database inside your insert statement some instances like any date formatting functions or math functions and so on.   



```java
 
        //connect to a database, below is an example for H2 database but you can change to any database
        DatabaseContext h2db = new Mintleaf.DatabaseBuilder().
                        withDriverSource(JdbcDataSource.class).
                        withUrl("jdbc:h2:file:./target/H2DbTests;mv_store=false;").
                        build();

        //import from csv
        DataAction action = new Mintleaf.CsvToDbBuilder().
                withSourceCsvFile("users.csv").
                withTargetDb(h2DatabaseContext).
                withTargetSqlTemplate("UPDATE HRDB.USERS SET USERNAME = '$USERNAME$-changed' WHERE USERID=$USERID$").
                build();

        dataAction.execute();
        
```  
  
 
## Database to Database
 
 
```java
 
        //connect to a database, below is an example for H2 database but you can change to any database
        DatabaseContext h2db = new Mintleaf.DatabaseBuilder().
                        withDriverSource(JdbcDataSource.class).
                        withUrl("jdbc:h2:file:./target/H2DbTests;mv_store=false;").
                        build();

        //import from csv
         DataAction action = new Mintleaf.DbToDbBuilder().
                withSourceDb(h2DatabaseContext).
                withSourceSql("SELECT * FROM HRDB.USERS").  // from users table
                withTargetDb(h2DatabaseContext).  //any target databse, here used the same database
                withTargetSqlTemplate("INSERT INTO HRDB.USERS_IMPORT_TABLE (USERID, USERNAME) VALUES ($USERID$, '$USERNAME$')").
                build();

        dataAction.execute();
        
```  
  
 

#Supported databases

## Oracle
```java
 
        DatabaseContext oracleDb = new Mintleaf.DatabaseBuilder(DbType.ORACLE).
                        withDriverSource(JdbcDataSource.class).                      
                        withUrl("jdbc:oracle:thin:@localhost:1521:xe").
                        build();    
        
```

## SQL Server

```java
 
        DatabaseContext oracleDb = new Mintleaf.DatabaseBuilder(DbType.MSSQL).
                        withDriverSource(JdbcDataSource.class).                      
                        withUrl("jdbc:sqlserver://localhost:1433/hrdb").
                        build();    
        
```


## H2

```java
 
        DatabaseContext h2db = new Mintleaf.DatabaseBuilder().
                        withDriverSource(JdbcDataSource.class).
                        withUrl("jdbc:h2:file:./target/H2DbTests;mv_store=false;").
                        build();
        
```

 

## MySQL

```java
 
        DatabaseContext h2db = new Mintleaf.DatabaseBuilder(DbType.MYSQL).
                        withDriverSource(JdbcDataSource.class).
                        withUrl("jdbc:mysql://localhost:3306/hrdb").
                        build();
        
```

 
#Data compare

With Mintleaf, you can compare two data sets of any kind and it is not just limited to between two database tables. For example one can compare CSV file against a database table or list of objects against a CSV file or against a database table or vice versa. See advanced section as how to implement your own custom comparer implementation.



## Comparing two database tables

```java

        DatabaseContext db = new Mintleaf.DatabaseBuilder().
                withDriverSource(JdbcDataSource.class).
                withUrl("jdbc:h2:file:./target/H2DbScriptTests;mv_store=false;").
                build();
                
       //this db has two tables called USERS and USERS_IMPORTED_TABLE
       //tables can come from two different databases too but for this example just one database having two tables
       FluentJdbc sourceTable = db.queryBuilder().withSql("SELECT * FROM HRDB.USERS");
       FluentJdbc targetTable = db.queryBuilder().withSql("SELECT * FROM HRDB.USERS_IMPORTED_TABLE");
       
       //so here is how you compare between HRDB.USERS and HRDB.USERS_IMPORTED_TABLE
       
        final ConsoleLogger logger = new ConsoleLogger();
        DataComparer dataComparer = new Mintleaf.ComparerBuilder().
                withSourceTable(sourceList).
                withTargetTable(targetListList).
                withMatchingResult((sourceColumn, targetColumn) -> {
                    
                    //here is where you add your compare criteria 
                
                    if (sourceColumn.equals(targetColumn)){
                        logger.info("matches");
                    }else {
                        logger.info("no match");
                    }
                    
                    //logger.info(String.format("[Source:%s] [Target:%s]", sourceRowState, targetRowState));
                }).
                build();


        //run data compare
        dataComparer.execute();   

                
```





## Compare two list of objects

For example you have a pojo class looks like the following.  For simplicity purpose no getter/setters for now but it has just two member columns UserName and Country. 


```java   
   public class User {         
        public String UserName;
        public String Country;
   } 
    
```


In order to do compare between _sourceUserList and targetUserList_, you must implement an interface **ComparableRow** on User class.   When you implement **ComparableRow** interface, you will have to override **getMetaData()**,  **setMetaData()** and **getValue()** methods as shown below.   Basically you provide column information as opposed a object properties so this way one can easily deal with nest objects and calculated column values manipluated before returning them.  So you can also implemnent some custom auto inferring mechanism like spring beans via annotations is an idea for those who wants it but its is not the scope here and Mintleaf provides complete freedom to the user as how they would like to implement it.  



```java

        private class User implements ComparableRow {
    
             public String UserName;
             public String Country;
                
            //add this line 
            private MetaDataCollection metaDataCollection; 
    
            //add this override to map your bean members to column***********
            @Override
            public Object getValue(int columnIndex) throws MintLeafException {
                switch (columnIndex) {
                    case 0:
                        return UserName;
                    case 1:
                        return Country;
                }
                return null;
            }
    
            //add this override to return column meta data information********************
            @Override
            public MetaDataCollection getMetaData() throws MintLeafException {
                return this.metaDataCollection;
            }
        
             //add this override to set column meta data information- framework injects the value********************
            @Override
            public void setMetaData(MetaDataCollection metaDataCollection) {
                this.metaDataCollection = metaDataCollection;
            }
        }

```


**now create two different list of Users and populate with some data.** 

```java

    //create a source list of users
   List<User> sourceUserList = new ArrayList<User> ();
   sourceUserList.add(new User(){
                                      {
                                          UserName = "Tomatoe";
                                          Country = "USA";
                                      }
                                  });
                                  
   //create a target list of users to be compared                               
   List<User> targetUserList = new ArrayList<User> ();
   targetUserList.add(new User(){
                                         {
                                             UserName = "Tomatoe";
                                             Country = "USA";
                                         }
                                     });
```


Lets see how compare works between sourceUserList and targetUserList,  


```java

        //logger
        final ConsoleLogger logger = new ConsoleLogger();
        
        //create comparer
        DataComparer dataComparer = new Mintleaf.ComparerBuilder().
                withSourceTable(sourceUserList).
                withTargetTable(targetUserList).
                withMatchingResult((sourceColumn, targetColumn) -> {
                    
                    //here is where you add your compare criteria 
                
                    if (sourceColumn.equals(targetColumn)){
                        logger.info("matches");
                    }else {
                        logger.info("no match");
                    }
                    
                    //logger.info(String.format("[Source:%s] [Target:%s]", sourceRowState, targetRowState));
                }).
                build();

        //compare now
        dataComparer.execute();
```