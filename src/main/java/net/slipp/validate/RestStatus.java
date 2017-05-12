package net.slipp.validate;

public class RestStatus {
    private boolean status;
    
    public RestStatus(boolean status) {
        this.status = status;
    }
    
    public boolean isStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "RestResponse [status=" + status + "]";
    }
}
