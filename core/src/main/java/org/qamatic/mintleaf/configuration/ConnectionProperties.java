package org.qamatic.mintleaf.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QAmatic Team on 3/28/17.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ConnectionProperties {

    @XmlElement(name = "add")
    private List<Property> items = new ArrayList<>();

    public List<Property> getItems() {
        return items;
    }

    public void setItems(List<Property> connectionProperties) {
        this.items = connectionProperties;
    }
}
