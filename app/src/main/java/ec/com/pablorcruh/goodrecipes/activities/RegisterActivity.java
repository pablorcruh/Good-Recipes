package ec.com.pablorcruh.goodrecipes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import ec.com.pablorcruh.goodrecipes.R;
import ec.com.pablorcruh.goodrecipes.common.Util;
import ec.com.pablorcruh.goodrecipes.model.User;
import ec.com.pablorcruh.goodrecipes.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getName();

    private AutoCompleteTextView registerEmail;

    private AutoCompleteTextView registerUsername;

    private TextView registerPassword;

    private TextView registerConfirmPassword;

    private Button buttonRegister;

    private RegisterViewModel registerViewModel;

    private User user;

    private View focusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        registerEmail = findViewById(R.id.register_email);

        registerUsername = findViewById(R.id.register_username);

        registerPassword = findViewById(R.id.register_password);

        registerConfirmPassword = findViewById(R.id.register_password_confirm);

        buttonRegister = findViewById(R.id.button_register);

        registerEmail.setError(null);
        registerPassword.setError(null);
        registerConfirmPassword.setError(null);
        registerUsername.setError(null);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = registerEmail.getText().toString().trim();
                String userPassword = registerPassword.getText().toString().trim();
                String userConfirmPassword = registerConfirmPassword.getText().toString().trim();
                String username = registerUsername.getText().toString().trim();
                if (isUsernameValid(username)) {
                    if (isEmailValid(userEmail)) {
                        if (isPasswordValid(userPassword, userConfirmPassword)) {
                            List<String> followers = new ArrayList<String>();
                            followers.add("start");
                            user = new User(username, userEmail, userPassword, followers);
                            LiveData<Task<AuthResult>> liveData = registerViewModel.registerNewUser(user);
                            liveData.observe(RegisterActivity.this, new Observer<Task<AuthResult>>() {
                                @Override
                                public void onChanged(Task<AuthResult> authResultTask) {
                                    Log.d(TAG, "onChanged: >>>>> creado con exito");
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    finish();
                                    startActivity(intent);
                                    registerViewModel.createUserOnFirestore(user);
                                    Toast.makeText(RegisterActivity.this, "User successfully created", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            }
        });

    }

    private boolean isUsernameValid(String username) {
        if (Util.isFieldEmpty(username)) {
            registerUsername.setError(getString(R.string.register_activity_error_empty_username));
            focusView = registerUsername;
            focusView.requestFocus();
            Log.d(TAG, "isUsernameValid: empty username");
            return false;
        } else {
            return true;
        }
    }


    private boolean isEmailValid(String email) {
        if (Util.isFieldEmpty(email)) {
            registerEmail.setError(getString(R.string.register_activity_error_empty_email));
            focusView = registerEmail;
            focusView.requestFocus();
            Log.d(TAG, "isEmailValid: empty email");
            return false;
        }
        if (Util.isValidEmail(email)) {
            Log.d(TAG, "isEmailValid: Email valid");
            return true;
        } else {
            Log.d(TAG, "isEmailValid: Email invalid");
            registerEmail.setError(getString(R.string.register_activity_error_invalid_email));
            focusView = registerEmail;
            focusView.requestFocus();
            return false;
        }
    }

    private boolean isPasswordValid(String password, String passwordConfirmation) {
        if (Util.isPasswordEmpty(password)) {
            Log.d(TAG, "isPasswordValid: Password empty");
            registerPassword.setError(getString(R.string.register_activity_error_passoword_empty));
            focusView = registerPassword;
            focusView.requestFocus();
            return false;
        }
        if (Util.isPasswordLengthValid(password)) {
            Log.d(TAG, "isPasswordValid: Password length valid");
            if (Util.isConfirmationPasswordSamePassword(password, passwordConfirmation)) {
                Log.d(TAG, "isPasswordValid: password and confirmation are the same");
                return true;
            } else {
                registerConfirmPassword.setError(getString(R.string.register_activity_error_passoword_not_equal_confirmPassword));
                focusView = registerConfirmPassword;
                focusView.requestFocus();
                Log.d(TAG, "isPasswordValid: password and confirmation are not the same");
                return false;
            }
        } else {
            registerPassword.setError(getString(R.string.register_activity_error_passoword_invalid_length));
            focusView = registerPassword;
            focusView.requestFocus();
            Log.d(TAG, "isPasswordValid: Password length not valid");
            return false;
        }
    }
}
