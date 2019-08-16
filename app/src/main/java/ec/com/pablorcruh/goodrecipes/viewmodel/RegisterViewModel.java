package ec.com.pablorcruh.goodrecipes.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentSnapshot;

import ec.com.pablorcruh.goodrecipes.model.User;
import ec.com.pablorcruh.goodrecipes.repository.FirebaseRepository;


public class RegisterViewModel extends AndroidViewModel {

    private static final String TAG = RegisterViewModel.class.getName();

    private FirebaseRepository repository;

    public RegisterViewModel(Application application){
        super(application);
        repository = new FirebaseRepository(application);
    }

    public void createUserOnFirestore(User user, Activity activity){
        repository.createUserOnFirestore(user, activity);
    }

    public LiveData<Task<AuthResult>> registerNewUser(User user, Activity activity){
        Log.d(TAG, "registerNewUser: >>> registrar usuario");
        return repository.registerNewUser(user, activity);
    }

}
