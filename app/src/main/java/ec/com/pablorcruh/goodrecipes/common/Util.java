package ec.com.pablorcruh.goodrecipes.common;

public class Util {

    public static boolean isValidEmail(String email){
        if(email.contains("@") && email.contains(".")){
            return true;
        }else
            return false;
    }

    public static boolean isEmptyEmail(String email){
        if(email.isEmpty() || email.equals("")){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isPasswordEmpty(String password){
        if(password.isEmpty() || password.equals("")){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isPasswordLengthValid(String password){
        if(password.length()>7){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isConfirmationPasswordSamePassword(String password, String confirmPassword){
        if(password.equals(confirmPassword)){
            return true;
        }else{
            return false;
        }
    }
}
