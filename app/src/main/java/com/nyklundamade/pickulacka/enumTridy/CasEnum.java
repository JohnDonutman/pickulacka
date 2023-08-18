package com.nyklundamade.pickulacka.enumTridy;

public enum CasEnum {
    SEKUNDA("s"),
    MINUTA("min"),
    HODINA("h"),
    DEN("D"),
    MESIC("M"),
    ROK("R");

    private final String znacka;

    CasEnum(String znacka) {
        this.znacka = znacka;
    }

    public String getZnacka() {
        return znacka;
    }

    public static CasEnum fromString(String znacka) {
        for (CasEnum casEnum : CasEnum.values()) {
            if (casEnum.getZnacka().equals(znacka)) {
                return casEnum;
            }
        }
        throw new IllegalArgumentException("Neplatná značka pro PlochaEnum: " + znacka);
    }

}
