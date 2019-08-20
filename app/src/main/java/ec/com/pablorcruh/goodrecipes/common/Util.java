package ec.com.pablorcruh.goodrecipes.common;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

public class Util {

    public static boolean isValidEmail(String email){
        if(email.contains("@") && email.contains(".")){
            return true;
        }else
            return false;
    }

    public static boolean isFieldEmpty(String email){
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

    public static String getFileExtension(Uri uri, Context context) {
        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
