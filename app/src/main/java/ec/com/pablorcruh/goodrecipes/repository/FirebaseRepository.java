package ec.com.pablorcruh.goodrecipes.repository;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ec.com.pablorcruh.goodrecipes.model.User;

public class FirebaseRepository {

    private static final String TAG = FirebaseRepository.class.getName();

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private Application application;

    private boolean createUserSuccess = false;

    private boolean loginExistingUserSuccess =false;

    private FirebaseUser firebaseUser;

    public FirebaseRepository(Application application) {
        this.application = application;
    }

    public boolean registerNewUser(User user, final Activity activity) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: se registra usuario");
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            Log.d(TAG, "onComplete: >>>>>>>>" + firebaseUser);
                            createUserSuccess = true;
                        } else {
                            Log.d(TAG, "onComplete: no se registra usuario");
                            createUserSuccess = false;
                        }
                    }
                })
                .addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onFailure: >>>>>>>>"+e.getMessage());
                        createUserSuccess=false;
                    }
                });
        return createUserSuccess;
    }

    public boolean loginExistingUser(User user, Activity activity){
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        firebaseUser = mAuth.getCurrentUser();
                        loginExistingUserSuccess = true;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loginExistingUserSuccess=false;
                        Log.d(TAG, "onFailure: >>>>>>>>>>>>>>>"+e.getMessage());
                    }
                });
        return loginExistingUserSuccess;
    }

    public void logout(){
        Log.d(TAG, "logout: ");
        mAuth.signOut();
    }
}
