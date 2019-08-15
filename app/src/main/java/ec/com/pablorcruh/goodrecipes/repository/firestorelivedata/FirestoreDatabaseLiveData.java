package ec.com.pablorcruh.goodrecipes.repository.firestorelivedata;

import android.util.Log;
import androidx.lifecycle.LiveData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import java.util.Map;

public class FirestoreDatabaseLiveData extends LiveData<Task<Void>> {

    private static final String TAG = FirestoreQueryLiveData.class.getName();

    private final DocumentReference documentReference;
    private final FirestoreDatabaseLiveData.MyValueEventListener listener = new FirestoreDatabaseLiveData.MyValueEventListener();

    private Map<String, Object> user;

    public FirestoreDatabaseLiveData(DocumentReference ref, Map<String, Object> user){
        this.documentReference = ref;
        this.user = user;
    }

    @Override
    protected void onActive() {
        documentReference.set(user).addOnSuccessListener(listener);
    }

    @Override
    protected void onInactive() {
        documentReference.set(user).addOnSuccessListener(listener);
    }

    private class MyValueEventListener implements OnSuccessListener<Void> {

        @Override
        public void onSuccess(Void aVoid) {
            Log.d(TAG, "onSuccess: usuario creato en base de datos");
        }
    }
}
