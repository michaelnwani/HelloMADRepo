package edu.neu.madcourse.michaelnwani;

/**
 * Created by kevin on 2/23/15.
 */
public class ParseKeyValue {

    private String key;
    private String value;

    public ParseKeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
