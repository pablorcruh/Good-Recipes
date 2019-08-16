package ec.com.pablorcruh.goodrecipes.repository.firestorelivedata;

import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;

public class FirestoreParameterizedQuerySnapshotLiveData extends LiveData<QuerySnapshot> {

    private static final String TAG = FirestoreParameterizedQuerySnapshotLiveData.class.getName();

    private final MyValueEventListener listener = new MyValueEventListener();

    private final CollectionReference colRef;
    private final String parameter, criteria;

    public FirestoreParameterizedQuerySnapshotLiveData(CollectionReference colRef, String parameter, String criteria){
        this.colRef = colRef;
        this.parameter = parameter;
        this.criteria = criteria;
    }

    @Override
    protected void onActive() {
        colRef.whereEqualTo(parameter,criteria).get().addOnSuccessListener(listener);
    }

    @Override
    protected void onInactive() {
        colRef.whereEqualTo(parameter,criteria).get().addOnSuccessListener(listener);
    }

    private class MyValueEventListener implements OnSuccessListener<QuerySnapshot> {

        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                setValue(queryDocumentSnapshots);
        }
    }
}