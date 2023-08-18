package com.nyklundamade.pickulacka;

public enum PlochaEnum {

    MILIMETR("mm\u00B2"),
    CENTIMETR("cm\u00B2"),
    DECIMETR("dm\u00B2"),
    METR("m\u00B2"),
    KILOMETR("km\u00B2");

    private final String znacka;

    PlochaEnum(String znacka) {
        this.znacka = znacka;
    }

    public String getZnacka() {
        return znacka;
    }

    public static PlochaEnum fromString(String znacka) {
        for (PlochaEnum plochaEnum : PlochaEnum.values()) {
            if (plochaEnum.getZnacka().equals(znacka)) {
                return plochaEnum;
            }
        }
        throw new IllegalArgumentException("Neplatná značka pro PlochaEnum: " + znacka);
    }
}
