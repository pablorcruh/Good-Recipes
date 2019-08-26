package ec.com.pablorcruh.goodrecipes.viewmodel;

import android.content.Context;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;

import ec.com.pablorcruh.goodrecipes.firebase.FirebaseService;
import ec.com.pablorcruh.goodrecipes.fragments.BottomModalRecipeFragment;
import ec.com.pablorcruh.goodrecipes.firebase.FirebaseServiceImpl;

public class MainViewModel extends ViewModel {

    private static final String TAG = MainViewModel.class.getName();

    private FirebaseService firebaseService;

    public MainViewModel() {
        firebaseService = new FirebaseServiceImpl();
    }

    public FirebaseUser getCurrentUser() throws FirebaseAuthException {
        return firebaseService.getCurrentUser();
    }


    public void logout(){
        Log.d(TAG, "logout: ");
        firebaseService.logout();
    }

    public LiveData<QuerySnapshot> getUserInformation(String email){
        return firebaseService.getUserRecipe(email);
    }

    public LiveData<QuerySnapshot> getAllRecipes(){
        return firebaseService.getAllRecipes();
    }


    public void openDialogRecipeMenu(Context context, String recipeId){
        BottomModalRecipeFragment dialogFragment = BottomModalRecipeFragment.newInstance(recipeId);
        dialogFragment.show(((AppCompatActivity)context).getSupportFragmentManager(), "BottomModalRecipeFragment");
    }

    public void deleteRecipe(String recipeId){
        firebaseService.deleteRecipe(recipeId);
    }

}
