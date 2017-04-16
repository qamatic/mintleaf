package org.qamatic.mintleaf;

import org.junit.Test;
import org.qamatic.mintleaf.core.ObjectRowListWrapper;
import org.qamatic.mintleaf.tools.BinaryFileImportFlavour;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by QAmatic Team on 4/11/17.
 */
public class BinaryImportTest extends H2TestCase {

    @Test
    public void readBinaryByteReader() throws MintleafException, URISyntaxException {
        URL url =Thread.currentThread().getContextClassLoader().getClass().getResource("/impexpfiles/bin-sample.txt");
        File file = new File(url.toURI());
        BinaryFileImportFlavour reader = new BinaryFileImportFlavour(file, 10);
       reader.doImport(new DataRowListener() {
           @Override
           public Object eachRow(int rowNum, Row row) throws MintleafException {
               return null;
           }
       });
    }

    @Test
    public void writeBinaryfile() throws MintleafException {

        List<CityRecord> cities = new ArrayList<CityRecord>() {
            {
                add(new CityRecord(1, "West Chester", "PA", "USA"));
                add(new CityRecord(2, "Cherry Hill", "NJ", "USA"));
                add(new CityRecord(3, "New York", "NY", "USA"));

            }
        };

        ObjectRowListWrapper rows = new ObjectRowListWrapper(cities, testMetaData());
        rows.next();

        //assertEquals("", rows.row().toString());

    }


    private static ColumnMetaDataCollection testMetaData() throws MintleafException {
        ColumnMetaDataCollection metaDataCollection = new ColumnMetaDataCollection("CITIES");
        metaDataCollection.add(new Column("Id", 4, Types.INTEGER));
        metaDataCollection.add(new Column("City", 16, Types.CHAR));
        metaDataCollection.add(new Column("State", 2, Types.CHAR));
        metaDataCollection.add(new Column("Country", 12, Types.CHAR));
        return metaDataCollection;
    }

}
