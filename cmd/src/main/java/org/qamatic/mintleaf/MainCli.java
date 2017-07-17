/*
 *
 *  *
 *  *  * <!--
 *  *  *   ~
 *  *  *   ~ The MIT License (MIT)
 *  *  *   ~
 *  *  *   ~ Copyright (c) 2010-2017 QAMatic
 *  *  *   ~
 *  *  *   ~ Permission is hereby granted, free of charge, to any person obtaining a copy
 *  *  *   ~ of this software and associated documentation files (the "Software"), to deal
 *  *  *   ~ in the Software without restriction, including without limitation the rights
 *  *  *   ~ to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  *  *   ~ copies of the Software, and to permit persons to whom the Software is
 *  *  *   ~ furnished to do so, subject to the following conditions:
 *  *  *   ~
 *  *  *   ~ The above copyright notice and this permission notice shall be included in all
 *  *  *   ~ copies or substantial portions of the Software.
 *  *  *   ~
 *  *  *   ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  *  *   ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  *  *   ~ FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  *  *   ~ AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  *  *   ~ LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  *  *   ~ OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  *  *   ~ SOFTWARE.
 *  *  *   ~
 *  *  *   ~
 *  *  *   -->
 *  *
 *  *
 *
 */

package org.qamatic.mintleaf;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;
import org.qamatic.mintleaf.cli.MigrationTask;
import org.qamatic.mintleaf.configuration.MintleafXmlConfiguration;

/**
 * Created by qamatic on 2/18/6/16.
 */

public class MainCli {

    @Parameter(names = {"-h", "--help"}, help = true)
    private String help;

    @Parameter(names = {"-v", "--version"}, help = true)
    private String version;

    private JCommander jc = new JCommander(this);
    private CommandMigrate cc = new CommandMigrate();

    public static void main(String[] args) throws MintleafException {
        System.out.println("Mintleaf v1.26 command line tool");
        new MainCli().parse(args);
    }

    public MainCli parse(String[] args) throws MintleafException {
        jc.setColumnSize(160);
        jc.setProgramName("Mintleaf");
        jc.addCommand("migrate", cc);
        jc.setCaseSensitiveOptions(false);
        jc.setAllowAbbreviatedOptions(true);
        try {
            if (args.length == 0) {
                throw new RuntimeException();
            }
            jc.parse(args);
            run();
        } catch (RuntimeException e) {
            usage();
        }
        return this;
    }

    private void println(String msg) {
        JCommander.getConsole().println(msg);
    }

    public void usage() {
        StringBuilder sb = new StringBuilder();
        jc.usage(sb);
        println(sb.toString().replaceAll("\n\n", "\n"));
    }

    public void run() throws MintleafException {

        if (jc.getCommands().containsKey("migrate")) {
            cc.execute();
        }
    }

    @Parameters(separators = "=", commandDescription = "")
    class CommonOptions {
        @Parameter(names = {"-config", "-configfile"}, required = true, description = "database settings and Schema version configuration file.")
        private String configFile;
    }

    @Parameters(separators = "=", commandDescription = "Migrate schema.\n        example: mintleaf migrate -config=db.xml -targetdb=abcDB")
    private class CommandMigrate implements MintleafCliTask {
        @ParametersDelegate
        private CommonOptions commonOptions = new CommonOptions();

        @Parameter(names = "-targetdb", required = true, description = "database id of the target database")
        private String targetDb;

        @Parameter(names = "-schemaVer", required = true, description = "schema version to be migrated")
        private String schemaVer;


        @Override
        public int execute() throws MintleafException {
            MintleafConfiguration newConfig = MintleafXmlConfiguration.deSerialize(commonOptions.configFile);
            Database db = newConfig.getDbConnectionInfo(targetDb).getNewDatabaseInstance();
            try (ConnectionContext connectionContext = db.getNewConnection()) {
                MintleafCliTask task = new MigrationTask(connectionContext, newConfig.getSchemaVersionInfo("1.0"), null);
                return task.execute();
            }
        }

        @Override
        public void close() throws Exception {

        }
    }
}

