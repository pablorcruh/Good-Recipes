package ec.com.pablorcruh.goodrecipes.repository.firestorelivedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class FirestoreQueryLiveData extends LiveData<Task<DocumentSnapshot>> {

    private static final String TAG = FirestoreQueryLiveData.class.getName();

    private final DocumentReference documentReference;
    private final MyValueEventListener listener = new MyValueEventListener();


    public FirestoreQueryLiveData(DocumentReference ref){
        this.documentReference = ref;
    }

    @Override
    protected void onActive() {
        documentReference.get().addOnCompleteListener(listener);
    }

    @Override
    protected void onInactive() {
        documentReference.get().addOnCompleteListener(listener);
    }

    private class MyValueEventListener implements OnCompleteListener<DocumentSnapshot>{

        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            setValue(task);
        }
    }
}
