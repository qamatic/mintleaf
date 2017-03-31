package org.qamatic.mintleaf;

import org.junit.Test;
import org.qamatic.mintleaf.configuration.ArgPatternHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by QAmatic Team on 3/30/17.
 */
public class ParameterTests {

    @Test
    public void substitueAntVar(){
        ArgPatternHandler argPatternHandler = new ArgPatternHandler("select ${col_name},${col_name} from ${table_name}");
        argPatternHandler.getUserProperties().put("col_name", "mycol");
        argPatternHandler.getUserProperties().put("table_name", "mytable");
        assertEquals("select mycol,mycol from mytable",argPatternHandler);


    }
}
