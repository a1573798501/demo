package open.zzj.demo.demo.enums;

public enum  CommenTypeEnums {
    QUESTION(1),
    COMMNET(2);


    private Integer type;

    public static boolean isExist(Integer type) {

        for (CommenTypeEnums commenTypeEnums : CommenTypeEnums.values()){
            if (commenTypeEnums.getType() == type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommenTypeEnums(Integer type) {
        this.type = type;
    }
}
