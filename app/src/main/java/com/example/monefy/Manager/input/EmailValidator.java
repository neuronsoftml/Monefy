package com.example.monefy.Manager.input;

public class EmailValidator {

    public static boolean detectVerificationEmail(String email){
        if(detectTypeEmail(separationOfaSpecialSymbol(email))){
            return true;
        }
        return false;
    }

    //Перевіряє чи їснує вказаний тип email зи бази email сервісов.
    private static boolean detectTypeEmail(String email){
        if(email == null || email.equals("")){
            return false;
        }
        HowlEmail[] allEmails = HowlEmail.getAllEmails();

        for(HowlEmail howlEmail: allEmails){
            if(email.equals(howlEmail.getTitleEmail())){
                return true;
            }
        }
        return false;
    }

    //Відокремлення спеціального синвола @.
    private static String separationOfaSpecialSymbol(String email){
        char[] charsArrayEmail = email.toCharArray();

        for(int i = 0; i < charsArrayEmail.length; i++){
            if(charsArrayEmail[i] == '@'){
                return email.substring(i);
            }
        }
        return null;
    }

}

