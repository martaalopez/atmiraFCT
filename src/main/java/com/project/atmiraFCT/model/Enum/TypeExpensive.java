package com.project.atmiraFCT.model.Enum;

public enum TypeExpensive {
    ALOJAMIENTO("Alojamiento"),
    DESPL_ESTRUCTURA("DESPL. ESTRUCTURA"),
    DESPL_PUNTUAL("DESPL. PUNTUAL"),
    DESPL_TEMP_TRASLADO("DESPL. TEMP.TRASLADO"),
    GASTOS_ISBAN("GASTOS ISBAN"),
    LOCOMOCION("Locomocion"),
    TRANSPORTE("Transporte"),
    VARIOS("Varios"),
    VEHICULO("Vehiculo");

    private final String displayName;

    TypeExpensive(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
