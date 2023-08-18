package com.nyklundamade.pickulacka.enumTridy;

public enum VzdalenostEnum {
    MILIMETR("mm"),
    CENTIMETR("cm"),
    DECIMETR("dm"),
    METR("m"),
    KILOMETR("km");

    private final String znacka;

    VzdalenostEnum(String znacka) {
        this.znacka = znacka;
    }

    public String getZnacka() {
        return znacka;
    }

    public static VzdalenostEnum fromString(String znacka) {
        for (VzdalenostEnum vzdalenostEnum : VzdalenostEnum.values()) {
            if (vzdalenostEnum.getZnacka().equals(znacka)) {
                return vzdalenostEnum;
            }
        }
        throw new IllegalArgumentException("Neplatná značka pro VzdalenostEnum: " + znacka);
    }
}
