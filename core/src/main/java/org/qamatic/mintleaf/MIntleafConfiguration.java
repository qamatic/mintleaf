package org.qamatic.mintleaf;

import org.qamatic.mintleaf.configuration.DbConnectionInfo;
import org.qamatic.mintleaf.configuration.SchemaVersionInfo;

import java.util.List;

/**
 * Created by QAmatic Team on 4/8/17.
 */
public interface MintleafConfiguration {

    String getConfigVersion();
    String getDescription();
    DbConnectionInfo getDbConnectionInfo(String databaseId);
    SchemaVersionInfo getSchemaVersionInfo(String versionId);
    List<DbConnectionInfo> getDatabases();
    List<SchemaVersionInfo> getSchemas();

}
