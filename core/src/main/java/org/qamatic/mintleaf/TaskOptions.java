package org.qamatic.mintleaf;

import java.util.HashMap;

/**
 * Created by QAmatic Team on 4/8/17.
 */
public class TaskOptions {
    private HashMap<String, Object> taskOptions = new HashMap<>();

    public void addOption(String optionKey, Object optionValue) {
        taskOptions.put(optionKey, optionValue);
    }

    public int asInt(String optionKey) {
        return (int) taskOptions.get(optionKey);
    }

    public String asString(String optionKey) {
        return (String) taskOptions.get(optionKey);
    }

    public Object asObject(String optionKey) {
        return taskOptions.get(optionKey);
    }
}
