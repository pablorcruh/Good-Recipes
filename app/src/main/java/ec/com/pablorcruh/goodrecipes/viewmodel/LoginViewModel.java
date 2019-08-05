package ec.com.pablorcruh.goodrecipes.viewmodel;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.firebase.auth.FirebaseUser;

import ec.com.pablorcruh.goodrecipes.model.User;
import ec.com.pablorcruh.goodrecipes.repository.FirebaseRepository;

public class LoginViewModel extends AndroidViewModel {

    private static final String TAG = LoginViewModel.class.getName();

    private FirebaseRepository repository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new FirebaseRepository(application);
    }

    public FirebaseUser loginExistingUser(User user, Activity activity){
        return repository.loginExistingUser(user, activity);

    }

}
