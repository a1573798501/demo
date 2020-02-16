package open.zzj.demo.demo.exception;

public class CustomizeException  extends  RuntimeException{


    private String message;

    private Integer code;


    public CustomizeException(InterfaceCustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
