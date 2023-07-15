package com.example.employeeCrud.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j(topic = "const class")
public class APPConst {
    @NonNull
    public static String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
    public static boolean isAlphabetical( String input) {
        return input.matches("[a-zA-Z]+");
    }
    public static boolean isNonZeroPositiveNumber(String input) {
        try {
            int number = Integer.parseInt(input);
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean isPositiveNumber(String input) {
        try {
            int number = Integer.parseInt(input);
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public static boolean isValidEmail( String input) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return input.matches(emailRegex);
    }
    public static boolean isStringNullOrEmptyOrBlank(String input) {
        return input == null || input.trim().isEmpty();
    }
    public static boolean isArrayNullOrEmpty(Object[] array) {
        return array == null || array.length == 0;
    }
    public static boolean isArrayOfStringsAlphabetic( String[] array) {
            for (String str : array)
                if (!isAlphabeticWithDot(str)) {
                    return false;
                }
            return true;
    }

    public static boolean isAlphabeticWithDot( String str) {
        return str.matches("[a-zA-Z].+");
    }

    public static boolean isAlphabeticWithSpace( String str) {
        return str.matches("[a-zA-Z ]+");
    }

    public static boolean isValidAge(String ageStr) {
        try {
            int age = Integer.parseInt(ageStr);
            return age >= 0 && age <= 100;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidAlphaNumeric( String str) {
        return str.matches("[a-zA-Z0-9\\-]+");
    }

}
