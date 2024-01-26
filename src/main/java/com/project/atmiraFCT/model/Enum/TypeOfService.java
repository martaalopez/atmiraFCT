package com.project.atmiraFCT.model.Enum;

public enum TypeOfService {
    MANTENIMIENTO("Mantenimiento"),
    DESARROLLO("Desarrollo"),
    FINANZAS("Finanzas"),
    MARKETING("Marketing"),
    OPERACIONES("Operaciones");

    private final String displayName;

    TypeOfService(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
