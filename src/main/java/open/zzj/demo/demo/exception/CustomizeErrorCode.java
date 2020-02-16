package open.zzj.demo.demo.exception;

public enum CustomizeErrorCode implements  InterfaceCustomizeErrorCode{


    QUSTION_NOT_FOUND(2001, "你找的问题被井学长吃了，下次记住，离井学长远点！！"),
//    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复")，
    TARGET_PARAM_NOT_FOUND(2002, "你选择的问题或者评论被井学长吃了，所以你没有办法回复哦！"),
//    NO_LOGIN(2003,"未登录，不能进行评论，请先登录!"),
    NO_LOGIN(2003,"你没有登录哦，不能进行评论，井学长劝你先登录，不然小心！"),
    SYS_ERROR(2004,"井学长干趴了服务器，大家躲一躲吧！！！"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在！！"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在了！");


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private String message;

    private Integer code;

    CustomizeErrorCode(Integer code, String message){
        this.message = message;
        this.code = code;
    }

}
