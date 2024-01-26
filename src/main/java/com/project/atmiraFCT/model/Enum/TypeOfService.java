package com.project.atmiraFCT.model.Enum;

public enum TypeOfService {
    MT("Mantenimiento"),
    DES("Desarrollo"),
    FINZ("Finanzas"),
    MARKETING("Marketing"),
    OPRT("Operaciones");

    private final String displayName;

    TypeOfService(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
