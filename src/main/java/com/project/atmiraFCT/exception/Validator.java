package com.project.atmiraFCT.exception;

import java.time.LocalDate;

/**
 * Clase de utilidad que proporciona métodos estáticos para validar datos.
 */
public class Validator {

    /**
     * Valida si una dirección de correo electrónico es un correo de Gmail válido.
     *
     * @param email La dirección de correo electrónico a validar.
     * @return true si la dirección de correo electrónico es válida, false de lo contrario.
     */
    public static boolean isValidGmail(String email) {
        String correoRegex = "^[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)*@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*(\\.[a-zA-Z]{2,})$";
        return email.matches(correoRegex);
    }

    /**
     * Valida si una fecha inicial es válida en comparación con la fecha actual.
     *
     * @param initialDate La fecha inicial a validar.
     * @return true si la fecha inicial es válida, false de lo contrario.
     */
    public static boolean isInitialDateValid(LocalDate initialDate) {
        LocalDate today = LocalDate.now();
        return !initialDate.isBefore(today);
    }

    /**
     * Valida si una fecha de finalización es válida en comparación con una fecha inicial.
     *
     * @param initialDate La fecha inicial.
     * @param endDate La fecha de finalización.
     * @return true si la fecha de finalización es válida, false de lo contrario.
     */
    public static boolean isEndDateValid(LocalDate initialDate, LocalDate endDate) {
        return !endDate.isBefore(initialDate);
    }
}
