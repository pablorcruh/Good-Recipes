package ec.com.pablorcruh.goodrecipes.activities;

import androidx.appcompat.app.AppCompatActivity;

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
    private List<Recipe> recipeList;


    private TextView tvRecipeTitle, tvListIngrerdients;
    private ImageView ivRecipePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);
        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra("recipe");
        tvRecipeTitle = findViewById(R.id.text_view_recipe_title);
        ivRecipePicture = findViewById(R.id.image_view_recipe_picture);
        tvListIngrerdients = findViewById(R.id.text_view_list_ingredient);
        tvRecipeTitle.setText(recipe.getName());
        tvListIngrerdients.setText(recipe.getIngredientes().toString());
        Glide.with(EditRecipe.this)
                .load(recipe.getRecipeImageUrl())
                .into(ivRecipePicture);
    }
}
