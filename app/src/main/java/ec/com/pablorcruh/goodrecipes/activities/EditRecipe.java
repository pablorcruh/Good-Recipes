package ec.com.pablorcruh.goodrecipes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ec.com.pablorcruh.goodrecipes.R;
import ec.com.pablorcruh.goodrecipes.adapter.ViewIngredientAdapter;
import ec.com.pablorcruh.goodrecipes.adapter.ViewStepsAdapter;
import ec.com.pablorcruh.goodrecipes.model.Recipe;

public class EditRecipe extends AppCompatActivity {

    public static final String TAG = EditRecipe.class.getSimpleName();
    private ViewIngredientAdapter ingredientAdapter;
    private ViewStepsAdapter stepAdapter;
    private List<String> ingredientList;
    private List<String> stepList;

    private TextView tvRecipeTitle;
    private TextView tvRecipeDescription;
    private ImageView ivRecipePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        this.getSupportActionBar().setTitle("Recipe Detail");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra("recipe");
        ingredientList = recipe.getIngredientes();
        stepList = recipe.getSteps();
        tvRecipeTitle = findViewById(R.id.text_view_recipe_title);
        tvRecipeDescription = findViewById(R.id.text_view_view_recipe_description);
        ivRecipePicture = findViewById(R.id.image_view_recipe_picture);
        tvRecipeTitle.setText(recipe.getName());
        tvRecipeDescription.setText(recipe.getDescription());
        Glide.with(EditRecipe.this)
                .load(recipe.getRecipeImageUrl())
                .error(Glide.with(ivRecipePicture).load(R.drawable.empty_recipe))
                .into(ivRecipePicture);



        RecyclerView recyclerViewIngredients = findViewById(R.id.view_recycler_view_ingredients);
        ingredientAdapter = new ViewIngredientAdapter(ingredientList);
        recyclerViewIngredients.setAdapter(ingredientAdapter);
        recyclerViewIngredients.setLayoutManager(new LinearLayoutManager(this));


        RecyclerView recyclerViewSteps = findViewById(R.id.view_recycler_view_steps);
        stepAdapter = new ViewStepsAdapter(stepList);
        recyclerViewSteps.setAdapter(stepAdapter);
        recyclerViewSteps.setLayoutManager(new LinearLayoutManager(this));

    }
}
