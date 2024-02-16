package com.example.foodorderapp.util;


import androidx.core.util.PatternsCompat;

public class StringUtils {
    public static Boolean isValidEmail(String email){
if(email.contains("@")){
     return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();}
return false;
    }
    public static String getDoubleNumber(int number) {
        if (number < 10) {
            return "0" + number;
        } else return "" + number;
    }
}
