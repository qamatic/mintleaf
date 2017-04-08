package org.qamatic.mintleaf.cli;

import org.qamatic.mintleaf.MintleafCliTask;
import org.qamatic.mintleaf.TaskOptions;

/**
 * Created by QAmatic team on 4/8/17.
 */
public class MigrationTask implements MintleafCliTask {

    private TaskOptions options;

    public MigrationTask(TaskOptions options){
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
