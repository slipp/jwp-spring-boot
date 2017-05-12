package net.slipp.validate;

import java.util.HashMap;
import java.util.Map;

public class RestResponse extends RestStatus {
    private Map<String, Object> result;
    
    public RestResponse() {
        super(true);
        result = new HashMap<>();
    }
    
    public void addAttribute(String key, Object value) {
        result.put(key, value);
    }
    
    public Map<String, Object> getResult() {
        return result;
    }
}
