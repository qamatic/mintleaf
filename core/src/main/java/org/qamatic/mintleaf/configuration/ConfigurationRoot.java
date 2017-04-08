/*
 *
 *   *
 *   *  *
 *   *  *   ~
 *   *  *   ~ The MIT License (MIT)
 *   *  *   ~
 *   *  *   ~ Copyright (c) 2010-2017 QAMatic Team
 *   *  *   ~
 *   *  *   ~ Permission is hereby granted, free of charge, to any person obtaining a copy
 *   *  *   ~ of this software and associated documentation files (the "Software"), to deal
 *   *  *   ~ in the Software without restriction, including without limitation the rights
 *   *  *   ~ to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   *  *   ~ copies of the Software, and to permit persons to whom the Software is
 *   *  *   ~ furnished to do so, subject to the following conditions:
 *   *  *   ~
 *   *  *   ~ The above copyright notice and this permission notice shall be included in all
 *   *  *   ~ copies or substantial portions of the Software.
 *   *  *   ~
 *   *  *   ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   *  *   ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   *  *   ~ FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   *  *   ~ AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   *  *   ~ LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   *  *   ~ OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   *  *   ~ SOFTWARE.
 *   *  *   ~
 *   *  *   ~
 *   *  *
 *   *
 *   *
 *
 * /
 */

package org.qamatic.mintleaf.configuration;

import org.qamatic.mintleaf.Database;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.MintLeafReader;
import org.qamatic.mintleaf.core.ContentStreamReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by qamatic on 3/6/16.
 */

@XmlRootElement(name = "mintleaf")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"description", "databases", "schemaVersions"})
public class ConfigurationRoot {

    @XmlAttribute
    private String version = "1.0";

    private String description = "Database connections and Schema version configuration file";

    private List<DbConnectionInfo> databases = new Databases();
    private SchemaVersions schemaVersions = new SchemaVersions();

    public static ConfigurationRoot deSerialize(String configFileName) throws MintLeafException {
        MintLeafReader reader = new ContentStreamReader(configFileName);
        reader.read();
        try {
            JAXBContext jc = JAXBContext.newInstance(ConfigurationRoot.class);
            Unmarshaller marshaller = jc.createUnmarshaller();
            StringReader sr = new StringReader(reader.toString());
            ConfigurationRoot configurationRoot = (ConfigurationRoot) marshaller.unmarshal(sr);
            return configurationRoot;

        } catch (JAXBException e) {
            throw new MintLeafException(e);
        }
    }

    public List<DbConnectionInfo> getDatabases() {
        return databases;
    }

    public void setDatabases(List<DbConnectionInfo> databases) {
        this.databases = databases;
    }

    public SchemaVersions getSchemaVersions() {
        return schemaVersions;
    }

    public void setSchemaVersions(SchemaVersions schemaVersions) {
        this.schemaVersions = schemaVersions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return serialize();
    }

    private String serialize() {
        try {
            JAXBContext jc = JAXBContext.newInstance(ConfigurationRoot.class);
            Marshaller marshaller = jc.createMarshaller();
            StringWriter sw = new StringWriter();
            marshaller.marshal(this, sw);
            return sw.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Database getDatabase(String databaseId) {
        for (DbConnectionInfo dbConnectionInfo : getDatabases()) {
            if (dbConnectionInfo.getId().equalsIgnoreCase(databaseId)) {
                return dbConnectionInfo.getNewDatabaseInstance();
            }
        }
        return null;
    }

    public class Databases extends ArrayList<DbConnectionInfo> {
    }
}
