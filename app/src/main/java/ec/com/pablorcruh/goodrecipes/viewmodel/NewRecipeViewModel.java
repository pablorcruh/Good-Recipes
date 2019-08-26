package ec.com.pablorcruh.goodrecipes.viewmodel;

import android.net.Uri;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.storage.UploadTask;

import ec.com.pablorcruh.goodrecipes.firebase.FirebaseService;
import ec.com.pablorcruh.goodrecipes.model.Recipe;
import ec.com.pablorcruh.goodrecipes.firebase.FirebaseServiceImpl;

public class NewRecipeViewModel extends ViewModel {

    private FirebaseService firebaseService;

    public NewRecipeViewModel() {
        firebaseService = new FirebaseServiceImpl();
    }

    public void saveRecipe(Recipe recipe){
        firebaseService.saveRecipe(recipe);
    }

    public LiveData<UploadTask.TaskSnapshot> saveRecipeImage(Uri imageUri){
     return firebaseService.uploadPhoto(imageUri);
    }

    public void updateRecipe(String imageUrl){
        firebaseService.updateRecipe(imageUrl);
    }
}
