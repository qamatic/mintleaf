package org.qamatic.mintleaf;

import org.junit.Test;
import org.qamatic.mintleaf.configuration.ArgPatternHandler;
import org.qamatic.mintleaf.configuration.MintleafXmlConfiguration;
import org.qamatic.mintleaf.configuration.DbConnectionInfo;
import org.qamatic.mintleaf.core.TextContentStreamReader;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by QAmatic Team on 3/30/17.
 */
public class ParameterTest {

    @Test
    public void substitueAntVar() {
        ArgPatternHandler argPatternHandler = new ArgPatternHandler("select ${col_name},${col_name} from ${table_name}");
        argPatternHandler.getUserProperties().put("col_name", "mycol");
        argPatternHandler.getUserProperties().put("table_name", "mytable");
        assertEquals("select mycol,mycol from mytable", argPatternHandler.getText());
    }

    @Test
    public void substitueAntVarSysVar() {
        ArgPatternHandler argPatternHandler = new ArgPatternHandler("select ${JAVA_HOME}");
        assertTrue(!argPatternHandler.getText().contains("JAVA_HOME"));
    }

    @Test
    public void substitueAntVarSysVarOverUser() {
        ArgPatternHandler argPatternHandler = new ArgPatternHandler("select ${JAVA_HOME}");
        argPatternHandler.getUserProperties().put("JAVA_HOME", "mytable");
        assertEquals("select mytable", argPatternHandler.getText());
    }

    @Test
    public void substitueAntVarSysVarOverVmArg() {
        ArgPatternHandler argPatternHandler = new ArgPatternHandler("select ${JAVA_HOME}");
        System.setProperty("JAVA_HOME", "VMArgMytable");
        assertEquals("select VMArgMytable", argPatternHandler.getText());
    }


    @Test
    public void dbconfigLoadFromXmlString() throws MintleafException {
        MintleafReader reader = new TextContentStreamReader(""){
            @Override
            public void read() throws MintleafException {
                MintleafXmlConfiguration dbConfiguration = new MintleafXmlConfiguration();
                dbConfiguration.getDatabases().add(new DbConnectionInfo("abcdb", DbType.ORACLE,
                        "jdbc:oracle:thin:${url}", "${user-name}", "${password}"));
                content.append(dbConfiguration.toString());
            }
        };
        reader.read();

        reader.getUserVariableMapping().put("url", "10.2.1.1:1044");
        reader.getUserVariableMapping().put("user-name", "testuser");
        reader.getUserVariableMapping().put("password", "testpassword");
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><mintleaf version=\"1.0\"><description>Database connections and Schema version configuration file</description>" +
                "<databases><id>abcdb</id><type>ORACLE</type><url>jdbc:oracle:thin:10.2.1.1:1044</url>" +
                "<username>testuser</username><password>testpassword</password><connectionProperties/></databases>" +
                "<schemaVersions/></mintleaf>", reader.toString());

    }

    @Test
    public void dbconfigLoadFromXml() throws MintleafException {

        MintleafConfiguration newConfig = MintleafXmlConfiguration.deSerialize("res:/test-config.xml");
        assertEquals("1.0", newConfig.getConfigVersion());
        assertEquals("Database connections and Schema version configuration file", newConfig.getDescription());
        assertEquals("abcdb", newConfig.getDbConnectionInfo("abcdb").getId());
        assertEquals("create schema, load seed data", newConfig.getSchemaVersionInfo("1.0").getChangeSets()); //just sanity check on deserialization.

    }

}

