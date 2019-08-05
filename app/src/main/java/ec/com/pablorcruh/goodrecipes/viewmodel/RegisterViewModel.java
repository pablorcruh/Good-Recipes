package ec.com.pablorcruh.goodrecipes.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import ec.com.pablorcruh.goodrecipes.model.User;
import ec.com.pablorcruh.goodrecipes.repository.FirebaseRepository;


public class RegisterViewModel extends AndroidViewModel {

    private static final String TAG = RegisterViewModel.class.getName();

    private FirebaseRepository repository;


    public RegisterViewModel(Application application){
        super(application);
        repository = new FirebaseRepository(application);
    }

    public boolean registerAuthenticationUser(User user, Activity activity){
        Log.d(TAG, "registerAuthenticationUser: ");
        return repository.registerAuthenticationUser(user, activity);
    }

    public boolean registerNewUser(User user, Activity activity){
        Log.d(TAG, "registerNewUser: ");
        return repository.createNewUser(user, activity);
    }


}
