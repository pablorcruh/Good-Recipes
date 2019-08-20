package ec.com.pablorcruh.goodrecipes.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.storage.UploadTask;

import ec.com.pablorcruh.goodrecipes.model.Recipe;
import ec.com.pablorcruh.goodrecipes.repository.FirebaseRepository;

public class NewRecipeViewModel extends AndroidViewModel {

    private FirebaseRepository repository;

    public NewRecipeViewModel(@NonNull Application application) {
        super(application);
        repository = new FirebaseRepository(application);
    }

    public void saveRecipe(Recipe recipe){
        repository.saveRecipe(recipe);
    }

    public LiveData<UploadTask.TaskSnapshot> saveRecipeImage(Uri imageUri, Activity activity){
     return repository.uploadPhoto(imageUri, activity);
    }
}
