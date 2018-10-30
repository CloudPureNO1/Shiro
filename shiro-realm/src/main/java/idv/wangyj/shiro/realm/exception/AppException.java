package idv.wangyj.shiro.realm.exception;

/**
 * 主要用于持久层的自定义异常
 */
public class AppException extends Exception {
    private String errorCode;
    private String sql;
    private String params;

    public AppException(){
        super();
    }
    public AppException(String msg){
        super(msg);
    }
    public AppException(String code, String msg){
        super(msg);
        errorCode=code;
    }
    public AppException(String msg, Exception cause){
        super(msg,cause);
    }
    public AppException(String code, String msg, Exception cause){
        super(msg,cause);
        errorCode=code;
    }
    public AppException(String msg, String sql, String params){
        super(msg);
        this.sql=sql;
        this.params=params;
    }
    public AppException(String msg, String sql, String params, Exception cause){
        super(msg,cause);
        this.sql=sql;
        this.params=params;
    }
    public AppException(String errorCode, String msg, String sql, String params){
        super(msg);
        this.errorCode=errorCode;
        this.sql=sql;
        this.params=params;
    }
    public AppException(String errorCode, String msg, String sql, String params, Exception cause){
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
