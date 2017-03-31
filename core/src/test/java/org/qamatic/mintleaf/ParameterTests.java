package org.qamatic.mintleaf;

import org.junit.Test;
import org.qamatic.mintleaf.configuration.ArgPatternHandler;
import org.qamatic.mintleaf.configuration.ConfigurationRoot;
import org.qamatic.mintleaf.configuration.DbConnectionInfo;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by QAmatic Team on 3/30/17.
 */
public class ParameterTests {

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
    public void dbconfigVarTest() {
        ConfigurationRoot dbConfiguration = new ConfigurationRoot();
        dbConfiguration.getDatabases().add(new DbConnectionInfo("abcdb", DbType.ORACLE,
                "jdbc:oracle:thin:${url}", "${user-name}", "${password}"));
        String xml = dbConfiguration.toString();
        assertTrue(xml.contains("${url}"));
        Map<String, String> userVars = new HashMap<>();
        userVars.put("url", "10.2.1.1:1044");
        userVars.put("user-name", "testuser");
        userVars.put("password", "testpassword");
        ConfigurationRoot newConfig = ConfigurationRoot.deSerialize(xml, userVars);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><mintleaf version=\"1.0\"><description>Database connections and Schema version configuration file</description>" +
                "<databases><id>abcdb</id><type>ORACLE</type><url>jdbc:oracle:thin:10.2.1.1:1044</url>" +
                "<username>testuser</username><password>testpassword</password><connectionProperties/></databases>" +
                "<schemaVersions/></mintleaf>", newConfig.toString());
    }
}

