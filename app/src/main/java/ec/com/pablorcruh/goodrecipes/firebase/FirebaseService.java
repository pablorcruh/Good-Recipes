package ec.com.pablorcruh.goodrecipes.firebase;

import android.net.Uri;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.UploadTask;

import java.util.List;

import ec.com.pablorcruh.goodrecipes.firebase.firestorelivedata.FirestoreAuthLiveData;
import ec.com.pablorcruh.goodrecipes.firebase.firestorelivedata.FirestoreLoginLiveData;
import ec.com.pablorcruh.goodrecipes.model.Recipe;
import ec.com.pablorcruh.goodrecipes.model.User;

public interface FirebaseService {

    FirestoreAuthLiveData registerNewUser(User user);

    FirestoreLoginLiveData loginExistingUser(User user);

    void createUserOnFirestore(User user);

    FirebaseUser getCurrentUser() throws FirebaseAuthException;

    LiveData<QuerySnapshot> getUserRecipe(String email);


    LiveData<QuerySnapshot> getAllRecipes();

    void saveRecipe( Recipe recipe);

    void deleteRecipe(String recipeId);

    void logout();

    void uploadPhotoStorage(Uri imageUri);

    void getFollowers(String author, Callback callback);

    void addFollower(List<String> followers, String author);

}
