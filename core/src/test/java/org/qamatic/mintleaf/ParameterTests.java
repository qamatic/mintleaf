package org.qamatic.mintleaf;

import org.junit.Test;
import org.qamatic.mintleaf.configuration.ArgPatternHandler;
import org.qamatic.mintleaf.configuration.ConfigurationRoot;
import org.qamatic.mintleaf.configuration.DbConnectionInfo;
import org.qamatic.mintleaf.core.TextContentStreamReader;

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
    public void dbconfigLoadFromXmlString() throws MintLeafException {
        MintLeafReader reader = new TextContentStreamReader(""){
            @Override
            public void read() throws MintLeafException {
                ConfigurationRoot dbConfiguration = new ConfigurationRoot();
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
    public void dbconfigLoadFromXml() throws MintLeafException {

        ConfigurationRoot newConfig = ConfigurationRoot.deSerialize("res:/test-config.xml");
        assertEquals("abcdb", newConfig.getDatabases().get(0).getId()); //just sanity check on deserialization.

    }

}

