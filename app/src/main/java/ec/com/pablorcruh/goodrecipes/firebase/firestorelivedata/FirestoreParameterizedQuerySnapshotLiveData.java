package ec.com.pablorcruh.goodrecipes.firebase.firestorelivedata;

import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import ec.com.pablorcruh.goodrecipes.constants.Constants;

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
        colRef.orderBy(Constants.SORTING_CRITERIA, Query.Direction.DESCENDING).whereEqualTo(parameter,criteria).get().addOnSuccessListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }

    private class MyValueEventListener implements OnSuccessListener<QuerySnapshot> {

        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                setValue(queryDocumentSnapshots);
        }
    }
}