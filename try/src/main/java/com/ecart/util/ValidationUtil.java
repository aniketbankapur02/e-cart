package com.ecart.util;

import java.util.regex.Pattern;

public class ValidationUtil {
    private static final String EMAIL_PATTERN = 
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    private static final String PHONE_PATTERN = "^[6-9]\\d{9}$";
    
    private static final String PASSWORD_PATTERN = 
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{10,}$";

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return Pattern.compile(EMAIL_PATTERN).matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        if (phone == null) return false;
        return Pattern.compile(PHONE_PATTERN).matcher(phone).matches();
    }

    public static boolean isValidPassword(String password) {
        if (password == null) return false;
        return Pattern.compile(PASSWORD_PATTERN).matcher(password).matches();
    }

    public static boolean isPositiveNumber(String number) {
        if (number == null) return false;
        try {
            return Integer.parseInt(number) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidCreditCard(String cardNumber) {
        if (cardNumber == null) return false;
        cardNumber = cardNumber.replaceAll("\\s+", "");
        return cardNumber.matches("\\d{16}");
    }

    public static boolean isValidCVV(String cvv) {
        if (cvv == null) return false;
        return cvv.matches("\\d{4}");
    }

    public static boolean isValidExpiryDate(String expiryDate) {
        if (expiryDate == null) return false;
        return expiryDate.matches("^(0[1-9]|1[0-2])/([0-9]{2})$");
    }

    public static boolean isValidCardHolderName(String name) {
        if (name == null) return false;
        return name.matches("[a-zA-Z\\s]{10,50}");
    }

    public static boolean isValidUPI(String upiId) {
        if (upiId == null) return false;
        return upiId.matches("^[\\w.-]+@[\\w.-]+$");
    }

    public static boolean isValidFeedbackRating(int rating) {
        return rating >= 1 && rating <= 5;
    }

    public static boolean isValidDescription(String description) {
        if (description == null) return false;
        return description.length() >= 10 && description.length() <= 500;
    }

    public static boolean isValidName(String name) {
        if (name == null) return false;
        return name.matches("[a-zA-Z\\s]{2,50}");
    }

    public static boolean isValidAddress(String address) {
        if (address == null) return false;
        return address.trim().length() > 0 && address.length() <= 200;
    }

    public static boolean isValidZipCode(String zipCode) {
        if (zipCode == null) return false;
        return zipCode.matches("\\d{6}");
    }
}
