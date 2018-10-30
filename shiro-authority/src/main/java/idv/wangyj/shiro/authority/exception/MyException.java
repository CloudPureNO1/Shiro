package idv.wangyj.shiro.authority.exception;

public class MyException extends Exception {
    private String errorCode;

    public MyException(){super();}
    public MyException(String msg){
        super(msg);
    }

    public MyException(String msg,Exception e){
        super(msg,e);
    }

    public MyException(String errorCode,String msg){
        super(msg);
        this.errorCode=errorCode;
    }

    public MyException(String errorCode,String msg,Exception e){
        super(msg,e);
        this.errorCode=errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
    public String getMessage(String msg){
       return super.getMessage();
    }
}
