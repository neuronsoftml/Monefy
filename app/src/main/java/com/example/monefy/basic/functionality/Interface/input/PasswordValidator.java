package com.example.monefy.basic.functionality.Interface.input;

public class PasswordValidator {
    public static boolean detectVerificationPassword(String password){
        if(checkingPasswordComplexity(password)){
            return true;
        }
        return false;
    }

    private static boolean checkingPasswordComplexity(String password){
        if(password.length() > 12){
            return true;
        }
        return false;
    }
}
