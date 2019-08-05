package ec.com.pablorcruh.goodrecipes.repository;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import ec.com.pablorcruh.goodrecipes.model.User;

public class FirebaseRepository {

    private static final String TAG = FirebaseRepository.class.getName();

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private Application application;

    private boolean createAuthenticationSuccess = false;

    private boolean createUserSuccess = false;

    private FirebaseUser firebaseUser;

    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    public FirebaseRepository(Application application) {
        this.application = application;
    }

    public boolean registerAuthenticationUser(User user, final Activity activity) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: se registra usuario");
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            Log.d(TAG, "onComplete: >>>>>>>>" + firebaseUser);
                            createAuthenticationSuccess = true;
                        } else {
                            Log.d(TAG, "onComplete: no se registra usuario");
                            createAuthenticationSuccess = false;
                        }
                    }
                })
                .addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onFailure: >>>>>>>>"+e.getMessage());
                        createAuthenticationSuccess =false;
                    }
                });
        return createAuthenticationSuccess;
    }


    public boolean createNewUser(User user, final Activity activity){
        Map<String,Object> userdb = new HashMap<>();
        userdb.put("username", user.getUserName());
        userdb.put("email", user.getEmail());
        database.collection("users").document(user.getUserName()).set(userdb)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: user saved");
                        Toast.makeText(activity, "User Saved", Toast.LENGTH_LONG).show();
                        createUserSuccess = true;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        createUserSuccess=false;
                        Log.e(TAG, "onFailure: ", e.getCause());
                        Toast.makeText(activity, "Error!", Toast.LENGTH_LONG).show();
                    }
                });

        return createUserSuccess;
    }

    public FirebaseUser loginExistingUser(User user, Activity activity){
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        firebaseUser = mAuth.getCurrentUser();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: >>>>>>>>>>>>>>>"+e.getMessage());
                    }
                });
        return firebaseUser;
    }

    public void RetrieveInformation(){

    }

    public void logout(){
        Log.d(TAG, "logout: ");
        mAuth.signOut();
    }
}
