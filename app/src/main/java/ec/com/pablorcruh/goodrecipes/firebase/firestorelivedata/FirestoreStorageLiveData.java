package ec.com.pablorcruh.goodrecipes.firebase.firestorelivedata;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import ec.com.pablorcruh.goodrecipes.common.MyApp;

public class FirestoreStorageLiveData extends LiveData<UploadTask.TaskSnapshot> {

    private static final String TAG = FirestoreStorageLiveData.class.getSimpleName();

    private final StorageReference storageReference;
    private Uri imageUri;

    private final MyValueEventListener listener = new MyValueEventListener();


    public FirestoreStorageLiveData(StorageReference storageReference, Uri imageUri){
        this.storageReference = storageReference;
        this.imageUri = imageUri;
    }

    @Override
    protected void onActive() {
        storageReference.putFile(imageUri)
                .addOnSuccessListener(listener)
                .addOnFailureListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        Toast.makeText(MyApp.getContext(), ">>>>>>>>>>>>>>> onInactive", Toast.LENGTH_SHORT).show();

    }

    private class MyValueEventListener implements OnSuccessListener<UploadTask.TaskSnapshot>, OnFailureListener {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            setValue(taskSnapshot);
        }

        @Override
        public void onFailure(@NonNull Exception e) {
            Log.e(TAG, "onFailure: ",e);
            Toast.makeText(MyApp.getContext(), "Error uploading photo", Toast.LENGTH_SHORT).show();
        }
    }
}
