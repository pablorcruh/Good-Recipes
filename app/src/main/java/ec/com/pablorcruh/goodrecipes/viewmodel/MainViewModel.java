package ec.com.pablorcruh.goodrecipes.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;

import ec.com.pablorcruh.goodrecipes.repository.FirebaseRepository;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getName();

    private FirebaseRepository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new FirebaseRepository();
    }

    public FirebaseUser getCurrentUser() throws FirebaseAuthException {
        return repository.getCurrentUser();
    }


    public void logout(){
        Log.d(TAG, "logout: ");
        repository.logout();
    }

    public LiveData<QuerySnapshot> getUserInformation(String email){
        return repository.getUserRecipe(email);
    }

    public LiveData<QuerySnapshot> getAllRecipes(){
        return repository.getAllRecipes();
    }
}
