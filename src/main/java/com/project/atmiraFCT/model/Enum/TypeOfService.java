package com.project.atmiraFCT.model.Enum;

public enum TypeOfService {
    IT("Information Technology"),
    HR("Human Resources"),
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
