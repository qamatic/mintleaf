package org.qamatic.mintleaf.configuration;

import org.qamatic.mintleaf.MintLeafLogger;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by QAmatic Team on 3/30/17.
 */
public class ArgPatternHandler {


    private static final MintLeafLogger logger = MintLeafLogger.getLogger(ArgPatternHandler.class);


    private final static Pattern p = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private String text;
    private Map<String, String> userProperties;
    private boolean bDone;

    public ArgPatternHandler(String text){
        this.text = text;
    }

    public Map<String, String> getUserProperties() {
        if (userProperties == null){
            userProperties = new HashMap<>();
        }
        return userProperties;
    }

    public void setUserProperties(Map<String, String> userProperties) {
        this.userProperties = userProperties;
    }

    public String getText() {
        if (!bDone){
            Matcher m = p.matcher(this.text);
            StringBuffer sb = new StringBuffer();
            while(m.find()) {

                if (getUserProperties().containsKey(m.group(1))){
                    m.appendReplacement(sb, getUserProperties().get(m.group(1)));
                } else{
                    logger.warn("parameter value not found"+m.group(0));
                }
            }
            m.appendTail(sb);
            this.text = sb.toString();
        }
        return this.text;
    }

    @Override
    public String toString() {
        return  getText();
    }


}
