package ec.com.pablorcruh.goodrecipes.firebase;

import android.net.Uri;

import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import ec.com.pablorcruh.goodrecipes.firebase.firestorelivedata.FirestoreLoginLiveData;
import ec.com.pablorcruh.goodrecipes.model.Recipe;
import ec.com.pablorcruh.goodrecipes.model.User;

public interface FirebaseService {

    Task<AuthResult> registerNewUser(User user);

    FirestoreLoginLiveData loginExistingUser(User user);

    LiveData<QuerySnapshot> getUserRecipe(String email);


    LiveData<QuerySnapshot> getAllRecipes();

    void saveRecipe( Recipe recipe);

    void deleteRecipe(String recipeId);

    void logout();

    void uploadPhotoStorage(Uri imageUri);

    void getFollowers(String author, Callback callback);

    void addFollower(List<String> followers, String author);

    void updateFCMToken(String token, String user);

    void getRecipeById(String idRecipe, CallbackGetRecipe callbackGetRecipe);


}
