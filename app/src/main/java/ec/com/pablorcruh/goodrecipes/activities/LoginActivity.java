package ec.com.pablorcruh.goodrecipes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;

import ec.com.pablorcruh.goodrecipes.R;
import ec.com.pablorcruh.goodrecipes.common.MyApp;
import ec.com.pablorcruh.goodrecipes.common.Util;
import ec.com.pablorcruh.goodrecipes.model.User;
import ec.com.pablorcruh.goodrecipes.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getName();

    private AutoCompleteTextView loginEmail;

    private EditText loginPassword;

    private LoginViewModel loginViewModel;

    private TextView tvRegisterLogin;

    private Button btnLogin;


    private View focusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_login);
            loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

            loginEmail = findViewById(R.id.login_email);
            loginPassword = findViewById(R.id.login_password);

            loginEmail.setError(null);
            loginPassword.setError(null);

            tvRegisterLogin = findViewById(R.id.text_view_login_register);
            tvRegisterLogin.setOnClickListener(new View.OnClickListener() {
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
                    String userEmail = loginEmail.getText().toString().trim();
                    String userPassword = loginPassword.getText().toString().trim();
                    User user = new User(userEmail, userPassword.toString());
                    if (isEmailValid(userEmail)) {
                        if (isPasswordValid(userPassword)) {
                            LiveData<Task<AuthResult>> liveData = loginViewModel.loginExistingUser(user);
                            liveData.observe(LoginActivity.this, new Observer<Task<AuthResult>>() {
                                @Override
                                public void onChanged(Task<AuthResult> authResultTask) {
                                    if (authResultTask.isSuccessful()) {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        finish();
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Please check yor credentials", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }
                    }
                }
            });
        }catch(Exception e){
            Crashlytics.log(e.getMessage());
            Log.e(TAG, "onCreate: ", e);
        }
    }

    private boolean isEmailValid(String email) {
        if (Util.isFieldEmpty(email)) {
            Log.d(TAG, "isEmailValid: must enter email");
            loginEmail.setError("Must enter email");
            focusView = loginEmail;
            focusView.requestFocus();
            return false;
        } else if (Util.isValidEmail(email)) {
            return true;
        } else {
            Log.d(TAG, "isEmailValid: check email");
            loginEmail.setError("Must check your email");
            focusView = loginEmail;
            focusView.requestFocus();
            return false;
        }
    }

    private boolean isPasswordValid(String password) {
        if (Util.isPasswordEmpty(password)) {
            loginPassword.setError("Must enter password");
            focusView = loginPassword;
            focusView.requestFocus();
            Log.d(TAG, "isPasswordValid: must enter password");
            return false;
        } else if (Util.isPasswordLengthValid(password)) {
            return true;
        } else {
            Log.d(TAG, "isPasswordValid: check password length");
            loginPassword.setError("check your password length");
            focusView = loginPassword;
            focusView.requestFocus();
            return false;
        }
    }
}
