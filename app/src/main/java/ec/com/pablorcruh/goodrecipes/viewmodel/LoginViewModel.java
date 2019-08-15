package ec.com.pablorcruh.goodrecipes.viewmodel;

import android.app.Activity;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import ec.com.pablorcruh.goodrecipes.model.User;
import ec.com.pablorcruh.goodrecipes.repository.FirebaseRepository;

public class LoginViewModel extends AndroidViewModel {

    private static final String TAG = LoginViewModel.class.getName();

    private FirebaseRepository repository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new FirebaseRepository(application);
    }

    public LiveData<Task<AuthResult>> loginExistingUser(User user, Activity activity){
        return repository.loginExistingUser(user, activity);
    }
}
