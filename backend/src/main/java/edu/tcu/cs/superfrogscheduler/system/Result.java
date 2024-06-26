package edu.tcu.cs.superfrogscheduler.system;

// defines the schema of the response
// used to encapsulate data prepared by the server side
// this object will be serialized to JSON before sent back to the client end
public class Result {
    private boolean flag; // true means success, false means not success
    private Integer code; // status code
    private String message; // response message
    private Object data; // response payload

    public Result(){

    }

    public Result(boolean flag, Integer code, String message){
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result(boolean flag, Integer code, String message, Object data){
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isFlag(){
        return flag;
    }

    public void setFlag(boolean flag){
        this.flag = flag;
    }

    public Integer getCode(){
        return code;
    }

    public void setCode(Integer code){
        this.code = code;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public Object getData(){
        return data;
    }

    public void setData(Object data){
        this.data = data;
    }

    public static class Period {
    }
}
