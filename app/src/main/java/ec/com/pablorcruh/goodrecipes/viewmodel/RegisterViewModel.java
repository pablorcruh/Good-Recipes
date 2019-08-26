package ec.com.pablorcruh.goodrecipes.viewmodel;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


import ec.com.pablorcruh.goodrecipes.firebase.FirebaseService;
import ec.com.pablorcruh.goodrecipes.model.User;
import ec.com.pablorcruh.goodrecipes.firebase.FirebaseServiceImpl;


public class RegisterViewModel extends ViewModel {

    private static final String TAG = RegisterViewModel.class.getName();

    private FirebaseService firebaseService;

    public RegisterViewModel(){
        firebaseService = new FirebaseServiceImpl();
    }

    public void createUserOnFirestore(User user){
        firebaseService.createUserOnFirestore(user);
    }

    public LiveData<Task<AuthResult>> registerNewUser(User user){
        Log.d(TAG, "registerNewUser: >>> registrar usuario");
        return firebaseService.registerNewUser(user);
    }

}
