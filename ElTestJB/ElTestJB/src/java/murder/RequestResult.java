package murder;




/**
 *
 * @author Zaid
 */
public class RequestResult {
    
    /**
     * data to be sent.
     */
    private Object data;
    
    /**
     * result code to be sent.
     */
    //private int resultCode;
 
    public RequestResult(Object data) {
        this.data = data;
    }
 
    public Object getData() {
        return data;
    }
 
    public void setData(Object data) {
        this.data = data;
    }
 
    /*public int getResultCode() {
        return resultCode;
    }
 
    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }*/
 
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(data);
        return sb.toString();
    }
 
}
