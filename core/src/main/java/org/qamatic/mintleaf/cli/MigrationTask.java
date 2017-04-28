package org.qamatic.mintleaf.cli;

import org.qamatic.mintleaf.*;
import org.qamatic.mintleaf.configuration.SchemaVersionInfo;
import org.qamatic.mintleaf.core.MultiChangeSetFileReader;
import org.qamatic.mintleaf.core.SqlChangeSets;

/**
 * Created by QAmatic team on 4/8/17.
 */
public class MigrationTask implements MintleafCliTask {
    private static final MintleafLogger logger = MintleafLogger.getLogger(MigrationTask.class);

    private ConnectionContext connectionContext;
    private SchemaVersionInfo schemaVersionInfo;
    private TaskOptions options;

    public MigrationTask(ConnectionContext connectionContext, SchemaVersionInfo schemaVersionInfo, TaskOptions options) {
        this.connectionContext = connectionContext;

        this.schemaVersionInfo = schemaVersionInfo;
        this.options = options;
    }

    @Override
    public int execute() throws MintleafException {

        SqlChangeSets changeSets = new SqlChangeSets(this.connectionContext,
                new MultiChangeSetFileReader(this.schemaVersionInfo.getScriptLocationAsList()),
                this.schemaVersionInfo.getChangeSetsAsList());
        changeSets.apply();

        return 0;
    }

    @Override
    public void close() throws Exception {

    }

}
