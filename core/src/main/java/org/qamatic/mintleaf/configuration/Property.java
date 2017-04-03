package org.qamatic.mintleaf.configuration;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by QAmatic Team on 3/28/17.
 */
public class Property {

    @XmlAttribute
    private String key;
    @XmlAttribute
    private String value;

    public Property() {

    }

    public Property(String key, String value) {

        this.key = key;
        this.value = value;
    }


}
