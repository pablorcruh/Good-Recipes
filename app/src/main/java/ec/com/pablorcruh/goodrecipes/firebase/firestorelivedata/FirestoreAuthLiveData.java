package ec.com.pablorcruh.goodrecipes.firebase.firestorelivedata;


import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import ec.com.pablorcruh.goodrecipes.common.MyApp;
import ec.com.pablorcruh.goodrecipes.model.User;

public class FirestoreAuthLiveData extends LiveData<Task<AuthResult>> {

    private static final String TAG = FirestoreAuthLiveData.class.getName();
    private final MyValueEventListener listener = new MyValueEventListener();
    private final MyErrorEventListenr errorListener = new MyErrorEventListenr();
    private final FirebaseAuth mAuth;
    private User user;


    public FirestoreAuthLiveData(FirebaseAuth mAuth, User user) {
        this.mAuth = mAuth;
        this.user = user;
    }

    @Override
    protected void onActive() {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(listener)
                .addOnFailureListener(errorListener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }

    private class MyValueEventListener implements OnCompleteListener<AuthResult>{
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                setValue(task);
            }else{
                String errorCode = ((FirebaseAuthException)task.getException()).getErrorCode();
                Log.d(TAG, "onComplete: >>>>>>>>>>>>>>>>>>>>>>>>>"+errorCode);
            }
        }
    }

    private class MyErrorEventListenr implements OnFailureListener{
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(MyApp.getContext(), "User already on use", Toast.LENGTH_SHORT).show();
        }
    }
}
