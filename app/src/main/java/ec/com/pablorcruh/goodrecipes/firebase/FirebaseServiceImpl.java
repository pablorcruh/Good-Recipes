package ec.com.pablorcruh.goodrecipes.firebase;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ec.com.pablorcruh.goodrecipes.common.MyApp;
import ec.com.pablorcruh.goodrecipes.common.SharedPreferencesManager;
import ec.com.pablorcruh.goodrecipes.common.Util;
import ec.com.pablorcruh.goodrecipes.constants.Constants;
import ec.com.pablorcruh.goodrecipes.model.Recipe;
import ec.com.pablorcruh.goodrecipes.model.User;
import ec.com.pablorcruh.goodrecipes.firebase.firestorelivedata.FirestoreAuthLiveData;
import ec.com.pablorcruh.goodrecipes.firebase.firestorelivedata.FirestoreLoginLiveData;
import ec.com.pablorcruh.goodrecipes.firebase.firestorelivedata.FirestoreParameterizedQuerySnapshotLiveData;
import ec.com.pablorcruh.goodrecipes.firebase.firestorelivedata.FirestoreQuerySnapshotLiveData;

public class FirebaseServiceImpl implements FirebaseService {

    private static final String TAG = FirebaseServiceImpl.class.getName();


    private FirebaseFirestore database;

    private FirebaseAuth firebaseAuth;

    private StorageReference storeRef;

    public FirebaseServiceImpl() {
        firebaseAuth = FirebaseAuth.getInstance();
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
        userdb.put("followers", user.getFollowers());
        userdb.put("token", user.getToken());
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
        DocumentReference docRef = database.document(Constants.RECIPE_COLLECTION + "/" + recipeId);
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

    public void deleteRecipe(String recipeId){
        database.collection(Constants.RECIPE_COLLECTION).document(recipeId).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Document Deleted");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void logout() {
        Log.d(TAG, "logout: ");
        firebaseAuth.signOut();
    }

    public void uploadPhotoStorage(final Uri imageUri){
        storeRef = FirebaseStorage.getInstance().getReference("all");
        StorageReference fileReference   = storeRef.child(System.currentTimeMillis() + "."+ Util.getFileExtension(imageUri));
        fileReference.putFile(imageUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            task.getResult().getMetadata().getReference().getDownloadUrl()
                                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                                 if(task.isSuccessful()){
                                                     updatePhotoRecipe(task.getResult());
                                                 }
                                        }
                                    });
                        }
                    }
                });

    }

    private void updatePhotoRecipe(Uri imageUri){
        Long idRecipe = SharedPreferencesManager.getSomeLongValue(Constants.PREF_ID_RECIPE);
        DocumentReference recipe = database.collection(Constants.RECIPE_COLLECTION + "/").document(""+idRecipe);
        recipe.update(Constants.URL_IMAGE,imageUri.toString())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MyApp.getContext(), "Image Updated", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: >>>>>>>>>>>>>>>>>>>>>>>>>"+e);
                    }
                });
    }

    public void removeRecipeImage(Recipe recipe) {
        FirebaseStorage mStorage;
        mStorage = FirebaseStorage.getInstance();
        StorageReference imageRef =mStorage.getReferenceFromUrl(recipe.getRecipeImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MyApp.getContext(), "Recipe removed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void getFollowers(String author, final Callback callback) {
        DocumentReference documentReference = database.collection(Constants.USER_COLLECTION).document(author);
                documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            User user = documentSnapshot.toObject(User.class);
                            List<String> followers = user.getFollowers();
                            callback.passStringList(followers);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: +++++++++++"+e);
                    }
                });
    }

    @Override
    public void addFollower(List<String> followers, String author) {
        DocumentReference recipe = database.collection(Constants.USER_COLLECTION + "/" ).document(""+author);
        recipe.update(Constants.USERS_FOLLOWERS_COLUMN,followers)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MyApp.getContext(), "Add follower", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e);
                    }
                });
    }

    @Override
    public void updateFCMToken(String token, String updatedUser) {
        DocumentReference user = database.collection(Constants.USER_COLLECTION + "/").document(""+updatedUser);
        user.update(Constants.USER_TOKEN_COLUMN, token)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MyApp.getContext(), "Token Stored", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e);
                    }
                });
    }

    @Override
    public void getRecipeById(final String idRecipe, final CallbackGetRecipe callbackGetRecipe) {
        DocumentReference documentReference = database.collection(Constants.RECIPE_COLLECTION + "/").document(idRecipe);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    Recipe recipe = documentSnapshot.toObject(Recipe.class);
                    callbackGetRecipe.getRecipeById(recipe);
                }
            }
        });

    }


}
