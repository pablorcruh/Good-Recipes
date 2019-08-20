package ec.com.pablorcruh.goodrecipes.repository;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.stats.GCoreWakefulBroadcastReceiver;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.io.Files;
import com.google.firebase.auth.AdditionalUserInfo;
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

import ec.com.pablorcruh.goodrecipes.common.Util;
import ec.com.pablorcruh.goodrecipes.constants.Constants;
import ec.com.pablorcruh.goodrecipes.model.Recipe;
import ec.com.pablorcruh.goodrecipes.model.User;
import ec.com.pablorcruh.goodrecipes.repository.firestorelivedata.FirestoreAuthLiveData;
import ec.com.pablorcruh.goodrecipes.repository.firestorelivedata.FirestoreLoginLiveData;
import ec.com.pablorcruh.goodrecipes.repository.firestorelivedata.FirestoreParameterizedQuerySnapshotLiveData;
import ec.com.pablorcruh.goodrecipes.repository.firestorelivedata.FirestoreQuerySnapshotLiveData;
import ec.com.pablorcruh.goodrecipes.repository.firestorelivedata.FirestoreStorageLiveData;

import static com.google.common.io.Files.getFileExtension;

public class FirebaseRepository {

    private static final String TAG = FirebaseRepository.class.getName();

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private Application application;

    private FirebaseFirestore database;

    private FirebaseAuth firebaseAuth;

    private StorageReference storeRef;

    public FirebaseRepository(Application application) {
        this.application = application;
    }

    public FirebaseFirestore getDatabaseInstance() {
        return database = FirebaseFirestore.getInstance();
    }

    public FirebaseAuth getAuthInstance() {
        return firebaseAuth = FirebaseAuth.getInstance();
    }


    public FirestoreAuthLiveData registerNewUser(User user, Activity activity) {
        FirebaseAuth firebaseAuth = getAuthInstance();
        FirestoreAuthLiveData authLiveData = new FirestoreAuthLiveData(firebaseAuth, activity, user);
        return authLiveData;
    }


    public FirestoreLoginLiveData loginExistingUser(User user, Activity activity) {
        FirebaseAuth firebaseAuth = getAuthInstance();
        FirestoreLoginLiveData loginLiveData = new FirestoreLoginLiveData(firebaseAuth, activity, user);
        return loginLiveData;
    }

    public void createUserOnFirestore(User user, final Activity activity) {
        Map<String, Object> userdb = new HashMap<>();
        userdb.put("username", user.getUserName());
        userdb.put("email", user.getEmail());
        FirebaseFirestore db = getDatabaseInstance();
        db.collection(Constants.USER_COLLECTION).document(user.getEmail()).set(userdb)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: user saved");
                        Toast.makeText(activity, "User Saved", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e.getCause());
                        Toast.makeText(activity, "Error!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public FirebaseUser getCurrentUser() throws FirebaseAuthException {
        FirebaseUser currentUser = getAuthInstance().getCurrentUser();
        if (currentUser == null) {
            throw new FirebaseAuthException("User not found", "User not found");
        } else {
            return currentUser;
        }
    }

    public LiveData<QuerySnapshot> getUserRecipe(String email) {
        FirebaseFirestore db = getDatabaseInstance();
        CollectionReference colRef = db.collection(Constants.RECIPE_COLLECTION);
        FirestoreParameterizedQuerySnapshotLiveData livedata = new FirestoreParameterizedQuerySnapshotLiveData(colRef, Constants.RECIPE_AUTHOR_COLUMN, email);
        return livedata;
    }


    public LiveData<QuerySnapshot> getAllRecipes(Activity activity) {
        FirebaseFirestore db = getDatabaseInstance();
        CollectionReference colRef = db.collection(Constants.RECIPE_COLLECTION);
        FirestoreQuerySnapshotLiveData liveData = new FirestoreQuerySnapshotLiveData(colRef, activity);
        return liveData;
    }

    public void saveRecipe( Recipe recipe) {
        DocumentReference docRef = getDatabaseInstance().document("recipe/" + System.currentTimeMillis());
        docRef.set(recipe)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(application, "Guardado con exito", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(application, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void logout() {
        Log.d(TAG, "logout: ");
        mAuth.signOut();
    }

    public LiveData<UploadTask.TaskSnapshot> uploadPhoto(Uri imageUri, Activity activity){
        storeRef = FirebaseStorage.getInstance().getReference("all");
        StorageReference fileReference   = storeRef.child(System.currentTimeMillis() + "."+ Util.getFileExtension(imageUri, activity));
        FirestoreStorageLiveData liveData = new FirestoreStorageLiveData(fileReference, imageUri,  activity);
        return liveData;
    }
}
