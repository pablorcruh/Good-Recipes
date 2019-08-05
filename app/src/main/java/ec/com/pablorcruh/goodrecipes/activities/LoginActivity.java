package ec.com.pablorcruh.goodrecipes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseUser;

import ec.com.pablorcruh.goodrecipes.R;
import ec.com.pablorcruh.goodrecipes.common.Util;
import ec.com.pablorcruh.goodrecipes.model.User;
import ec.com.pablorcruh.goodrecipes.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getName();

    private AutoCompleteTextView loginEmail;

    private EditText loginPassword;

    private LoginViewModel loginViewModel;


    private Button btnLoginRegister;

    private Button btnLogin;

    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);


        // TO DO AGREGAR LOS MENSAJES DE VALIDACIÓN DENTRO DE LOS CAMPOS

        btnLoginRegister = findViewById(R.id.button_login_register);
        btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btnLogin = findViewById(R.id.button_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail= loginEmail.getText().toString().trim();
                String userPassword = loginPassword.getText().toString().trim();
                User user = new User(userEmail, userPassword.toString());
                if(isEmailValid(userEmail)){
                    if(isPasswordValid(userPassword)){
                        firebaseUser = loginViewModel.loginExistingUser(user, LoginActivity.this);
                        if(firebaseUser!=null){
                            Log.d(TAG, "onClick: >>>>>>>>>>>>>>>>"+firebaseUser.getEmail());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            Toast.makeText(LoginActivity.this, "login successful", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    private boolean isEmailValid(String email){
        if(Util.isFieldEmpty(email)){
            return false;
        }else if(Util.isValidEmail(email)){
            return true;
        }else{
            return false;
        }
    }

    private boolean isPasswordValid(String password){
        if(Util.isPasswordEmpty(password)){
            return false;
        }else if(Util.isPasswordLengthValid(password)){
            return true;
        }else{
            return false;
        }

    }
}
