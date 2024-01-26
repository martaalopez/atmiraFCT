package com.project.atmiraFCT.model.Enum;

public enum TypeOfService {
    IT("Mantenimiento"),
    HR("Desarrollo"),
    FINANCE("Finance"),
    MARKETING("Marketing"),
    OPERATIONS("Operations");

    private final String displayName;

    TypeOfService(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
