package ec.com.pablorcruh.goodrecipes.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import ec.com.pablorcruh.goodrecipes.repository.FirebaseRepository;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getName();

    private FirebaseRepository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new FirebaseRepository(application);
    }

    public void logout(){
        Log.d(TAG, "logout: ");
        repository.logout();
    }
}
