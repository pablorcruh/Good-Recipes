package ec.com.pablorcruh.goodrecipes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

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
}
