package ec.com.pablorcruh.goodrecipes.repository.firestorelivedata;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ec.com.pablorcruh.goodrecipes.model.User;

public class FirestoreAuthLiveData extends LiveData<Task<AuthResult>> {

    private static final String TAG = FirestoreAuthLiveData.class.getName();
    private final MyValueEventListener listener = new MyValueEventListener();
    private final FirebaseAuth mAuth;
    private Activity activity;
    private User user;


    public FirestoreAuthLiveData(FirebaseAuth mAuth, Activity activity, User user) {
        this.mAuth = mAuth;
        this.activity = activity;
        this.user = user;
    }

    @Override
    protected void onActive() {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(activity, listener);
    }

    @Override
    protected void onInactive() {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(activity, listener);
    }

    private class MyValueEventListener implements OnCompleteListener<AuthResult>{
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            setValue(task);
        }
    }
}
