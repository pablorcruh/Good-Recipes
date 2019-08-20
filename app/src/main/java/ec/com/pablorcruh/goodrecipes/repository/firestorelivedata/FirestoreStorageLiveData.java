package ec.com.pablorcruh.goodrecipes.repository.firestorelivedata;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FirestoreStorageLiveData extends LiveData<UploadTask.TaskSnapshot> {

    private static final String TAG = FirestoreStorageLiveData.class.getSimpleName();

    private final StorageReference storageReference;
    private Uri imageUri;
    private Context context;

    private final MyValueEventListener listener = new MyValueEventListener();


    public FirestoreStorageLiveData(StorageReference storageReference, Uri imageUri, Context context){
        this.storageReference = storageReference;
        this.imageUri = imageUri;
        this.context = context;

    }

    @Override
    protected void onActive() {
        storageReference.putFile(imageUri)
                .addOnSuccessListener(listener)
                .addOnFailureListener(listener);
    }

    @Override
    protected void onInactive() {
        storageReference.putFile(imageUri)
                .addOnSuccessListener(listener)
                .addOnFailureListener(listener);    }

    private class MyValueEventListener implements OnSuccessListener<UploadTask.TaskSnapshot>, OnFailureListener {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            setValue(taskSnapshot);
        }

        @Override
        public void onFailure(@NonNull Exception e) {
            Log.e(TAG, "onFailure: ",e);
            Toast.makeText(context, "Error uploading photo", Toast.LENGTH_SHORT).show();
        }
    }
}
