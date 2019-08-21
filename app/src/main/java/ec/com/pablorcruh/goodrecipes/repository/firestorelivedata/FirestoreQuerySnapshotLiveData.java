package ec.com.pablorcruh.goodrecipes.repository.firestorelivedata;

import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class FirestoreQuerySnapshotLiveData extends LiveData<QuerySnapshot> {

    private final CollectionReference colRef;

    public FirestoreQuerySnapshotLiveData(CollectionReference colRef){
        this.colRef = colRef;
    }

    private final MyValueEventListener listener = new MyValueEventListener();

    @Override
    protected void onActive() {
        colRef.addSnapshotListener(listener);
    }


    @Override
    protected void onInactive() {
        colRef.addSnapshotListener(listener);
    }

    private class MyValueEventListener implements EventListener<QuerySnapshot> {

        @Override
        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
            setValue(queryDocumentSnapshots);
        }
    }
}
