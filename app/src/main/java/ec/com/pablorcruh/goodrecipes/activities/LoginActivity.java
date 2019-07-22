package ec.com.pablorcruh.goodrecipes.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import ec.com.pablorcruh.goodrecipes.R;
import ec.com.pablorcruh.goodrecipes.constants.Constants;
import io.grpc.okhttp.internal.framed.ErrorCode;

public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        login();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                login();
                //finish();
            }else{
                IdpResponse response = IdpResponse.fromResultIntent(data);
                if(response == null){
                    Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show();
                    return;
                }else if(response.getError().getErrorCode() == ErrorCodes.NO_NETWORK){
                    Toast.makeText(this,"Sin Conexión a internet", Toast.LENGTH_LONG).show();
                    return;
                }else if(response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR){
                    Toast.makeText(this,"Error desconocido", Toast.LENGTH_LONG).show();
                    return;
                }

            }
        }
    }

    private void login(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Toast.makeText(LoginActivity.this,"iniciar sesion: "+
                    user.getDisplayName() + " - "+
                    user.getEmail()+" - ",Toast.LENGTH_LONG).show();
            Intent initialActivity = new Intent(LoginActivity.this, MainActivity.class);
            initialActivity.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NEW_TASK|
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                    );
            startActivity(initialActivity);
        }else{
            startActivityForResult(AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(Arrays.asList(
                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                    new AuthUI.IdpConfig.EmailBuilder().build()))
            .setIsSmartLockEnabled(false).build(), Constants.RC_SIGN_IN
            );
        }
    }
}
