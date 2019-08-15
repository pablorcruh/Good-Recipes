package ec.com.pablorcruh.goodrecipes.repository;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;

import ec.com.pablorcruh.goodrecipes.model.User;
import ec.com.pablorcruh.goodrecipes.repository.firestorelivedata.FirestoreAuthLiveData;
import ec.com.pablorcruh.goodrecipes.repository.firestorelivedata.FirestoreDatabaseLiveData;
import ec.com.pablorcruh.goodrecipes.repository.firestorelivedata.FirestoreLoginLiveData;
import ec.com.pablorcruh.goodrecipes.repository.firestorelivedata.FirestoreQueryLiveData;

public class FirebaseRepository {

    private static final String TAG = FirebaseRepository.class.getName();

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private Application application;

    private FirebaseFirestore database;

    private FirebaseAuth firebaseAuth;


    public FirebaseRepository(Application application) {
        this.application = application;
    }

    public FirebaseFirestore getDatabaseInstance(){
        return database= FirebaseFirestore.getInstance();
    }

    public FirebaseAuth getAuthInstance(){
        return firebaseAuth=FirebaseAuth.getInstance();
    }


    public FirestoreAuthLiveData registerNewUser(User user, Activity activity){
        FirebaseAuth firebaseAuth = getAuthInstance();
        FirestoreAuthLiveData authLiveData = new FirestoreAuthLiveData(firebaseAuth, activity, user);
        return authLiveData;
    }


    public FirestoreLoginLiveData loginExistingUser(User user, Activity activity){
        FirebaseAuth firebaseAuth = getAuthInstance();
        FirestoreLoginLiveData loginLiveData = new FirestoreLoginLiveData(firebaseAuth, activity, user);
        return loginLiveData;
    }

    public FirestoreDatabaseLiveData createUserOnFirestore(User user){
        Map<String, Object> userdb = new HashMap<>();
        userdb.put("username", user.getUserName());
        userdb.put("email",user.getEmail());
        FirebaseFirestore firestore = getDatabaseInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        firestore.setFirestoreSettings(settings);
        FirestoreDatabaseLiveData createUserliveData= new FirestoreDatabaseLiveData(firestore.collection("users").document(user.getUserName()),userdb);
        return createUserliveData;
    }

    public void RetrieveInformation(){

    }

    public void logout(){
        Log.d(TAG, "logout: ");
        mAuth.signOut();
    }
}
