package org.qamatic.mintleaf.cli;

import org.qamatic.mintleaf.Database;
import org.qamatic.mintleaf.MintleafCliTask;
import org.qamatic.mintleaf.MintleafLogger;
import org.qamatic.mintleaf.TaskOptions;
import org.qamatic.mintleaf.configuration.SchemaVersionInfo;

import java.util.List;

/**
 * Created by QAmatic team on 4/8/17.
 */
public class MigrationTask implements MintleafCliTask {
    private static final MintleafLogger logger = MintleafLogger.getLogger(MigrationTask.class);

    private Database database;
    private SchemaVersionInfo schemaVersionInfo;
    private TaskOptions options;

    public MigrationTask(Database database, SchemaVersionInfo schemaVersionInfo, TaskOptions options){
        this.database = database;
        this.schemaVersionInfo = schemaVersionInfo;
        this.options = options;
    }

    @Override
    public int execute() {
        return 0;
    }

    @Override
    public void close() throws Exception {

    }

}
