package ec.com.pablorcruh.goodrecipes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import ec.com.pablorcruh.goodrecipes.R;
import ec.com.pablorcruh.goodrecipes.model.Recipe;

public class EditRecipe extends AppCompatActivity {

    public static final String TAG = EditRecipe.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);
        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra("recipe");
        Log.d(TAG, "onCreate: "+recipe.getDescription());
    }
}
