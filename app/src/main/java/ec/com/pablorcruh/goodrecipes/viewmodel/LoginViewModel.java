package ec.com.pablorcruh.goodrecipes.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import ec.com.pablorcruh.goodrecipes.firebase.FirebaseService;
import ec.com.pablorcruh.goodrecipes.model.User;
import ec.com.pablorcruh.goodrecipes.firebase.FirebaseServiceImpl;

public class LoginViewModel extends ViewModel {

    private static final String TAG = LoginViewModel.class.getName();

    private FirebaseService firebaseService ;

    public LoginViewModel() {
        firebaseService = new FirebaseServiceImpl();
    }

    public LiveData<Task<AuthResult>> loginExistingUser(User user){
        return firebaseService.loginExistingUser(user);
    }

}
