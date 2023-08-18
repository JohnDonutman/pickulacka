package com.nyklundamade.pickulacka;

public enum ObjemEnum {
    MILILITR("ml"),
    CENTILITR("cl"),
    DECILITR("dl"),
    LITR("l"),
    HEKTOLITR("hl"),
    KUBIK_CENTIMETR("cm\u00B3"),
    KUBIK_DECIMETR("dm\u00B3"),
    KUBIK_METR("m\u00B3");

    private final String znacka;

    ObjemEnum(String znacka) {
        this.znacka = znacka;
    }

    public String getZnacka() {
        return znacka;
    }

    public static ObjemEnum fromString(String znacka) {
        for (ObjemEnum objemEnum : ObjemEnum.values()) {
            if (objemEnum.getZnacka().equals(znacka)) {
                return objemEnum;
            }
        }
        throw new IllegalArgumentException("Neplatná značka pro PlochaEnum: " + znacka);
    }

}
