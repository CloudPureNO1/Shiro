package idv.wangyj.shiro.realm.exception;

/**
 * 主要用于持久层的自定义异常
 */
public class MyException extends Exception {
    private String errorCode;
    private String sql;
    private String params;

    public MyException(){
        super();
    }
    public MyException(String msg){
        super(msg);
    }
    public MyException(String code,String msg){
        super(msg);
        errorCode=code;
    }
    public MyException(String msg,Exception cause){
        super(msg,cause);
    }
    public MyException(String code,String msg,Exception cause){
        super(msg,cause);
        errorCode=code;
    }
    public MyException(String msg,String sql,String params){
        super(msg);
        this.sql=sql;
        this.params=params;
    }
    public MyException(String msg,String sql,String params,Exception cause){
        super(msg,cause);
        this.sql=sql;
        this.params=params;
    }
    public MyException(String errorCode,String msg,String sql,String params){
        super(msg);
        this.errorCode=errorCode;
        this.sql=sql;
        this.params=params;
    }
    public MyException(String errorCode,String msg,String sql,String params,Exception cause){
        super(msg,cause);
        this.errorCode=errorCode;
        this.sql=sql;
        this.params=params;
    }

    public String getErrorCode(){
        return errorCode;
    }
    public String getSql(){
        return sql;
    }
    public String getParams(){
        return params;
    }
}
