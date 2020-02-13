package open.zzj.demo.demo.exception;

public enum CustomizeErrorCode implements  InterfaceCustomizeErrorCode{


    QUSTION_NOT_FOUND("你找的问题被井学长吃了，下次记住，离井学长远点！！");



    @Override
    public String getMessage() {
        return message;
    }

    private String message;

    CustomizeErrorCode(String message){
        this.message = message;
    }

}
