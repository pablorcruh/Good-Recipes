package ec.com.pablorcruh.goodrecipes.firebase.firestorelivedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class FirebaseDocumentSnapshotLiveData extends LiveData<Task<DocumentSnapshot>> {

    private final FirebaseAuth mAuth;
    private DocumentReference docReference;

    private final MyValueEventListener listener = new MyValueEventListener();


    public FirebaseDocumentSnapshotLiveData(FirebaseAuth mAuth, DocumentReference docReference){
        this.mAuth = mAuth;
        this.docReference = docReference;
    }

    @Override
    protected void onActive() {
        docReference.get().addOnCompleteListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }

    private class  MyValueEventListener implements OnCompleteListener<DocumentSnapshot>{

        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            setValue(task);
        }
    }
}
