package com.project.atmiraFCT.exception;

import java.time.LocalDate;

public class Validator {

    public static boolean isValidGmail(String email) {
        String gmailRegex = "^[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)*@gmail.com$";
        return email.matches(gmailRegex);
    }


    public static boolean isInitialDateValid(LocalDate initialDate) {
        LocalDate today = LocalDate.now();
        return !initialDate.isBefore(today);
    }


    public static boolean isEndDateValid(LocalDate initialDate, LocalDate endDate) {
        return !endDate.isBefore(initialDate);
    }
}
