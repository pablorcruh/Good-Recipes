package ec.com.pablorcruh.goodrecipes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ec.com.pablorcruh.goodrecipes.R;
import ec.com.pablorcruh.goodrecipes.adapter.RecipeAdapter;
import ec.com.pablorcruh.goodrecipes.adapter.ViewIngredientAdapter;
import ec.com.pablorcruh.goodrecipes.model.Recipe;

public class EditRecipe extends AppCompatActivity {

    public static final String TAG = EditRecipe.class.getSimpleName();
    private ViewIngredientAdapter ingredientAdapter;
    private List<String> ingredientList;


    private TextView tvRecipeTitle;
    private ImageView ivRecipePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);
        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra("recipe");
        ingredientList = recipe.getIngredientes();
        tvRecipeTitle = findViewById(R.id.text_view_recipe_title);
        ivRecipePicture = findViewById(R.id.image_view_recipe_picture);
        tvRecipeTitle.setText(recipe.getName());
        Glide.with(EditRecipe.this)
                .load(recipe.getRecipeImageUrl())
                .into(ivRecipePicture);


        RecyclerView recyclerViewIngredients = findViewById(R.id.view_recycler_view_recipes);
        ingredientAdapter = new ViewIngredientAdapter(ingredientList);
        recyclerViewIngredients.setAdapter(ingredientAdapter);
        recyclerViewIngredients.setLayoutManager(new LinearLayoutManager(this));


    }
}
