package ec.com.pablorcruh.goodrecipes.firebase.firestorelivedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ec.com.pablorcruh.goodrecipes.model.User;

public class FirestoreLoginLiveData extends LiveData<Task<AuthResult>> {

    private static final String TAG = FirestoreLoginLiveData.class.getName();
    private final MyValueEventListener listener = new MyValueEventListener();
    private final FirebaseAuth mAuth;
    private User user;


    public FirestoreLoginLiveData(FirebaseAuth mAuth, User user) {
        this.mAuth = mAuth;
        this.user=user;    }

    @Override
    protected void onActive() {
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }

    private class MyValueEventListener implements OnCompleteListener<AuthResult>{
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            setValue(task);
        }
    }
}
