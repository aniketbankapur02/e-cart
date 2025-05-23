package com.ecart.util;

import java.util.regex.Pattern;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ValidationUtil {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]{1,50}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[1-9][0-9]{9,14}$");
    private static final Pattern PASSWORD_PATTERN = 
        Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{10,}$");
    private static final Pattern ZIPCODE_PATTERN = Pattern.compile("^[0-9]{6}$");

    public static boolean validateName(String name) {
        return name != null && NAME_PATTERN.matcher(name).matches();
    }

    public static boolean validateEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean validatePhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean validatePassword(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean validateZipcode(String zipcode) {
        return zipcode != null && ZIPCODE_PATTERN.matcher(zipcode).matches();
    }

    public static boolean validateConfirmPassword(String password, String confirmPassword) {
        return password != null && password.equals(confirmPassword);
    }

    public static String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String validateRegistrationForm(String name, String email, String phone, 
            String password, String confirmPassword, String zipcode) {
        
        StringBuilder errors = new StringBuilder();

        if (!validateName(name)) {
            errors.append("Name should contain only letters and spaces (max 50 characters)\n");
        }

        if (!validateEmail(email)) {
            errors.append("Please enter a valid email address\n");
        }

        if (!validatePhone(phone)) {
            errors.append("Phone number should not start with 0 and must be 10-15 digits\n");
        }

        if (!validatePassword(password)) {
            errors.append("Password must be at least 10 characters long, containing at least one number, " +
                         "one uppercase letter, one lowercase letter and one special character\n");
        }

        if (!validateConfirmPassword(password, confirmPassword)) {
            errors.append("Passwords do not match\n");
        }

        if (!validateZipcode(zipcode)) {
            errors.append("Please enter a valid 6-digit zipcode\n");
        }

        return errors.length() > 0 ? errors.toString() : null;
    }
}
