package ec.com.pablorcruh.goodrecipes.repository;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import ec.com.pablorcruh.goodrecipes.common.MyApp;
import ec.com.pablorcruh.goodrecipes.common.SharedPreferencesManager;
import ec.com.pablorcruh.goodrecipes.common.Util;
import ec.com.pablorcruh.goodrecipes.constants.Constants;
import ec.com.pablorcruh.goodrecipes.model.Recipe;
import ec.com.pablorcruh.goodrecipes.model.User;
import ec.com.pablorcruh.goodrecipes.repository.firestorelivedata.FirestoreAuthLiveData;
import ec.com.pablorcruh.goodrecipes.repository.firestorelivedata.FirestoreLoginLiveData;
import ec.com.pablorcruh.goodrecipes.repository.firestorelivedata.FirestoreParameterizedQuerySnapshotLiveData;
import ec.com.pablorcruh.goodrecipes.repository.firestorelivedata.FirestoreQuerySnapshotLiveData;
import ec.com.pablorcruh.goodrecipes.repository.firestorelivedata.FirestoreStorageLiveData;


public class FirebaseRepository {

    private static final String TAG = FirebaseRepository.class.getName();

    private static FirebaseAuth  mAuth;

    private FirebaseFirestore database;

    private FirebaseAuth firebaseAuth;

    private StorageReference storeRef;

    public FirebaseRepository() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public FirestoreAuthLiveData registerNewUser(User user) {
        FirestoreAuthLiveData authLiveData = new FirestoreAuthLiveData(firebaseAuth, user);
        return authLiveData;
    }


    public FirestoreLoginLiveData loginExistingUser(User user) {
        FirestoreLoginLiveData loginLiveData = new FirestoreLoginLiveData(firebaseAuth, user);
        return loginLiveData;
    }

    public void createUserOnFirestore(User user) {
        Map<String, Object> userdb = new HashMap<>();
        userdb.put("username", user.getUserName());
        userdb.put("email", user.getEmail());
        FirebaseFirestore db = database;
        db.collection(Constants.USER_COLLECTION).document(user.getEmail()).set(userdb)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: user saved");
                        Toast.makeText(MyApp.getContext(), "User Saved", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e.getCause());
                        Toast.makeText(MyApp.getContext(), "Error!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public FirebaseUser getCurrentUser() throws FirebaseAuthException {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            throw new FirebaseAuthException("User not found", "User not found");
        } else {
            return currentUser;
        }
    }

    public LiveData<QuerySnapshot> getUserRecipe(String email) {
        FirebaseFirestore db = database;
        CollectionReference colRef = db.collection(Constants.RECIPE_COLLECTION);
        FirestoreParameterizedQuerySnapshotLiveData livedata = new FirestoreParameterizedQuerySnapshotLiveData(colRef, Constants.RECIPE_AUTHOR_COLUMN, email);
        return livedata;
    }


    public LiveData<QuerySnapshot> getAllRecipes() {
        FirebaseFirestore db = database;
        CollectionReference colRef = db.collection(Constants.RECIPE_COLLECTION);
        FirestoreQuerySnapshotLiveData liveData = new FirestoreQuerySnapshotLiveData(colRef);
        return liveData;
    }

    public void saveRecipe( Recipe recipe) {
        Long recipeId = System.currentTimeMillis();
        SharedPreferencesManager.setSomeLongValue(Constants.PREF_ID_RECIPE, recipeId);
        DocumentReference docRef = database.document(Constants.RECIPE_COLLECTION +"/" + recipeId);
        docRef.set(recipe)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MyApp.getContext(), "Guardado con exito", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MyApp.getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void logout() {
        Log.d(TAG, "logout: ");
        mAuth.signOut();
    }

    public LiveData<UploadTask.TaskSnapshot> uploadPhoto(Uri imageUri){
        storeRef = FirebaseStorage.getInstance().getReference("all");
        StorageReference fileReference   = storeRef.child(System.currentTimeMillis() + "."+ Util.getFileExtension(imageUri));
        FirestoreStorageLiveData liveData = new FirestoreStorageLiveData(fileReference, imageUri);
        return liveData;
    }

    public void updateRecipe(String imageUri){
        Long idRecipe = SharedPreferencesManager.getSomeLongValue(Constants.PREF_ID_RECIPE);
        DocumentReference recipe = database.collection(Constants.RECIPE_COLLECTION +"/" ).document(""+idRecipe);
        recipe.update(Constants.URL_IMAGE,imageUri)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MyApp.getContext(), "Image Updated", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
